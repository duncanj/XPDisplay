/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import xpdisplay.model.data.DataObject;
import xpdisplay.model.Metadata;
import xpdisplay.model.SimState;

public class Attitude extends DataObject {
    
    // pitch,__deg |   _roll,__deg |   hding,_true |   hding,__mag |   __mag,_comp |   mavar,__deg 
    
    @Metadata(displayLabel="Pitch (deg)")
    private Float pitch;
    
    @Metadata(displayLabel="Roll (deg)")
    private Float roll;
    
    @Metadata(displayLabel="Heading (true)")
    private Float headingTrue;
    
    @Metadata(displayLabel="Heading (magnetic)")
    private Float headingMagnetic;
    
    @Metadata(displayLabel="Heading (compass)")
    private Float headingCompass;
    
    @Metadata(displayLabel="Local magnetic variation (deg)")
    private Float magneticVariation; //dataref:sim/flightmodel/position/magnetic_variation
    
    
    public Attitude() {
    }

    public void updateSimState(SimState simState) {
        Attitude o = simState.getAttitude();
        o.setHeadingCompass(headingCompass);
        o.setHeadingMagnetic(headingMagnetic);
        o.setHeadingTrue(headingTrue);
        o.setMagneticVariation(magneticVariation);
        o.setPitch(pitch);
        o.setRoll(roll);
        o.notifyListeners();
    }

    public Float getPitch() {
        return pitch;
    }

    public void setPitch(Float pitch) {
        if( pitch == null ) return;
        this.pitch = pitch;
    }

    public Float getRoll() {
        return roll;
    }

    public void setRoll(Float roll) {
        if( roll == null ) return;
        this.roll = roll;
    }

    public Float getHeadingTrue() {
        return headingTrue;
    }

    public void setHeadingTrue(Float headingTrue) {
        if( headingTrue == null ) return;
        this.headingTrue = headingTrue;
    }

    public Float getHeadingMagnetic() {
        return headingMagnetic;
    }

    public void setHeadingMagnetic(Float headingMagnetic) {
        if( headingMagnetic == null ) return;
        this.headingMagnetic = headingMagnetic;
    }

    public Float getHeadingCompass() {
        return headingCompass;
    }

    public void setHeadingCompass(Float headingCompass) {
        if( headingCompass == null ) return;
        this.headingCompass = headingCompass;
    }

    public Float getMagneticVariation() {
        return magneticVariation;
    }

    public void setMagneticVariation(Float magneticVariation) {
        if( magneticVariation == null ) return;
        this.magneticVariation = magneticVariation;
    }
    
    
    
}
