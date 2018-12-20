package xyz.hydrion.hycloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hydrion.hycloud.dao.DataDao;
import xyz.hydrion.hycloud.dao.ProjectDao;
import xyz.hydrion.hycloud.domain.Project;
import xyz.hydrion.hycloud.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final static String PROJECT_TABLE_NAME = "project";

    private ProjectDao projectDao;
    private DataDao dataDao;
    private IncrementerService incrementerService;

    public Project getProjectById(int id){
        return projectDao.queryProjectById(id);
    }

    public List<Project> getProjectList(){
        return projectDao.queryAllProject();
    }

    public int createProject(Project project){
        int id = incrementerService.getNextId(PROJECT_TABLE_NAME);
        project.setId(id);
        project.setCreate_time(TimeUtil.getCurrentTime());
        //数据库操作
        projectDao.insert(project);
        incrementerService.createTable(dataDao.createTable(project));
        return id;
    }

    @Autowired
    public void setDataDao(DataDao dataDao) {
        this.dataDao = dataDao;
    }

    @Autowired
    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Autowired
    public void setIncrementerService(IncrementerService incrementerService) {
        this.incrementerService = incrementerService;
    }
}
