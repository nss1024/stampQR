package com.stampQR.stampqrmobile;

public class Utils {

    public boolean validateTextLength(String text, int charLength){
        if(text.isEmpty() | text.length()<charLength){return false;}
        else{return true;}
    }


}
