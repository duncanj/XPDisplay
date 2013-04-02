/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.ui.map;

import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class MicrosoftMapTileProvider {
    private static final String VERSION = "174";
    private static final int minZoom = 1;
    private static final int maxZoom = 16;
    private static final int mapZoom = 17;
    private static final int tileSize = 256;
    private static final boolean xr2l = true;
    private static final boolean yt2b = true;
    private static final String baseURL = "http://a2.ortho.tiles.virtualearth.net/tiles/a";

    private static final MicrosoftTileFactoryInfo MICROSOFT_MAPS_TILE_INFO = new MicrosoftTileFactoryInfo(
            minZoom, maxZoom, mapZoom, tileSize, xr2l, yt2b, baseURL);

    public static TileFactory getDefaultTileFactory() {
        return (new DefaultTileFactory(MICROSOFT_MAPS_TILE_INFO));
    }

    private static class MicrosoftTileFactoryInfo extends TileFactoryInfo {

        public MicrosoftTileFactoryInfo(int minZoom, int maxZoom, int mapZoom,
                int tileSize, boolean xr2l, boolean ytb2, String baseURL) {
            super(minZoom, maxZoom, mapZoom, tileSize, xr2l, ytb2, baseURL,
                    null, null, null);
        }

        public String getTileUrl(int x, int y, int zoom) {
            return baseURL + xyzoom2quadrants(x, y, zoom) + ".jpeg?g="
                    + VERSION;
        }

        public String xyzoom2quadrants(int x, int y, int zoom) {
            StringBuffer quad = new StringBuffer();
            int level = 1 << (maxZoom - zoom);
            while (level > 0) {
                int ix = 0;
                if (x >= level) {
                    ix++;
                    x -= level;
                }
                if (y >= level) {
                    ix += 2;
                    y -= level;
                }
                quad.append(ix);
                // now descend into that square
                level /= 2;
            }
            return new String(quad);
        }

    }
    
}
