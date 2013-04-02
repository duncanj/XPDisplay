/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.model;

import xpdisplay.model.data.DataObject;

public interface StateChangeListener {

    public void notifyStateChanged(DataObject objectThatWasChanged);
    
}
