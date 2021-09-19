package com.hust.minileetcode.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class DockerClientBase {

    private static DockerClient dockerClient;

    @Value("${DOCKER_SERVER_HOST}")
    private String DOCKER_SERVER_HOST;

    public DockerClientBase() {
    }

    @Bean
    public void initDockerClientBase(){
        dockerClient = DefaultDockerClient.builder()
                .uri(URI.create("http://localhost:12375"))
                .connectionPoolSize(100)
                .build();
        try {
            System.out.println("ping " + dockerClient.ping());
        } catch (DockerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public DockerClient getDockerClient(){
        return dockerClient;
    }



}
