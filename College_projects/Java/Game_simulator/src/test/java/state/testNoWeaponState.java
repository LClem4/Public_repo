package state;

import static org.junit.Assert.*;

import org.junit.Test;

import environment.Environment;
import lifeform.Direction;
import lifeform.Human;
import weapon.MockWeapon;

public class testNoWeaponState {

  @Test
  public void testPickUpWeaponInCell() {
    Environment e = Environment.getEnvironment(3, 3);
    e.clearBoard();
    e.resizeEnvironment(3, 3);
    Human h = new Human("Bob", 40, 0);
    h.setCurrentLifePoints(10);
    MockWeapon w = new MockWeapon();
    e.addWeapon(w, 1, 1);
    e.addLifeForm(h, 1, 1);
    AIContext a = new AIContext(h, e);
    NoWeaponState n = new NoWeaponState(a);
    a.setState(n);
    assertEquals(n, a.getCurrentState());
    n.executeAction();
    
    assertEquals(a.getCurrentState(), a.getHasWeapon());
  }
  
  @Test
  public void testNoWeaponSoMoves() {
    Environment e = Environment.getEnvironment(3, 3);
    e.clearBoard();
    e.resizeEnvironment(3, 3);
    Human h = new Human("Bob", 40, 0);
    e.addLifeForm(h, 1, 1);
    Direction d = null;
    AIContext a = new AIContext(h, e);
    NoWeaponState n = new NoWeaponState(a);
    a.setState(n);
    assertEquals(n, a.getCurrentState());
    n.executeAction();
    
    assertNotEquals(h.getDirection(), d.NORTH);
  }
  
  @Test
  public void testNoWeaponButDead() {
    Environment e = Environment.getEnvironment(3, 3);
    Human h = new Human("Bob", 40, 0);
    h.setCurrentLifePoints(0);
    e.addLifeForm(h, 1, 1);
    AIContext a = new AIContext(h, e);
    NoWeaponState n = new NoWeaponState(a);
    a.setState(n);
    assertEquals(n, a.getCurrentState());
    n.executeAction();
    assertEquals(a.getCurrentState(), a.getDead());
  }

}
