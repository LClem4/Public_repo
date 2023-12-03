package weapon;

import exceptions.WeaponException;

/**
 * 
 * @author nh5830
 *
 */
public class PlasmaCannon extends GenericWeapon {

  /**
   *
   */
  public PlasmaCannon() {
    this.baseDamage = 50;
    this.maxRange = 40; // in feet
    this.rateOfFire = 1;
    this.maxAmmo = 4;
    this.shotsLeft = rateOfFire;
    this.currentAmmo = this.maxAmmo;
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

    double damage = ((double) (getCurrentAmmo() + 1) / (double) getMaxAmmo());
    return (int) ((double) getBaseDamage() * damage);
  }

  public String toString() {
    return "PlasmaCannon";
  }

}
