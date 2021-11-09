package com.hust.minileetcode.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor(onConstructor_ = @Autowired)
@Data
public class Constants {
    private Map<String, Integer> MapLevelOrder = new HashMap<>();

    @Bean
    public void initConstants(){
        MapLevelOrder.put("easy", 1);
        MapLevelOrder.put("medium", 2);
        MapLevelOrder.put("hard", 3);
    }


}