package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.common.config.MyMapper;
import cc.mrbird.febs.system.domain.DDataHistory;

public interface DDataHistoryMapper extends MyMapper<DDataHistory> {
    Integer countMonth(Long userId);

    Integer countTotal(Long userId);
}