package xyz.hydrion.hycloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hydrion.hycloud.dao.DataDao;
import xyz.hydrion.hycloud.domain.DataPoint;
import xyz.hydrion.hycloud.util.TimeUtil;

import java.util.List;

@Service
public class DataService {

    private DataDao dataDao;
    private IncrementerService incrementerService;

    public void uploadData(int projectId,DataPoint dataPoint){
        String tableName = "data_" + projectId;
        if (dataDao.isExist(tableName)) {
            int id = incrementerService.getNextId(tableName);
            dataPoint.setId(id);
            dataPoint.setTime(TimeUtil.getCurrentTime());
            dataDao.insert(projectId,dataPoint);
        }
        //todo:处理异常
    }

    public List<DataPoint> getAllData(int projectId){
        return dataDao.queryAllData(projectId);
    }

    public List<DataPoint> getDataWithPage(int projectId,int page,int size){
        return dataDao.queryByPageAndSize(projectId,page,size);
    }

    @Autowired
    public void setDataDao(DataDao dataDao) {
        this.dataDao = dataDao;
    }

    @Autowired
    public void setIncrementerService(IncrementerService incrementerService) {
        this.incrementerService = incrementerService;
    }
}
