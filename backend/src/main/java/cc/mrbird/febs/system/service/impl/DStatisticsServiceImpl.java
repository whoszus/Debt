package cc.mrbird.febs.system.service.impl;


import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.common.utils.DateTimeUtils;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.DData;
import cc.mrbird.febs.system.domain.DStatistics;
import cc.mrbird.febs.system.service.DStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Service("dStatisticsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DStatisticsServiceImpl extends BaseService<DStatistics> implements DStatisticsService {
    @Override
    public List<DStatistics> getMonthStatistics() {
        // TODO: 2/24/2019 统计报表功能
        Example example = new Example(DData.class);
        Example.Criteria criteria = example.createCriteria();
        String firstDayOfTheMonth = DateTimeUtils.getFirstOfMonth();

//        if (dData.getOperatorId() != null) {
//            criteria.andCondition("operator_id=", dData.getOperatorId());
//        }
//        criteria.andLessThanOrEqualTo("")
//        FebsUtil.handleSort(queryRequest, example, "CREATE_TIME");
        return null;
    }
}
