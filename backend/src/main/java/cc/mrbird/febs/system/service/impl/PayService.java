package cc.mrbird.febs.system.service.impl;


import cc.mrbird.febs.common.domain.FebsResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.trans.SymbolicName;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Slf4j
//@Service
public class PayService {

    public static  FebsResponse generateParams(String money) throws NoSuchAlgorithmException {
        String AuthorizationURL = "http://pays.kooo8.com/Pay_Index.html";
        String keyValue = "t4ig5acnpx4fet4zapshjacjd9o4bhbi";
        String pay_bankcode = "907";

        String pay_memberid = "10002";//商户id
        String pay_orderid = generateOrderId();//20位订单号 时间戳+6位随机字符串组成
        String pay_applydate = generateTime();//yyyy-MM-dd HH:mm:ss
        String pay_notifyurl = "http://39.108.233.243/payUrl/notify";//通知地址
        String pay_callbackurl = "http://39.108.233.243/payUrl/payCallbackUrl";//回调地址
        String pay_amount = money;
        String pay_attach = "";
        String pay_productname = "测试支付";
        String pay_productnum = "";
        String pay_productdesc = "";
        String pay_producturl = "";
        String stringSignTemp = "pay_amount=" + pay_amount + "&pay_applydate=" + pay_applydate + "&pay_bankcode=" + pay_bankcode + "&pay_callbackurl=" + pay_callbackurl + "&pay_memberid=" + pay_memberid + "&pay_notifyurl=" + pay_notifyurl + "&pay_orderid=" + pay_orderid + "&key=" + keyValue + "";
        String pay_md5sign = md5(stringSignTemp);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("pay_memberid", pay_memberid);
        map.add("pay_orderid", pay_orderid);
        map.add("pay_applydate", pay_applydate);
        map.add("pay_bankcode", pay_bankcode);
        map.add("pay_notifyurl", pay_notifyurl);
        map.add("pay_callbackurl", pay_callbackurl);
        map.add("pay_amount", pay_amount);
        map.add("pay_reserved1", "");
        map.add("pay_reserved2", "");
        map.add("pay_reserved3", "");
        map.add("pay_productname", pay_productname);
        map.add("pay_productnum", pay_productnum);
        map.add("pay_productdesc", pay_productdesc);
        map.add("pay_producturl", pay_producturl);
        map.add("pay_md5sign", pay_md5sign);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(AuthorizationURL, request, String.class);

        log.info("调用支付接口返回参数:"+response.getBody());

        return new FebsResponse().data(response.getBody());
    }

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            //字符数组转换成字符串
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString().toUpperCase();
            // 16位的加密
            //return buf.toString().substring(8, 24).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateOrderId() {
        String keyup_prefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String keyup_append = String.valueOf(new Random().nextInt(899999) + 100000);
        String pay_orderid = keyup_prefix + keyup_append;//订单号
        return pay_orderid;
    }

    public static String generateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }


    public static void main(String[] args) {
        try {
            generateParams("400");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
