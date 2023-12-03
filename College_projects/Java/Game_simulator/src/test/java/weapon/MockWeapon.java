package weapon;

import exceptions.WeaponException;

public class MockWeapon extends GenericWeapon {
  private int shotsFired;

  public MockWeapon() {
    this.baseDamage = 10;
    this.maxRange = 10; // in feet
    this.rateOfFire = 3;
    this.shotsLeft = this.rateOfFire;
    this.maxAmmo = 100;
    this.currentAmmo = this.maxAmmo;
  }

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
    return (int) ((double) getBaseDamage() * (((double) (getMaxRange() - distance + 10) / getMaxRange())));
  }

  public String toString() {
    return "MockWeapon";
  }
  public void setShotsLeft(int ammo) {
    shotsLeft=ammo;
  }
}
