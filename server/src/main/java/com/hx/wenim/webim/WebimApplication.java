package com.hx.wenim.webim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WebimApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebimApplication.class, args);
    }

}
