package com.hust.minileetcode.docker;

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

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
@Configuration
public class DockerClientBase {

    private static DockerClient dockerClient;
    private String gccId;
    private String gccName = "gcc";
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
            containerExist();
        } catch (DockerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void containerExist() throws DockerException, InterruptedException {
        List<Container> listContainers = dockerClient.listContainers(
                DockerClient.ListContainersParam.withStatusExited(),
                DockerClient.ListContainersParam.filter("label", "names=leetcode"));
        for(Container container : listContainers){
            dockerClient.startContainer(container.id());
        }
    }


//    public String createGccContainer() throws DockerException, InterruptedException {
//        Map<String,String> m = new HashMap<String,String>();
//        m.put("names", "leetcode");
//        ContainerConfig gccContainerConfig = ContainerConfig.builder()
//                .image("gcc:8.5-buster")
//                .workingDir("/workdir")
//                .hostname("test1")
//                .cmd("sh", "-c", "while :; do sleep 1; done")
//                .labels(m)
//                .attachStdout(true)
//                .attachStdin(true)
//                .build();
//        ContainerCreation gccCreation = dockerClient.createContainer(gccContainerConfig, gccName);
//        dockerClient.startContainer(gccCreation.id());
//        return gccCreation.id();
//    }

    public DockerClient getDockerClient(){
        return dockerClient;
    }

    public String runExecutable(String path, String containerId, String dirName, String fileName) throws DockerException, InterruptedException, IOException {
        String[] mkdirCommand = {"mkdir", "-p", dirName};
        String[] runCommand = {"bash", fileName};
        dockerClient.execCreate(containerId, mkdirCommand, DockerClient.ExecCreateParam.attachStdout());
        dockerClient.copyToContainer(new File(path).toPath(), containerId, "/workdir/"+dirName);
        ExecCreation execCreation = dockerClient.execCreate(
                containerId, runCommand, DockerClient.ExecCreateParam.attachStdout(),
                DockerClient.ExecCreateParam.attachStderr());
        LogStream output = dockerClient.execStart(execCreation.id());
        String execOutput = output.readFully();
        return execOutput;
    }


}
