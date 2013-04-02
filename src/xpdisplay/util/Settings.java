/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.util;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class Settings {

    public static final String NUMBER_OF_EXECUTIONS = "xpdisplay.executions";
    public static final String XPLANE_VERSION = "xplane.version";
    public static final String KEEP_ON_TOP = "xpdisplay.keepontop";

    private static Properties properties = new Properties();
    private static String filename = System.getProperty("user.home")+ File.separator+".xpdisplay"+File.separator+"xpdisplay.properties";

    private static String CONF_LOCATION;


    // cannot instantiate
    private Settings() {
    }

    public static void init() {
        CONF_LOCATION = System.getenv("XPDISPLAY_CONF");
        if( CONF_LOCATION != null ) {
            filename = CONF_LOCATION+"/xpdisplay.properties";
        } else {
            CONF_LOCATION = System.getProperty("user.home")+File.separator+".xpdisplay";
        }

        load();
        Runtime.getRuntime().addShutdownHook(new SaveSettingsShutdownHook());
    }

    public static String getConfLocation() {
        return CONF_LOCATION;
    }

    public static void writeCopyOfProperties(PrintWriter printWriter) {
        Map m = new TreeMap(properties);
        for( Object key : m.keySet() ) {
            printWriter.println(key+"="+m.get(key));
        }
    }

    public static String get(String propertyKey, String defaultValue) {
        if( properties.containsKey(propertyKey ) ) {
            return properties.getProperty(propertyKey);
        }
        if( defaultValue != null ) {
            properties.put(propertyKey, defaultValue);
        }
        return defaultValue;
    }

    public static String get(String propertyKey) {
        return properties.getProperty(propertyKey);
    }

    public static boolean getAsBoolean(String key, boolean defaultValue) {
        if( properties.containsKey(key) ) {
            String value = properties.getProperty(key);
            return Boolean.valueOf(value);
        }
        properties.put(key, ""+defaultValue);
        return defaultValue;
    }

    public static int getAsInteger(String key, int defaultValue) {
        if( properties.containsKey(key) ) {
            String value = properties.getProperty(key);
            return Integer.parseInt(value);
        }
        properties.put(key, ""+defaultValue);
        return defaultValue;
    }

    public static void set(String propertyKey, String propertyValue) {
        Object oldValue = properties.get(propertyKey);
        if( propertyValue == null ) {
            properties.remove(propertyKey);
        } else {
            properties.setProperty(propertyKey, propertyValue);
        }
    }

    public static void load() {
        // Read properties file.
        try {
            InputStream stream = new FileInputStream(filename);
            if( stream != null ) {
                properties.load(stream);
                System.out.println("XPDisplay: loaded configuration from "+new File(filename).getAbsolutePath());
            } else {
                System.out.println("Could not load "+filename+" - no problem, it will be created.");
            }
        } catch (Exception e) {
        }
    }

    public static void save() {
        // Write properties file.
//        logger.info("Saving settings.");

        incrementNumericalSetting(NUMBER_OF_EXECUTIONS);

        try {
            new File(filename).getParentFile().mkdirs();
            properties.store(new FileOutputStream(filename), null);
        } catch (IOException e) {
            System.err.println("Error saving settings to "+filename+": "+e);
        }
    }

    private static class SaveSettingsShutdownHook extends Thread {
        public void run() {
            Settings.save();
        }
    }

    public static int incrementNumericalSetting(String propertyName) {
        int result = 0;
        try {
            int numExec = Integer.parseInt(get(propertyName, "0"));
            int oldValue = numExec;
            numExec++;
            set(propertyName, ""+numExec);
            result = numExec;
        } catch(NumberFormatException e) {
            // never mind.
        }
        return result;
    }


}
