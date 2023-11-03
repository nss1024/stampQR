package com.stampQR.ImageFileServer;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



@Controller
public class MainController {

    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("imageName") String imageName) throws IOException {
        File file = ResourceUtils.getFile("C:\\StampQRFiles\\QRCodeImages\\"+imageName);
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);
    }

}
