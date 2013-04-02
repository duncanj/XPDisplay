/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.ui;

import java.awt.Graphics;
import javax.swing.JLabel;
import xpdisplay.util.GuiUtils;

public class AaLabel extends JLabel {
    
    public AaLabel() {
        
    }
    
    public AaLabel(String text) {
        super(text);
    }
    
    public void paint(Graphics g) {
        GuiUtils.enableAntialiasing(g);
        super.paint(g);
    }
    
}
