package lifeform;

import exceptions.RecoveryRateException;

import gameplay.TimerObserver;
import recovery.RecoveryBehavior;
import recovery.RecoveryNone;

public class Alien extends LifeForm {
  private int recoveryRate = 0;
  private RecoveryBehavior recovBehavior;

  /**
   * Creates SubClass Alien
   * 
   * @Params name
   * @Params life
   */
  public Alien(String name, int life) {
    super(name, life);
    this.recoveryRate = 0;
    this.recovBehavior = new RecoveryNone();
    setAttackStrength(10);
    maxSpeed = 2;
    speed = maxSpeed;
  }

  /**
   * Creates SubClass Alien
   * 
   * @Params name
   * @Params life
   * @Params rb
   */
  public Alien(String name, int life, RecoveryBehavior rb) {
    super(name, life);
    this.recoveryRate = 0;
    this.recovBehavior = rb;
    setAttackStrength(10);
    maxSpeed = 2;
    speed = maxSpeed;
  }

  /**
   * Creates SubClass Alien
   * 
   * @Params name
   * @Params life
   * @Params rb
   * @Params rate
   */
  public Alien(String name, int life, RecoveryBehavior rb, int rate) throws RecoveryRateException {
    super(name, life);
    checkRecoveryRate(rate);
    this.recoveryRate = rate;
    this.recovBehavior = rb;
    setAttackStrength(10);
    maxSpeed = 2;
    speed = maxSpeed;
  }

  /**
   * Checks recoveryRate instance variable throws exception if rate is less than 0
   * 
   * @Params recoveryRate is the value to be checked
   */
  private void checkRecoveryRate(int recoveryRate) throws RecoveryRateException {
    if (recoveryRate >= 0) {
      this.recoveryRate = recoveryRate;

    } else {
      throw new RecoveryRateException("Invalid Recovery Rate");
    }
  }

  /**
   * gets/returns recoveryRate instance variable
   */
  public int getRecoveryRate() {
    return recoveryRate;
  }

  /**
   * sets recoveryRate instance variable to the passed in parameter
   * 
   * @Params newRecoveryRate is the value recoveryRate will be set to
   */
  public void setRecoveryRate(int newRecoveryRate) {
    recoveryRate = newRecoveryRate;
  }

  /**
   * calls calculateRecovery from recoveryBehavior then sets the currentLifePoints
   * to the return value
   */
  public void recover() {
    int result = recovBehavior.calculateRecovery(getCurrentLifePoints(), getMaxLifePoints());
    setCurrentLifePoints(result);
  }

  /**
   * sets current round as instance variable, time given from seperate thread from
   * SimpleTimer Class
   * 
   * @Params time
   */
  public void updateTime(int time) {
    super.updateTime(time);
    int thisRound = getCurrentRound();
    if (recoveryRate != 0 && thisRound % recoveryRate == 0) {
      this.recover();
    }
    //System.out.println(this.getName() + " I got a new time! " + thisRound);
  }
  
  public String getSpecies() {
    return "Alien";
  }

}
