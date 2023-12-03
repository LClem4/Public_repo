package lifeform;

public class Human extends LifeForm {
  private int armorPoints;

  /**
   * Human with instance variable armor,life, and name *NO RECOVERY METHOD*
   */
  public Human(String name, int life, int armorPoints) {
    super(name, life);
    this.armorPoints = checkArmorPoints(armorPoints);
    setAttackStrength(5);
    maxSpeed = 3;
    speed = maxSpeed;
  }

  /**
   * Checks if the value assigned to constructor is valid
   * 
   * @Params initalArmorPoints value to be checked
   */
  private int checkArmorPoints(int initalArmorPoints) {
    if (initalArmorPoints >= 0) {
      return initalArmorPoints;
    } else {
      return 0;
    }
  }

  /**
   * gets/returns ArmorPoints instance variable
   */
  public int getArmorPoints() {
    return armorPoints;
  }

  /**
   * sets armorPoints instance variable
   * 
   * @Params newArmorPoints value to be checked
   */
  public void setArmorPoints(int newArmorPoints) {
    if (newArmorPoints >= 0) {
      armorPoints = newArmorPoints;
    } else {
      return;
    }
  }

  @Override
  public void takeHit(int damage) {
    if (((getCurrentLifePoints() + armorPoints) - damage) > 0) {
      if (damage <= armorPoints) {
        setArmorPoints(armorPoints - damage);
      } else {
        setCurrentLifePoints((getCurrentLifePoints() + armorPoints) - damage);
        setArmorPoints(0);
      }
    } else {
      setCurrentLifePoints(0);
    }
  }
  
  public String getSpecies() {
    return "Human";
  }

}
