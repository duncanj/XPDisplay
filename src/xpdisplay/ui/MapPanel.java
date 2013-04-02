/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.painter.Painter;
import xpdisplay.model.StateChangeListener;
import xpdisplay.model.data.DataObject;
import xpdisplay.model.data.OtherAircraftPosition;
import xpdisplay.model.data.Position;
import xpdisplay.ui.map.GoogleMapTileProvider;
import xpdisplay.ui.map.GoogleTerrainTileProvider;
import xpdisplay.ui.map.MicrosoftMapTileProvider;
import xpdisplay.ui.map.OpenStreetMapTileProvider;

/**
 *
 *
 */
public class MapPanel extends javax.swing.JPanel implements StateChangeListener {
    
    private JXMapKit map;
//    private Set<Waypoint> waypointSet = new HashSet();
//    private List<Waypoint> waypointList = new ArrayList();
    private Map<Integer, Waypoint> waypoints = new TreeMap();
    
    private Float[] latitudes = new Float[8];
    private Float[] longitudes = new Float[8];
    private Float[] altitudes = new Float[8];

    private Float[] previousLatitudes = new Float[8];
    private Float[] previousLongitudes = new Float[8];
    private Float[] previousAltitudes = new Float[8];
    
    
    
    private Map<String, TileFactory> tileFactories = new TreeMap();
    
    public MapPanel() {
        initComponents();
        
        map = new JXMapKit();
        add(BorderLayout.CENTER, map);
        
        tileFactories.put("OpenStreetMap (Map)", OpenStreetMapTileProvider.getDefaultTileFactory());
        tileFactories.put("Microsoft (Aerial)", MicrosoftMapTileProvider.getDefaultTileFactory());
        tileFactories.put("Google (Map)", GoogleMapTileProvider.getDefaultTileFactory());
        tileFactories.put("Google (Terrain)", GoogleTerrainTileProvider.getDefaultTileFactory());
        
        mapSourceDropdown.addItem("OpenStreetMap (Map)");
        mapSourceDropdown.addItem("Microsoft (Aerial)");
        mapSourceDropdown.addItem("Google (Map)");
        mapSourceDropdown.addItem("Google (Terrain)");
        
        mapSourceDropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {        
                GeoPosition p = map.getCenterPosition();
                double lat = p.getLatitude();
                double lon = p.getLongitude();
                String providerName = (String) mapSourceDropdown.getSelectedItem();
                map.setTileFactory(tileFactories.get(providerName));
                map.setCenterPosition(new GeoPosition(lat, lon));
            }
        });
        
        setBackground(Color.RED);
        

        
/*
// Use NASA's Blue Marble imagery
        WMSService wms = new WMSService();
        wms.setLayer("BMNG");
        wms.setBaseUrl("http://wms.jpl.nasa.gov/wms.cgi?");
        TileFactory fact = new WMSTileFactory(wms);
        map.setTileFactory(fact);
 */
        
//        TileFactoryInfo tfi = new GoogleTileFactoryInfo(0, 15, 17, 256, true, true, false);
//        TileFactoryInfo tfi = new GoogleTileFactoryInfo(0, 17, 17, 256, true, true, GoogleTileFactoryInfo.MODE_TERRAIN);
//        map.setTileFactory(new DefaultTileFactory(tfi));
        
// New-style Tile Providers        
//        map.setTileFactory(GoogleTerrainTileProvider.getDefaultTileFactory());
//        map.setTileFactory(GoogleMapTileProvider.getDefaultTileFactory());
//        map.setTileFactory(MicrosoftMapTileProvider.getDefaultTileFactory());
        map.setTileFactory(OpenStreetMapTileProvider.getDefaultTileFactory());
      
        map.getZoomSlider().setValue(14);        
        
