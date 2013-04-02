/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.io.provider;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import xpdisplay.io.*;

public class UDPReaderDataProvider extends DataProvider {
    private Thread receiver;
    private boolean stopped = false;

    private String address;
    private int port;
    
    public UDPReaderDataProvider(final int port, final InetAddress addr) {
        this.port = port;
        this.address = addr.getHostAddress();

        receiver = new Thread() {
            public void run() {
                DatagramSocket socket = null;
                try {
                    byte[] inbuf = new byte[2100];  // default size
                    socket = new DatagramSocket(port, addr);

                    DatagramPacket packet = new DatagramPacket(inbuf, inbuf.length);

                    // Wait for packet

                    while(!isHalting()) {                
                        while(stopped) {
                            Thread.sleep(250);
                        }

                        socket.receive(packet);
                        
                        notifyListeners(packet.getData());
                    }
                    
                } catch( Exception e ) {
                    System.err.println(e);
                    e.printStackTrace(System.err);
                } finally {
                    if( socket != null ) {
                        try {
                            socket.close();
                        } catch( Exception e ) {
                        }
                    }
                }
            }
        };
        receiver.start();
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


    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
