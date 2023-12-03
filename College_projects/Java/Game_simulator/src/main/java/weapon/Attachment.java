package weapon;

import exceptions.WeaponException;

/**
 * 
 * @author ew7860
 *
 */
public abstract class Attachment implements Weapon {

  protected Weapon base;

  public abstract int fire(int distance) throws WeaponException;

  public int getBaseDamage() {
    return base.getBaseDamage();
  }

  public int getCurrentAmmo() {
    return base.getCurrentAmmo();
  }

  public int getMaxAmmo() {
    return base.getMaxAmmo();
  }

  public int getMaxRange() {
    return base.getMaxRange();
  }

  public int getNumAttachments() {
    return 1 + base.getNumAttachments();
  }

  public int getRateOfFire() {
    return base.getRateOfFire();
  }

  public int getShotsLeft() {
    return base.getShotsLeft();
  }

  public void reload() {
    base.reload();
  }

  public void updateTime(int time) {
    base.updateTime(time);
  }

  public void fireShot() {
    base.fireShot();
  }

  public abstract String toString();

}
