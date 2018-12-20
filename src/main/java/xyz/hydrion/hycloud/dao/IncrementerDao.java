package xyz.hydrion.hycloud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IncrementerDao {
    private final static String SQL_QUERY_BY_TABLE_NAME =
            "SELECT number FROM incrementer WHERE table_name = ?";
    private final static String SQL_INSERT =
            "INSERT INTO incrementer(table_name,number) VALUES(?,0)";
    private final static String SQL_UPDATE =
            "UPDATE incrementer SET number=? WHERE table_name=?";

    private JdbcTemplate jdbcTemplate;

    public Integer getNumberByTableName(String tableName){
        return jdbcTemplate.queryForObject(SQL_QUERY_BY_TABLE_NAME,
                new Object[]{tableName},Integer.class);
    }

    public void insert(String tableName){
        jdbcTemplate.update(SQL_INSERT, tableName);
    }

    public void update(String tableName,Integer number){
        jdbcTemplate.update(SQL_UPDATE, number, tableName);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
