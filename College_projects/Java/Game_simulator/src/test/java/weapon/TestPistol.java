package weapon;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.WeaponException;

public class TestPistol {

  @Test
  public void testDamageAtDistanceOne_Pistol() {
    // Default Values
    int testBaseDamage = 10;
    int testMaxRange = 50; // in feet
    int testRateOfFire = 2;
    int testMaxAmmo = 10;
    int testDistance = 10;

    GenericWeapon testWeapon = new Pistol();

    int baseDamageAtTestDistanceCalculation = (int) ((double) testBaseDamage
        * (((double) (testMaxRange - testDistance + 10) / testMaxRange))); // Will only test first shot

    assertEquals(testBaseDamage, testWeapon.getBaseDamage());
    assertEquals(testMaxRange, testWeapon.getMaxRange());
    assertEquals(testRateOfFire, testWeapon.getRateOfFire());
    assertEquals(testMaxAmmo, testWeapon.getMaxAmmo());

    try {
      assertEquals(testWeapon.fire(testDistance), baseDamageAtTestDistanceCalculation);
    } catch (WeaponException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void testDamageAtDistanceTwo_PistolOutOfBounds() {
    // Default Values
    int testBaseDamage = 10;
    int testMaxRange = 50; // in feet
    int testRateOfFire = 2;
    int testMaxAmmo = 10;
    int testDistance = 150;

    GenericWeapon testWeapon = new Pistol();
    assertEquals(testBaseDamage, testWeapon.getBaseDamage());
    assertEquals(testMaxRange, testWeapon.getMaxRange());
    assertEquals(testRateOfFire, testWeapon.getRateOfFire());
    assertEquals(testMaxAmmo, testWeapon.getMaxAmmo());

    try {
      assertEquals(testWeapon.fire(testDistance), 0);
    } catch (WeaponException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
