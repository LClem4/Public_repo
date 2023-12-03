package lifeform;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the functionality provided by the LifeForm class
 *
 */
public class TestHuman {
  
  /**
   * Lab 6 Tests Start
   * 
   */
  
  @Test
  public void HumanMaxSpeed() {
    Human entity;
    int armorPoints = 10;
    entity = new Human("Bob", 40, armorPoints);
    assertEquals(3, entity.getMaxSpeed());
  }
  
  /**
   * When a LifeForm is created, it should know its name and how many life points
   * it has.
   */
  @Test
  public void HumanInitializationTest() {
    try {
      LifeForm entity = new Human("Bob", 40, 2);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void HumanSetArmorPointsTest() {
    Human entity;
    int armorPoints = 10;
    entity = new Human("Bob", 40, armorPoints);
    int newArmorPoints = 30;
    entity.setArmorPoints(newArmorPoints);
    assertEquals(entity.getArmorPoints(), newArmorPoints);
  }

  @Test
  public void HumanGetArmorPointsTest() {
    Human entity;
    int armorPoints = 10;
    entity = new Human("Bob", 40, armorPoints);
    int newArmorPoints = 30;
    entity.setArmorPoints(newArmorPoints);
    assertEquals(entity.getArmorPoints(), newArmorPoints);
  }

  @Test
  public void ArmorPointsLessThanZeroTest() {
    Human entity;
    int armorPoints = -1;
    entity = new Human("Bob", 40, armorPoints);
    assertEquals(entity.getArmorPoints(), 0);
  }

  @Test
  public void testDefaultAttack() {
    Human Alan = new Human("Alan", 20, 5);
    assertEquals(5, Alan.getAttackStrength());
  }

  @Test
  public void testArmorMoreThanDamage() {
    int damage = 10;
    int armor = 15;
    Human tony = new Human("Tony", 50, armor);
    tony.takeHit(damage);
    assertEquals(5, tony.getArmorPoints());
    assertEquals(50, tony.getCurrentLifePoints());
  }

  @Test
  public void testArmorLessThanDamage() {
    int damage = 15;
    int armor = 10;
    Human tony = new Human("Tony", 50, armor);
    tony.takeHit(damage);
    assertEquals(0, tony.getArmorPoints());
    assertEquals(45, tony.getCurrentLifePoints());
  }

  @Test
  public void testArmorEqualToDamage() {
    int damage = 10;
    int armor = 10;
    Human tony = new Human("Tony", 50, armor);
    tony.takeHit(damage);
    assertEquals(0, tony.getArmorPoints());
    assertEquals(50, tony.getCurrentLifePoints());
  }

}