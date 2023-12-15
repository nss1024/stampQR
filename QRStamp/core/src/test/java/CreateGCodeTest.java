import com.qrstamp.core.generator.CreateGcode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateGCodeTest {

    CreateGcode createGcode = new CreateGcode();

    @Test
    public void gCodeTest1(){
        assertEquals("G21 \nG90 \nG00 Y1.0\nG00 Y1.0\n",createGcode.generateGCode("00-00",1.0,2.0));
    }

    @Test
    public void gCodeTest2(){
        assertEquals("G21 \nG90 \nG00 Y1.0\n",createGcode.generateGCode("",1.0,2.0));
    }

    @Test
    public void gCodeTest3(){
        assertEquals("G21 \nG90 \nG00 Y1.0\n",createGcode.generateGCode("0",1.0,2.0));
    }

    @Test
    public void gCodeTest4(){

        assertEquals("G21 \nG90 \nG00 Y1.0\nG00 Y01.0\nG00 X01.0\nG00 Z-2.0\nG00 Z00\nG00 Y01.0\nG00 X04.0\nG00 Z-2.0\nG00 Z00\n",createGcode.generateGCode("00000-010010",1.0,2.0));

    }


}
