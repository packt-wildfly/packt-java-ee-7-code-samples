package com.packtpub.wflydevelopment.chapter3.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtils {

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static String readLine(String s) {
        System.out.print(s);
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int readInt(String s) {
        System.out.print(s);

        try {
            final String text = bufferedReader.readLine();
            return Integer.parseInt(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
