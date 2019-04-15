package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.DData;

import java.util.List;

public interface DDataService extends IService<DData> {

    List<DData> findDData(QueryRequest queryRequest,DData dData,Integer userId);

    void createDData(DData dData);

    void updateDData(DData dData);

    void deleteDData(Integer dataId);

    void finishData(DData dData) throws Exception;

    void distributeData(Integer[] dataIds, Integer userId);

    void batchInsert(List<DData> dataList);
}
