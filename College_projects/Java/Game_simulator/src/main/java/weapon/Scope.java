package weapon;

import exceptions.AttachmentException;
import exceptions.WeaponException;

public class Scope extends Attachment {

  /**
   * @param baseWeapon
   * @throws AttachmentException
   */
  public Scope(Weapon baseWeapon) throws AttachmentException {
    if (baseWeapon.getNumAttachments() >= 2) {
      throw new AttachmentException("Cannot have more than two attachments");
    }
    base = baseWeapon;
  }

  /**
   * @param distance
   * @return
   * @throws WeaponException
   */
  public int fire(int distance) throws WeaponException {
    if (distance < 0) {
      throw new WeaponException("Distance cannot be negative.");
    }

    if (getShotsLeft() <= 0) {
      return 0;
    }

    if (distance > getMaxRange()) {
      base.fireShot();
      return 0;

    }

    if (base.getMaxRange() < distance && distance <= getMaxRange()) {
      return base.fire(base.getMaxRange()) + 5;
    }

    double damage = (1 + ((double) getMaxRange() - (double) distance) / (double) getMaxRange());
    return Double.valueOf(Math.floor((double) base.fire(distance) * damage)).intValue();
  }

  /**
   * For printing
   */
  public String toString() {
    return base.toString() + " +Scope";
  }

  /**
   * Get max Range
   */
  public int getMaxRange() {
    return 10 + base.getMaxRange();
  }

}