/*
// Boston Free Map
        WMSService wms = new WMSService();
        wms.setLayer("water");
        wms.setBaseUrl("http://boston.freemap.in/cgi-bin/mapserv?map=%2Fwww%2Ffreemap.in%2Fboston%2Fmap%2Fgmaps.map&");
        TileFactory fact = new WMSTileFactory(wms);
        map.setTileFactory(fact);
 */
        
/*
        WMSService wms = new WMSService();
        wms.setLayer("Geology");
        wms.setBaseUrl("http://grid1.wdcb.ru/cgi-bin/mapserv?map=/var/www/html/mapFiles/geology.map&SRS=AUTO&");
        TileFactory fact = new WMSTileFactory(wms);
        map.setTileFactory(fact);
 */
        
//        map.setAddressLocation(new GeoPosition(41.32644,  -74.41193));
        
        
        Painter<JXMapViewer> painter = new Painter<JXMapViewer>() {

        public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
            g = (Graphics2D) g.create();
            //convert from viewport to world bitmap
            Rectangle rect = map.getViewportBounds();
            g.translate(-rect.x, -rect.y);
            
            for( Integer index : waypoints.keySet() ) {
                Waypoint wp = waypoints.get(index);
                Point2D pt = map.getTileFactory().geoToPixel(wp.getPosition(), map.getZoom());
                int x = (int)pt.getX();
                int y = (int)pt.getY();

                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(1.0f));
                if( previousLatitudes[index] != null && previousLongitudes[index] != null ) {
                        
                    Point2D p_a = map.getTileFactory().geoToPixel(new GeoPosition(latitudes[index], longitudes[index]), map.getZoom());
                    Point2D p_b = map.getTileFactory().geoToPixel(new GeoPosition(previousLatitudes[index], previousLongitudes[index]), map.getZoom());
                    double dx = p_a.getX() - p_b.getX();
                    double dy = p_a.getY() - p_b.getY();
                    double len = p_a.distance(p_b);
                    double sf = 14.0 / len;
                    double ndx = dx * sf;
                    double ndy = dy * sf;

                    g.drawLine(x, y, x+(int)ndx, y+(int)ndy);
                }
                
                
                
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(3.0f));
                g.drawOval(x-7,y-7,15,15);

                
                g.setStroke(new BasicStroke(1.5f));
                g.drawOval(x,y,1,1);
                
                if( index == 0 ) {
                    g.setColor(new Color(255,255,255));
                } else {
                    g.setColor(new Color(0,255,0));
                }
                
                g.drawOval(x-7,y-7,15,15);
                
                paintDetails(index, g, x+15, y-5);
                
/*                
                g.fillOval(x-7,y-7,15,15);
                g.setColor(new Color(255,255,255,200));
                g.drawOval(x-7,y-7,15,15);
                g.drawString(""+index,x-3,y+5);
 */
                
                
            }
            g.dispose();
        }

    };

        
        

