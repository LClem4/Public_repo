package environment;

import lifeform.LifeForm;
import weapon.Weapon;

/**
 * A Cell that can hold a LifeForm.
 */
public class Cell {

  LifeForm newLifeForm = null;
  boolean hasLifeForm = false;
  int weaponCount = 0;
  Weapon weapon1 = null;
  Weapon weapon2 = null;

  /**
   * @return (lifeForm) object in the cell
   */
  public LifeForm getLifeForm() {

    return newLifeForm;
  }

  /**
   * @return (String) name of LifeForm
   */
  public String getNameOfLifeForm() {
    return newLifeForm.getName();
  }

  /**
   * returns current occupation status of the cell
   * 
   * @return (boolean) hasLifeForm boolean value
   */
  public boolean hasLifeForm() {
    return hasLifeForm;
  }

  /**
   * @param entity the lifeForm held in the cell
   * @return true if the LifeForm was added to the cell, false if cell is
   *         currently occupied.
   */
  public boolean addLifeForm(LifeForm entity) {
    if (hasLifeForm == false) {
      newLifeForm = entity;
      hasLifeForm = true;
      return true;
    } else {
      return false;
    }
  }

  /**
   * returns cell to default values sets lifeForm variable to false sets the
   * occupation flag to false
   */
  public void removeLifeForm() {
    newLifeForm = null;
    hasLifeForm = false;
  }

  /**
   * Gets weapon count
   * 
   * @return
   */
  public int getWeaponsCount() {
    return weaponCount;
  }

  /**
   * Gets weapon 1
   * 
   * @return
   */
  public Weapon getWeapon1() {
    return weapon1;
  }

  /**
   * Gets weapon 2
   * 
   * @return
   */
  public Weapon getWeapon2() {
    return weapon2;
  }

  /**
   * Adds weapon to lifeform
   * 
   * @param weapon
   * @return
   */
  public boolean addWeapon(Weapon weapon) {
    if (weapon1 == null && getWeaponsCount() < 2 && weapon != weapon2) {
      weapon1 = weapon;
      weaponCount++;
      return true;
    }
    if (weapon2 == null && getWeaponsCount() < 2 && weapon != weapon1) {
      weapon2 = weapon;
      weaponCount++;
      return true;
    }
    return false;
  }

  /**
   * 
   * @param weapon
   * @return
   */
  public Weapon removeWeapon(Weapon weapon) {
    if (weapon == getWeapon1()) {
      weaponCount--;
      weapon1 = null;
      return weapon;
    }
    if (weapon == getWeapon2()) {
      weaponCount--;
      weapon2 = null;
      return weapon;
    }
    return null;

  }

}
