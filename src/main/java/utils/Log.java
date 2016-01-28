package utils;

import main.Main;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by user on 28/01/16 AD.
 */
public class Log {
    public static OutputStream out = new BufferedOutputStream( System.out );
    public static void error(Object... objects) {
        if(Main.DEBUG) {
            for(Object object: objects)
                System.err.print(object + " ");
            System.err.println();
        }
    }
    public static void print(String s) {
        try {
            out.write(s.getBytes());
            if(Main.DEBUG)
                out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.print(s);
    }
    public static void println(String s) {
        print(s + System.getProperty("line.separator"));
//        System.out.println(s);
    }
    public static void println() {
        println("");
//        System.out.println();
    }

    public static void flush() {
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
