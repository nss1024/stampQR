package com.qrstamp.core.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QRSelector {

private Map<Integer, Integer> L_CODES = new HashMap<>();
private Map<Integer, Integer> M_CODES = new HashMap<>();
private Map<Integer, Integer> Q_CODES = new HashMap<>();
private Map<Integer, Integer> H_CODES = new HashMap<>();

    { // instance initializer to store QRCode version and max text size a version supports by error correction level
        L_CODES.put(25,21);L_CODES.put(47,25);L_CODES.put(77,29);L_CODES.put(114,33);L_CODES.put(154,37);L_CODES.put(195,41);L_CODES.put(224,45);L_CODES.put(279,49);L_CODES.put(335,53);L_CODES.put(395,57);L_CODES.put(468,61);L_CODES.put(535,65);L_CODES.put(619,69);L_CODES.put(667,73);L_CODES.put(758,77);L_CODES.put(854,81);L_CODES.put(938,85);L_CODES.put(1046,89);L_CODES.put(1153,93);L_CODES.put(1249,97);L_CODES.put(1352,101);L_CODES.put(1460,105);L_CODES.put(1588,109);L_CODES.put(1704,113);L_CODES.put(1853,117);L_CODES.put(1990,121);L_CODES.put(2132,125);L_CODES.put(2223,129);L_CODES.put(2369,133);L_CODES.put(2520,137);L_CODES.put(2677,141);L_CODES.put(2840,145);L_CODES.put(3009,149);L_CODES.put(3183,153);L_CODES.put(3351,157);L_CODES.put(3537,161);L_CODES.put(3729,165);L_CODES.put(3927,169);L_CODES.put(4087,173);L_CODES.put(4296,177);
        M_CODES.put(20,21);M_CODES.put(38,25);M_CODES.put(61,29);M_CODES.put(90,33);M_CODES.put(122,37);M_CODES.put(154,41);M_CODES.put(178,45);M_CODES.put(221,49);M_CODES.put(262,53);M_CODES.put(311,57);M_CODES.put(366,61);M_CODES.put(419,65);M_CODES.put(483,69);M_CODES.put(528,73);M_CODES.put(600,77);M_CODES.put(656,81);M_CODES.put(734,85);M_CODES.put(816,89);M_CODES.put(909,93);M_CODES.put(970,97);M_CODES.put(1035,101);M_CODES.put(1134,105);M_CODES.put(1248,109);M_CODES.put(1326,113);M_CODES.put(1451,117);M_CODES.put(1542,121);M_CODES.put(1637,125);M_CODES.put(1732,129);M_CODES.put(1839,133);M_CODES.put(1994,137);M_CODES.put(2113,141);M_CODES.put(2238,145);M_CODES.put(2369,149);M_CODES.put(2506,153);M_CODES.put(2632,157);M_CODES.put(2780,161);M_CODES.put(2894,165);M_CODES.put(3054,169);M_CODES.put(3220,173);M_CODES.put(3391,177);
        Q_CODES.put(16,21);Q_CODES.put(29,25);Q_CODES.put(47,29);Q_CODES.put(67,33);Q_CODES.put(87,37);Q_CODES.put(108,41);Q_CODES.put(125,45);Q_CODES.put(157,49);Q_CODES.put(189,53);Q_CODES.put(221,57);Q_CODES.put(259,61);Q_CODES.put(296,65);Q_CODES.put(352,69);Q_CODES.put(376,73);Q_CODES.put(426,77);Q_CODES.put(470,81);Q_CODES.put(531,85);Q_CODES.put(574,89);Q_CODES.put(644,93);Q_CODES.put(702,97);Q_CODES.put(742,101);Q_CODES.put(823,105);Q_CODES.put(890,109);Q_CODES.put(963,113);Q_CODES.put(1041,117);Q_CODES.put(1094,121);Q_CODES.put(1172,125);Q_CODES.put(1263,129);Q_CODES.put(1322,133);Q_CODES.put(1429,137);Q_CODES.put(1499,141);Q_CODES.put(1618,145);Q_CODES.put(1700,149);Q_CODES.put(1787,153);Q_CODES.put(1867,157);Q_CODES.put(1966,161);Q_CODES.put(2071,165);Q_CODES.put(2181,169);Q_CODES.put(2298,173);Q_CODES.put(2420,177);
        H_CODES.put(10,21);H_CODES.put(20,25);H_CODES.put(35,29);H_CODES.put(50,33);H_CODES.put(64,37);H_CODES.put(84,41);H_CODES.put(93,45);H_CODES.put(122,49);H_CODES.put(143,53);H_CODES.put(174,57);H_CODES.put(200,61);H_CODES.put(227,65);H_CODES.put(259,69);H_CODES.put(283,73);H_CODES.put(321,77);H_CODES.put(365,81);H_CODES.put(408,85);H_CODES.put(452,89);H_CODES.put(493,93);H_CODES.put(557,97);H_CODES.put(587,101);H_CODES.put(640,105);H_CODES.put(672,109);H_CODES.put(744,113);H_CODES.put(779,117);H_CODES.put(864,121);H_CODES.put(910,125);H_CODES.put(958,129);H_CODES.put(1016,133);H_CODES.put(1080,137);H_CODES.put(1150,141);H_CODES.put(1226,145);H_CODES.put(1307,149);H_CODES.put(1394,153);H_CODES.put(1431,157);H_CODES.put(1530,161);H_CODES.put(1591,165);H_CODES.put(1658,169);H_CODES.put(1774,173);H_CODES.put(1852,177);

    }

    public int getQRCodeSizeForText(String textToEncode, String errorCorrectionLevel){

        int result = getVersionByTextSize(getTextSize(textToEncode),getMapByECC(errorCorrectionLevel));

        return result;
    }

    private int getTextSize(String s){

        return s.length();

    }

    private int getVersionByTextSize(int txtSize, Map<Integer,Integer> codes){

        int key=0;
        int result = 0;
        Set<Integer> maxSizes = codes.keySet(); // get all keys from key set these are the max text sizes a qr code version can support
            List<Integer> sortedList = new ArrayList<>(maxSizes);
            Collections.sort(sortedList);
        for(Integer i : sortedList){ // iterate through the max test sizes and get the first one that is equal to or greater than the size of the text to be encoded
            if(i>=txtSize){
                key = i;
                System.out.println("Selected size is: "+key);
                break;
            }
        }

        result = codes.get(key);
        System.out.println("QR Code version to use is: "+result);

        return result;
    }

    private Map getMapByECC(String eccLvl){

        switch (eccLvl) {
            case "L": return L_CODES;
            case "M": return M_CODES;
            case "Q" : return Q_CODES;
            case "H" : return H_CODES;
            default: return null;

        }
    }

}
