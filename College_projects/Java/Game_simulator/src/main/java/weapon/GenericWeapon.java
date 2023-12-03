package weapon;

import exceptions.WeaponException;

/**
 * @author aa0245
 */
public abstract class GenericWeapon implements Weapon {
  protected int baseDamage;
  protected int currentAmmo;
  protected int maxRange;
  protected int rateOfFire;
  protected int maxAmmo;
  protected int shotsLeft;

  public int getBaseDamage() {
    return baseDamage;
  }

  public int getCurrentAmmo() {
    return currentAmmo;
  }

  public int getMaxRange() {
    return maxRange;
  }

  public int getMaxAmmo() {
    return maxAmmo;
  }

  public int getRateOfFire() {
    return rateOfFire;
  }

  public int getShotsLeft() {
    return shotsLeft;
  }

  public void reload() {
    currentAmmo = maxAmmo;
  }

  public void fireShot() {
    currentAmmo--;
    shotsLeft--;
  }

  public void updateTime(int time) {
    shotsLeft = rateOfFire;
  }

  public int getNumAttachments() {
    return 0;
  }

  public abstract String toString();

  public abstract int fire(int distance) throws WeaponException;
}
