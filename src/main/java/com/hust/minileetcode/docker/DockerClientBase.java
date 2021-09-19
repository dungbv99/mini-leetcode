package com.hust.minileetcode.docker;

import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class DockerClient {

    private static int t = 0;

    public DockerClient() {
    }

    @Bean
    public void init(){
        this.t = 0;
    }

    public void println(){
        System.out.println("t: "+t);
        t++;
    }
}
