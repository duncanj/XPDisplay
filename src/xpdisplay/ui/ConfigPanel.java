/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.ui;

import xpdisplay.util.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ConfigPanel extends JPanel {
    private JPanel panel1;
    private JComboBox versionDropdown;
    private JCheckBox keepVisible;
    private JButton goButton;

    public ConfigPanel(final XPDisplayFrame f) {

        setLayout(new BorderLayout());
        add(panel1, BorderLayout.CENTER);

        boolean kot = Settings.getAsBoolean(Settings.KEEP_ON_TOP, false);
        keepVisible.setSelected(kot);

        String version = Settings.get(Settings.XPLANE_VERSION, "9.xx");
        versionDropdown.getModel().setSelectedItem(version);

        versionDropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED ) {
                    String s = (String) versionDropdown.getSelectedItem();
                    Settings.set(Settings.XPLANE_VERSION, s);
                    f.setPacketParser(s);
                }
            }
        });
        keepVisible.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean onTop = keepVisible.isSelected();
                Settings.set(Settings.KEEP_ON_TOP, ""+onTop);
                f.setAlwaysOnTop(onTop);
            }
        });
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.clearPacketStats();
            }
        });
    }
}

