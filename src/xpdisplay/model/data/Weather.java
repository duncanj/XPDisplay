/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import xpdisplay.model.data.DataObject;
import xpdisplay.model.Metadata;
import xpdisplay.model.SimState;

public class Weather extends DataObject {
    
    @Metadata(displayLabel="Sea Level Pressure (mm Hg)")
    private Float seaLevelPressureInHg;         // dataref:sim/weather/barometer_sealevel_inhg
    
    @Metadata(displayLabel="Sea Level Temperature (deg C)")
    private Float seaLevelTemperatureInDegC;    // dataref:sim/weather/temperature_sealevel_c
    
    @Metadata(displayLabel="Wind Speed (kts)")
    private Float windSpeedKts;                 // dataref:sim/weather/wind_speed_kt[0] ?
    
    @Metadata(displayLabel="Wind Direction (deg)")
    private Float windDirectionDegrees;         // dataref:sim/weather/wind_direction_degt[0] ?
    
    @Metadata(displayLabel="Turbulence")
    private Float turbulence;                   // dataref:sim/weather/turbulence[0] ?
    
    @Metadata(displayLabel="Precipitation")
    private Float precipitation;                // dataref:sim/weather/rain_percent ?
    
    @Metadata(displayLabel="Hail")
    private Float hail;                         // ?
    
    
    
    
    public Weather() {
    }


    public void updateSimState(SimState simState) {
        Weather w = simState.getWeather();
        w.setSeaLevelPressureInHg(seaLevelPressureInHg);
        w.setSeaLevelTemperatureInDegC(seaLevelTemperatureInDegC);
        w.setWindSpeedKts(windSpeedKts);
        w.setWindDirectionDegrees(windDirectionDegrees);
        w.setTurbulence(turbulence);
        w.setPrecipitation(precipitation);
        w.setHail(hail);
        w.notifyListeners();
    }    
    
    
    public Float getSeaLevelPressureInHg() {
        return seaLevelPressureInHg;
    }

    public void setSeaLevelPressureInHg(Float seaLevelPressureInHg) {
        this.seaLevelPressureInHg = seaLevelPressureInHg;
    }

    public Float getSeaLevelTemperatureInDegC() {
        return seaLevelTemperatureInDegC;
    }

    public void setSeaLevelTemperatureInDegC(Float seaLevelTemperatureInDegC) {
        this.seaLevelTemperatureInDegC = seaLevelTemperatureInDegC;
    }

    public Float getWindSpeedKts() {
        return windSpeedKts;
    }

    public void setWindSpeedKts(Float windSpeedKts) {
        this.windSpeedKts = windSpeedKts;
    }

    public Float getWindDirectionDegrees() {
        return windDirectionDegrees;
    }

    public void setWindDirectionDegrees(Float windDirectionDegrees) {
        this.windDirectionDegrees = windDirectionDegrees;
    }

    public Float getTurbulence() {
        return turbulence;
    }

    public void setTurbulence(Float turbulence) {
        this.turbulence = turbulence;
    }

    public Float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Float precipitation) {
        this.precipitation = precipitation;
    }

    public Float getHail() {
        return hail;
    }

    public void setHail(Float hail) {
        this.hail = hail;
    }
}
