/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import xpdisplay.model.data.DataObject;
import xpdisplay.model.Metadata;
import xpdisplay.model.SimState;

public class Position extends DataObject {
    
    @Metadata(displayLabel="Latitude")
    private Float latitude;
    
    @Metadata(displayLabel="Longitude")
    private Float longitude;
    
    @Metadata(displayLabel="Altitude (ft above mean sea-level)")
    private Float altitudeInFeetAboveMeanSeaLevel;
    
    @Metadata(displayLabel="Altitude (ft above ground-level)")
    private Float altitudeInFeetAboveGroundLevel;
    
    @Metadata(displayLabel="Altitude (indicated)")
    private Float altitudeIndicated;
    
    public Position() {
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getAltitudeInFeetAboveMeanSeaLevel() {
        return altitudeInFeetAboveMeanSeaLevel;
    }

    public void setAltitudeInFeetAboveMeanSeaLevel(Float altitudeInFeetAboveMeanSeaLevel) {
        this.altitudeInFeetAboveMeanSeaLevel = altitudeInFeetAboveMeanSeaLevel;
    }

    public Float getAltitudeInFeetAboveGroundLevel() {
        return altitudeInFeetAboveGroundLevel;
    }

    public void setAltitudeInFeetAboveGroundLevel(Float altitudeInFeetAboveGroundLevel) {
        this.altitudeInFeetAboveGroundLevel = altitudeInFeetAboveGroundLevel;
    }

    public Float getAltitudeIndicated() {
        return altitudeIndicated;
    }

    public void setAltitudeIndicated(Float altitudeIndicated) {
        this.altitudeIndicated = altitudeIndicated;
    }
    
    public void updateSimState(SimState simState) {
        Position p = simState.getOurPosition();
        p.setLatitude(getLatitude());
        p.setLongitude(getLongitude());
        p.setAltitudeInFeetAboveGroundLevel(getAltitudeInFeetAboveGroundLevel());
        p.setAltitudeInFeetAboveMeanSeaLevel(getAltitudeInFeetAboveMeanSeaLevel());
        p.setAltitudeIndicated(getAltitudeIndicated());
        p.notifyListeners();
    }
    
    public String toString() {
        return "("+getLatitude()+","+getLongitude()+","+getAltitudeInFeetAboveMeanSeaLevel()+"ft)";
    }
    
}
