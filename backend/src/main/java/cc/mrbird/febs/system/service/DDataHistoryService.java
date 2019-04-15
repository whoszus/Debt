package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.DData;
import cc.mrbird.febs.system.domain.DDataHistory;

import java.util.List;
import java.util.Map;

public interface DDataHistoryService extends IService<DDataHistory> {

    Map<String,Integer> statistics(Long userid );
}
