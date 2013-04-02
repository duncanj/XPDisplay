/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.ui.map;

import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class OpenStreetMapTileProvider {
    private static final int minZoom = 1;
    private static final int maxZoom = 15;
    private static final int mapZoom = 17;
    private static final int tileSize = 256;
    private static final boolean xr2l = true;
    private static final boolean yt2b = true;
    private static final String baseURL = "http://tile.openstreetmap.org/";

    private static final TileFactoryInfo OPEN_STREET_MAPS_TILE_INFO = new OpenStreetTileFactoryInfo(
            minZoom, maxZoom, mapZoom, tileSize, xr2l, yt2b, baseURL);

    public static TileFactory getDefaultTileFactory() {
        return (new DefaultTileFactory(OPEN_STREET_MAPS_TILE_INFO));
    }

    private static class OpenStreetTileFactoryInfo extends TileFactoryInfo {

        public OpenStreetTileFactoryInfo(int minZoom, int maxZoom, int mapZoom,
                int tileSize, boolean xr2l, boolean ytb2, String baseURL) {
            super(minZoom, maxZoom, mapZoom, tileSize, xr2l, ytb2, baseURL,
                    null, null, null);
        }

        public String getTileUrl(int x, int y, int zoom) {
            zoom = mapZoom - zoom;
            // http://c.tile.openstreetmap.org/7/65/41.png
            String url = this.baseURL + "/" + zoom + "/" + x + "/" + y + ".png";
            return url;
        }

    }

    
    
}
