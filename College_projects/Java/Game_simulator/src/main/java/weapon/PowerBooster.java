package weapon;

import exceptions.AttachmentException;
import exceptions.WeaponException;

/**
 * @author ew7860
 */
public class PowerBooster extends Attachment {

  /**
   * @param baseWeapon
   * @throws AttachmentException
   */
  public PowerBooster(Weapon baseWeapon) throws AttachmentException {
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

    double calculation1 = 1 + (double) getCurrentAmmo() / (double) getMaxAmmo();
    double calculation2 = (double) base.fire(distance) * calculation1;
    return Double.valueOf(Math.floor(calculation2)).intValue();

  }

  public String toString() {
    return base.toString() + " +PowerBooster";
  }

}
