/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.io.provider;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import xpdisplay.io.*;

public class FileReaderDataProvider extends DataProvider {
    
    private final long SLEEP_DELAY = 10;
    
    private boolean running = true;
    private boolean stopped = false;
    
    private String filename;
    
    public FileReaderDataProvider(final String filename) {
        this.filename = filename;
        
        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    
                    File f = new File(filename);
                    long length = f.length(); // normally this would come from packet length
                    DataInputStream in = new DataInputStream(new FileInputStream(f));

                    while(in.available() > 0 && running) {
                        while( stopped ) {
                            Thread.sleep(200);
                        }
                        
                        byte[] data = new byte[2102];
                        in.read(data);            
            //            System.out.println("Packets: "+count+"  first byte="+data[0]);

        //                System.out.println("--- Packet "+count+" -------------------");
            //            pp.parse(data);

        //                pp.updateSimStateWithData(simState, data);

                        notifyListeners(data);

        //                count++;
                        
                        if( running ) {
                            Thread.sleep(SLEEP_DELAY);
                        }
                    }  
                    
                    System.out.println("FINISHED");

                } catch(Exception e) {
                    System.err.println(e);
                    e.printStackTrace(System.err);
                }
            }
        };
        t.start();        
    }

    public void start() {
        stopped = false;
    }

    public void stop() {
        stopped = true;
    }
    
    public boolean isRunning() {
        return !stopped;
    }
    
}
