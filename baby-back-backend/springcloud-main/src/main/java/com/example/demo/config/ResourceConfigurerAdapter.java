package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class ResourceConfigurerAdapter extends WebMvcConfigurerAdapter {

    String path = "";

    /**
     * 配置静态访问资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        File directory = new File(".");
        path = directory.getAbsolutePath();

        registry.addResourceHandler("/resource/**").addResourceLocations("file:" + path);
        super.addResourceHandlers(registry);
    }
}
