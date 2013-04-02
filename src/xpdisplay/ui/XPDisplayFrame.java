/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import xpdisplay.io.DataListener;
import xpdisplay.io.DataProvider;
import xpdisplay.io.DataRecorder;
import xpdisplay.io.PacketParser;
import xpdisplay.io.impl.PacketParser10xx;
import xpdisplay.io.impl.PacketParser9xx;
import xpdisplay.io.provider.UDPReaderDataProvider;
import xpdisplay.model.Metadata;
import xpdisplay.model.SimState;
import xpdisplay.model.StateChangeListener;
import xpdisplay.model.data.DataObject;
import xpdisplay.util.GuiUtils;
import xpdisplay.util.Settings;

public class XPDisplayFrame extends javax.swing.JFrame implements DataListener, StateChangeListener {
    private PacketParser packetParser;
    private SimState simState = new SimState();
    private DataProvider dataProvider;
    private MainPanel mainPanel;
    private JTable rawDataTable;
    private MapPanel map;
    private RadarPanel radarPanel;
    private ConfigPanel configPanel;
    private DashboardPanel dash;
    private Vector<DataRecorder> dataRecorders = new Vector();
    public boolean dataRecorderStatus = false;

    private int packetsReceived;
    private int unknownPackets;
    private Set<Integer> packetTypes = new TreeSet<Integer>();
    private Set<Integer> unknownPacketTypes = new TreeSet<Integer>();
    
    public XPDisplayFrame(DataProvider dataProvider) {
        this.dataProvider = dataProvider;

        Settings.init();

        String version = Settings.get(Settings.XPLANE_VERSION, "9.xx");
        setPacketParser(version);


        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("XPDisplay");

        setSize(800,600);

        RawDataPanel rawDataPanel = new RawDataPanel();

        configPanel = new ConfigPanel(this);

        mainPanel = new MainPanel(this);
        mainPanel.setRawDataPanel(rawDataPanel);
        rawDataTable = rawDataPanel.getRawDataTable();
        mainPanel.setConfigPanel(configPanel);
        
        dash = new DashboardPanel();
        map = new MapPanel();
        radarPanel = new RadarPanel();
        
        mainPanel.setDashboardPanel(dash);
        mainPanel.setMapPanel(map);
        mainPanel.setRadarPanel(radarPanel);


        mainPanel.updateStatusLabel(dataProvider.isRunning());
        mainPanel.updateRecorderStatusLabel(dataRecorderStatus);

        getContentPane().add(mainPanel);

//        initStatus();
        
        initIpAddressLabel();
        
        registerSimListeners();
        
        initTables();
        
        GuiUtils.centre(this);        
//        GuiUtils.maximize(this);
        
        setVisible(true);
        
        dataProvider.addListener(this);
    }
    
    private void initIpAddressLabel() {
        if( dataProvider instanceof UDPReaderDataProvider ) {
            UDPReaderDataProvider p = (UDPReaderDataProvider) dataProvider;
            String s = p.getAddress()+":"+p.getPort();
            mainPanel.setIPAndPort(s);
        } else {
            mainPanel.setIPAndPort("Unable to determine IP Address");
        }
    }
/*
    private void initStatus() {
        statusLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if( dataProvider.isRunning() ) {
                    dataProvider.stop();
                    updateStatusLabel();
                } else {
                    dataProvider.start();
                    updateStatusLabel();
                }
            }
        });
        updateStatusLabel();

        
        recorderStatusLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if( dataRecorderStatus ) {
                    setDataRecorderEnabled(false);
                    updateRecorderStatusLabel();
                } else {
                    setDataRecorderEnabled(true);
                    updateRecorderStatusLabel();
                }
            }
        });
        updateRecorderStatusLabel();
    
    }
*/
    public void setDataRecorderEnabled(boolean enabled) {
        for( DataRecorder dr : dataRecorders ) {
            dr.setEnabled(enabled);
        }
        dataRecorderStatus = enabled;
    }

    private void shutdownDataRecorders() {
        for( DataRecorder dr : dataRecorders ) {
            dr.shutdown();
        }
    }    
    
    public void dispose() {
        shutdownDataRecorders();
        dataProvider.halt();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        System.exit(0);
    }


    public void notifyDataArrived(byte[] data) {
        packetParser.updateSimStateWithData(simState, data);
    }
    
