package lifeform;

import exceptions.WeaponException;

import gameplay.TimerObserver;

import weapon.Weapon;

/**
 * Super/Parent-class for Human & Alien subclasses Stores name, current
 * lifepoints, inital lifepoints, and attack integers
 */

public abstract class LifeForm implements TimerObserver {
  private String name;
  private int currentLifePoints;
  private int maxLifePoints;
  private int attackStrength;
  private int currentRound = 0;
  
  protected Direction direction;
  protected int maxSpeed;
  protected int speed;
  protected Weapon weapon;
  protected int row;
  protected int col;
  

  /**
   * Base Class To Hold Current LifePoints, Inital/MaxLifePoints, and Character
   * Name
   *
   * @Params name -Character Name
   * @Params life -InitalLifePoints
   */
  public LifeForm(String name, int life) {
    this.name = name;
    this.currentLifePoints = life;
    this.maxLifePoints = life;
    this.attackStrength = 1;
    this.row = -1;
    this.col = -1;
    maxSpeed = 0;
    direction = direction.NORTH;
  }

  /**
   * @param name
   * @param life
   * @param attack
   */
  public LifeForm(String name, int life, int attack) {
    this.name = name;
    this.currentLifePoints = life;
    this.maxLifePoints = life;
    this.attackStrength = attack;
    this.row = -1;
    this.col = -1;
    maxSpeed = 0;
    direction = direction.NORTH;
  }

  /**
   * gets/returns name instance variable(String)
   */
  public String getName() {
    return this.name;
  }
  
  public String getSpecies() {
    return "LifeForm";
  }

  /**
   * gets/returns initial/Max LifePoints instance variable(int)
   */
  public int getMaxLifePoints() {
    return this.maxLifePoints;
  }

  /**
   * gets/returns initial/Max LifePoints instance variable(int)
   */
  public int getCurrentRound() {
    return this.currentRound;
  }

  /**
   * gets/returns initial/Max LifePoints instance variable(int)
   */
  public void setCurrentRound(int time) {
    this.currentRound = time;
  }

  /**
   * gets/returns currentLifePoints instance variable(int)
   */
  public int getCurrentLifePoints() {
    return this.currentLifePoints;
  }

  /**
   * sets currentLifePoints instance variable
   */
  public void setCurrentLifePoints(int newCurrentLifePoints) {
    currentLifePoints = newCurrentLifePoints;
  }

  public int getAttackStrength() {
    return attackStrength;
  }

  public void setAttackStrength(int attack) {
    this.attackStrength = attack;
  }

  /**
   * decrements then sets health based damage parameter until health is 0
   *
   * @Params damage
   */
  public void takeHit(int damage) {
    if ((currentLifePoints - damage) > 0) {
      setCurrentLifePoints(currentLifePoints - damage);
    } else {
      setCurrentLifePoints(0);
    }
  }

  /**
   * Takes in another lifeform and uses the takeHit method passing in the
   * currentAttackStrength
   *
   * @param opponentLifeForm
   * @throws WeaponException
   */
  public void attack(LifeForm opponentLifeForm, int distance) throws WeaponException {
    if (opponentLifeForm != null) {
      if (getCurrentLifePoints() > 0 && hasWeapon() && weapon.getCurrentAmmo() > 0) {
        opponentLifeForm.takeHit(weapon.fire(distance));
      }
      if (getCurrentLifePoints() > 0 && (!hasWeapon() || weapon.getCurrentAmmo() <= 0)) {
        if (distance <= 5) {
          opponentLifeForm.takeHit(this.attackStrength);
        }
      }
    }
  }

  /**
   * @return
   */
  public boolean hasWeapon() {
    if (weapon == null) {
      return false;
    }

    return true;
  }

  /**
   * @return
   */
  public Weapon dropWeapon() {
    Weapon dropped = weapon;
    weapon = null;
    return dropped;
  }

  /**
   * @param weapon
   * @return
   */
  public boolean pickUpWeapon(Weapon weapon) {
    if (this.weapon == null) {
      this.weapon = weapon;
      return true;
    }
    return false;
  }

  /**
   * Sets location of cell
   * 
   * @param row
   * @param col
   */
  public void setLocation(int row, int col) {
    if (row >= 0 && col >= 0) {
      this.row = row;
      this.col = col;
    }
  }

  /**
   * Gets Row
   * 
   * @return
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets Col
   * 
   * @return
   */
  public int getCol() {
    return col;
  }
  
  //LAB 6 METHODS BEGIN
  
  /**
   * Sets last direction to a new direction
   * 
   * @param d
   */
  public void rotate(Direction d) { 
    this.direction = d;
  }
  
  /**
   * gets MaxSpeed
   * 
   * @return int maxSpeed
   */
  public int getMaxSpeed() {
    return maxSpeed;
  }
  
  
  /**
   * gets current speed
   * 
   * @return int speed
   */
  public int getCurrentSpeed() {
    return speed;
  }
  
  /**
   * decrease speed
   */
  public void decrementSpeed() {
    speed--;
  }
  
  /**
   * Set speed to 0
   */
  public void setSpeed0() {
    speed = 0;
  }
  
  /**
   * Resets speed to maxSpeed
   */
  public void resetSpeed() {
    speed = maxSpeed;
  }
  
  /**
   * gets current Direction
   * 
   * @return Direction direction
   */
  public Direction getDirection() {
    return direction;
  }
  
  /**
   * 
   * @return Weapon weapon
   */
  public Weapon getWeapon() {
    return weapon;
  }
  
  @Override
  public void updateTime(int time) {
    setCurrentRound(time);
    speed = maxSpeed;
    if (hasWeapon()) {
      weapon.updateTime(time);
    }
  }

}