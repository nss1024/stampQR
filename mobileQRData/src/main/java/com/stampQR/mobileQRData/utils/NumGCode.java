package com.stampQR.mobileQRData.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class NumGCode {
    private float xCoordCode = 100;
    private float yCoordCode = 200;
    private float zCoordCode = 300;
    private float G21 = 921;
    private float G90 = 990;

    private float[] numericGCode;


    public void generateNumericGCode(String gCodeArray[]){
        float result[] = new float[1000];

        for (int i=0;i<gCodeArray.length;i++){
                String s = gCodeArray[i];
                //System.out.println(s);
                switch (s.charAt(0)){
                    case'X':
                        result[i]=(float)1000+Float.valueOf(s.substring(1));
                        //System.out.println(result[i]);
                        break;
                    case'Y':result[i]=(float)2000+Float.valueOf(s.substring(1));
                        //System.out.println(result[i]);
                        break;
                    case'Z':result[i]=(float)3000+ Math.abs(Float.valueOf(s.substring(1)));
                        //System.out.println(result[i]);
                        break;
                    case'G':
                        if(s.equals("G21")){
                        result[i]=(float)921;
                        //System.out.println(result[i]);
                        break;} else if (s.equals("G90")) {
                            result[i]=(float)990;
                            //System.out.println(result[i]);
                        }
                }

        }

        numericGCode = result;
    }

    public float[] getNumbericGCode(){
        return numericGCode;
    }

}
