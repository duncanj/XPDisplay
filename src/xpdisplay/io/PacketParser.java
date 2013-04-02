/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.io;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import xpdisplay.model.SimState;
import xpdisplay.model.data.DataObject;

public abstract class PacketParser {
    
    public abstract List<DataObject> parse(byte[] data);
    
    protected int readInt(byte a, byte b, byte c, byte d) {
        return (((d & 0xff) << 24) | ((c & 0xff) << 16) | ((b & 0xff) << 8) | (a & 0xff));
    }
    
    protected float readFloat(byte a, byte b, byte c, byte d) {
        int bits = readInt(a,b,c,d);
        return Float.intBitsToFloat(bits);
    }

    protected String printArray(float[] values) {
        String s = "[";        
        for( int i=0; i<values.length; i++ ) {
            if( i != 0 ) {
                s += ",";
            }
            s += values[i];
        }
        s += "]";
        return s;
    }
    
    public void updateSimStateWithData(SimState simState, byte[] data) {
        List<DataObject> updates = parse(data);
        Set<DataObject> uniqueObjects = new HashSet(updates);
        for( DataObject o : uniqueObjects ) {
            o.updateSimState(simState);
        }
    }
    
}
