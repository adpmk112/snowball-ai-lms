package com.ace.ai.admin.mvcConfig;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfig implements WebMvcConfigurer{
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path teacherUploadDir =Paths.get("./assets/img");
        String teacherUploadPath = teacherUploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/assets/img/**").addResourceLocations("file:/"+ teacherUploadPath + "/");
    }
}
