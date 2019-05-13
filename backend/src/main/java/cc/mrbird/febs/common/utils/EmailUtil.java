package cc.mrbird.febs.common.utils;

import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.Properties;


@Component
public class EmailUtil {

    @PostConstruct
    private static void before() throws GeneralSecurityException {
        // 配置，一次即可
        OhMyEmail.config(SMTP_163_SSL(), "tinkerkl@163.com", "tinker1122");
    }

    public static void sendEmail(String subject,String fromMessage ,String destinationEmail, String content) throws MessagingException, SendMailException, GeneralSecurityException {
        before();
        OhMyEmail.subject(subject)
                .from(fromMessage)
                .to(destinationEmail)
                .text(content)
                .send();
    }


    /**
     * smtp 163
     *

     * @return
     */
    private static Properties SMTP_163_SSL() {
        Properties props = OhMyEmail.defaultConfig(false);
        props.put("mail.smtp.host", "smtp.163.com");
        props.put("mail.smtp.ssl.trust", "*") ;// Change host to "*" if you want to trust all host.
        return props;
    }

}
