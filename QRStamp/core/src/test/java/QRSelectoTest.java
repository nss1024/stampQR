import com.qrstamp.core.generator.QRSelector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Version refers to the size of the QR code a result of 21 = V1 and is a 21 X 21 square
//
public class QRSelectoTest {

    QRSelector qrSelector;

    @Test
    public void selector_21_L(){
        qrSelector=new QRSelector();
        //test for V1 QR code 21x21 max alphanumeric size is 25 anything <= 25 should return 21
        assertEquals(21,qrSelector.getQRCodeSizeForText("15 char test...","L"));
        assertEquals(21,qrSelector.getQRCodeSizeForText("13 char test.","L"));
        assertEquals(21,qrSelector.getQRCodeSizeForText("10chartest","L"));
    }

    @Test
    public void selector_25_L(){
        qrSelector=new QRSelector();
        //test for V2 QR code 25x25 max alphanumeric size is 47 anything >25 & <=47 should return 25
        assertEquals(25,qrSelector.getQRCodeSizeForText("26 character test qr_code.","L"));
        assertEquals(25,qrSelector.getQRCodeSizeForText("thirty character test qr_code.","L"));
        assertEquals(25,qrSelector.getQRCodeSizeForText("forty seven character test for the qr_code.....","L"));
    }

    @Test
    public void selector_29_L(){
        qrSelector=new QRSelector();
        //test for V3 QR code 25x25 max alphanumeric size is 77 anything >48 & <=77 should return 29
        assertEquals(29,qrSelector.getQRCodeSizeForText("uwjirlhnabiuaniofkymxkfacoxzjumrxtsyneevntqegsfonl","L"));//50
        assertEquals(29,qrSelector.getQRCodeSizeForText("dmnybxazuqlskpkbfvgoejgzmhqwalhukoqwhphtkshovpupwckhxcnjuajetu","L"));//65
        assertEquals(29,qrSelector.getQRCodeSizeForText("dmnytdqjtmpsjkosqbqhkxodoxiepwpdiodecxypmqtpbyykmskrromzngfjpixoregjweuwulwyu","L"));//77
    }

    @Test
    public void selector_33_L(){
        qrSelector=new QRSelector();
        //test for V4 QR code 25x25 max alphanumeric size is 114 anything >78 & <=114 should return 33
        assertEquals(33,qrSelector.getQRCodeSizeForText("nvppvlyucymzoejfhbddyroisizdvrudckbqwqapfmgraivoocjpanxtqspcqcntysuroytqzajoghijgr","L"));//82
        assertEquals(33,qrSelector.getQRCodeSizeForText("xgaannkpqqllryzslzmcsoflnywsfddmymrcharyzoaipdtzawmbevvrnjinvfsbjwfgrcnrorfxhafzfolebigtjisdbwrxnhjdoniscdaelmznv","L"));//113
        assertEquals(33,qrSelector.getQRCodeSizeForText("gcgkpwenduhunoxymppoorgekdjcdesqpexzhqcsskrsihiibffopxsglwiyvlucdkheyxpfsnnzynfmkifhidmrrimnnfzzyettjtlhufljjhdlxe","L"));//144
    }

    @Test
    public void selector_37_L(){
        qrSelector=new QRSelector();
        //test for V5 QR code 25x25 max alphanumeric size is 154 anything >145 & <=154 should return 37
        assertEquals(37,qrSelector.getQRCodeSizeForText("ewzbvpjsygpjhllvvmmmbmgpgxplfetrfeeqsxpgvvqzhymetjpkvnpfvzcvocjuomimwvtyzhqopmlwlijkmoxtcygysngralcjunowwyaxghadycxhictffkpxvwvlciyugaaerkpqayaxybqjnb","L"));//154
        assertEquals(37,qrSelector.getQRCodeSizeForText("izptzwkmqyfeoccotqjwxngcadaxuhdxsyrsiseakhghgsgvredkneidnppnoigmzeyqvkybrluiujihclpbkgxjyasqymnqwzbkvszubcnpoovunlvvuaehgdtiexihsciykhbqiblrvcgmmeyibbvfwg","L"));//154
        assertEquals(37,qrSelector.getQRCodeSizeForText("ptlfquthoyzijqplwvaqqhkgypubykkrtnwghikgvzrmykxgyjppwwsyvbltqskoydztoghssxjohhqeedhqqrkozpebyskwmkauchnppakkqkcppkgmratjlfabtgyawenojjuaitqcfnpgdvi","L"));//147
    }

