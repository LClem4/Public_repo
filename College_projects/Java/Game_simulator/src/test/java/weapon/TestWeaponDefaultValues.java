package weapon;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestWeaponDefaultValues {
  // Checks Default Values for each subclass of GenericWeapon

  @Test
  public void testInitalizationGeneric() {
    // Default Values
    int testBaseDamage = 10;
    int testMaxRange = 10; // in feet
    int testRateOfFire = 3;
    int testMaxAmmo = 100;

    GenericWeapon testWeapon = new MockWeapon();
    assertEquals(testBaseDamage, testWeapon.getBaseDamage());
    assertEquals(testMaxRange, testWeapon.getMaxRange());
    assertEquals(testRateOfFire, testWeapon.getRateOfFire());
    assertEquals(testMaxAmmo, testWeapon.getMaxAmmo());

  }

  @Test
  public void testInitalizationPistol() {
    // Default Values
    int testBaseDamage = 10;
    int testMaxRange = 50; // in feet
    int testRateOfFire = 2;
    int testMaxAmmo = 10;

    GenericWeapon testWeapon = new Pistol();
    assertEquals(testBaseDamage, testWeapon.getBaseDamage());
    assertEquals(testMaxRange, testWeapon.getMaxRange());
    assertEquals(testRateOfFire, testWeapon.getRateOfFire());
    assertEquals(testMaxAmmo, testWeapon.getMaxAmmo());

  }

  @Test
  public void testInitalizationChainGun() {
    // Default Values
    int testBaseDamage = 15;
    int testMaxRange = 60; // in feet
    int testRateOfFire = 4;
    int testMaxAmmo = 40;

    GenericWeapon testWeapon = new ChainGun();
    assertEquals(testBaseDamage, testWeapon.getBaseDamage());
    assertEquals(testMaxRange, testWeapon.getMaxRange());
    assertEquals(testRateOfFire, testWeapon.getRateOfFire());
    assertEquals(testMaxAmmo, testWeapon.getMaxAmmo());

  }

  @Test
  public void testInitalizationPlasmaCannon() {
    // Default Values
    int testBaseDamage = 50;
    int testMaxRange = 40; // in feet
    int testRateOfFire = 1;
    int testMaxAmmo = 4;

    GenericWeapon testWeapon = new PlasmaCannon();

    assertEquals(testBaseDamage, testWeapon.getBaseDamage());
    assertEquals(testMaxRange, testWeapon.getMaxRange());
    assertEquals(testRateOfFire, testWeapon.getRateOfFire());
    assertEquals(testMaxAmmo, testWeapon.getMaxAmmo());

  }

}
