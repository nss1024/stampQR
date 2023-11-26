package com.qrstamp.core.generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
@Service
@Setter
public class CreateQR {

    String textToEncode;
    String ECCLvl;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private QRSelector qrSelector;
    private QRRequestresponseObject qrRequestresponseObject;


    public QRRequestresponseObject getResponseData (){
        qrRequestresponseObject = new QRRequestresponseObject();

        int size = qrSelector.getQRCodeSizeForText(textToEncode,ECCLvl);
        ErrorCorrectionLevel eccLvl = getZXECCLevl(ECCLvl);
        try {
            createQRImage(textToEncode, size, eccLvl, qrRequestresponseObject);
        } catch (Exception e ){
            System.out.println("Failed to generate QR code");
            qrRequestresponseObject.setError("Failed to create QR code object!");
        }
        return qrRequestresponseObject;
    }



    private void createQRImage(String qrCodeText, int size, ErrorCorrectionLevel eccLvl,
                               QRRequestresponseObject qrrro)
            throws WriterException, IOException {
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, eccLvl);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth*40, matrixWidth*40);
        //System.out.println("Matrix width: "+matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            //System.out.println();
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                    //System.out.print("X");
                }else{
                    //System.out.print(" ");
                }
            }
        }

        String imgName =  generateQRImageFile(image,1);
        //set fields of response object
        qrrro.setTextToEncode(qrCodeText);
        qrrro.setImg(imgName);
        qrrro.setVersion(size);
        qrrro.setEncodedText(generateEncodedText(byteMatrix));
        qrrro.setError("OK");
    }

    //convert a text ECC level to a google zxing ecc level
    private ErrorCorrectionLevel getZXECCLevl(String txtECCLvl){

        switch(txtECCLvl){
            case "L": return ErrorCorrectionLevel.L;
            case "M": return ErrorCorrectionLevel.M;
            case "Q": return ErrorCorrectionLevel.Q;
            case "H": return ErrorCorrectionLevel.H;
            default:return null;
        }

    }
    //generate a String form a matrix by appending the rows with ";" as separator
    private String generateEncodedText(BitMatrix bm){
        BitArray ba = null;
        int size = bm.getHeight();// get the number of rows in the matrix
        StringBuilder sb = new StringBuilder();

        //**************************************************************//
        for (int i = 0; i < size; i++) {

            if(i!=0){sb.append("-");}//changed ; to -. ; used by servlet and is removed from url
            for (int j = 0; j < size; j++) {
                if (bm.get(i, j)) {
                    sb.append("1");
                }else{
                    sb.append("0");
                }
            }
        }

        //**************************************************************//
        return sb.toString();
    }

    private String generateQRImageFile(BufferedImage img, int userId ){
        String filePath = "C:\\StampQRFiles\\QRCodeImages\\";
        String imgFileExtension = "png";
        String imgFileName = Integer.toString(userId)+"_"+System.currentTimeMillis();
        String imgFilePath = "";
        String imgFileURL = imgFilePath+imgFileName+"."+imgFileExtension;

        try {
            BufferedImage bi = img;  // retrieve image
            File outputfile = new File(filePath+imgFileName +".png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Unable to generate image file!");
        }

        return imgFileName+".png";
    }

}
