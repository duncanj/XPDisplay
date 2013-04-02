/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteWriter {
    
    public ByteWriter() {
    }
    
    public static void main(String[] args) {
        File f = new File("data.dat");
        
        // 68 65 84 65 38 0 0 0 34 68 151 111 166 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
        
        int[] data = new int[] {68,65,84,65,38,0,0,0,34,68,151,111,166,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
            for( int v : data ) {
                out.writeByte(v);
            }
            out.close();
            
            System.out.println("Written "+data.length+" bytes to "+f);
        } catch (IOException e) {
            System.err.println(e);
        }
        
        
    }
    
}
