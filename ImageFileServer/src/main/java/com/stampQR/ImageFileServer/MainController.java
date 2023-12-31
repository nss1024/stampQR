package com.stampQR.ImageFileServer;
//10.0.1.20:8070
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



@Controller
public class MainController {
    String img = "";
    RestTemplate restTemplate = new RestTemplate();
    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("imageName") String imageName) throws IOException {
        if(!imageName.contains(".png")){
            img=imageName+".png";
        }else{img=imageName;}

        String imageUrl = "http://10.0.1.20:8070/images/"+img;
        ImageWrapper imageWrapper = restTemplate.getForObject(imageUrl, ImageWrapper.class);
        return imageWrapper.getImageByteArray();
    }

}
