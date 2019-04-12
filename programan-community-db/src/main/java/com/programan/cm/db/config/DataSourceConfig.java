package com.programan.cm.db.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableAutoConfiguration
public class DataSourceConfig {

    @Primary
    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

}
