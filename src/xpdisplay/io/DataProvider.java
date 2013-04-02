/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.io;

import java.util.Vector;

public abstract class DataProvider {
    private Vector<DataListener> listeners = new Vector(0);
    protected boolean halting = false;    
    
    protected void notifyListeners(byte[] data) {
        for( DataListener listener : listeners ) {
            listener.notifyDataArrived(data);
        }
    }
    
    public void addListener(DataListener listener) {
        if( !listeners.contains(listener) ) {
            listeners.add(listener);
        }
    }
    
    public void removeListener(DataListener listener) {
        listeners.remove(listener);
    }
    
    
    public abstract void start();
    public abstract void stop();
    public abstract boolean isRunning();
    
    
    public void halt() {
        halting = true;
    }
    
    public boolean isHalting() {
        return halting;
    }
}
