package cc.mrbird.febs.system.service.impl;


import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.dao.DDataMapper;
import cc.mrbird.febs.system.dao.UserMapper;
import cc.mrbird.febs.system.domain.DData;
import cc.mrbird.febs.system.domain.DDataHistory;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.service.DDataHistoryService;
import cc.mrbird.febs.system.service.DDataService;
import com.wuwenze.poi.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Service("dDataService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DDataServiceImpl extends BaseService<DData> implements DDataService {

    @Autowired
    DDataHistoryService dDataHistoryService;

    @Autowired
    DDataMapper dDataMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public List<DData> findDData(QueryRequest queryRequest, DData dData, Integer userId) {
        Example example = new Example(DData.class);
        Example.Criteria criteria = example.createCriteria();
        if (userId != 1) {
            criteria.andCondition("operator_id=", userId);
        }
        if(dData.getClientPhone()!=null){
            criteria.andCondition("client_phone=", dData.getClientPhone());
        }
        FebsUtil.handleSort(queryRequest, example, "CREATE_TIME");
        return this.selectByExample(example);
    }


    @Override
    public void createDData(DData dData) {
        dData.setCreateTime(new Date());
        this.save(dData);
    }

    @Override
    public void updateDData(DData dData) {
        dData.setLastOperatTime(new Date());
        this.updateNotNull(dData);
    }

    @Override
    public void deleteDData(Integer dataId) {
        this.delete(dataId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishData(DData dData) throws Exception {
        DDataHistory dDataHistory = new DDataHistory();
        BeanUtil.copyProperties(dDataHistory, dData);

        this.dDataHistoryService.save(dDataHistory);
        this.delete(dData.getId());
    }

    @Override
    public void distributeData(Integer[] dataIds, Integer userId) {
        DData dData;
        User user = userMapper.selectByPrimaryKey(userId);
        for (Integer dataId : dataIds) {
            dData = new DData();
            dData.setId(dataId);
            dData.setOperatorId(userId);
            dData.setOperatorName(user.getUsername());
            this.updateNotNull(dData);
        }
    }

    @Override
    @Transactional
    public void batchInsert(List<DData> list) {
        int total = list.size();
        int max = 100;
        int count = total / max;
        int left = total % max;
        int length;
        if (left == 0) {
            length = count;
        } else {
            length = count + 1;
        }
        for (int i = 0; i < length; i++) {
            int start = max * i;
            int end = max * (i + 1);
            if (i != count) {
                this.dDataMapper.insertList(list.subList(start, end));
            } else {
                end = total;
                this.dDataMapper.insertList(list.subList(start, end));
            }
        }
    }
}
