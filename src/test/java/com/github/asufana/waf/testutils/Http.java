package com.github.asufana.waf.testutils;

import java.io.*;

import com.squareup.okhttp.*;

public class Http {
    
    private static String BASE_URL = "http://localhost";
    
    public static String get(final Integer port, final String url) {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(createUrl(port, url))
                                                     .build();
        
        try {
            final Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static String createUrl(final Integer port, final String url) {
        return String.format("%s:%s%s", BASE_URL, port.toString(), url);
    }
}
