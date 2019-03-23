package com.programan.cm.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@ComponentScan(value = {"com.programan.*"})
public class StartMainApplication extends SpringBootServletInitializer implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(StartMainApplication.class);
    private static ConfigurableApplicationContext ctx;


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartMainApplication.class);
    }

    @Override
    public void destroy() {
        if (null != ctx && ctx.isRunning()) {
            ctx.close();
        }
    }

    public static void main(String[] args) {
        //   Nd4j.setDataType(DataBuffer.Type.DOUBLE);
        ctx = SpringApplication.run(StartMainApplication.class, args);
        logger.info("spring.profiles.active:");
        logger.info("Boot Server started.");
        logger.info("max memory:{} MB", Runtime.getRuntime().maxMemory() / 1024 / 1024);
        logger.info("total memory:{} MB", Runtime.getRuntime().totalMemory() / 1024 / 1024);
        logger.info("free memory:{} MB", Runtime.getRuntime().freeMemory() / 1024 / 1024);
        logger.info("used memory:{} MB", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
    }
}
