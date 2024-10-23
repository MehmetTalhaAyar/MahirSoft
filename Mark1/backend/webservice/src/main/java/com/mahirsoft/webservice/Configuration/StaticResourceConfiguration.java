package com.mahirsoft.webservice.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration implements WebMvcConfigurer {


    private MahirsoftProperties mahirsoftProperties;

    

    public StaticResourceConfiguration(MahirsoftProperties mahirsoftProperties) {
        this.mahirsoftProperties = mahirsoftProperties;
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = Paths.get(mahirsoftProperties.getStorage().getRoot()).toAbsolutePath().toString() + "/";
        registry.addResourceHandler("/assets/**")
            .addResourceLocations("file:"+path)
            .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));

    
    
        // TODO Auto-generated method stub
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    

    
    
    @Bean
    CommandLineRunner createStorageDirections(){
        return args ->{
            createFolder(Paths.get(mahirsoftProperties.getStorage().getRoot()));
            createFolder(Paths.get(mahirsoftProperties.getStorage().getRoot(),mahirsoftProperties.getStorage().getProfile()));
            
        };
    }


    private void createFolder(Path path){
        File file = path.toFile();
        
        boolean isFolderExist = file.exists() && file.isDirectory();
        if(!isFolderExist){
            file.mkdir();
        }
    }
    
}
