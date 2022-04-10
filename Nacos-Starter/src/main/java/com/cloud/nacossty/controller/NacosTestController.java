package com.cloud.nacossty.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: miao
 * @date 2022/4/7
 */
@RefreshScope
@RestController
public class NacosTestController {
    @Value("${user.name}")
    private String userName;

    @Value("${user.age}")
    private int userAge;

}
