package lifeform;

import static org.junit.Assert.*;
import org.junit.Test;

import exceptions.RecoveryRateException;
import exceptions.WeaponException;
import gameplay.SimpleTimer;
import recovery.RecoveryBehavior;
import recovery.RecoveryLinear;

/**
 * Tests the functionality provided by the LifeForm class
 *
 */
public class TestAlien {
  
  /**
   * Lab 6 Tests Start
   * 
   */
  
  @Test
  public void AlienMaxSpeed() {
    LifeForm entity;
    entity = new Alien("Tom", 40);
    assertEquals(2, entity.getMaxSpeed());
  }
  
  /**
   * When a LifeForm is created, it should know its name and how many life points
   * it has.
   * 
   * @throws RecoveryRateException
   */
  @Test
  public void AlienInitializationTest() throws RecoveryRateException {
    LifeForm entity;

    entity = new Alien("Bob", 40);
    assertEquals("Bob", entity.getName());
    assertEquals(40, entity.getCurrentLifePoints());
  }

  @Test
  public void AlienRecoveryInitalizationTest() throws RecoveryRateException {
    LifeForm entity;
    RecoveryBehavior x = new RecoveryLinear(0);
    entity = new Alien("Bob", 40, x, 3);
    assertEquals("Bob", entity.getName());
    assertEquals(40, entity.getCurrentLifePoints());
  }

  @Test
  public void testDefaultAttackStrength() throws WeaponException {
    Alien tom = new Alien("Tom", 40);
    Human tony = new Human("Tom", 40, 2);
    tony.attack(tom, 3);
    assertEquals(10, tom.getAttackStrength());
  }

  @Test
  public void testSetRecovery() throws RecoveryRateException {
    LifeForm entity;
    RecoveryBehavior x = new RecoveryLinear(0);
    entity = new Alien("Bob", 40, x, 3);
    assertEquals("Bob", entity.getName());
    assertEquals(40, entity.getCurrentLifePoints());
  }

  @Test
  public void testSetRecoveryZero() throws RecoveryRateException, InterruptedException {
    Alien entity;
    RecoveryBehavior x = new RecoveryLinear(3);
    entity = new Alien("Bob", 40, x, 0);
    entity.takeHit(10);
    SimpleTimer timer = new SimpleTimer(1000);
    timer.addTimeObserver(entity);
    timer.start();

    Thread.sleep(250);
    for (int i = 0; i < 3; i++) {
      assertEquals(30, entity.getCurrentLifePoints());
    }
    assertEquals("Bob", entity.getName());

  }

  @Test
  public void testSetRecoveryGreaterThanZero() throws RecoveryRateException, InterruptedException {
    Alien entity;
    RecoveryBehavior x = new RecoveryLinear(3);
    entity = new Alien("Bob", 40, x, 2);
    entity.takeHit(10);

    SimpleTimer st = new SimpleTimer(1000);
    st.addTimeObserver(entity);
    st.start();
    Thread.sleep(250); // So we are 1/4th a second different
    int lifePointsCompare = 0;
    for (int i = 0; i < 5; i++) {
      if (i == 3) {
        assertEquals(33, entity.getCurrentLifePoints());
      }
      assertEquals(entity.getCurrentRound(), st.getRound()); // assumes round starts at 0
      Thread.sleep(1000); // wait for the next time change

    }
    assertEquals(36, entity.getCurrentLifePoints());
    assertEquals("Bob", entity.getName());
  }

  @Test
  public void testSetRecoveryGreaterThanZeroTwo() throws RecoveryRateException, InterruptedException {
    Alien entity;
    RecoveryBehavior x = new RecoveryLinear(5);
    entity = new Alien("Bob", 40, x, 5);
    entity.takeHit(10);

    SimpleTimer st = new SimpleTimer(1000);
    st.addTimeObserver(entity);
    st.start();
    Thread.sleep(250); // So we are 1/4th a second different
    for (int i = 0; i < 10; i++) {
      if (i == 5) {
        assertEquals(35, entity.getCurrentLifePoints());
      }
      assertEquals(entity.getCurrentRound(), st.getRound()); // assumes round starts at 0
      Thread.sleep(1000); // wait for the next time change

    }
    assertEquals(40, entity.getCurrentLifePoints());
    assertEquals("Bob", entity.getName());
  }

  @Test
  public void testRemoveObserverNoRecovery() throws RecoveryRateException, InterruptedException {
    Alien entity;
    RecoveryBehavior x = new RecoveryLinear(5);
    entity = new Alien("Bob", 40, x, 3);
    entity.takeHit(10);

    SimpleTimer st = new SimpleTimer(1000);
    st.addTimeObserver(entity);
    st.start();
    Thread.sleep(250); // So we are 1/4th a second different
    for (int i = 0; i < 6; i++) {
      if (i == 3) {
        assertEquals(35, entity.getCurrentLifePoints());
        st.removeTimeObserver(entity);
      }
      Thread.sleep(1000); // wait for the next time change

    }
    assertEquals(35, entity.getCurrentLifePoints());
    assertEquals("Bob", entity.getName());
  }

  @Test
  public void testObserverTrackRoundsOverTime() throws RecoveryRateException, InterruptedException {

    SimpleTimer st = new SimpleTimer(1000);
    Alien tom = new Alien("Tom", 40);
    st.addTimeObserver(tom);
    st.start();
    Thread.sleep(250); // So we are 1/4th a second different
    for (int x = 0; x < 5; x++) {
      assertEquals(tom.getCurrentRound(), st.getRound()); // assumes round starts at 0
      Thread.sleep(1000); // wait for the next time change
    }
  }

}