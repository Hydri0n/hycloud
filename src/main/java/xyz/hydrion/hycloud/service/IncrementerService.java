package xyz.hydrion.hycloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hydrion.hycloud.dao.IncrementerDao;

@Service
public class IncrementerService {
    private IncrementerDao incrementerDao;

    public int getNextId(String tableName){
        Integer i = incrementerDao.getNumberByTableName(tableName);
        if (i != null) {
            incrementerDao.update(tableName,++i);
            return i;
        }
        //todo:抛出异常
        else return 0;
    }

    public void createTable(String tableName){
        incrementerDao.insert(tableName);
    }

    @Autowired
    public void setIncrementerDao(IncrementerDao incrementerDao) {
        this.incrementerDao = incrementerDao;
    }
}
