package com.wounom.cloudpan.service.serviceImp;

import com.wounom.cloudpan.common.Result;
import com.wounom.cloudpan.entity.Email;
import com.wounom.cloudpan.mapper.EmailMapper;
import com.wounom.cloudpan.service.EmailService;
import com.wounom.cloudpan.utils.EmailUtil;
import jakarta.annotation.Resource;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/7 21:06
 */
@Service
public class EmailServiceImp implements EmailService {
    @Resource
    private EmailMapper emailMapper;
    @Resource
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.mail.password}")
    private String mailpassword;
    @Value("${spring.mail.host}")
    private String mailhost;
    @Value("${spring.mail.default-encoding}")
    private String mailencode;
    @Override
    public Result sendEamil(String email, int i){
        try {
            //查询数据库中是否已有该数据
            Email emailCompare = emailMapper.findCode(email);
            String code = EmailUtil.CreateCode();
            sendEmail(email,code,i);//发送邮件
            //将验证码存入数据库
            LocalDateTime activeTime = LocalDateTime.now().plusMinutes(10);//验证码有效时间为10分钟
            Email emailnew = new Email();
            emailnew.setEmail(email);
            emailnew.setCode(code);
            emailnew.setActiveTime(activeTime);
            if (emailCompare==null){
                emailMapper.insertCode(emailnew);
            }else {
                emailMapper.updateCode(emailnew);
            }
            return Result.success();
        } catch (EmailException e) {
            e.printStackTrace();
            return Result.error(444,"发送异常");
        }
    }
    public void sendEmail(String email,String code,int way) throws EmailException {
        HtmlEmail sendEmail = new HtmlEmail();
        sendEmail.setHostName(mailhost);
        sendEmail.setCharset(mailencode);
        sendEmail.addTo(email);//注入需要发送的邮箱
        sendEmail.setFrom(mailUsername,"WOUNOM-CloudPan");
        sendEmail.setAuthentication(mailUsername,mailpassword);
        sendEmail.setSubject("WOUNOM-CloudPan");
        Date date = new Date();
        sendEmail.setSentDate(date);
        Context context = new Context();
        context.setVariable("email",email);
        context.setVariable("code",code);
        String text;
        if (way == 1){//发送注册邮件
            text = templateEngine.process("regist.html",context);
        }else {
            text = templateEngine.process("resetpw.html",context);
        }
        sendEmail.setContent(text,"text/html");
        sendEmail.send();
    }
}
