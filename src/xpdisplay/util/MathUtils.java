/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.util;

import java.awt.geom.Point2D;

public class MathUtils {
    
    private MathUtils() {
    }
    
    
    /*
        x = rho * sin(theta) * cos(phi)
        y = rho * sin(theta) * sin(phi)
        z = rho * cos(theta)
     
     */
    public static Point2D.Double convertToCartesian(double latitude, double longitude) {
        double x = longitude*60.0*1852.0*Math.cos(Math.toRadians(latitude));
        double y = latitude*60.0*1852.0;
        Point2D.Double p = new Point2D.Double(x,y);
        return p;
    }
    
}
