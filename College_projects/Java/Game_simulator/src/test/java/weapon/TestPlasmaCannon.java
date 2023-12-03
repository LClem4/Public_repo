package weapon;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.WeaponException;

public class TestPlasmaCannon {

  @Test
  public void testDamageAtDistanceOne_PlasmaCannon() {
    // Default Values
    int testBaseDamage = 50;
    int testMaxRange = 40; // in feet
    int testRateOfFire = 1;
    int testMaxAmmo = 4;
    int testDistance = 10;

    GenericWeapon testWeapon = new PlasmaCannon();

    int baseDamageAtTestDistanceCalculation = (int) ((double) testBaseDamage
        * ((double) (3 + 1) / (double) testMaxAmmo)); // Will only test first shot

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
  public void testDamageAtDistanceTwo_PlasmaCannonOutOfBounds() {
    // Default Values
    int testBaseDamage = 50;
    int testMaxRange = 40; // in feet
    int testRateOfFire = 1;
    int testMaxAmmo = 4;
    int testDistance = 150;

    GenericWeapon testWeapon = new PlasmaCannon();
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
