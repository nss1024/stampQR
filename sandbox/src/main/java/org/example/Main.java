package org.example;



public class Main {

    static String rawCode = "00000000000000000000000000000-00000000000000000000000000000-00000000000000000000000000000-00000000000000000000000000000-00001111111011111011111110000-00001000001010011010000010000-00001011101010001010111010000-00001011101011110010111010000-00001011101011101010111010000-00001000001000110010000010000-00001111111010101011111110000-00000000000010110000000000000-00000101011010110110111110000-00000101100101100101000110000-00001110001001011010101100000-00001010010011010010100110000-00001111101001111100011110000-00000000000010010011001100000-00001111111000101000101010000-00001000001010010001100000000-00001011101001000101010000000-00001011101011010111111100000-00001011101000110001010100000-00001000001010101011000010000-00001111111001010101100000000-00000000000000000000000000000-00000000000000000000000000000-00000000000000000000000000000-00000000000000000000000000000";

    public static void main(String[] args) {
        NumGCode numGCode = new NumGCode();
        float result[] = new float[1000];
        StringBuilder sb = new StringBuilder();

        String [] rawCodeArray = rawCode.split("-");

        sb.append("G21");//use mm
        sb.append(";");
        sb.append("G90");//use absolute positioning
        sb.append(";");
        for (int i = 0; i<rawCodeArray.length;i++){
                String leadingZeroY = i<10? "0":"";
                String s = rawCodeArray[i];
                if(!s.contains("1")){
                    sb.append("Y1");
                    sb.append(";");
                }else{
                    //process 1 line of text
                    for(int j=0;j<s.length();j++){
                        String leadingZeroX = j<10? "0":"";
                        if(s.charAt(j)=='1'){
                            sb.append("Y"+i);
                            sb.append(";");
                            sb.append("X"+j);
                            sb.append(";");
                            sb.append("Z-2");
                            sb.append(";");
                            sb.append("Z0");
                            sb.append(";");
                        }
                    }
                }

        }

        //System.out.println(sb.toString());

        result = numGCode.generateNumericGCode(sb.toString().split(";"));
        /*
        if(result[0]!=0) {
            for (float f : result) {
                System.out.println(f);
            }
        }*/
    }


}