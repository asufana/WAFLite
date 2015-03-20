package com.github.asufana;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

public class WAFLiteTest {
    
    @Test
    public void testStart() throws Exception {
        final WAFLite waf = new WAFLite();
        final Stoppable stoppable = waf.start();
        assertThat(isPortOpen(8080), is(true));
        
        stoppable.stop();
        assertThat(isPortOpen(8080), is(false));
    }
    
    private boolean isPortOpen(final Integer portNumber) throws IOException {
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
