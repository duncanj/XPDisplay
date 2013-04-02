/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class GuiUtils {
    
    private static final boolean LOG_IMAGE_LOCATIONS = true;
    private static Map<String, Image> imageCache = new TreeMap();
    
    private GuiUtils() {
    }
    
    public static void setNativeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Unable to load native look and feel: "+ex);
        }        
    }
    
    public static void centre(JFrame frame) {
        //Center frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = frame.getSize();
        screenSize.height = screenSize.height/2;
        screenSize.width = screenSize.width/2;
        size.height = size.height/2;
        size.width = size.width/2;
        int y = screenSize.height - size.height;
        int x = screenSize.width - size.width;
        frame.setLocation(x, y);        
    }
    
    public static void enableAntialiasing(Graphics g) {
        // Retrieve the graphics context; this object is used to paint shapes
        Graphics2D g2d = (Graphics2D)g;
    
        // Determine if antialiasing is enabled
//        RenderingHints rhints = g2d.getRenderingHints();
//        boolean antialiasOn = rhints.containsValue(RenderingHints.VALUE_ANTIALIAS_ON);
    
        // Enable antialiasing for shapes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
    
        // Disable antialiasing for shapes
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                             RenderingHints.VALUE_ANTIALIAS_OFF);
    
        // Draw shapes...; see e586 Drawing Simple Shapes
    
        // Enable antialiasing for text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
        // Draw text...; see e591 Drawing Simple Text
    
        // Disable antialiasing for text
//        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                             RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);        
    }
    
    public static void maximize(Frame frame) {
        int state = frame.getExtendedState();
    
        // Set the maximized bits
        state |= Frame.MAXIMIZED_BOTH;
    
        // Maximize the frame
        frame.setExtendedState(state);
    }   
    
    /**
     * Get the specified image using the appropriate classloader, or directly from the filesystem if run locally.
     * E.g. getImage("test_icon.png") will return the Image from src/main/images/resources/test_icon.png.
     **/
    public static Image getImage(String imageFilename) {
        if( imageCache.containsKey(imageFilename) ) {
            return imageCache.get(imageFilename);
        }
        
        ImageIcon icon = null;
        String resourceName = "resources/"+imageFilename;
        
//        logger.trace("Obtaining image from: "+resourceName);

        // Note: do not use the system class loader if you're using webstart.
//        URL resource = ClassLoader.getSystemClassLoader().getResource(resourceName);
        
        URL resource = GuiUtils.class.getClassLoader().getResource(resourceName);
        if( resource != null )  {
            if( LOG_IMAGE_LOCATIONS ) {
                System.out.println("Loading image from URL: "+resource);
            }
            icon = new ImageIcon(resource);
        } else {
            File file = new File(resourceName);
            if( file != null && file.exists() ) {
                if( LOG_IMAGE_LOCATIONS ) {
                    System.out.println("Loading image from File: "+resourceName);
                }
                icon = new ImageIcon(resourceName);
            } else {                
                resourceName = "../"+resourceName;            
                file = new File(resourceName);
                if( file != null && file.exists() ) {
                    if( LOG_IMAGE_LOCATIONS ) {
                        System.out.println("Loading image from File: "+resourceName);
                    }
                    icon = new ImageIcon(resourceName);
                } else {                
                    System.out.println("Could not locate image in File: "+resourceName+" ("+file.getAbsolutePath()+")");
                }
            }
            
        }
        if( icon != null ) {
            Image result = icon.getImage();
            imageCache.put(imageFilename, result);
            return result;
        } else {
            return null;
        }
    }    
    
    public static String colorToString(Color c) {
        return "["+c.getRed()+","+c.getGreen()+","+c.getBlue()+"]";
    }
    
    public static Color stringToColor(String s) {
        if( s.startsWith("[") ) {
            s = s.substring(1);
        }
        if( s.endsWith("]") ) {
            s = s.substring(0,s.length()-1);
        }
//        System.out.println(s);
        int i = 0;
        
        int red = 0;
        int green = 0;
        int blue = 0;
                
        for( String p : s.split(",") ) {
            if( i == 0 ) {
                red = Integer.parseInt(p);
            } else
            if( i == 1 ) {
                green = Integer.parseInt(p);
            } else
            if( i == 2 ) {
                blue = Integer.parseInt(p);
            }            
            i++;
        }
        return new Color( red, green, blue );
    }
}

