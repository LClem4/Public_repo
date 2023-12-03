package recovery;

public class RecoveryNone implements RecoveryBehavior {
  public int calculateRecovery(int currentLife, int maxLife) {
    return currentLife;
  }
}
