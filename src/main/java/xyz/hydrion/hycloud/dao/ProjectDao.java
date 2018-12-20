package xyz.hydrion.hycloud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import xyz.hydrion.hycloud.domain.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDao {
    private final static String SQL_INSERT =
            "INSERT INTO project(id,name,msg,create_time,data_msg) VALUES(?,?,?,?,?)";
    private final static String SQL_QUERY_BY_ID =
            "SELECT * FROM project WHERE id=?";
    private final static String SQL_QUERY_ALL =
            "SELECT * FROM project";

    private JdbcTemplate jdbcTemplate;

    public void insert(Project project){
        jdbcTemplate.update(SQL_INSERT,
                project.getId(),project.getName(),
                project.getMsg(),project.getCreate_time(),
                dataMsgToString(project));
    }

    public Project queryProjectById(int id){
        return jdbcTemplate.queryForObject(SQL_QUERY_BY_ID, new Object[]{id}, new RowMapper<Project>() {
            @Override
            public Project mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSetToProject(resultSet);
            }
        });
    }

    public List<Project> queryAllProject(){
        return jdbcTemplate.query(SQL_QUERY_ALL, (resultSet, i)
                -> resultSetToProject(resultSet));
    }

    private Project resultSetToProject(ResultSet resultSet) throws SQLException{
        Project project = new Project();
        project.setName(resultSet.getString("name"));
        project.setMsg(resultSet.getString("msg"));
        project.setId(resultSet.getInt("id"));
        project.setCreate_time(resultSet.getTimestamp("create_time"));
        project.setData(stringToDataMsg(resultSet.getString("data_msg")));
        return project;
    }

    private static String dataMsgToString(Project project){
        //todo:处理传入参数带有#号的情况
        StringBuilder result = new StringBuilder();
        for (Project.Data data : project.getData()) {
            result.append(data.getName()!=null?data.getName():"").append("#")
                    .append(data.getType()!=null?data.getType():"").append("#")
                    .append(data.getNote()!=null?data.getNote():"").append("#");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private static List<Project.Data> stringToDataMsg(String string){
        String[] phrase = string.split("#");
        List<Project.Data> dataList = new ArrayList<>();
        for (int i = 0;i < phrase.length;i += 3){
            Project.Data data = new Project.Data();
            data.setName(phrase[i]);
            data.setType(phrase[i + 1]);
            data.setNote(phrase[i + 2]);
            dataList.add(data);
        }
        return dataList;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
