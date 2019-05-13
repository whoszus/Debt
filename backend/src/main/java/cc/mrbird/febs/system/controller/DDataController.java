package cc.mrbird.febs.system.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.DateTimeUtils;
import cc.mrbird.febs.common.utils.EmailUtil;
import cc.mrbird.febs.system.dao.DDataMapper;
import cc.mrbird.febs.system.domain.DData;
import cc.mrbird.febs.system.service.DDataService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import io.github.biezhi.ome.SendMailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Validated
@RestController
@RequestMapping("ddata")
public class DDataController extends BaseController {

    @Autowired
    private DDataService dDataService;

    @Autowired
    private DDataMapper dDataMapper;


    @GetMapping
    @Log("查询数据列表")
    public Map<String, Object> ddataList(QueryRequest queryRequest, DData dData, Integer userId) {
        return super.selectByPageNumSize(queryRequest, () -> this.dDataService.findDData(queryRequest, dData, userId));
    }


    @PostMapping("/create")
    public void createData(@Valid DData dData) {
        this.dDataService.createDData(dData);
    }


    @RequestMapping("/vip/{mobile}/{clientName}/{creditCount}/{clientIdNum}")
    public FebsResponse vipData(@PathVariable(value = "mobile") String mobile, @PathVariable(value = "clientName") String clientName,
                                @PathVariable(value = "creditCount") String creditCount, @PathVariable(value = "clientIdNum") String clientIdNum) {

        DData dData = new DData();
        dData.setClientPhone(mobile);
        DData dData1 = dDataMapper.selectOne(dData);
        if (dData1 != null) {
            return new FebsResponse().data(dData1);
        }
        dData.setAmount((float) calculateMoney(Integer.valueOf(creditCount)));
        dData.setClientName(clientName);
        dData.setDataSource("VIP");
        dData.setClientIdNum(clientIdNum);
        this.dDataService.save(dData);
        String content = "收到新的Vip 用户申请信息," + "用户姓名:" + clientName + ",手机号码:" + mobile + ",系统额度" + dData.getAmount();
        try {
            EmailUtil.sendEmail("银通商企收到VIP用户 " + clientName + " 申请信息,请及时处理!", "银通 Vip 通知", "576465249@qq.com", content);
        } catch (MessagingException | SendMailException | GeneralSecurityException e) {
            log.error("发送邮件失败",e.getMessage());
        }
        return new FebsResponse().data(dData);
    }

    private static int calculateMoney(int origin) {
        int max = 50000;
        int min = 10000;
        Random random = new Random();

        int plusInt = random.nextInt(max) % (max - min + 1) + min;
        if (origin < 500) return plusInt / 1000 * 1000;
        if (origin > 600) return 20000 + plusInt / 1000 * 1000;
        return 10000 + plusInt / 1000 * 1000;
    }

    @PostMapping("/update")
    public FebsResponse updateData(@Valid DData dData) throws FebsException {
        String des = dData.getDescribeAdd();
        if (StringUtils.isNotEmpty(des)) {
            dData.setDescribe(dData.getDescribe() + des + "  ---" + DateTimeUtils.convertDateToStringByFormat(new Date()) + "\n\r");
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
