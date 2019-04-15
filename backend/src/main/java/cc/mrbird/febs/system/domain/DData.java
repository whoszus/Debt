package cc.mrbird.febs.system.domain;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * d_data
 * @author tinker
 */
@Data
@ToString
@Table(name = "d_data")
@Excel("客户数据")
public class DData implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "ID")
    private Integer id;

    /**
     * 客户名
     */
    @Column(name = "CLIENT_NAME")
    @ExcelField(value = "客户姓名")
    private String clientName;

    /**
     * 客户身份证号
     */
    @Column(name = "CLIENT_ID_NUM")
    @ExcelField(value = "身份证号")
    private String clientIdNum;

    /**
     * 客户电话
     */
    @Column(name = "CLIENT_PHONE")
    @ExcelField(value = "客户电话")
    private String clientPhone;

    /**
     * 数据状态 'redist 重新配分','finish 放款','dist 已分配','init 待签约', 'refused 拒绝'
     */
    @Column(name = "DATA_STATUS")
//    @ExcelField(value = "数据状态")
    private String dataStatus;

    /**
     * 业务员id
     */
    @Column(name = "OPERATOR_ID")
//    @ExcelField(value = "业务员id")
    private Integer operatorId;

    /**
     * 业务员姓名
     */
    @Column(name = "OPERATOR_NAME")
//    @ExcelField(value = "业务员姓名")
    private String operatorName;

    /**
     * 数据源
     */
    @Column(name = "DATA_SOURCE")
    @ExcelField(value = "数据源")
    private String dataSource;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
//    @ExcelField(value = "创建时间")
    private Date createTime;

    /**
     * 最后操作时间
     */
    @Column(name = "LAST_OPERAT_TIME")
//    @ExcelField(value = "最后操作时间")
    private Date lastOperatTime;

    /**
     * 创建人
     */
    @Column(name = "CREATE_USER_ID")
//    @ExcelField(value = "创建人")
    private String createUserId;

    /**
     * 记录（备注）
     */
    @Column(name = "OPERATOR_DESCRIBE")
//    @ExcelField(value = "记录")
    private String describe;

    @Column(name = "AMOUNT")
    @ExcelField(value = "金额(万)")
    private Float amount;


    @Transient
    private String describeAdd;


    private static final long serialVersionUID = 1L;


}