package weapon;

import static org.junit.Assert.*;

import exceptions.AttachmentException;
import org.junit.Test;
import exceptions.WeaponException;

public class TestStabilizer {

  @Test
  public void testPlasmaStabilizer() throws AttachmentException, WeaponException {
    Weapon plasmaCannon = new Stabilizer(new PlasmaCannon());
    assertEquals(40, plasmaCannon.getMaxRange());
    assertEquals(4, plasmaCannon.getCurrentAmmo());
    assertEquals(62, plasmaCannon.fire(30));
    plasmaCannon.fire(10);
    assertEquals(3, plasmaCannon.getCurrentAmmo());
    plasmaCannon.fire(25);
    assertEquals(3, plasmaCannon.getCurrentAmmo());
  }

  @Test
  public void testDoubleStablizer() throws AttachmentException, WeaponException {
    Weapon plasmaCannon = new Stabilizer(new Stabilizer(new PlasmaCannon()));
    assertEquals(40, plasmaCannon.getMaxRange());
    assertEquals(77, plasmaCannon.fire(25));
  }

  @Test
  public void testPistolStabilizer() throws AttachmentException, WeaponException {
    Weapon glock = new Stabilizer(new Scope(new Pistol()));
    assertEquals(60, glock.getMaxRange());
    assertEquals(16, glock.fire(20));
  }

  @Test
  public void testChainPower() throws AttachmentException, WeaponException {
    Weapon gau12 = new Stabilizer(new PowerBooster(new ChainGun()));
    assertEquals(60, gau12.getMaxRange());
    assertEquals(30, gau12.fire(50));
  }
}
