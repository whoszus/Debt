package cc.mrbird.febs.system.domain;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
/**
*
*  @author author
*/
@Data
@ToString
@Table(name = "d_statistics")
@Excel("统计报表")
public class DStatistics implements Serializable {

    private static final long serialVersionUID = 1551016988586L;


    /**
    * 主键
    * 
    * isNullAble:0
    */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "ID")
    private Integer id;

    /**
    * 
    * isNullAble:1
    */
    @Column(name = "USER_ID")
    private Integer userId;

    /**
    * 总成交额
    * isNullAble:1
    */
    @Column(name = "TOTAL_AMOUNT")
    private Integer totalAmount;

    /**
    * 用户名
    * isNullAble:1
    */
    @Column(name = "USER_NAME")
    private String userName;

    /**
    * 订单数量
    * isNullAble:1
    */
    @Column(name = "ORDER_COUNT")
    private Integer orderCount;




}
