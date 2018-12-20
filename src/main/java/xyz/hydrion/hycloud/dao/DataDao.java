package xyz.hydrion.hycloud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.hydrion.hycloud.domain.DataPoint;
import xyz.hydrion.hycloud.domain.Project;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataDao {
    private final static String DATA_PREFIX = "data_";
    private final static String TABLE_PREFIX = "data_";
    private final static String SQL_QUERY =
            "SHOW TABLES LIKE ?";

    private JdbcTemplate jdbcTemplate;

    public boolean isExist(String tableName){
        try {
            jdbcTemplate.queryForObject(SQL_QUERY,
                    new Object[]{tableName}, String.class);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public String createTable(Project project){
        int id = project.getId();
        String tableName = TABLE_PREFIX + id;
        if (!isExist(tableName)){
            StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + "(" +
                    "id INT NOT NULL PRIMARY KEY," +
                    "upload_time TIMESTAMP,");
            for (Project.Data data : project.getData()) {
                String type = data.getType();
                sql.append(DATA_PREFIX).append(data.getName())
                        .append(" ").append(type).append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
            jdbcTemplate.execute(sql.toString());
        }
        return tableName;
    }

    public void insert(int projectId,DataPoint dataPoint){

        if (!dataPoint.getData().isEmpty()) {
            StringBuilder sql = new StringBuilder("INSERT INTO " + TABLE_PREFIX
                    + projectId + "(id,upload_time,");
            StringBuilder sql2 = new StringBuilder(") VALUES(?,?,");
            Object[] objects = new Object[dataPoint.getData().size() + 2];
            objects[0] = dataPoint.getId();
            objects[1] = dataPoint.getTime();
            int i = 2;
            for (DataPoint.Data data : dataPoint.getData()) {
                sql.append(DATA_PREFIX).append(data.getKey()).append(",");
                sql2.append("?,");
                objects[i++] = data.getValue();
            }
            sql.deleteCharAt(sql.length() - 1);
            sql2.deleteCharAt(sql2.length() - 1);
            sql.append(sql2).append(")");
            jdbcTemplate.update(sql.toString(),objects);
        }
    }

    public List<DataPoint> queryAllData(int projectId){
        String sql = "SELECT * FROM " + TABLE_PREFIX + projectId;
        return jdbcTemplate.query(sql,
                (resultSet, i) -> resultSetToDataPoint(resultSet));
    }

    public List<DataPoint> queryByPageAndSize(int projectId,int page,int size){
        String sql = "SELECT * FROM " + TABLE_PREFIX + projectId +
                " ORDER BY id DESC" + " LIMIT " + (page - 1) * size + "," + size;
        return jdbcTemplate.query(sql,
                (resultSet, i) -> resultSetToDataPoint(resultSet));
    }

    public DataPoint queryRecentData(int projectId){
        String sql = "SELECT * FROM " + DATA_PREFIX + projectId +
                " ORDER BY id DESC LIMIT 1";
        return jdbcTemplate.query(sql,
                (resultSet, i) -> resultSetToDataPoint(resultSet)).get(0);
    }

    private DataPoint resultSetToDataPoint(ResultSet resultSet) throws SQLException{
        DataPoint dataPoint = new DataPoint();
        dataPoint.setId(resultSet.getInt("id"));
        dataPoint.setTime(resultSet.getTimestamp("upload_time"));
        List<DataPoint.Data> dataList = new ArrayList<>();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        for (int i = 3;i <= rsmd.getColumnCount();i++){
            DataPoint.Data data = new DataPoint.Data();
            String key = rsmd.getColumnName(i);
            data.setValue(resultSet.getObject(key));
            //直接获取的key会带有前缀，需要删除
            key = key.replace(DATA_PREFIX,"");
            data.setKey(key);
            dataList.add(data);
        }
        dataPoint.setData(dataList);
        return dataPoint;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
