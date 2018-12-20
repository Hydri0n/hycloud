package xyz.hydrion.hycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.hydrion.hycloud.domain.DataPoint;
import xyz.hydrion.hycloud.service.DataService;
import xyz.hydrion.hycloud.util.ResponseUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/data")
public class DataController {

    private DataService dataService;

    @RequestMapping(value = "/{project_id}",method = RequestMethod.POST)
    public Map<String,Object> uploadData(@PathVariable("project_id") int projectId,
                                         @RequestBody DataPoint dataPoint){
        dataService.uploadData(projectId,dataPoint);
        return ResponseUtil.generateMap(0,"suc",
                new String[]{},new Object[]{});
    }

    @RequestMapping(value = "/{project_id}",method = RequestMethod.GET)
    public List<DataPoint> getData(@PathVariable("project_id") int projectId,
                                   @RequestParam(value = "page",required = false) Integer page,
                                   @RequestParam(value = "size",required = false) Integer size){
        if (page == null || size == null){
            return dataService.getAllData(projectId);
        }
        else {
            return dataService.getDataWithPage(projectId,page,size);
        }

    }

    @Autowired
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
