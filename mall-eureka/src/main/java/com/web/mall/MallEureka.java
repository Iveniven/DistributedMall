package com.web.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author iven
 * @version 2021.2.3
 * @date 2022/9/5 15:16
 */
@SpringBootApplication
@EnableEurekaServer
public class MallEureka {
    public static void main(String[] args) {
        SpringApplication.run(MallEureka.class,args);
    }
}
