package org.eldslott.ww.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
    private static final String LOCATION = "/var/log/ww/output.log";
    private static final String ERROR = "ERROR";
    private static final String INFO = "INFO";
    private static final String WARN = "WARN";
    
    private String className = null;
    
    public Logger(Class<?> klass) {
        className = klass.getCanonicalName();
    }
    
    public void info(String s) {
        write(s, INFO);
    }
    
    public void warn(String s) {
        write(s, WARN);
    }
    
    public void error(String s) {
        write(s, ERROR);
    }
    
    private void write(String s, String type) {
        try {
            FileWriter fstream = new FileWriter(LOCATION);
            BufferedWriter out = new BufferedWriter(fstream);
            
            String date = new Date().toString();
            
            out.write("[" + date + "] [" + type + "] [" + className + "] - " + s);
            out.write("\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void error(String s, Exception e) {
        try {
            FileWriter fstream = new FileWriter(LOCATION, true);
            BufferedWriter out = new BufferedWriter(fstream);
            
            String date = new Date().toString();
            
            out.write("[" + date + "] [ERROR] [" + className + "] - " + s);
            out.write("\n");
            
            for (StackTraceElement element : e.getStackTrace()) {
                append(out, "    " + element.toString() + "\n");
            }
            
            out.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private void append(BufferedWriter out, String s) throws IOException {
        out.write(s);
    }
}
