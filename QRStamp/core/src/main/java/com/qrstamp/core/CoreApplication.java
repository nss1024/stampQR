package com.qrstamp.core;
//10.0.1.126:8081
import com.qrstamp.core.generator.*;
import jakarta.annotation.Resource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
public class CoreApplication {

	@Autowired
	CreateQR createQR;

	@Autowired
	CreateGcode createGcode;

	RestTemplate restTemplate = new RestTemplate();
	QRRequestresponseObject qrRequestresponseObject;


	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}


	@CrossOrigin
	@GetMapping("/qrdata/{textToEncode}/{errorCorrectionLevel}/{tag}/{userId}")
	public ResponseEntity getQRData(@PathVariable ("textToEncode") String textToEncode, @PathVariable("errorCorrectionLevel") String errorCorrectionLevel,
											 @PathVariable("tag") String tag, @PathVariable("userId") Long userId){
		try {
			createQR.setTextToEncode(textToEncode);
			createQR.setECCLvl(errorCorrectionLevel);
			qrRequestresponseObject = createQR.getResponseData();
			qrRequestresponseObject.setEncodedText(createGcode.generateGCode(qrRequestresponseObject.getEncodedText(), 1.0, 2.0));

			String uri = "http://10.0.1.126:8081/qrdata/save/" + qrRequestresponseObject.getTextToEncode() + "/" + qrRequestresponseObject.getEncodedText() + "/" + errorCorrectionLevel + "/" +
					tag + "/" + qrRequestresponseObject.getImg() + "/" + qrRequestresponseObject.getVersion() + "/" + userId + "/" + "true";

			System.out.println(uri);

			restTemplate.getForObject(uri, Objects.class);
			return new ResponseEntity(HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/qrdata/produceQrCode/{textToEncode}/{errorCorrectionLevel}/{tag}/{userId}")
	public ResponseEntity produceQRData(@PathVariable ("textToEncode") String textToEncode, @PathVariable("errorCorrectionLevel") String errorCorrectionLevel,
							@PathVariable("tag") String tag, @PathVariable("userId") Long userId){

		StampConfig stampConfig=null;
		try {
			String stampConfigURL = "http://10.0.1.126:8081/stampConfig/getStampConfig/" + 1;
			stampConfig = restTemplate.getForObject(stampConfigURL, StampConfig.class);
		}catch(Exception e){
			System.out.println("Error getting stamp configuration!");
		}
		if(Objects.isNull(stampConfig)){
			stampConfig = new StampConfig();
			stampConfig.setConfigName("Default");
			stampConfig.setId(1L);
			stampConfig.setStampHeight(4.0);
			stampConfig.setStampWidth(21.0);
			stampConfig.setStampFrameWidth(0.5);
			stampConfig.setPixelDistance(1.0);
			stampConfig.setPixelSize(1.0);

		}

		try {
			createQR.setTextToEncode(textToEncode);
			createQR.setECCLvl(errorCorrectionLevel);
			qrRequestresponseObject = createQR.getResponseData();
			qrRequestresponseObject.setEncodedText(createGcode.generateGCode(qrRequestresponseObject.getEncodedText(), stampConfig.getPixelDistance(), stampConfig.getStampHeight()));
			qrRequestresponseObject.setError(errorCorrectionLevel);
			String uri = "http://10.0.1.126:8081/qrdata/save2/" + tag + "/" + userId + "/" + true;
			restTemplate.postForEntity(uri, qrRequestresponseObject, Object.class);
			return new ResponseEntity(HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}



	@GetMapping(value = "/images/{imageName}")
	public ImageWrapper getImage(@PathVariable("imageName") String imageName) throws IOException {
		File file = ResourceUtils.getFile("C:\\StampQRFiles\\QRCodeImages\\"+imageName);
		InputStream in = new FileInputStream(file);
		ImageWrapper imageWrapper = new ImageWrapper();
		imageWrapper.setImageByteArray(IOUtils.toByteArray(in));
		return imageWrapper;
	}

}
