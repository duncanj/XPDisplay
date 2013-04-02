/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.ui;

import javax.swing.*;
import java.awt.*;

public class RawDataPanel extends JPanel {
    private JPanel panel1;
    private JTable rawDataTable;

    public RawDataPanel() {
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.CENTER);
    }

    public JTable getRawDataTable() {
        return rawDataTable;
    }
}
