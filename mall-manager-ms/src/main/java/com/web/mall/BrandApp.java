package com.web.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/5 17:00
 */
@SpringBootApplication
@MapperScan("com.web.mall.mapper")
@EnableEurekaClient
public class BrandApp {
    public static void main(String[] args) {
        SpringApplication.run(BrandApp.class, args);
    }
}
