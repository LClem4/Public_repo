package weapon;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.WeaponException;

public class TestChainGun {

  @Test
  public void testDamageAtDistanceOne_ChainGun() {
    // Default Values
    int testBaseDamage = 15;
    int testMaxRange = 60; // in feet
    int testRateOfFire = 4;
    int testMaxAmmo = 40;
    int testDistance = 10;

    int baseDamageAtTestDistanceCalculation = (int) ((double) testBaseDamage * ((double) testDistance / testMaxRange));

    GenericWeapon testWeapon = new ChainGun();
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
  public void testDamageAtDistanceTwo_ChainGunOutOfBounds() {
    // Default Values
    int testBaseDamage = 15;
    int testMaxRange = 60; // in feet
    int testRateOfFire = 4;
    int testMaxAmmo = 40;
    int testDistance = 150;

    GenericWeapon testWeapon = new ChainGun();
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
