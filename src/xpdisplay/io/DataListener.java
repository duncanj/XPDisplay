/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.io;

public interface DataListener {
    public void notifyDataArrived(byte[] data);
    
}