    //**************************************************************************************************************************************************************************************************************************************************
    @Test
    public void selector_21_Q(){
        qrSelector=new QRSelector();
        //test for V1 QR code 21x21 max alphanumeric size is 16 anything <= 16 should return 21
        assertEquals(21,qrSelector.getQRCodeSizeForText("knkng","Q"));//5
        assertEquals(21,qrSelector.getQRCodeSizeForText("ihcqbjuvcvjl","Q"));//12
        assertEquals(21,qrSelector.getQRCodeSizeForText("hsqwieputlqxxluh","Q"));//20
    }

    @Test
    public void selector_25_Q(){
        qrSelector=new QRSelector();
        //test for V2 QR code 25x25 max alphanumeric size is 29 anything >17 & <=29 should return 25
        assertEquals(25,qrSelector.getQRCodeSizeForText("lxhhwmshxthkisfnwwaoa","Q"));//21
        assertEquals(25,qrSelector.getQRCodeSizeForText("lxhhwmshxthkisfnww","Q"));//18
        assertEquals(25,qrSelector.getQRCodeSizeForText("lxhhwmshxthkisfnwwaoadjyndutb","Q"));//29
    }

    @Test
    public void selector_29_Q(){
        qrSelector=new QRSelector();
        //test for V3 QR code 25x25 max alphanumeric size is 47 anything >30 & <=47 should return 29
        assertEquals(29,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprf","Q"));//35
        assertEquals(29,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzynftge","Q"));//30
        assertEquals(29,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfaoenyauuabfd","Q"));//47
    }

    @Test
    public void selector_33_Q(){
        qrSelector=new QRSelector();
        //test for V4 QR code 25x25 max alphanumeric size is 67 anything >48 & <=67 should return 33
        assertEquals(33,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfaoenyauuabfdwgu","Q"));//50
        assertEquals(33,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprsjyengegtmoahtndiemftaktdfpmt","Q"));//63
        assertEquals(33,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprsjyengegtmoahtndiemftaktdfpmtstmr","Q"));//67
    }

    @Test
    public void selector_37_Q(){
        qrSelector=new QRSelector();
        //test for V5 QR code 25x25 max alphanumeric size is 87 anything >68 & <=87 should return 37
        assertEquals(37,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprsjyengegtmoahtndiemftaktdfpmtstmranots","Q"));//72
        assertEquals(37,qrSelector.getQRCodeSizeForText("antloftknlrqpiwgfepdnloavgjhxtfzllzutslyprsjyengegtmoahtndiemftaktdfpmtstmranots","Q"));//80
        assertEquals(37,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprsjyengegtmoahtndiemftaktdfpmtstmranotsktnftsltnstglua","Q"));//87
    }
//**************************************************************************************************************************************************************************************************************************************************
    @Test
    public void selector_21_M(){
        qrSelector=new QRSelector();
        //test for V1 QR code 21x21 max alphanumeric size is 20 anything <= 20 should return 21
        assertEquals(21,qrSelector.getQRCodeSizeForText("knkng","M"));//5
        assertEquals(21,qrSelector.getQRCodeSizeForText("ihcqbjuvcvjl","M"));//12
        assertEquals(21,qrSelector.getQRCodeSizeForText("hsqwieputlqxxluhdavc","M"));//20
    }

    @Test
    public void selector_25_M(){
        qrSelector=new QRSelector();
        //test for V2 QR code 25x25 max alphanumeric size is 38 anything >21 & <=38 should return 25
        assertEquals(25,qrSelector.getQRCodeSizeForText("lxhhwmshxtcxhkisfnwwaoa","M"));//23
        assertEquals(25,qrSelector.getQRCodeSizeForText("daobtepwaehiobhmrxvvxvizihywhbe","M"));//31
        assertEquals(25,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfauu","M"));//38
    }

    @Test
    public void selector_29_M(){
        qrSelector=new QRSelector();
        //test for V3 QR code 25x25 max alphanumeric size is 61 anything >39 & <=61 should return 29
        assertEquals(29,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfauuabfd","M"));//42
        assertEquals(29,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfauuabfdakynftge","M"));//50
        assertEquals(29,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfauuabfdakynftgegkrndostemy","M"));//61
    }

    @Test
    public void selector_33_M(){
        qrSelector=new QRSelector();
        //test for V4 QR code 25x25 max alphanumeric size is 90 anything >62 & <=90 should return 33
        assertEquals(33,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfauuabfdakynftgegkrndostemyleng","M"));//65
        assertEquals(33,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfauuabfdakynftgegkrndostemylengegtmoahtndiemftaktdfpmt","M"));//88
        assertEquals(33,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfauuabfdakynftgegkrndostemylengegtmoahtndiemftaktdfpmtsp","M"));//90
    }

    @Test
    public void selector_37_M(){
        qrSelector=new QRSelector();
        //test for V5 QR code 25x25 max alphanumeric size is 122 anything >91 & <=122 should return 37
        assertEquals(37,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfauuabfdakynftgegkrndostemylengegtmoahtndiemftaktdfpmtsporn","M"));//93
        assertEquals(37,qrSelector.getQRCodeSizeForText("beynzzfcthufogkpmmasmcxjptwbqfgygdfwjgaszhbugdmleibagnkqtzhhhfvvwdxdqkwvgaxmvnwtlicoromkogwvaiwkxzynytrmgbdsijohoz","M"));//114
        assertEquals(37,qrSelector.getQRCodeSizeForText("gzbztjeqbvolqgahksivyrycaehypaohfqvdhfvxqxdnxiypsvdntwzcxgoeriikpknjgcqicfhoqbmaviipoogujpyfyrlrwkwywbygcivdmbdtjiqfhvzvhg","M"));//122
    }
    //**************************************************************************************************************************************************************************************************************************************************
    @Test
    public void selector_21_H(){
        qrSelector=new QRSelector();
        //test for V1 QR code 21x21 max alphanumeric size is 10 anything <= 10 should return 21
        assertEquals(21,qrSelector.getQRCodeSizeForText("knkng","H"));//5
        assertEquals(21,qrSelector.getQRCodeSizeForText("ihcqbjuv","H"));//8
        assertEquals(21,qrSelector.getQRCodeSizeForText("hsqwieputl","H"));//10
    }

    @Test
    public void selector_25_H(){
        qrSelector=new QRSelector();
        //test for V2 QR code 25x25 max alphanumeric size is 20 anything >11 & <=20 should return 25
        assertEquals(25,qrSelector.getQRCodeSizeForText("lxhhwmshxtcxsfnwwaoa","H"));//20
        assertEquals(25,qrSelector.getQRCodeSizeForText("lxhhwmshxtcx","H"));//12
        assertEquals(25,qrSelector.getQRCodeSizeForText("mshxtcxsfnwwaoa","H"));//15
    }

    @Test
    public void selector_29_H(){
        qrSelector=new QRSelector();
        //test for V3 QR code 25x25 max alphanumeric size is 35 anything >21 & <=35 should return 29
        assertEquals(29,qrSelector.getQRCodeSizeForText("gfepdnloavgjhxtfzllzutslyprfauuabfd","H"));//35
        assertEquals(29,qrSelector.getQRCodeSizeForText("nloavgjhxtfzllzutslyprfauuabfd","H"));//30
        assertEquals(29,qrSelector.getQRCodeSizeForText("zllzutslyprfauuabfdAYRNT","H"));//24
    }

    @Test
    public void selector_33_H(){
        qrSelector=new QRSelector();
        //test for V4 QR code 25x25 max alphanumeric size is 50 anything >36 & <=50 should return 33
        assertEquals(33,qrSelector.getQRCodeSizeForText("SKTNFTEnloavgjhxtfzllzutslyprfauuabfd","H"));//37
        assertEquals(33,qrSelector.getQRCodeSizeForText("nlrqpiwgfepdnloavgjhxtfzllzutslyprfauuabfdakynftge","H"));//50
        assertEquals(33,qrSelector.getQRCodeSizeForText("fepdnloavgjhxtfzllzutslyprfauuabfdakynftge","H"));//42
    }

    @Test
    public void selector_37_H(){
        qrSelector=new QRSelector();
        //test for V5 QR code 25x25 max alphanumeric size is 64 anything >51 & <=64 should return 37
        assertEquals(37,qrSelector.getQRCodeSizeForText("ANDTGnlrqpiwgfepdnloavgjhxtfzllzutslyprfauuabfdakynftge","H"));//56
        assertEquals(37,qrSelector.getQRCodeSizeForText("cvosizbcvbhzxsvypskbwijcmcyfgnaaaxphefplnkmkzemrygtjkqmnbza","H"));//59
        assertEquals(37,qrSelector.getQRCodeSizeForText("vqsvxcvosizbcvbhzxsvypskbwijcmcyfgnaaaxphefplnkmkzemrygtjkqmnbza","H"));//64
    }

}
