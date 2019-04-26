package com.programan.cm.web.security;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private DruidDataSource baseDataSource;

//    @Autowired
//    private CmUserDetailService cmUserDetailService;

    //身份验证管理生成器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //1.启用内存用户存储
//        auth.inMemoryAuthentication()
//                .withUser("wangqianfeng").password(passwordEncoder().encode("acm666666")).roles("USER");
//                .withUser("tom").password(passwordEncoder().encode("1234")).roles("USER");
        //2.基于数据库表进行验证
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username,password,enabled from user where username = ?")
//                .authoritiesByUsernameQuery("select username,rolename from role where username=?")
//                .passwordEncoder(passwordEncoder());
        //3.配置自定义的用户服务
        auth.userDetailsService(tcmUserDetailService()).passwordEncoder(passwordEncoder());
    }
    //HTTP请求安全处理
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login.html").usernameParameter("username").passwordParameter("password").loginProcessingUrl("/user/login").defaultSuccessUrl("/index").permitAll()
            .and()
            .logout().logoutUrl("/user/logout").logoutSuccessUrl("/index.html")
            .and()
            .authorizeRequests()
            .antMatchers("/gold.html", "/bootstrap/**","/css/**","/html/**","/img/**",
                    "/js/**","/layer/**","/page/**","/index/**","/index").permitAll()
            .antMatchers("/articleComment/**", "/articleLike/**", "/userFollow/**", "/userrole/**").authenticated()
//            .antMatchers("/manager.html").hasRole("ADMIN")
//            .antMatchers("/user/**").hasRole("USER")
//            .regexMatchers("/admin1/.*").access("hasRole('ADMIN') or hasRole('ADMIN1')")
//            .anyRequest().authenticated()
            .anyRequest().permitAll()
            .and()
//            .requiresChannel().antMatchers("/add").requiresSecure()//https://127.0.0.1:8443/add
//            .and()
            .rememberMe().rememberMeParameter("remeber").tokenValiditySeconds(2419200).tokenRepository(persistentTokenRepository()).alwaysRemember(true)
            .and()
            .csrf().disable();

    }
    //WEB安全
    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(baseDataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService tcmUserDetailService() {
        return new CmUserDetailService();
    }

}
