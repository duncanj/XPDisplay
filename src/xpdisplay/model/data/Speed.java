/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import xpdisplay.model.data.DataObject;
import xpdisplay.model.Metadata;
import xpdisplay.model.SimState;

public class Speed extends DataObject {
    
    // Vtrue,_ktas |   _Vind,_kias |   Vtrue,__mph |   _Vind,__mph |   __VVI,__fpm |   Vtotl,_ktas |   Vtotl,__mph
    
    @Metadata(displayLabel="True airspeed (kts)")
    private Float vTrueKts;
    
    @Metadata(displayLabel="Indicated airspeed (kts)")
    private Float vIndKts;
    
    @Metadata(displayLabel="True airspeed (mph)")
    private Float vTrueMph;
    
    @Metadata(displayLabel="Indicated airspeed (mph)")
    private Float vIndMph;
    
    @Metadata(displayLabel="Vertical velocity (fpm)")
    private Float vviFpm; // vertical speed, feet-per-min
    
    @Metadata(displayLabel="Total airspeed (kts)")
    private Float vTotalKts;
    
    @Metadata(displayLabel="Total airspeed (mph)")
    private Float vTotalMph;
    
    public Speed() {
    }

    public void updateSimState(SimState simState) {
        Speed s = simState.getSpeed();
        s.setVIndMph(vIndMph);
        s.setVIndKts(vIndKts);
        s.setVTotalKts(vTotalKts);
        s.setVTotalMph(vTotalMph);
        s.setVTrueKts(vTrueKts);
        s.setVTrueMph(vTrueMph);
        s.setVviFpm(vviFpm);
        s.notifyListeners();
    }

    public Float getVTrueKts() {
        return vTrueKts;
    }

    public void setVTrueKts(Float vTrueKts) {
        this.vTrueKts = vTrueKts;
    }

    public Float getVIndKts() {
        return vIndKts;
    }

    public void setVIndKts(Float vIndKts) {
        this.vIndKts = vIndKts;
    }

    public Float getVTrueMph() {
        return vTrueMph;
    }

    public void setVTrueMph(Float vTrueMph) {
        this.vTrueMph = vTrueMph;
    }

    public Float getVIndMph() {
        return vIndMph;
    }

    public void setVIndMph(Float vIndMph) {
        this.vIndMph = vIndMph;
    }

    public Float getVviFpm() {
        return vviFpm;
    }

    public void setVviFpm(Float vviFpm) {
        this.vviFpm = vviFpm;
    }

    public Float getVTotalKts() {
        return vTotalKts;
    }

    public void setVTotalKts(Float vTotalKts) {
        this.vTotalKts = vTotalKts;
    }

    public Float getVTotalMph() {
        return vTotalMph;
    }

    public void setVTotalMph(Float vTotalMph) {
        this.vTotalMph = vTotalMph;
    }
    
    
    
}
