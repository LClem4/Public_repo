package weapon;

import exceptions.AttachmentException;
import exceptions.WeaponException;

/**
 * @author ew7860
 */
public class Stabilizer extends Attachment {

  /**
   * @param baseWeapon
   * @throws AttachmentException
   */
  public Stabilizer(Weapon baseWeapon) throws AttachmentException {
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

    double increase = (double) base.fire(distance) * 1.25;

    if (getCurrentAmmo() <= 0) {
      reload();
    }

    return Double.valueOf(Math.floor(increase)).intValue();
  }

  public String toString() {
    return base.toString() + " +Stabilizer";
  }

}
