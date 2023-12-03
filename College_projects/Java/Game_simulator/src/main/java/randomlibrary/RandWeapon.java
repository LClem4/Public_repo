package randomlibrary;

import java.util.List;

import exceptions.AttachmentException;
import weapon.Attachment;
import weapon.ChainGun;
import weapon.GenericWeapon;
import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.PowerBooster;
import weapon.Scope;
import weapon.Stabilizer;
import weapon.Weapon;

public class RandWeapon implements Random<Weapon> {
  List<Weapon> weaponChoices = List.of(new Pistol(), new ChainGun(), new PlasmaCannon());
  
  /**
   * Luke Javadoc this
   */
  public Weapon choose() {
    int choice = new RandInt(0, 4).choose().intValue();
    int choice2 = new RandInt(0, 4).choose().intValue();
    Weapon newWeapon =  new FromList<>(weaponChoices).choose();
    if (choice == 1 || choice2 == 1) {
      try {
        newWeapon = new Scope(newWeapon);
      } catch (AttachmentException e) {
        e.printStackTrace();
      }
    }
    if (choice == 2 || choice2 == 2) {
      try {
        newWeapon = new Stabilizer(newWeapon);
      } catch (AttachmentException e) {
        e.printStackTrace();
      }
    }
    if (choice == 3 || choice2 == 3) {
      try {
        newWeapon = new PowerBooster(newWeapon);
      } catch (AttachmentException e) {
        e.printStackTrace();
      }
    }
    
    return newWeapon;
  }
}
