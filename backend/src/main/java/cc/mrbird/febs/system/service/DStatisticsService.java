package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.DStatistics;

import java.util.List;

public interface DStatisticsService extends IService<DStatistics> {
    List<DStatistics>  getMonthStatistics();
}
