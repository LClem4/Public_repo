package recovery;

/**
 * @Params steps
 */
public class RecoveryLinear implements RecoveryBehavior {
  private int recoveryStep;

  public RecoveryLinear(int additionalHealth) {
    this.recoveryStep = additionalHealth;
  }

  /**
   * Increments health based on recoveryStep instance variable until max life is
   * reached, and returns the new number of healthpoints
   * 
   * @Params currentLife
   * @Params maxLife
   */
  public int calculateRecovery(int currentLife, int maxLife) {
    if (currentLife == 0) {
      return 0;
    }
    if ((maxLife - currentLife) >= recoveryStep) {
      return (currentLife + recoveryStep);
    } else {
      return maxLife;
    }
  }
}
