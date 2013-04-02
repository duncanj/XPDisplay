/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model;

import xpdisplay.model.data.Atmosphere;
import xpdisplay.model.data.FrameRate;
import xpdisplay.model.data.OtherAircraftPosition;
import xpdisplay.model.data.Attitude;
import xpdisplay.model.data.Position;
import xpdisplay.model.data.SimStats;
import xpdisplay.model.data.Speed;
import xpdisplay.model.data.Times;
import xpdisplay.model.data.Weather;

public class SimState {
    private FrameRate frameRate = new FrameRate();
    private Times times = new Times();
    private SimStats simStats = new SimStats();
    private Position ourPosition = new Position();
    private Attitude attitude = new Attitude();
    private Speed speed = new Speed();
    private Position[] otherAircraftPositions = new Position[8];
    private Weather weather = new Weather();
    private Atmosphere atmosphere = new Atmosphere();
    
    public SimState() {
        for( int i=0; i<otherAircraftPositions.length; i++ ) {
            otherAircraftPositions[i] = new OtherAircraftPosition(i);
        }
    }
    
    public FrameRate getFrameRate() {
        return frameRate;
    }
    
    public Times getTimes() {
        return times;
    }
    
    public SimStats getSimStats() {
        return simStats;
    }
    
    public Position getOurPosition() {
        return ourPosition;
    }
    
    public Attitude getAttitude() {
        return attitude;
    }
    
    public Speed getSpeed() {
        return speed;
    }
    
    public Position getOtherAircraftPosition(int index) {
        return otherAircraftPositions[index];
    }
    
    public Weather getWeather() {
        return weather;
    }
    
    public Atmosphere getAtmosphere() {
        return atmosphere;
    }
    
    // convenience method
    public void addListenerToOtherAircraftPositions(StateChangeListener listener) {
        for( int i=0; i<otherAircraftPositions.length; i++ ) {
            otherAircraftPositions[i].addListener(listener);
        }
    }
    
}
