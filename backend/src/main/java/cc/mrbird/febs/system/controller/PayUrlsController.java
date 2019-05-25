package cc.mrbird.febs.system.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
@RestController
@RequestMapping("payUrl")
public class PayUrlsController {



    @RequestMapping("/payCallbackUrl")
    public void payCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String memberid=request.getParameter("memberid");
        String orderid=request.getParameter("orderid");
        String amount=request.getParameter("amount");
        String datetime=request.getParameter("datetime");
        String returncode=request.getParameter("returncode");
        String transaction_id=request.getParameter("transaction_id");
        String attach=request.getParameter("attach");
        String sign=request.getParameter("sign");
//        String SignTemp="amount="+amount+"+datetime="+datetime+"+memberid="+memberid+"+orderid="+orderid+"+returncode="+returncode+"+transaction_id="+transaction_id+"+key="+keyValue+"";


        PrintWriter pw=response.getWriter();
        pw.write("ok");

//        String md5sign=MD5(SignTemp,32,1);//MD5加密
//        if (sign.equals(md5sign)){
//            if(returncode.equals("00")){
//                //支付成功，写返回数据逻辑
//                PrintWriter pw=response.getWriter();
//                pw.write("ok");
//            }else{
//                PrintWriter pw=response.getWriter();
//                pw.write("支付失败");
//            }
//        }else{
//            PrintWriter pw=response.getWriter();
//            pw.write("验签失败");
//        }
    }

}
