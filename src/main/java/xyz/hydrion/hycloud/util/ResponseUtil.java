package xyz.hydrion.hycloud.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static Map<String,Object> generateMap(int code,String msg,
                                                 String[] key,Object[] value){
        Map<String,Object> response = new HashMap<>();
        response.put("code",code);
        response.put("msg",msg);
        if (key.length == value.length){
            for (int i=0;i < key.length;i++){
                response.put(key[i],value[i]);
            }
        }
        return response;
    }
}
