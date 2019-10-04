package com.hx.webim.util;


import org.apache.commons.lang.time.DateUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateUtil {

    private DateUtil(){

    }
    public static long getDate(){
        ZoneOffset zoneOffset= ZoneOffset.ofHours(8);
        LocalDateTime localDateTime= LocalDateTime.now();
        long unixTimestamp=localDateTime.toEpochSecond(zoneOffset);
        return unixTimestamp;
    }
    public static long unixTimestampToTimestamp(long unixTimestamp){
        return unixTimestamp*1000;
    }

    public static long timestampToUnixTimestamp(long timestamp){
        return timestamp/1000;
    }
}
