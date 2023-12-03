package weapon;

import static org.junit.Assert.*;
import exceptions.AttachmentException;
import org.junit.Test;
import exceptions.WeaponException;

public class TestScope {

  @Test
  public void testPistolScope() throws AttachmentException, WeaponException {
    Weapon luger = new Scope(new Pistol());
    assertEquals(60, luger.getMaxRange());
    assertEquals(13, luger.fire(20));
  }

  @Test
  public void testDoubleScope() throws AttachmentException, WeaponException {
    Weapon deagle = new Scope(new Scope(new Pistol()));
    assertEquals(70, deagle.getMaxRange());
    assertEquals(22, deagle.fire(20));
  }

  @Test
  public void testScopeChainGun() throws AttachmentException, WeaponException {
    Weapon gau8 = new PowerBooster(new Scope(new ChainGun()));
    assertEquals(70, gau8.getMaxRange());
    assertEquals(28, gau8.fire(40));
    gau8.reload();
    assertEquals(40, gau8.fire(65));
  }

  @Test
  public void testScopePlasma() throws AttachmentException, WeaponException {
    Weapon spearOfCain = new Scope(new Stabilizer(new PlasmaCannon()));
    assertEquals(50, spearOfCain.getMaxRange());
    assertEquals(67, spearOfCain.fire(45));
  }
}
