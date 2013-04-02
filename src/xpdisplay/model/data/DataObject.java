/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model.data;

import java.util.Vector;
import xpdisplay.model.*;

public abstract class DataObject {

    private Vector<StateChangeListener> listeners = new Vector(0);
    
    protected void notifyListeners() {
        for( StateChangeListener listener : listeners ) {
            listener.notifyStateChanged(this);
        }
    }
    
    public void addListener(StateChangeListener listener) {
        if( !listeners.contains(listener) ) {
            listeners.add(listener);
        }
    }
    
    public void removeListener(StateChangeListener listener) {
        listeners.remove(listener);
    }
    
    public abstract void updateSimState(SimState simState);
}
