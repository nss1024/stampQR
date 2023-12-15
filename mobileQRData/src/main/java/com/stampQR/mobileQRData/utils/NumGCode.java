package com.stampQR.mobileQRData.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class NumGCode {

    private float[] numericGCode;


    public void generateNumericGCode(String gCodeArray[]){
        float result[] = new float[50000];
        int valueCounter = 1;

        for (int i=0;i<gCodeArray.length;i++){
                String s = gCodeArray[i];
                //System.out.println(s);
                switch (s.charAt(0)){
                    case'X':
                        valueCounter++;
                        result[i]=(float)1000+Float.valueOf(s.substring(1));
                        //System.out.println(result[i]);
                        break;
                    case'Y':
                        valueCounter++;
                        result[i]=(float)2000+Float.valueOf(s.substring(1));
                        //System.out.println(result[i]);
                        break;
                    case'Z':
                        valueCounter++;
                        result[i]=(float)3000+ Math.abs(Float.valueOf(s.substring(1)));
                        //System.out.println(result[i]);
                        break;
                    case'G':
                        valueCounter++;
                        if(s.equals("G21")){
                        result[i]=(float)921;
                        //System.out.println(result[i]);
                        break;} else if (s.equals("G90")) {
                            result[i]=(float)990;
                            //System.out.println(result[i]);
                        }
                }

        }
        float [] returnArray = new float[valueCounter];
        for (int i = 0; i < valueCounter; i++) {
            returnArray[i]=result[i];
        }

        numericGCode = returnArray;
    }

    public float[] getNumbericGCode(){
        return numericGCode;
    }

}
