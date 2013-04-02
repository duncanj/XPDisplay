/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.ui;

import xpdisplay.io.DataProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel footer;
    private JLabel dataReceiverStatusLabel;
    private JLabel dataRecorderStatusLabel;
    private JLabel ipAddressAndPortLabel;
    private JPanel rdTabPanel;
    private JPanel dbTabPanel;
    private JPanel mapTabPanel;
    private JPanel radarTabPanel;
    private JPanel configTabPanel;

    public MainPanel(final XPDisplayFrame f) {
        dataReceiverStatusLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        dataRecorderStatusLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        dataReceiverStatusLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                DataProvider dataProvider = f.getDataProvider();
                if( dataProvider.isRunning() ) {
                    dataProvider.stop();
                    updateStatusLabel(dataProvider.isRunning());
                } else {
                    dataProvider.start();
                    updateStatusLabel(dataProvider.isRunning());
                }
            }
        });

        dataRecorderStatusLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if( f.dataRecorderStatus ) {
                    f.setDataRecorderEnabled(false);
                    updateRecorderStatusLabel(false);
                } else {
                    f.setDataRecorderEnabled(true);
                    updateRecorderStatusLabel(true);
                }
            }
        });

        setLayout(new BorderLayout());
        add(panel1, BorderLayout.CENTER);
    }


    public void setIPAndPort(String s) {
        ipAddressAndPortLabel.setText(s);
        ipAddressAndPortLabel.setToolTipText("To change this, provide command-line arguments: [port] [address]");
    }

    public void updateStatusLabel(boolean dataProviderIsRunning) {
        if( dataProviderIsRunning ) {
            dataReceiverStatusLabel.setText("running");
            dataReceiverStatusLabel.setBackground(Color.CYAN);
        } else {
            dataReceiverStatusLabel.setText("stopped");
            dataReceiverStatusLabel.setBackground(Color.GRAY);
        }
    }

    public void updateRecorderStatusLabel(boolean dataRecorderIsRunning) {
        if( dataRecorderIsRunning ) {
            dataRecorderStatusLabel.setText("running");
            dataRecorderStatusLabel.setBackground(Color.YELLOW);
        } else {
            dataRecorderStatusLabel.setText("stopped");
            dataRecorderStatusLabel.setBackground(Color.GRAY);
        }
    }

    public void setRawDataPanel(RawDataPanel p) {
        rdTabPanel.add(p, BorderLayout.CENTER);
    }
    public void setDashboardPanel(DashboardPanel p) {
        dbTabPanel.add(p, BorderLayout.CENTER);
    }
    public void setMapPanel(MapPanel p) {
        mapTabPanel.add(p, BorderLayout.CENTER);
    }
    public void setRadarPanel(RadarPanel p) {
        radarTabPanel.add(p, BorderLayout.CENTER);
    }
    public void setConfigPanel(ConfigPanel p) {
        configTabPanel.add(p, BorderLayout.CENTER);
    }
}
