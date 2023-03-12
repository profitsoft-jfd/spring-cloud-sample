package com.springcloud.estates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EstatesApplication {

  public static void main(String[] args) {
    SpringApplication.run(EstatesApplication.class, args);
  }

}
