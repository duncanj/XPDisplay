/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.ui;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class GoogleTileFactoryInfo extends TileFactoryInfo {
  
    public static final int MODE_MAP = 1;
    public static final int MODE_SATELLITE = 2;
    public static final int MODE_TERRAIN = 3;
  
    private int mode = MODE_MAP;
    
  public GoogleTileFactoryInfo (int minimumZoomLevel, int maximumZoomLevel, int totalMapZoom,
            int tileSize, boolean xr2l, boolean yt2b,
            int mode) {
    super (minimumZoomLevel, maximumZoomLevel, totalMapZoom, tileSize, xr2l, yt2b, null, null, null, null);
    this.mode = mode;
  }
 
  @Override
  public String getTileUrl(int x, int y, int zoom) {
    switch( mode ) {
        case MODE_MAP: return getMapUrl(x, y, zoom);
        case MODE_SATELLITE: return getSatUrl(x, y, zoom);
        case MODE_TERRAIN: return getTerrainUrl(x, y, zoom);
        default: return getMapUrl(x,y,zoom);
    }
  }
 
  // http://mt1.google.com/mt/v=w2.92&hl=en&x=16371&s=&y=10889&z=15&s=Galileo
  
  protected String getMapUrl (int x, int y, int zoom)  {
    String url = "http://mt.google.com/mt?w=2.43" +
            "&x=" + x +
            "&y=" + y +
            "&zoom=" + zoom;
    return url;
  }

// http://khm1.google.co.uk/kh/v=37&hl=en&x=4091&y=2723&z=13&s=Galileo
  public String getSatUrl(int x, int y, int zoom) {
    int ya = 1 << (17 - zoom);
    if ((y < 0) || (ya - 1 < y)) {
      return "http://www.google.co.uk/mapfiles/transparent.gif";
    }
    
//    int serverId = (int)(4.0 * Math.random()); // 0-3
    
    String url = "http://khm1.google.com/kh/v=37&hl=en" +
            "&x=" + x +
            "&y=" + y +
            "&zoom=" + zoom +
            "&s=Galileo";
    return url;    
    
/*    
    if ((x < 0) || (ya - 1 < x)) {
      x = x % ya;
      if (x < 0) {
        x += ya;
      }
    }

    StringBuffer str = new StringBuffer();
    str.append('t');
    for (int i = 16; i >= zoom; i--) {
      ya = ya / 2;
      if (y < ya) {
        if (x < ya) {
          str.append('q');
        } else {
          str.append('r');
          x -= ya;
        }
      } else {
        if (x < ya) {
          str.append('t');
          y -= ya;
        } else {
          str.append('s');
          x -= ya;
          y -= ya;
        }
      }
    }
    return "http://khml.google.com/kh/v=37&t=" + str;
 */
  }
  
  
  // http://mt3.google.com/mt/v=w2p.87&hl=en&x=8183&y=5442&z=14&s=Galileo
  protected String getTerrainUrl (int x, int y, int zoom)  {
    String url = "http://mt3.google.com/mt/v=w2p.87?hl=en" +
            "&x=" + x +
            "&y=" + y +
            "&z=" + zoom;
    return url;
  }  
} 