//        WaypointPainter<JXMapViewer> painter = new WaypointPainter<JXMapViewer>();
//        painter.setWaypoints(waypointSet);
        map.getMainMap().setOverlayPainter(painter);
        
        
        
        map.setSize(this.getSize());
        map.setVisible(true);
        
    }
    
    private void drawOutlinedText(String s, Graphics2D g, int x, int y) {
        Color c = g.getColor();
        g.setStroke(new BasicStroke(2.0f));

        FontRenderContext frc = g.getFontRenderContext();
        Font f = g.getFont();
        TextLayout tl = new TextLayout(s, f, frc);
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(x-0.2,y-0.2);
        Shape shape = tl.getOutline(transform);
        g.setColor(Color.BLACK);
        g.draw(shape);
        
        g.setStroke(new BasicStroke(1.0f));
        g.setColor(c);
        tl.draw(g, x, y);
    }
    
    private void drawText(String s, Graphics2D g, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(s);
        int h = fm.getHeight();
        Color c = g.getColor();
        g.setColor(new Color(100,100,100,200));
        g.fillRect(x-3,y-1,w+6,h+2);
        g.setColor(c);
        g.drawString(s, x, y+11);
    }
    
    private void paintDetails(int index, Graphics2D g, int x, int y) {
        if( showIdCheckbox.isSelected() ) {
            drawText("#"+index,g,x,y);
        }
        if( showAltCheckbox.isSelected() ) {            
            if( altitudes[index] != null ) {
                Float alt = altitudes[index];
                if( alt != null ) {
                    int flightLevel = (int)(alt / 100.0f);
                    drawText("FL"+flightLevel, g, x, y+16);
                } else {
                    drawText("?", g, x, y+16);
                }
            }
        }
    }
    
    public void setMapLocation(double lat, double lon) {
        map.setAddressLocation(new GeoPosition(lat, lon));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        controlPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        mapSourceDropdown = new javax.swing.JComboBox();
        showIdCheckbox = new javax.swing.JCheckBox();
        showAltCheckbox = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Map source:");

        showIdCheckbox.setSelected(true);
        showIdCheckbox.setText("Show ID");
        showIdCheckbox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        showIdCheckbox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        showAltCheckbox.setSelected(true);
        showAltCheckbox.setText("Show Alt");
        showAltCheckbox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        showAltCheckbox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mapSourceDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(showIdCheckbox)
                .addGap(20, 20, 20)
                .addComponent(showAltCheckbox)
                .addContainerGap(127, Short.MAX_VALUE))
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(mapSourceDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showIdCheckbox)
                    .addComponent(showAltCheckbox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        add(controlPanel, java.awt.BorderLayout.NORTH);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox mapSourceDropdown;
    private javax.swing.JCheckBox showAltCheckbox;
    private javax.swing.JCheckBox showIdCheckbox;
    // End of variables declaration//GEN-END:variables
    
    
    public static void main(String[] args) {
        //Properties systemSettings = System.getProperties();
        //systemSettings.put("http.proxyHost","FM-EU-LON-PROXY.fm.rbsgrp.net") ;
        //systemSettings.put("http.proxyPort", "8080") ;
        
        
        JFrame f = new JFrame("Test");
        f.getContentPane().add(new MapPanel());
        f.setSize(400,300);
        maximize(f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
    
    public static void maximize(Frame frame) {
        int state = frame.getExtendedState();
        
        // Set the maximized bits
        state |= Frame.MAXIMIZED_BOTH;
        
        // Maximize the frame
        frame.setExtendedState(state);
    }
    
    public synchronized void notifyStateChanged(DataObject objectThatWasChanged) {
        if( objectThatWasChanged instanceof OtherAircraftPosition ) {
            OtherAircraftPosition p = (OtherAircraftPosition) objectThatWasChanged;            
            if( p.getLatitude() != null && p.getLongitude() != null ) {
                int index = p.getIndex();
                if( !waypoints.containsKey(index) ) {
                    Waypoint wp = new Waypoint();
                    waypoints.put(index, wp);
                }                
                Waypoint waypoint = waypoints.get(index);
                waypoint.setPosition(new GeoPosition(p.getLatitude(), p.getLongitude()));
                if( p.getAltitudeInFeetAboveMeanSeaLevel() == null && index > 0 ) {
                    altitudes[index] = altitudes[index-1];
                } else {
                    altitudes[index] = p.getAltitudeInFeetAboveMeanSeaLevel();
                }

                Float oldLat = latitudes[index];
                Float oldLong = longitudes[index];
                Float oldAlt = altitudes[index];
                
                if( oldLat != null && oldLong != null ) {
//                    System.out.print(""+index+":");
                    
//                    boolean longitudeChanged = old.getLongitude() != p.getLongitude();
//                    boolean latitudeChanged = old.getLatitude() != p.getLatitude();
//                    boolean altitudeChanged = old.getAltitudeInFeetAboveMeanSeaLevel() != p.getAltitudeInFeetAboveMeanSeaLevel();
                    
                    boolean longitudeChanged = oldLong.floatValue() != p.getLongitude().floatValue();
                    boolean latitudeChanged = oldLat.floatValue() != p.getLatitude().floatValue();
                    boolean altitudeChanged = p.getAltitudeInFeetAboveMeanSeaLevel() != null && (oldAlt == null || oldAlt.floatValue() != p.getAltitudeInFeetAboveMeanSeaLevel().floatValue());

                    if( longitudeChanged ) {
//                        System.out.print(" lon "+oldLong+" to "+p.getLongitude());
                        previousLongitudes[index] = longitudes[index];
                        longitudes[index] = p.getLongitude();
                    }
                    if( latitudeChanged ) {
//                        System.out.print(" lat "+oldLat+" to "+p.getLatitude());
                        previousLatitudes[index] = latitudes[index];
                        latitudes[index] = p.getLatitude();
                    }
                    if( altitudeChanged ) {
//                        System.out.print(" alt "+oldAlt+"ft to "+p.getAltitudeInFeetAboveMeanSeaLevel()+"ft");
                        previousAltitudes[index] = altitudes[index];
                        altitudes[index] = p.getAltitudeInFeetAboveMeanSeaLevel();
                    }
                    if( !longitudeChanged && !latitudeChanged && !altitudeChanged )
                    {
//                        System.out.print("(no change detected)");
                    }
//                    System.out.println(".");
                    
                } else {
                    latitudes[index] = p.getLatitude();
                    longitudes[index] = p.getLongitude();
                    altitudes[index] = p.getAltitudeInFeetAboveMeanSeaLevel();
                }
                
                
                
                
            }
        } else if( objectThatWasChanged instanceof Position ) {
            Position p = (Position) objectThatWasChanged;
            setMapLocation(p.getLatitude(), p.getLongitude());
            
            int index = 0;
            Float oldLat = latitudes[index];
            Float oldLong = longitudes[index];
            Float oldAlt = altitudes[index];

            if( oldLat != null && oldLong != null ) {
//                System.out.print(""+index+":");

//                    boolean longitudeChanged = old.getLongitude() != p.getLongitude();
//                    boolean latitudeChanged = old.getLatitude() != p.getLatitude();
//                    boolean altitudeChanged = old.getAltitudeInFeetAboveMeanSeaLevel() != p.getAltitudeInFeetAboveMeanSeaLevel();

                boolean longitudeChanged = oldLong.floatValue() != p.getLongitude().floatValue();
                boolean latitudeChanged = oldLat.floatValue() != p.getLatitude().floatValue();
                boolean altitudeChanged = (oldAlt == null || oldAlt.floatValue() != p.getAltitudeInFeetAboveMeanSeaLevel().floatValue());

                if( longitudeChanged ) {
//                    System.out.print(" lon "+oldLong+" to "+p.getLongitude());
                    previousLongitudes[index] = longitudes[index];
                    longitudes[index] = p.getLongitude();
                }
                if( latitudeChanged ) {
//                    System.out.print(" lat "+oldLat+" to "+p.getLatitude());
                    previousLatitudes[index] = latitudes[index];
                    latitudes[index] = p.getLatitude();
                }
                if( altitudeChanged ) {
//                    System.out.print(" alt "+oldAlt+"ft to "+p.getAltitudeInFeetAboveMeanSeaLevel()+"ft");
                    previousAltitudes[index] = altitudes[index];
                    altitudes[index] = p.getAltitudeInFeetAboveMeanSeaLevel();
                }
                if( !longitudeChanged && !latitudeChanged && !altitudeChanged )
                {
//                    System.out.print("(no change detected)");
                }
//                System.out.println(".");

            } else {
                latitudes[index] = p.getLatitude();
                longitudes[index] = p.getLongitude();
                altitudes[index] = p.getAltitudeInFeetAboveMeanSeaLevel();
            }
        }
        map.repaint();
    }
}
