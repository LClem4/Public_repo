package state;

import static org.junit.Assert.*;

import org.junit.Test;

import environment.Environment;
import exceptions.EnvironmentException;
import exceptions.WeaponException;
import lifeform.Alien;
import lifeform.Direction;
import lifeform.Human;
import weapon.MockWeapon;
import weapon.Pistol;
import weapon.Weapon;

public class testHasWeaponState {

  @Test
  public void testNoTarget() throws WeaponException, EnvironmentException {
    Environment environment = Environment.getEnvironment(3, 3);
    environment.clearBoard();
    Human human = new Human("Bob", 40, 0);
    environment.addLifeForm(human, 1, 1);
    Pistol pistol = new Pistol();
    human.pickUpWeapon(pistol);
    AIContext action = new AIContext(human, environment);
    ActionState weapon= action.getHasWeapon();
    action.setState(weapon);
    assertEquals(weapon, action.getCurrentState());
    weapon.executeAction();
    Direction d = null;
    assertNotEquals(d.NORTH,action.getLifeForm().getDirection());
  }
  @Test
  public void testforSameSpecies() throws WeaponException, EnvironmentException {
    Environment environment = Environment.getEnvironment(3, 3);
    environment.clearBoard();
    Human human1 = new Human("Bob", 40, 0);
    Human human2 = new Human("Bib", 40, 0);
    environment.addLifeForm(human1, 1, 1);
    environment.addLifeForm(human2, 1, 0);
    Pistol pistol = new Pistol();
    human1.pickUpWeapon(pistol);
    AIContext action = new AIContext(human1, environment);
    ActionState weapon= action.getHasWeapon();
    action.setState(weapon);
    assertEquals(weapon, action.getCurrentState());
    weapon.executeAction();
    assertEquals(40, human2.getCurrentLifePoints());
  }
  @Test
  public void testforDifferentSpecies() throws WeaponException, EnvironmentException {
    Environment environment = Environment.getEnvironment(3, 3);
    environment.clearBoard();
    Human human1 = new Human("Bob", 40, 0);
    Alien Alien = new Alien("Bib", 40);
    environment.addLifeForm(human1, 1, 1);
    environment.addLifeForm(Alien, 1, 0);
    Pistol pistol = new Pistol();
    human1.pickUpWeapon(pistol);
    AIContext action = new AIContext(human1, environment);
    ActionState weapon= action.getHasWeapon();
    action.setState(weapon);
    assertEquals(weapon, action.getCurrentState());
    weapon.executeAction();
    assertEquals(29, Alien.getCurrentLifePoints());
  }
  @Test
  public void testForOneShot() throws WeaponException, EnvironmentException {
    Environment environment = Environment.getEnvironment(3, 3);
    environment.clearBoard();
    Human human1 = new Human("Bob", 40, 0);
    Alien Alien = new Alien("Bib", 40);
    environment.addLifeForm(human1, 1, 1);
    environment.addLifeForm(Alien, 1, 0);
    MockWeapon gun= new MockWeapon();
    gun.setShotsLeft(1);
    human1.pickUpWeapon(gun);
    AIContext action = new AIContext(human1, environment);
    ActionState weapon= action.getHasWeapon();
    action.setState(weapon);
    assertEquals(weapon, action.getCurrentState());
    weapon.executeAction();
    assertEquals(25, Alien.getCurrentLifePoints());
  }
  @Test
  public void testforOutOfRange() throws WeaponException, EnvironmentException {
    Environment environment = Environment.getEnvironment(20, 20);
    environment.clearBoard();
    Human human1 = new Human("Bob", 40, 0);
    Alien Alien = new Alien("Bib", 40);
    environment.addLifeForm(human1, 1, 19);
    environment.addLifeForm(Alien, 1, 0);
    MockWeapon gun= new MockWeapon();
    gun.setShotsLeft(1);
    human1.pickUpWeapon(gun);
    AIContext action = new AIContext(human1, environment);
    ActionState weapon= action.getHasWeapon();
    action.setState(weapon);
    assertEquals(weapon, action.getCurrentState());
    weapon.executeAction();
    assertEquals(40, Alien.getCurrentLifePoints());
  }
  
  @Test
  public void testForDead() throws WeaponException, EnvironmentException {
    Environment environment = Environment.getEnvironment(3, 3);
    environment.clearBoard();
    Human human1 = new Human("Bob", 40, 0);
    Alien Alien = new Alien("Bib", 40);
    human1.setCurrentLifePoints(0);
    environment.addLifeForm(human1, 1, 1);
    environment.addLifeForm(Alien, 1, 0);
    MockWeapon gun= new MockWeapon();
    gun.setShotsLeft(1);
    human1.pickUpWeapon(gun);
    AIContext action = new AIContext(human1, environment);
    ActionState weapon= action.getHasWeapon();
    action.setState(weapon);
    weapon.executeAction();
    assertEquals(action.getDead(), action.getCurrentState());
    
  }

}
