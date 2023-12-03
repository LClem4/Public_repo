package recovery;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRecoveryNone {

  @Test
  public void equalRecoveryNoneTest() {
    RecoveryNone r1 = new RecoveryNone();
    int maxLifePts = 30;
    int result = r1.calculateRecovery(maxLifePts, maxLifePts);
    assertEquals(maxLifePts, result);
  }

  @Test
  public void lessThanRecoveryNoneTest() {
    RecoveryBehavior r1 = new RecoveryNone();
    int maxLifePts = 30;
    int currentLifePts = 15;
    int result = r1.calculateRecovery(currentLifePts, maxLifePts);
    assertEquals(result, currentLifePts);
  }

}
