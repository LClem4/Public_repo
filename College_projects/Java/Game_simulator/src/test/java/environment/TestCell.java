package environment;

import static org.junit.Assert.*;
import org.junit.Test;
import lifeform.LifeForm;
import lifeform.MockLifeForm;
import weapon.ChainGun;
import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.Weapon;

/**
 * The test cases for the Cell class Previous labs: Source code and tests
 * written by Andrew Abruzzese. Lab5 Source code and tests written by Noah Hays.
 *
 */
public class TestCell {

  /**
   * First 4 tests are tests for lab5
   */

  /**
   * At initialization, the Cell should be empty and not contain a LifeForm.
   */
  @Test
  public void cellInitializationTest() {
    Cell cell = new Cell();
    assertNull(cell.getLifeForm());
    assertEquals(0, cell.getWeaponsCount());
    assertNull(cell.getWeapon1());
    assertNull(cell.getWeapon2());
  }

  /**
   * an add one or two weapons
   */
  @Test
  public void canAddWeapons() {
    Weapon weapon1 = new Pistol();
    Weapon weapon2 = new ChainGun();
    Weapon weapon3 = new PlasmaCannon();
    Cell newCell = new Cell();
    boolean pass = newCell.addWeapon(weapon1);
    assertTrue(pass);
    assertEquals(1, newCell.getWeaponsCount());
    pass = newCell.addWeapon(weapon2);
    assertTrue(pass);
    assertEquals(2, newCell.getWeaponsCount());
    pass = newCell.addWeapon(weapon3);
    assertFalse(pass);
    assertEquals(2, newCell.getWeaponsCount());
  }

  /**
   * can remove one or two weapons
   */
  @Test
  public void canRemoveWeapons() {
    Weapon weapon1 = new Pistol();
    Weapon weapon2 = new ChainGun();
    Cell newCell = new Cell();
    newCell.addWeapon(weapon1);
    newCell.addWeapon(weapon2);
    assertEquals(weapon2, newCell.removeWeapon(weapon2));
    assertEquals(1, newCell.getWeaponsCount());
    assertEquals(weapon1, newCell.removeWeapon(weapon1));
    assertEquals(0, newCell.getWeaponsCount());
  }

  /**
   * cannot place a weapon in a full cell
   */
  @Test
  public void cannotAddMoreThanTwoWeapons() {
    Weapon weapon1 = new Pistol();
    Weapon weapon2 = new ChainGun();
    Weapon weapon3 = new PlasmaCannon();
    Cell newCell = new Cell();
    newCell.addWeapon(weapon1);
    newCell.addWeapon(weapon2);
    boolean pass = newCell.addWeapon(weapon3);
    assertFalse(pass);
  }

  /**
   * This is where tests from previous labs begin
   */

  /**
   * Checks to see if we change the LifeForm held by the Cell that getLifeForm
   * properly responds to this change.
   */
  @Test
  public void addLifeFormTest() {
    LifeForm bob = new MockLifeForm("Bob", 40);
    LifeForm fred = new MockLifeForm("Fred", 40);
    Cell cell = new Cell();
    // The cell is empty so this should work.
    boolean success = cell.addLifeForm(bob);
    assertTrue(success);
    assertEquals(bob, cell.getLifeForm());
    // The cell is not empty so this should fail.
    success = cell.addLifeForm(fred);
    assertFalse(success);
    assertEquals(bob, cell.getLifeForm());
  }

  /**
   * Tests adding two lifeforms to cell
   */
  @Test
  public void doubleAddLifeFormTest() {
    LifeForm bob = new MockLifeForm("Bob", 40);
    LifeForm fred = new MockLifeForm("Fred", 40);
    Cell newCell = new Cell();
    boolean pass = newCell.addLifeForm(fred);
    assertTrue(pass);
    pass = newCell.addLifeForm(bob);
    assertFalse(pass);

  }

  /**
   * Tests removing lifeform from cell
   */
  @Test
  public void canRemoveLifeFormTest() {
    LifeForm fred = new MockLifeForm("Fred", 40);
    Cell newCell = new Cell();
    boolean pass = newCell.addLifeForm(fred);
    assertTrue(pass);
    assertEquals("Fred", newCell.getLifeForm().getName());
    newCell.removeLifeForm();
    pass = newCell.hasLifeForm();
    assertFalse(pass);

  }

}