/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import xpdisplay.model.SimState;

public class OtherAircraftPosition extends Position {
    private int index = 0;
    
    public OtherAircraftPosition(int index) {
        this.index = index;
    }
    
    public void updateSimState(SimState simState) {
        OtherAircraftPosition p = (OtherAircraftPosition) simState.getOtherAircraftPosition(index);
        if( getLatitude() != null ) {
            p.setLatitude(getLatitude());
        }
        if( getLongitude() != null ) {
            p.setLongitude(getLongitude());
        }
        if( getAltitudeInFeetAboveMeanSeaLevel() != null ) {
//            System.out.println("other "+getIndex()+": "+getAltitudeInFeetAboveMeanSeaLevel());
            p.setAltitudeInFeetAboveMeanSeaLevel(getAltitudeInFeetAboveMeanSeaLevel());
        }

        p.notifyListeners();
    }
    
    public int getIndex() {
        return index;
    }
    
    public String toString() {
        return "#"+index+": "+super.toString();
    }
    
}
