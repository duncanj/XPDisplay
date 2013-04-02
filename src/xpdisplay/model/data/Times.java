/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import xpdisplay.model.data.DataObject;
import xpdisplay.model.Metadata;
import xpdisplay.model.SimState;

public class Times extends DataObject {
    
    @Metadata(displayLabel="Real time")
    private Float realTime;
    
    @Metadata(displayLabel="Total time")
    private Float totalTime;
    
    @Metadata(displayLabel="Mission time")
    private Float missionTime;
    
    @Metadata(displayLabel="Timer time")
    private Float timerTime;
    
    @Metadata(displayLabel="Zulu time")
    private Float zuluTime;
    
    @Metadata(displayLabel="Local time")
    private Float localTime;
    
    @Metadata(displayLabel="Hobbs time")
    private Float hobbsTime;
    
    
    public Times() {
    }

    public void updateSimState(SimState simState) {
        Times t = simState.getTimes();
        t.setRealTime(realTime);
        t.setTimerTime(timerTime);
        t.setTotalTime(totalTime);
        t.setMissionTime(missionTime);
        t.setZuluTime(zuluTime);
        t.setLocalTime(localTime);
        t.setHobbsTime(hobbsTime);
        t.notifyListeners();
    }

    public Float getRealTime() {
        return realTime;
    }

    public void setRealTime(Float realTime) {
        this.realTime = realTime;
    }

    public Float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Float totalTime) {
        this.totalTime = totalTime;
    }

    public Float getMissionTime() {
        return missionTime;
    }

    public void setMissionTime(Float missionTime) {
        this.missionTime = missionTime;
    }

    public Float getTimerTime() {
        return timerTime;
    }

    public void setTimerTime(Float timerTime) {
        this.timerTime = timerTime;
    }

    public Float getZuluTime() {
        return zuluTime;
    }

    public void setZuluTime(Float zuluTime) {
        this.zuluTime = zuluTime;
    }

    public Float getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Float localTime) {
        this.localTime = localTime;
    }

    public Float getHobbsTime() {
        return hobbsTime;
    }

    public void setHobbsTime(Float hobbsTime) {
        this.hobbsTime = hobbsTime;
    }
    
    
}
