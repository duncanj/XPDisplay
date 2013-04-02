/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.io;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataRecorder implements DataListener {
    private boolean enabled = false;
    private boolean open = false;
    private File f;
    private DataOutputStream out;
    private boolean written = false;
    
    public DataRecorder(String filename) throws IOException {
        f = new File(filename);
    }

    public void notifyDataArrived(byte[] data) {
//        System.out.println("DataRecorder received "+data.length+" bytes.");
        if( !enabled ) {
            return;
        }

        try {
            if( !open ) {
                out = new DataOutputStream(new FileOutputStream(f));
                open = true;
            }

            out.write(data);
            written = true;
        } catch(IOException e) {
            enabled = false;
            System.err.println("Unable to write data to file: "+e);
        }
    }
    
    public void shutdown() {
        if( out != null ) {
            try {
                out.close();
            } catch(Exception e) {                
            }
        }
        
        if( !written && f != null) {
            try {
                f.delete();
            } catch( Throwable t ) {                
            }
        }
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
}
