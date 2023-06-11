package com.wounom.cloudpan.service;

import com.wounom.cloudpan.common.Result;
import org.apache.commons.mail.EmailException;

/**
 * @author Litind
 * @version 1.0
 * @data 2023/6/7 21:05
 */
public interface EmailService {
    Result sendEamil(String email, int i) throws EmailException;
}
