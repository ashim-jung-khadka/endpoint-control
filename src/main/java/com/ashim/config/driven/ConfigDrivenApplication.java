package com.ashim.config.driven;

import com.ashim.config.driven.endpoint.handler.EndpointFilter;
import com.ashim.config.driven.endpoint.handler.EndpointProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;

@SpringBootApplication
@EnableConfigurationProperties(EndpointProperties.class)
public class ConfigDrivenApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigDrivenApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(EndpointFilter endpointFilter) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(endpointFilter);
        registration.setName(endpointFilter.getClass().getName());
        return registration;
    }
}

@Component
class CommandLineAppStartupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Autowired
    private EndpointProperties endpointProperties;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void run(String... args) {

        logger.info("endpoint-properties : {}", endpointProperties);
        logger.info("contextPath : {}", contextPath);

    }

}
