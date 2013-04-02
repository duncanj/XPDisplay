/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.io.impl;

import java.util.ArrayList;
import java.util.List;
import xpdisplay.io.PacketParser;
import xpdisplay.model.data.Atmosphere;
import xpdisplay.model.data.DataObject;
import xpdisplay.model.data.FrameRate;
import xpdisplay.model.data.OtherAircraftPosition;
import xpdisplay.model.data.Attitude;
import xpdisplay.model.data.Position;
import xpdisplay.model.data.SimStats;
import xpdisplay.model.data.Speed;
import xpdisplay.model.data.Times;
import xpdisplay.model.data.Weather;
import xpdisplay.ui.XPDisplayFrame;


// 9.0rc1 - 9.21rc2 - problem with other plane pos?
public class PacketParser9xx extends PacketParser {
    private XPDisplayFrame f;

    public PacketParser9xx(final XPDisplayFrame f) {
        this.f = f;
    }

    public List<DataObject> parse(byte[] data) {
        List<DataObject> results = new ArrayList();
        try {
//            System.out.println(data[4]);
            int i = 5;
            int channel = 0;
            while( i < data.length && channel < 7) {
                int index = readInt(data[i++], data[i++], data[i++], data[i++]);
//                System.out.println(""+channel+":  Index="+index);
                float[] values = new float[8];
                for( int k=0; k<8; k++ ) {
//                    System.out.print("  "+readFloat(data[i++], data[i++], data[i++], data[i++]));
                    values[k] = readFloat(data[i++], data[i++], data[i++], data[i++]);
                }

                f.addPacketType(index);
                f.incrementPacketsReceived();
                
                switch( index ) {
                    case 0  : frameRate(values, results); break;
                    case 1  : times(values, results); break;
                    case 2  : simStats(values, results); break;
                    case 3  : speed(values, results); break;
                    case 5  : weather(values, results); break;
                    case 6  : atmosphere(values, results); break;
                    case 18 : pitchRollHeading(values, results); break;
                    case 20 : latLongAlt(values, results); break;
                    case 21 : locVelDist(values, results); break;
                    case 22 : allLat(values, results); break;
                    case 23 : allLong(values, results); break;
                    case 24 : allAlt(values, results); break;
                    case 38 : propRpm(values, results); break;
                    default : unknown(channel, index);
                }

                
//                System.out.print(printArray(values));
//                System.out.println();
                channel++;
            }
        } catch( ArrayIndexOutOfBoundsException e ) {
            // can happen, never mind
            System.out.println();
        }        
     
        return results;
    }
    
    private void unknown(int channel, int index) {
        f.incrementUnknownPackets();
        f.addUnknownPacketType(index);
//        System.out.println("channel "+channel+": Unknown index: "+index);
    }
    
    protected void frameRate(float[] values, List<DataObject> results) {
//        System.out.println("Frame Rate: "+values[0]+"   Time Ratio: "+values[2]+"   Vis Ratio: "+values[3]+"   Grnd Ratio: "+values[5]+"   Flit Ratio: "+values[6]);
//        System.out.println(printArray(values));
  
        FrameRate f = new FrameRate();
        f.setFrameRate(values[0]);
        f.setTimeRatio(values[2]);
        f.setVisRatio(values[3]);
        f.setGrndRatio(values[5]);
        f.setFlitRatio(values[6]);        
        results.add(f);
    }
    
    protected void times(float[] values, List<DataObject> results) {
        // Times: [916.26733,56.0272,55.04984,0.0,-999.0,10.539206,10.539206,155.7238]
        
        Times t = new Times();
        t.setRealTime(values[0]);
        t.setTotalTime(values[1]);
        t.setMissionTime(values[2]);
        t.setTimerTime(values[3]);
        t.setZuluTime(values[5]);
        t.setLocalTime(values[6]);
        t.setHobbsTime(values[7]);
        results.add(t);
        //System.out.println("Times: "+printArray(values));
    }
    
    protected void simStats(float[] values, List<DataObject> results) {
        // System.out.println("SimStats: "+printArray(values));
        // SimStats: [0.0,0.0,0.0,0.0,74.0,0.0,50437.0,0.0]
        SimStats s = new SimStats();
        s.setExploDim(values[0]);
        s.setExploUse(values[1]);
        s.setCratrDim(values[2]);
        s.setCratrUse(values[3]);
        s.setPuffsTot(values[4]);
        s.setPuffsVis(values[5]);
        s.setTrianglesVisible(values[6]);
        s.setQDepth(values[7]);
        results.add(s);
    }
    
