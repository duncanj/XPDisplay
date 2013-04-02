/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import xpdisplay.io.DataRecorder;
import xpdisplay.io.PacketParser;
import xpdisplay.io.impl.PacketParser9xx;
import xpdisplay.io.provider.FileReaderDataProvider;
import xpdisplay.io.provider.UDPReaderDataProvider;
import xpdisplay.model.SimState;
import xpdisplay.model.StateChangeListener;
import xpdisplay.model.data.DataObject;
import xpdisplay.model.data.OtherAircraftPosition;
import xpdisplay.model.data.Position;
import xpdisplay.ui.XPDisplayFrame;
import xpdisplay.util.GuiUtils;


// http://weblogs.java.net/blog/joshy/archive/2006/10/nasa_maps_in_yo.html
// http://today.java.net/pub/a/today/2007/10/30/building-maps-into-swing-app-with-jxmapviewer.html
// http://today.java.net/pub/a/today/2007/11/13/mapping-mashups-with-jxmapviewer.html

public class Main {
    
    public Main() {
    }

    private static void usage() {
        System.out.println("Note: you can provide command line arguments [port] [address] to bind to a specific port and IP address, e.g. java -jar XPDisplay.jar 49050 192.168.0.5");
    }

    public static void main(String[] args) throws Exception {
        if( args.length == 0 ) {
            usage();
        }

        int port = 49003;
        InetAddress address = InetAddress.getLocalHost();

        try {

            if( args.length == 1 ) {
                port = Integer.parseInt(args[0]);
            }
            if( args.length == 2 ) {
                address = InetAddress.getByName(args[1]);
            }
            if( args.length > 2 ) {
                System.out.println("Unexpected number of arguments.  Got "+args.length+", expected none, 1 or 2.");
                usage();
                return;
            }
        } catch( Throwable t ) {
            System.out.println("Sorry, I couldn't understand the command-line arguments.  If in doubt, specify both port and IP address, e.g. java -jar XPDisplay.jar 49005 192.168.0.5");
            System.out.println(" >>> Underlying error: "+t.getMessage());
            return;
        }

        final int portF = port;
        final InetAddress addressF = address;
        System.out.println("Setting listen port to "+portF);
        System.out.println("Setting bind address to "+addressF.getHostAddress());

        GuiUtils.setNativeLookAndFeel();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//               FileReaderDataProvider dp = new FileReaderDataProvider("data-2009-04-15--15.54.51.dat");
//                FileReaderDataProvider dp = new FileReaderDataProvider("/home/user/shared/received.dat");
                UDPReaderDataProvider dp = new UDPReaderDataProvider(portF, addressF);
                XPDisplayFrame frame = new XPDisplayFrame(dp);
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd--HH.mm.ss");
                    DataRecorder recorder = new DataRecorder("data-"+sdf.format(new Date())+".dat");
                    dp.addListener(recorder);
                    frame.registerDataRecorder(recorder);
                } catch(IOException e) {
                    System.err.println("Error creating data recorder: "+e);
                }
            }
        });
        
        
        if( 1 == 1 ) {
            return;
        }
        
/*        
        
        File f = new File("received2.dat");
        long length = f.length(); // normally this would come from packet length
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        String type = "";
        
//        printAll(in, length);
        debug(in, length);
*/        
/*        
        for( int i=0; i<4; i++ ) {
            type += ""+(char)in.readByte();
        }
        System.out.println("type="+type);
        
        if( type.equals("DATA") ) {        
            in.readByte(); // throwaway

            int channels = (int)(length - 5) / 36;
            System.out.println("channels="+channels);

            for( int channel=0; channel<channels; channel++ ) {
                int index = in.readInt();
                System.out.print("channel "+channel+": index="+index+"  ->  ");

                float[] values = new float[8]; // always 8x 4-byte segments
                for( int i=0; i<values.length; i++ ) {
                    values[i] = in.readFloat(); // 4 bytes
                }

                switch( index ) {
                    case 0  : frameRate(values); break;
                    case 18 : latLongAlt(values); break;
                    case 34 : propRpm(values); break;
                    default : System.out.println("channel "+channel+": Unknown index: "+index);
                }
            }
        }
*/        
//        in.close();
    }

    private static void printAll(DataInputStream in, long length) throws Exception {
        int a = -1;
        int b = -1;
        int count = 0;
        for( int i=0; i<length; i++ ) {
            int v = in.readUnsignedByte();
            System.out.print(v+" ");
            
            count++;
            
            if( v == 10 && a == 0 && b == 0 ) {
                System.out.println();
                System.out.println(count);
                count = 0;
            }
            a = b;
            b = v;
        }
    }
}