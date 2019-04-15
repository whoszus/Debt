package cc.mrbird.febs.system.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.DateTimeUtils;
import cc.mrbird.febs.system.domain.DData;
import cc.mrbird.febs.system.service.DDataService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
@Validated
@RestController
@RequestMapping("ddata")
public class DDataController extends BaseController {

    @Autowired
    private DDataService dDataService;


    @GetMapping
    @Log("查询数据列表")
    public Map<String, Object> ddataList(QueryRequest queryRequest, DData dData, Integer userId) {
        return super.selectByPageNumSize(queryRequest, () -> this.dDataService.findDData(queryRequest, dData, userId));
    }


    @PostMapping("/create")
    public void createData(@Valid DData dData) {
        this.dDataService.createDData(dData);
    }

    @PostMapping("/update")
    public FebsResponse updateData(@Valid DData dData) throws FebsException {
        String des = dData.getDescribeAdd();
        if (StringUtils.isNotEmpty(des)){
            dData.setDescribe(dData.getDescribe()+des+ "  ---" + DateTimeUtils.convertDateToStringByFormat(new Date())+"\n\r");
        }
        if ("finish".equals(dData.getDataStatus())) {
            if (dData.getAmount() == null || dData.getAmount() < 0.1) {
                return new FebsResponse().message("放款金额不能小于1000元");
            }
            try {
                this.dDataService.finishData(dData);
            } catch (Exception e) {
                log.error("数据转化异常", e);
                throw new FebsException("数据归档异常");
            }
            return new FebsResponse().message("数据已归档");
        }
        this.dDataService.updateDData(dData);
        return new FebsResponse().message("成功");
    }

    /**
     * 分配数据
     *
     * @param dataIds
     * @param userId
     * @return
     */
    @GetMapping("/distribute")
    public FebsResponse distributeData(@Valid String dataIds, @Valid Integer userId) {
        String[] dataArray = dataIds.split(",");
        Integer[] dataIdArray = new Integer[dataArray.length];
        for (int i = 0, length = dataArray.length; i < length; i++) {
            dataIdArray[i] = Integer.valueOf(dataArray[i]);
        }
        this.dDataService.distributeData(dataIdArray, userId);
        return new FebsResponse().message("分配成功");
    }

    @GetMapping("/delete/{dataId}")
    @RequiresPermissions("data:delete")
    public FebsResponse deleteData(@PathVariable Integer dataId) {
        this.dDataService.deleteDData(dataId);
        return new FebsResponse().message("已删除数据编号" + dataId);
    }


    @GetMapping("/statistics")
    public FebsResponse statistics() {
        return new FebsResponse().data("");
    }


    /**
     * 生成 Excel导入模板
     */
    @PostMapping("template")
    public void generateImportTemplate(HttpServletResponse response) {
        // 构建数据
        List<DData> list = new ArrayList<>();
        IntStream.range(0, 2).forEach(i -> {
            DData dData = new DData();
            dData.setDataSource("社保");
            dData.setClientName("大款土豪哥");
            dData.setClientPhone("17302007890");
            dData.setClientIdNum("449887676623423884394");
            dData.setAmount(19.8f);
            list.add(dData);
        });
        // 构建模板
        ExcelKit.$Export(DData.class, response).downXlsx(list, true);
    }


    /**
     * 导入Excel数据，并批量插入
     */
    @PostMapping("import")
    public FebsResponse importExcels(@RequestParam("file") MultipartFile file) throws FebsException {
        try {
            if (file.isEmpty()) {
                throw new FebsException("导入数据为空");
            }
            String filename = file.getOriginalFilename();
            if (!StringUtils.endsWith(filename, ".xlsx")) {
                throw new FebsException("只支持.xlsx类型文件导入");
            }
            // 开始导入操作
            long beginTimeMillis = System.currentTimeMillis();
            final List<DData> dataList = Lists.newArrayList();
            final List<Map<String, Object>> error = Lists.newArrayList();
            ExcelKit.$Import(DData.class).readXlsx(file.getInputStream(), new ExcelReadHandler<DData>() {
                @Override
                public void onSuccess(int sheet, int row, DData dData) {
                    // 数据校验成功时，加入集合
                    dData.setCreateTime(new Date());
                    dData.setDataStatus("init");
                    dataList.add(dData);
                }

                @Override
                public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
                    // 数据校验失败时，记录到 error集合
                    error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
                }
            });
            if (!dataList.isEmpty()) {
                // 将合法的记录批量入库
                this.dDataService.batchInsert(dataList);
            }
            long time = ((System.currentTimeMillis() - beginTimeMillis));
            ImmutableMap<String, Object> result = ImmutableMap.of(
                    "time", time,
                    "data", dataList,
                    "error", error
            );
            return new FebsResponse().data(result);
        } catch (Exception e) {
            String message = "导入Excel数据失败," + e.getMessage();
            log.error(message);
            throw new FebsException(message);
        }
    }


}
