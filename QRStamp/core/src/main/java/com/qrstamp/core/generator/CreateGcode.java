package com.qrstamp.core.generator;

import org.springframework.stereotype.Service;

@Service
public class CreateGcode {

    StringBuilder sb = new StringBuilder();

    /**
     *
     * @param rawCode - a string representing the QR Code as 0s and 1s
     * @param step - the length in millimeters the cnc has to move to get from the location of 1
     *             point of the QR code (1 pixel) to the next.
     * @param depth - distance the Z axis should move, how much should a pixel rod be pushed
     * @return - returns the generated G Code
     */
    public String generateGCode(String rawCode, double step, double depth){
        String [] rawCodeArray = rawCode.split("-");
        sb.setLength(0);
        sb.append("G21 \n");//use millimeters
        sb.append("G90 \n");//use relative positioning - all XYZ positions provided will be relative to a 0,0 home coordinate

        for (int i = 0; i<rawCodeArray.length;i++){
            String leadingZeroY = i<10? "0":"";
            String s = rawCodeArray[i];
            //if a row of input is only 0s, there is no X coordinate movement, move the Y axis and skip the row.
            if(!s.contains("1")){
                sb.append("G00 Y"+step+"\n");
            }else{
                //process 1 line of text
                for(int j=0;j<s.length();j++){
                    //figure out if we need a leading "0" after the G, if numbers are less than 10, put a leading "0" (=G09),
                    // if not, use a blank string so we don't get G010
                    String leadingZeroX = j<10? "0":"";
                    if(s.charAt(j)=='1'){
                        sb.append("G00 Y"+leadingZeroY+i*step+"\n");//generate X coordinate movement
                        sb.append("G00 X"+leadingZeroX+j*step+"\n");//generate Y coordinate movement
                        sb.append("G00 Z-"+depth+"\n");//generate Z coordinate movement
                        sb.append("G00 Z00"+"\n");//return Z coordinate to 0 before moving on
                    }
                }
            }

        }

        return sb.toString();

    }

}
