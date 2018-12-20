package xyz.hydrion.hycloud.util;

import java.sql.Timestamp;

public class TimeUtil {
    public static Timestamp getCurrentTime(){
        return new Timestamp(new java.util.Date().getTime());
    }
}
