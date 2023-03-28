package com.stdu.zhpb.store;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.stdu.zhpb")
public class ServiceStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceStoreApplication.class, args);
    }
}