    protected void speed(float[] values, List<DataObject> results) {
        Speed s = new Speed();
        s.setVTrueKts(values[0]);
        s.setVIndKts(values[1]);
        s.setVTrueMph(values[2]);
        s.setVIndMph(values[3]);
        s.setVviFpm(values[4]);
        s.setVTotalKts(values[6]);
        s.setVTotalMph(values[7]);
        results.add(s);
    }
    
    protected void weather(float[] values, List<DataObject> results) {
        Weather w = new Weather();
        w.setSeaLevelPressureInHg(values[0]);
        w.setSeaLevelTemperatureInDegC(values[1]);
        w.setWindSpeedKts(values[3]);
        w.setWindDirectionDegrees(values[4]);
        w.setTurbulence(values[5]);
        w.setPrecipitation(values[6]);
        w.setHail(values[7]);
        results.add(w);
    }
    
    protected void atmosphere(float[] values, List<DataObject> results) {
//        System.out.println("ATM: "+printArray(values));
        // ATM: [29.380486,5.224512,5.6083207,1.0166385,650.10156,10.170635,-999.0,32.14522]

        Atmosphere a = new Atmosphere();
        a.setAmbientPressureHg(values[0]);
        a.setAmbientTemperatureDegC(values[1]);
        a.setLeadingEdgeTemperatureDegC(values[2]);
        a.setAirDensityRatio(values[3]);
        a.setAKts(values[4]);
        a.setQPsf(values[5]);
        a.setGravityFtSecSqrd(values[7]);        
        results.add(a);
    }    
    
    protected void pitchRollHeading(float[] values, List<DataObject> results) {
//        System.out.println("PRH: "+printArray(values));
    
        // pitch,__deg |   _roll,__deg |   hding,_true |   hding,__mag |   __mag,_comp |   mavar,__deg 
        // PRH: [0.42730588,0.25508523,47.93867,61.36609,36.789173,-999.0,-999.0,13.427419]
        
        Attitude o = new Attitude();
        o.setPitch(values[0]);
        o.setRoll(values[1]);
        o.setHeadingTrue(values[2]);
        o.setHeadingMagnetic(values[3]);
        o.setHeadingCompass(values[4]);
        o.setMagneticVariation(values[7]);        
        results.add(o);
    }
    
    protected void latLongAlt(float[] values, List<DataObject> results) {
        //System.out.println("Latitude (Origin Reference Point): "+values[0]+"   Longitude: "+values[1]+"  Altitude - fmsl: "+values[2]+"   Altitude - fagl: "+values[3]+"    Altitude - indicated: "+values[5]+"    Latitude - South: "+values[6]+"    Longitude - West: "+values[7]);
        Position p = new Position();
        p.setLatitude(values[0]);
        p.setLongitude(values[1]);
        p.setAltitudeInFeetAboveMeanSeaLevel(values[2]);
        p.setAltitudeInFeetAboveGroundLevel(values[3]);
        p.setAltitudeIndicated(values[5]);
        results.add(p);
    }
    
    protected void locVelDist(float[] values, List<DataObject> results) {
        //System.out.print("X: "+values[0]+"   Y: "+values[1]+"   Z: "+values[2]);
        //System.out.print("   vX: "+values[3]+"   vY: "+values[4]+"   vZ: "+values[5]);
        //System.out.println("   dist(ft): "+values[6]+"   dist(nm): "+values[7]);
//        System.out.println(printArray(values));
    }
    
    protected void allLat(float[] values, List<DataObject> results) {
//        System.out.print("Latitude:");
        for( int i=0; i<values.length; i++ ) {
            OtherAircraftPosition p = new OtherAircraftPosition(i);
            p.setLatitude(values[i]);
            results.add(p);            
        }
//        System.out.println(printArray(values));
    }

    protected void allLong(float[] values, List<DataObject> results) {
//        System.out.print("Longitude:");
        for( int i=0; i<values.length; i++ ) {
            OtherAircraftPosition p = new OtherAircraftPosition(i);
            p.setLongitude(values[i]);
            results.add(p);            
        }
//        System.out.println();
//        System.out.println(printArray(values));
    }

    protected void allAlt(float[] values, List<DataObject> results) {
//        System.out.print("Altitude:");
        for( int i=0; i<values.length; i++ ) {
            OtherAircraftPosition p = new OtherAircraftPosition(i);
            p.setAltitudeInFeetAboveMeanSeaLevel(values[i]);
            results.add(p);            
        }
//        System.out.println();
//        System.out.println(printArray(values));
    }

    protected void propRpm(float[] values, List<DataObject> results) {
        System.out.println("Prop RPM (each engine): "+printArray(values));
    }
 
    
}
