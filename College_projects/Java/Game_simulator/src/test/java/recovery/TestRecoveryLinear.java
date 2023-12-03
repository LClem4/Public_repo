package recovery;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRecoveryLinear {

  @Test
  public void EqualRecoveryLinearTest() {
    RecoveryLinear r1 = new RecoveryLinear(3);
    int maxLifePts = 30;
    int result = r1.calculateRecovery(maxLifePts, maxLifePts);
    assertEquals(maxLifePts, result);
  }

  @Test
  public void fullRecoveryLinearTest() {
    RecoveryLinear r1 = new RecoveryLinear(3);
    int maxLifePts = 30;
    int result = r1.calculateRecovery(27, maxLifePts);
    assertEquals(maxLifePts, result);
    int result_two = r1.calculateRecovery(29, maxLifePts);
    assertEquals(maxLifePts, result_two);
  }

  @Test
  public void fullRecoveryLinearTestTwo() {
    RecoveryLinear r1 = new RecoveryLinear(3);
    int maxLifePts = 30;
    int result_two = r1.calculateRecovery(29, maxLifePts);
    assertEquals(maxLifePts, result_two);
  }

  @Test
  public void deadRecoveryLinearTest() {
    RecoveryLinear r1 = new RecoveryLinear(3);
    int maxLifePts = 30;
    int result = r1.calculateRecovery(0, maxLifePts);
    assertEquals(0, result);

  }

}
