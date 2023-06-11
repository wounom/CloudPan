package com.wounom.cloudpan.controller;

import com.wounom.cloudpan.common.Result;
import com.wounom.cloudpan.service.EmailService;
import jakarta.annotation.Resource;
import org.apache.commons.mail.EmailException;
import org.springframework.web.bind.annotation.*;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/7 19:01
 */
@RestController
@RequestMapping("/email")
@CrossOrigin
public class EmailController {
    @Resource
    private EmailService emailService;

    @PostMapping("/sendRegist")
    public Result<?> SendRegist(@RequestParam(value = "email") String email) throws EmailException {
        return emailService.sendEamil(email,1);
    }

    @PostMapping("/sendReset")
    public Result<?> SendReset(@RequestParam(value = "email") String email) throws EmailException {
        return emailService.sendEamil(email,0);
    }
}
