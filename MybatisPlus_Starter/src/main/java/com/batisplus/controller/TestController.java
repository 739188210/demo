package com.batisplus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: miao
 * @date 2022/3/23
 */
@RestController
@RequestMapping("/druid")
public class TestController {

    @RequestMapping("/getDruid")
    public Object getDuridInfo() {
        return null;
    }
}
