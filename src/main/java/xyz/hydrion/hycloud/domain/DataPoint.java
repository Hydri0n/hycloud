package xyz.hydrion.hycloud.domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPoint {
    private int id;
    private Timestamp time;
    private List<Data> data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data{
        String key;
        Object value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    public Map<String,Object> getDataAsMap(){
        Map<String,Object> map = new HashMap<>();
        for (Data data : this.data){
            map.put(data.getKey(),data.getValue());
        }
        return map;
    }
}
