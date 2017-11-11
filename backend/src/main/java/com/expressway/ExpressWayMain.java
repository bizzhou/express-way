package com.expressway;

import com.expressway.jwt.JwtAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpressWayMain {

    public static void main(String[] args) {
        SpringApplication.run(ExpressWayMain.class, args);
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthFilter filter = new JwtAuthFilter(
                "/api/**");
        registrationBean.setFilter(filter);
        return registrationBean;
    }

}
