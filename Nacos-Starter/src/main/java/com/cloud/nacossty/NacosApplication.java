package com.cloud.nacossty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author: miao
 * @date 2022/3/7
 */
@SpringBootApplication
public class NacosApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(NacosApplication.class, args);
        String nameStr = application.getEnvironment().getProperty("user.name");
        String ageStr = application.getEnvironment().getProperty("user.age");
        System.out.println(nameStr + "---------" + ageStr);
    }
}
