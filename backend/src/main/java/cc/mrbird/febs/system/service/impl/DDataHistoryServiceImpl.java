package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.system.dao.DDataHistoryMapper;
import cc.mrbird.febs.system.domain.DDataHistory;
import cc.mrbird.febs.system.service.DDataHistoryService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("dDataHistoryService")
public class DDataHistoryServiceImpl extends BaseService<DDataHistory> implements DDataHistoryService {

    @Autowired
    private DDataHistoryMapper dDataHistoryMapper;

    @Override
    public Map<String, Integer> statistics(Long userid) {
        HashMap<String, Integer> map = Maps.newHashMap();
        map.put("month", this.countMonth(userid));
        map.put("total", this.countTotal(userid));
        return map;
    }


    private Integer countMonth(Long userId) {
        return dDataHistoryMapper.countMonth(userId);
    }

    private Integer countTotal(Long userId) {
        return dDataHistoryMapper.countTotal(userId);
    }
}
