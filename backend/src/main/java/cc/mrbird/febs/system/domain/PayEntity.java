package cc.mrbird.febs.system.domain;


import cc.mrbird.febs.common.utils.MD5Util;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Data
public class PayEntity {


    private String pay_memberid = "10911";
    private String pay_orderid;
    private String pay_applydate ;
    private String pay_bankcode;
    private String pay_notifyurl;
    private String pay_callbackurl;
    private String pay_amount;
    private String pay_md5sign;
    private String pay_attach;
    private String pay_productname;
    private String pay_productnum= "1";
    private String pay_productdesc;

}
