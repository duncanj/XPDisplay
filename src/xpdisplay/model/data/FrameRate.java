/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import xpdisplay.model.data.DataObject;
import xpdisplay.model.Metadata;
import xpdisplay.model.SimState;

public class FrameRate extends DataObject {
    
    @Metadata(displayLabel="Frame Rate")
    private Float frameRate;
    
    @Metadata(displayLabel="Time Ratio") // dataref:sim/operations/misc/time_ratio
    private Float timeRatio;
    
    @Metadata(displayLabel="Visibility Ratio")
    private Float visRatio;
    
    @Metadata(displayLabel="Ground Ratio?")
    private Float grndRatio;
    
    @Metadata(displayLabel="Flight Ratio?")
    private Float flitRatio;    
    
    public FrameRate() {
    }

    public void updateSimState(SimState simState) {
        FrameRate f = simState.getFrameRate();
        f.setFlitRatio(flitRatio);
        f.setTimeRatio(timeRatio);
        f.setVisRatio(visRatio);
        f.setGrndRatio(grndRatio);
        f.setFrameRate(frameRate);
        f.notifyListeners();
    }

    public Float getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Float frameRate) {
        this.frameRate = frameRate;
    }

    public Float getTimeRatio() {
        return timeRatio;
    }

    public void setTimeRatio(Float timeRatio) {
        this.timeRatio = timeRatio;
    }

    public Float getVisRatio() {
        return visRatio;
    }

    public void setVisRatio(Float visRatio) {
        this.visRatio = visRatio;
    }

    public Float getGrndRatio() {
        return grndRatio;
    }

    public void setGrndRatio(Float grndRatio) {
        this.grndRatio = grndRatio;
    }

    public Float getFlitRatio() {
        return flitRatio;
    }

    public void setFlitRatio(Float flitRatio) {
        this.flitRatio = flitRatio;
    }
    
    
    
}
