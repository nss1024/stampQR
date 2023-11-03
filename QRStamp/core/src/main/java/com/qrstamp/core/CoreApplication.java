package com.qrstamp.core;

import com.qrstamp.core.generator.CreateQR;
import com.qrstamp.core.generator.QRRequestresponseObject;
import jakarta.annotation.Resource;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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

	RestTemplate restTemplate = new RestTemplate();
	QRRequestresponseObject qrRequestresponseObject;


	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}


	@CrossOrigin
	@GetMapping("/qrdata/{textToEncode}/{errorCorrectionLevel}/{tag}/{userId}")
	public String getQRData(@PathVariable ("textToEncode") String textToEncode, @PathVariable("errorCorrectionLevel") String errorCorrectionLevel,
											 @PathVariable("tag") String tag, @PathVariable("userId") Long userId){

		createQR.setTextToEncode(textToEncode);
		createQR.setECCLvl(errorCorrectionLevel);
		qrRequestresponseObject = createQR.getResponseData();

		String uri ="http://localhost:8081/qrdata/save/"+qrRequestresponseObject.getTextToEncode()+"/"+qrRequestresponseObject.getEncodedText()+"/"+errorCorrectionLevel+"/"+
				tag+"/"+qrRequestresponseObject.getImg()+"/"+qrRequestresponseObject.getVersion()+"/"+userId+"/"+"true";

		System.out.println(uri);

		restTemplate.getForObject(uri, Objects.class);

		return "success";
	}



	@GetMapping( value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<ByteArrayResource> getQRImage() throws IOException {
		final ByteArrayResource bar = new ByteArrayResource(Files.readAllBytes(Paths.get("C:\\Users\\nsimo\\Documents\\QRCode\\JD.png")));

		createQR.setTextToEncode("a");
		createQR.setECCLvl("L");
		qrRequestresponseObject = createQR.getResponseData();

		System.out.println(qrRequestresponseObject.getEncodedText());
		System.out.println(qrRequestresponseObject.getVersion());


		return ResponseEntity
				.status(HttpStatus.OK)
				.contentLength(bar.contentLength())
				.body(bar);

	}

}
