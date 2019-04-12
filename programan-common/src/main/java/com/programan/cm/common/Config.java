package com.programan.cm.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
@ConfigurationProperties(prefix = "programan.workspace")
@Data
@NoArgsConstructor
public class Config {

    private String path;

    @PostConstruct
    public void init() {
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
    }

}
