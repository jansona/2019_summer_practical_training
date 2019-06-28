package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ResourceConfigurerAdapter extends WebMvcConfigurerAdapter {

    final String path = "file:D:/jupyter_workplace/2019_summer_practical_training/BabyBack/";

    /**
     * 配置静态访问资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations(path);
        super.addResourceHandlers(registry);
    }
}
