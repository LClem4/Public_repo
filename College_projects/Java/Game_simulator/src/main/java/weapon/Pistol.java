package weapon;

import exceptions.WeaponException;

/**
 * 
 * @author aa0245
 *
 */
public class Pistol extends GenericWeapon {

  /**
   *
   */
  public Pistol() {
    this.baseDamage = 10;
    this.maxRange = 50; // in feet
    this.rateOfFire = 2;
    shotsLeft = rateOfFire;
    this.maxAmmo = 10;
    currentAmmo = maxAmmo;
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

    double damage = (((double) (getMaxRange() - distance + 10) / getMaxRange()));
    return (int) ((double) getBaseDamage() * damage);
  }

  public String toString() {
    return "Pistol";
  }

}
