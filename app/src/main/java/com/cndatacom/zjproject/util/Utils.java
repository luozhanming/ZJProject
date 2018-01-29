package com.cndatacom.zjproject.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by cdc4512 on 2018/1/24.
 */

public class Utils {

    public static String getDate(long time){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(time));
    }
}
