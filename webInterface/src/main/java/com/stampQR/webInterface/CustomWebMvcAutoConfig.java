package com.stampQR.webInterface;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CustomWebMvcAutoConfig implements WebMvcConfigurer {

    String myExternalFilePath = "file:C:\\Users\\nsimo\\Documents\\StampQRFiles\\"; // end your path with a /


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/QRCodeImages/**").addResourceLocations(myExternalFilePath);
    }

}
