package weapon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import exceptions.WeaponException;

public class TestGenericWeapon {
  @Test
  public void testGenericWeaponDecrementsAmmo() {

    GenericWeapon testWeapon = new MockWeapon();
    int originalAmmoBeforeFire = testWeapon.getCurrentAmmo();

    try {
      int testShotsFired = 0;
      for (int i = 0; i < testWeapon.getRateOfFire(); i++) {
        testWeapon.fire(3);
        testShotsFired++;
        if ((testWeapon.getMaxAmmo() - testShotsFired) != testWeapon.getCurrentAmmo()) {
          System.out.println(originalAmmoBeforeFire);
          System.out.println(testWeapon.getMaxAmmo() - testShotsFired);
          System.out.println(testWeapon.getCurrentAmmo());
          fail();
        }
      }
    } catch (WeaponException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void testGenericWeaponRateOfFire() {

    GenericWeapon testWeapon = new MockWeapon();
    int originalAmmoBeforeFire = testWeapon.getCurrentAmmo();

    try {
      int testShotsFired = 0;
      for (int i = 0; i < testWeapon.getRateOfFire(); i++) {
        testWeapon.fire(3);
        testShotsFired++;
        if ((testWeapon.getMaxAmmo() - testShotsFired) != testWeapon.getCurrentAmmo()) {
          System.out.println(originalAmmoBeforeFire);
          System.out.println(testWeapon.getMaxAmmo() - testShotsFired);
          System.out.println(testWeapon.getCurrentAmmo());
          fail();
        }
      }
      assertEquals(testWeapon.fire(1), 0); // Tests no damage is done if fire is call after max shots are fired in round

    } catch (WeaponException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void testGenericWeaponReload() {
    GenericWeapon testWeapon = new MockWeapon();

    try {
      int testShotsFired = 0;
      for (int i = 0; i == testWeapon.getRateOfFire(); i++) {
        testWeapon.fire(3);
        testShotsFired++;
        if ((testWeapon.getMaxAmmo() - testShotsFired) != testWeapon.getCurrentAmmo()) {
          fail();
        }
      }
      testWeapon.reload();
      assertEquals(testWeapon.getCurrentAmmo(), 100); // Tests no damage is done if fire is call after max shots are
                                                      // fired in round

    } catch (WeaponException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void testGenericWeaponNoDamageOutOfAmmo() {
    GenericWeapon testWeapon = new MockWeapon();
    int noShotsLeft = 0;

    try {
      int testShotsFired = 0;
      for (int i = 0; i < testWeapon.getRateOfFire(); i++) {
        testWeapon.fire(3);
        testShotsFired++;
        if ((testWeapon.getMaxAmmo() - testShotsFired) == noShotsLeft) {
          assertEquals(testWeapon.fire(3), 0);
        }
      }

    } catch (WeaponException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void testGenericWeaponOutOfRangeFire() {
    GenericWeapon testWeapon = new MockWeapon();
    int oneShotLeft = 1;
    int outOfRangeDistance = 2000;

    try {
      int testShotsFired = 0;
      for (int i = 0; i < testWeapon.getRateOfFire(); i++) {
        assertEquals(0, testWeapon.fire(outOfRangeDistance));
        testShotsFired++;
        if ((testWeapon.getMaxAmmo() - testShotsFired) != testWeapon.getCurrentAmmo()) {
          fail();
        }
      }

    } catch (WeaponException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
