package com.stdu.zhpb.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.stdu")
@ComponentScan(basePackages = "com.stdu")
public class ServiceEmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEmployeeApplication.class, args);
    }
}
