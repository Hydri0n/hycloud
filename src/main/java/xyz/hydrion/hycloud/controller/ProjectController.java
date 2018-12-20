package xyz.hydrion.hycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.hydrion.hycloud.domain.Project;
import xyz.hydrion.hycloud.service.ProjectService;
import xyz.hydrion.hycloud.util.ResponseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/project")
public class ProjectController {

    private ProjectService projectService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public Map<String,Object> createProject(@RequestBody Project project){
        //todo:合法性检查
        int id = projectService.createProject(project);
        return ResponseUtil.generateMap(0,"suc",
                new String[]{"project_id"},new Object[]{id});
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Project getProjectDetail(@PathVariable("id") Integer id){
        return projectService.getProjectById(id);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Project> getAll(){
        return projectService.getProjectList();
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
}
