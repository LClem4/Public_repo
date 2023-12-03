package recovery;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRecoveryFractional {

  @Test
  public void equalRecoveryFractionalTest() {
    RecoveryFractional r1 = new RecoveryFractional(.3);
    int maxLifePts = 100;
    int result = r1.calculateRecovery(maxLifePts, maxLifePts + 30);
    assertEquals(130, result);
  }

  @Test
  public void fullRecoveryFractionalTest() {
    RecoveryFractional r1 = new RecoveryFractional(.3);
    int maxLifePts = 100;
    int result = r1.calculateRecovery(maxLifePts, maxLifePts + 30);
    assertEquals(130, result);
  }

  @Test
  public void halfRecoveryFractionalTest() {
    RecoveryFractional r1 = new RecoveryFractional(.5);
    int maxLifePts = 100;
    int result = r1.calculateRecovery(50, maxLifePts);
    assertEquals(75, result);
  }

  @Test
  public void zeroRecoveryFractionalTest() {
    RecoveryFractional r1 = new RecoveryFractional(.3);
    int maxLifePts = 100;
    int result = r1.calculateRecovery(maxLifePts, maxLifePts + 30);
    assertEquals(130, result);
  }
}