    private void registerSimListeners() {
        simState.getFrameRate().addListener(this);
        simState.getTimes().addListener(this);
        simState.getOurPosition().addListener(this);
        simState.getAttitude().addListener(this);
        simState.getSpeed().addListener(this);
        simState.getWeather().addListener(this);
        simState.getAtmosphere().addListener(this);
        simState.getSimStats().addListener(this);
        
        simState.getOurPosition().addListener((MapPanel)map);
        simState.addListenerToOtherAircraftPositions((MapPanel)map);
        
        simState.getOurPosition().addListener((RadarPanel)radarPanel);
        simState.addListenerToOtherAircraftPositions((RadarPanel)radarPanel);
        
        simState.getOurPosition().addListener(dash);
        simState.getAttitude().addListener(dash);
        simState.getSpeed().addListener(dash);
    }
    
    public void registerDataRecorder(DataRecorder recorder) {
        dataRecorders.add(recorder);
    }
    
    private void initTables() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Data");
        model.addColumn("Value");

        rawDataTable.setModel(model);        
    }
    
    private Map<String, Integer> tablePathToRowMap = new TreeMap();
    
    private void ref(DataObject o) {
        DefaultTableModel model = (DefaultTableModel) rawDataTable.getModel();

        for( Field f : o.getClass().getDeclaredFields() ) {
            Metadata a = (Metadata) f.getAnnotation(Metadata.class);
            if( a != null ) {
//                System.out.println(o.getClass().getName()+": "+f.getName()+" has "+a.displayLabel());
                String tablePath = o.getClass().getSimpleName() + "." + f.getName();
                if( !tablePathToRowMap.containsKey(tablePath) ) {
                    model.addRow(new Object[] {a.displayLabel(), null});
                    int rowIndex = model.getRowCount() - 1;                    
                    tablePathToRowMap.put(tablePath, rowIndex);
                }
                int rowIndex = tablePathToRowMap.get(tablePath);
                String firstChar = ""+f.getName().charAt(0);
                firstChar = firstChar.toUpperCase();
                String methodName = "get" + firstChar + f.getName().substring(1);
                try {
                    Method m = o.getClass().getMethod(methodName);
                    Object value = m.invoke(o);
                    rawDataTable.setValueAt(value, rowIndex, 1);                
                } catch (Exception ex) {
                    System.err.println("Error setting raw table value: "+ex);
                }
            }
            // a.displayLabel()
        }
        model.fireTableDataChanged();
    }


    

    public void notifyStateChanged(DataObject objectThatWasChanged) {
        ref(objectThatWasChanged);
    }

    public void setPacketParser(String s) {
        if( s.equals("9.xx") ) {
            packetParser = new PacketParser9xx(this);
        } else
        if( s.equals("10.xx") ) {
            packetParser = new PacketParser10xx(this);
        } else {
            System.out.println("Unknown packet parser: "+s);
        }
    }


    public DataProvider getDataProvider() {
        return dataProvider;
    }

    public void incrementPacketsReceived() {
        packetsReceived++;
        updateRow("XPDisplay.packetsReceived", packetsReceived);
    }

    public void incrementUnknownPackets() {
        unknownPackets++;
        updateRow("XPDisplay.unknownPackets", unknownPackets);
    }

    public void addPacketType(int type) {
        if( !packetTypes.contains(type) ) {
            packetTypes.add(type);
            updateRow("XPDisplay.packetTypes", packetTypes.toString());
        }
    }

    public void addUnknownPacketType(int type) {
        if( !unknownPacketTypes.contains(type) ) {
            unknownPacketTypes.add(type);
            updateRow("XPDisplay.unknownPacketTypes", unknownPacketTypes.toString());
        }
    }

    private void updateRow(String key, Object value) {
        DefaultTableModel model = (DefaultTableModel) rawDataTable.getModel();
        if( !tablePathToRowMap.containsKey(key) ) {
            model.addRow(new Object[] {key, null});
            int rowIndex = model.getRowCount() - 1;
            tablePathToRowMap.put(key, rowIndex);
            model.fireTableDataChanged();
        }
        int rowIndex = tablePathToRowMap.get(key);
        rawDataTable.setValueAt(value, rowIndex, 1);
        model.fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void clearPacketStats() {
        packetsReceived = 0;
        unknownPackets = 0;
        unknownPacketTypes.clear();
        packetTypes.clear();
        updateRow("XPDisplay.packetsReceived", packetsReceived);
        updateRow("XPDisplay.unknownPackets", unknownPackets);
        updateRow("XPDisplay.unknownPacketTypes", unknownPacketTypes.toString());
        updateRow("XPDisplay.packetTypes", packetTypes.toString());
    }
}
