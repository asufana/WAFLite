package com.github.asufana.waf.testutils;

import java.io.*;
import java.util.*;

public class Netstat {
    
    public static boolean isPortOpen(final Integer portNumber) throws IOException {
        final String cmdString = String.format("netstat -an | grep .%s",
                                               portNumber.toString());
        final String[] cmdarray = {"/bin/sh", "-c", cmdString};
        final Process process = Runtime.getRuntime().exec(cmdarray);
        try (final Scanner sc = new Scanner(process.getInputStream())) {
            sc.useDelimiter(System.getProperty("line.separator"));
            while (sc.hasNext()) {
                if (sc.next().indexOf("LISTEN") != -1) {
                    return true;
                }
            }
        }
        return false;
    }
}
