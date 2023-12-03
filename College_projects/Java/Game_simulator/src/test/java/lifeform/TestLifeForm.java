package lifeform;

import static org.junit.Assert.*;
import org.junit.Test;

import exceptions.WeaponException;

/**
 * Tests the functionality provided by the LifeForm class Previous labs: Source
 * code and tests written by Andrew Abruzzese. Lab5 Source code and tests
 * written by Ryan Keser.
 *
 */
public class TestLifeForm {
  
  /**
   * Lab 6 Tests Start
   * 
   */
  
  @Test
  public void testDefaultDirection() {
    LifeForm entity = new MockLifeForm("Bob", 0);
    Direction d = null;
    d = d.NORTH;
    assertEquals(d, entity.getDirection());
  }
  
  @Test
  public void testChangeDirection() {
    LifeForm entity = new MockLifeForm("Bob", 0);
    Direction d = null;
    d = d.SOUTH;
    entity.rotate(d);
    assertEquals(d, entity.getDirection());
  }
 
  /**
   * First 3 tests are tests for lab5
   */

  /**
   * Can store valid row and col
   */
  @Test
  public void testValidRowAndCol() {
    LifeForm entity = new MockLifeForm("Bob", 0);
    entity.setLocation(3, 2);
    assertEquals(2, entity.getCol());
    assertEquals(3, entity.getRow());
  }

  /**
   * row and col initialized to -1's
   */
  @Test
  public void testIntializeRowAndCol() {
    LifeForm entity = new MockLifeForm("Bob", 0);
    assertEquals(-1, entity.getCol());
    assertEquals(-1, entity.getRow());
  }

  /**
   * row and col remain -1 if either from set Location is negative
   */
  @Test
  public void testRowAndColWithNegatives() {
    LifeForm entity = new MockLifeForm("Bob", 0);
    entity.setLocation(-3, -2);
    assertEquals(-1, entity.getCol());
    assertEquals(-1, entity.getRow());
  }

  /**
   * This is where tests from previous labs begin
   */

  /**
   * When a LifeForm is created, it should know its name and how many life points
   * it has.
   */
  @Test
  public void lifeFormInitializationTest() {
    try {
      LifeForm entity;
      entity = new MockLifeForm("Bob", 40);
    } catch (Exception e) {
      fail();
    }

  }

  /**
   * Tests initialization
   */
  @Test
  public void lifeFormNameTest() {
    LifeForm entity;
    entity = new MockLifeForm("Bob", 40);
    assertEquals("Bob", entity.getName());
    assertEquals(40, entity.getCurrentLifePoints());
  }

  /**
   * Tests hit
   */
  @Test
  public void lifeFormFirstHitTest() {
    LifeForm entity;
    entity = new MockLifeForm("Bob", 40);
    int damage = 10;
    entity.takeHit(damage);
    assertEquals(entity.getCurrentLifePoints(), 30);
  }

  /**
   * Tests hit
   */
  @Test
  public void lifeFormSecondHitTest() {
    LifeForm entity;
    entity = new MockLifeForm("Bob", 40);
    int damage = 10;
    entity.takeHit(damage);
    assertEquals(entity.getCurrentLifePoints(), 30);
    entity.takeHit(damage);
    assertEquals(entity.getCurrentLifePoints(), 20);
  }
  
  /**
   * Tests alien attacking lifeform
   * NORTH,
   SOUTH,
   EAST,
   WEST
   * @throws WeaponException
   */
  @Test
  public void testAttackLifeForm() throws WeaponException {
    LifeForm entity = new MockLifeForm("Bob", 40);
    Alien gerbert = new Alien("Gerbert", 40); // default attack strength is 10
    gerbert.attack(entity, 3);
    assertEquals(entity.getCurrentLifePoints(), 30);
  }

  /**
   * Tests attack while dead
   * 
   * @throws WeaponException
   */
  @Test
  public void testAttackDeadLifeForm() throws WeaponException {
    LifeForm entity = new MockLifeForm("Bob", 0);
    Alien gerbert = new Alien("Gerbert", 40); // default attack strength is 10
    entity.attack(gerbert, 3);
    assertEquals(gerbert.getCurrentLifePoints(), 40);
  }

}
