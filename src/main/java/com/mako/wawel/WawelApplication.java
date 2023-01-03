package com.mako.wawel;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WawelApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(WawelApplication.class, args);
    }

}
