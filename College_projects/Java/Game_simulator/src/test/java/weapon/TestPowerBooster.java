package weapon;

import static org.junit.Assert.*;
import exceptions.AttachmentException;
import org.junit.Test;
import exceptions.WeaponException;

public class TestPowerBooster {

  @Test
  public void testPowerChain() throws AttachmentException, WeaponException {
    Weapon m134 = new PowerBooster(new ChainGun());
    assertEquals(60, m134.getMaxRange());
    assertEquals(12, m134.fire(25));
  }

  @Test
  public void testScopePowerPistol() throws AttachmentException, WeaponException {
    Weapon magnum = new Scope(new PowerBooster(new Pistol()));
    assertEquals(60, magnum.getMaxRange());
    assertEquals(31, magnum.fire(15));
  }

  @Test
  public void testDoublePower() throws AttachmentException, WeaponException {
    Weapon bushmaster = new PowerBooster(new PowerBooster(new ChainGun()));
    assertEquals(60, bushmaster.getMaxRange());
    assertEquals(52, bushmaster.fire(55));
  }

  @Test
  public void testPlasmaPowerStablizer() throws AttachmentException, WeaponException {
    Weapon superCannon = new Stabilizer(new PowerBooster(new PlasmaCannon()));
    assertEquals(40, superCannon.getMaxRange());
    assertEquals(125, superCannon.fire(35));
  }
}
