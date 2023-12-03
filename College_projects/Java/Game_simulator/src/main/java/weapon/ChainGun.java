package weapon;

import exceptions.WeaponException;

/**
 * 
 * @author nh5830
 *
 */
public class ChainGun extends GenericWeapon {

  /**
   *
   */
  public ChainGun() {
    this.baseDamage = 15;
    this.maxRange = 60; // in feet
    this.rateOfFire = 4;
    shotsLeft = rateOfFire;
    this.maxAmmo = 40;
    this.currentAmmo = maxAmmo;
  }

  /**
   * @param distance
   * @return
   * @throws WeaponException
   */
  public int fire(int distance) throws WeaponException {
    if (distance < 0) {
      throw new WeaponException("Negative Distances are Not Allowed");
    }
    if (getShotsLeft() <= 0 || getCurrentAmmo() <= 0) {
      return 0;
    }

    fireShot();

    if (distance > getMaxRange()) {
      return 0;
    }
    return (int) ((double) getBaseDamage() * ((double) distance / getMaxRange()));
  }

  public String toString() {
    return "ChainGun";
  }

}
