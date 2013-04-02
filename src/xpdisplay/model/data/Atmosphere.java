/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import xpdisplay.model.data.DataObject;
import xpdisplay.model.Metadata;
import xpdisplay.model.SimState;

public class Atmosphere extends DataObject {
    
    // AMprs,_inHG |   AMtmp,_degC |   LEtmp,_degC |   _dens,ratio |   ____A,_ktas |   ____Q,__psf |   gravi,_fts2
    
    // barometer_current_inhg ?         ambient/outside pressure
    // temperature_ambient_c ?          ambient/outside temperature
    // temperature_le_c                 leading edge temperature
    // sigma                            air density as a ratio of sealevel
    // ?                                in knots..  speed of sound?
    // dataref:sim/flightmodel/misc/Qstatic     Q
    // gravity in feet-per-second squared       g
    
    @Metadata(displayLabel="Ambient Pressure (mm Hg)")
    private Float ambientPressureHg;
    
    @Metadata(displayLabel="Ambient Temperature (deg C)")
    private Float ambientTemperatureDegC;
    
    @Metadata(displayLabel="Leading Edge Temperature (deg C)")
    private Float leadingEdgeTemperatureDegC;
    
    @Metadata(displayLabel="Air Density Ratio (sigma)")
    private Float airDensityRatio;
    
    @Metadata(displayLabel="A (kts)")
    private Float aKts;
    
    @Metadata(displayLabel="Q (pounds per square ft)")
    private Float qPsf;
    
    @Metadata(displayLabel="Gravity (ft per second^2)")
    private Float gravityFtSecSqrd;
    
    
    
    public Atmosphere() {
    }

    public Float getAmbientPressureHg() {
        return ambientPressureHg;
    }

    public void setAmbientPressureHg(Float ambientPressureHg) {
        this.ambientPressureHg = ambientPressureHg;
    }

    public Float getAmbientTemperatureDegC() {
        return ambientTemperatureDegC;
    }

    public void setAmbientTemperatureDegC(Float ambientTemperatureDegC) {
        this.ambientTemperatureDegC = ambientTemperatureDegC;
    }

    public Float getLeadingEdgeTemperatureDegC() {
        return leadingEdgeTemperatureDegC;
    }

    public void setLeadingEdgeTemperatureDegC(Float leadingEdgeTemperatureDegC) {
        this.leadingEdgeTemperatureDegC = leadingEdgeTemperatureDegC;
    }

    public Float getAirDensityRatio() {
        return airDensityRatio;
    }

    public void setAirDensityRatio(Float airDensityRatio) {
        this.airDensityRatio = airDensityRatio;
    }

    public Float getAKts() {
        return aKts;
    }

    public void setAKts(Float aKts) {
        this.aKts = aKts;
    }

    public Float getQPsf() {
        return qPsf;
    }

    public void setQPsf(Float qPsf) {
        this.qPsf = qPsf;
    }

    public Float getGravityFtSecSqrd() {
        return gravityFtSecSqrd;
    }

    public void setGravityFtSecSqrd(Float gravityFtSecSqrd) {
        this.gravityFtSecSqrd = gravityFtSecSqrd;
    }

    
    public void updateSimState(SimState simState) {
        Atmosphere a = simState.getAtmosphere();
        a.setAKts(aKts);
        a.setAirDensityRatio(airDensityRatio);
        a.setAmbientPressureHg(ambientPressureHg);
        a.setAmbientTemperatureDegC(ambientTemperatureDegC);
        a.setGravityFtSecSqrd(gravityFtSecSqrd);
        a.setLeadingEdgeTemperatureDegC(leadingEdgeTemperatureDegC);
        a.setQPsf(qPsf);
        a.notifyListeners();
    }
    
    
    
}
