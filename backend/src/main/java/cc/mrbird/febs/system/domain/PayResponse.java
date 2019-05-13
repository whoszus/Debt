package cc.mrbird.febs.system.domain;


import lombok.Data;

@Data
public class PayResponse {
    private String memberid;
    private String orderid;
    private String amount;
    private String transaction_id;
    private String datetime;
    private String returncode;
    private String attach;
    private String sign;


}
