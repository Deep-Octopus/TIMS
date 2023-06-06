package com.example.tims.util;

import java.time.LocalDateTime;

public class LogUtils {
    public static void error(String msg) {
        System.out.println("ERROR:" + msg);
    }

    public static void info(String msg) {
        System.out.println("INFO " + LocalDateTime.now() + " - " + msg);
    }
}
