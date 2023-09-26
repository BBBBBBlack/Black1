package com.example.test01_coll.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static void printDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(format.format(date));
    }
}
