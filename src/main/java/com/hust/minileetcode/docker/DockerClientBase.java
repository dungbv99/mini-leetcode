package com.hust.minileetcode.docker;

import com.hust.minileetcode.utils.ComputerLanguage.Languages;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ExecCreation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.spotify.docker.client.DockerClient.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
@Configuration
public class DockerClientBase {
    @Value("${DOCKER_SERVER_HOST}")
    private String DOCKER_SERVER_HOST;

    private static DockerClient dockerClient;

    private static HashMap<String , String> m = new HashMap<>();


    public DockerClientBase() {
    }

    @Bean
    public void initDockerClientBase(){
        dockerClient = DefaultDockerClient.builder()
                .uri(URI.create(DOCKER_SERVER_HOST))
                .connectionPoolSize(100)
                .build();
        try {
            System.out.println("ping " + dockerClient.ping());
//            createGccContainer();
            containerExist();
        } catch (DockerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void containerExist() throws DockerException, InterruptedException {
        List<Container> listContainerStart = dockerClient.listContainers(
                ListContainersParam.withStatusRunning(),
                ListContainersParam.filter("label", "names=leetcode")
        );
        for(Container container : listContainerStart){
            m.put(container.names().get(0), container.id());
        }
        List<Container> listContainersStop = dockerClient.listContainers(
                ListContainersParam.withStatusExited(),
                ListContainersParam.filter("label", "names=leetcode"));
        for(Container container : listContainersStop){
            m.put(container.names().get(0), container.id());
            dockerClient.startContainer(container.id());
        }
        System.out.println(m.toString());

    }


    public String createGccContainer() throws DockerException, InterruptedException {
        Map<String,String> m = new HashMap<String,String>();
        m.put("names", "leetcode");
        ContainerConfig gccContainerConfig = ContainerConfig.builder()
                .image("gcc:8.5-buster")
                .workingDir("/workdir")
                .hostname("test1")
                .cmd("sh", "-c", "while :; do sleep 1; done")
                .labels(m)
                .attachStdout(true)
                .attachStdin(true)
                .build();
        ContainerCreation gccCreation = dockerClient.createContainer(gccContainerConfig, "gcc");
        dockerClient.startContainer(gccCreation.id());
        return gccCreation.id();
    }

    public DockerClient getDockerClient(){
        return dockerClient;
    }

    public String runExecutable(Languages languages, String dirName) throws DockerException, InterruptedException, IOException {
//        String[] mkdirCommand = {"mkdir", "-p", dirName};
        String[] runCommand = {"bash", dirName+".sh"};
//        String[] rmCommand= {"rm","-rf", dirName};
        String containerId = "";
        switch (languages){
            case CPP:
                containerId = m.get("/gcc");
                break;
            default:
                System.out.println("language err");
                return "err";
        }
//        ExecCreation mkdirExecCreation = dockerClient.execCreate(
//                containerId, mkdirCommand);
//        dockerClient.execStart(mkdirExecCreation.id()).close();
        dockerClient.copyToContainer(new java.io.File("./temp_dir/"+dirName).toPath(), containerId, "/workdir/");
        ExecCreation runExecCreation = dockerClient.execCreate(
                containerId, runCommand, DockerClient.ExecCreateParam.attachStdout(),
                DockerClient.ExecCreateParam.attachStderr());
        LogStream output = dockerClient.execStart(runExecCreation.id());
        String execOutput = output.readFully();
//        ExecCreation rmdirExecCreation = dockerClient.execCreate(containerId, rmCommand);
//        dockerClient.execStart(rmdirExecCreation.id()).close();
        return execOutput;
    }


}
