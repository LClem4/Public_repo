package recovery;

public class RecoveryFractional implements RecoveryBehavior {
  private double percentRecovery;

  public RecoveryFractional(double percentage) {
    this.percentRecovery = percentage;
  }

  /**
   * Increments health based on percentageRecovery(percent of missing health)
   * instance variable until max life is reached, and returns the new number of
   * healthpoints
   * 
   * @Params currentLife
   * @Params maxLife
   */
  public int calculateRecovery(int currentLife, int maxLife) {
    int newCurrentLifePoints = (int) (Math.ceil(((currentLife) * percentRecovery) + (currentLife)));
    if (currentLife == 0) {
      return 0;
    }
    if ((newCurrentLifePoints) <= maxLife) {
      return (newCurrentLifePoints);
    } else {
      return maxLife;
    }

  }
}
