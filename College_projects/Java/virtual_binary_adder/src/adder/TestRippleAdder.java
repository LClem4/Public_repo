package adder;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRippleAdder {

  @Test
  public void test() {
    for(int i = 0; i < 128;i++) {
      for(int j = -128; i < 0;i++) {
      RippleAdder a1 = new RippleAdder(8);
      assertEquals(i+j,a1.addDecimalNum(i,j));
      }
    }
    
    for(int i = -128; i < 0;i++) {
      for(int j = 0; i < 128;i++) {
      RippleAdder a1 = new RippleAdder(8);
      assertEquals(i+j,a1.addDecimalNum(i,j));
      }
    }
    
    for(int i = 0; i < 32768;i++) {
      for(int j = -32768; i < 0;i++) {
      RippleAdder a1 = new RippleAdder(16);
      assertEquals(i+j,a1.addDecimalNum(i,j));
      }
    }
    
    for(int i = -32768; i < 0;i++) {
      for(int j = 0; i < 32768;i++) {
      RippleAdder a1 = new RippleAdder(16);
      assertEquals(i+j,a1.addDecimalNum(i,j));
      }
    }
  }

}
