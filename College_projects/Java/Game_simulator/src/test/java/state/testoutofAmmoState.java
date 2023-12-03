package state;

import static org.junit.Assert.*;

import org.junit.Test;

import environment.Environment;
import exceptions.WeaponException;
import lifeform.Human;
import weapon.MockWeapon;

public class testoutofAmmoState {
  
  @Test
  public void testInitialized() throws WeaponException {
    Environment e = Environment.getEnvironment(3, 3);
    e.clearBoard();
    Human h = new Human("Bob", 40, 0);
    e.addLifeForm(h, 1, 1);
    MockWeapon w = new MockWeapon();
    e.addWeapon(w, 1, 1);
    h.pickUpWeapon(w);
    AIContext a = new AIContext(h, e);
    OutOfAmmoState n = new OutOfAmmoState(a);
    a.setState(n);
    assertEquals(n, a.getCurrentState());
    assertTrue(h.hasWeapon());
  }
  
  @Test
  public void testReload() throws WeaponException {
    Environment e = Environment.getEnvironment(3, 3);
    e.clearBoard();
    Human h = new Human("Bob", 40, 0);
    e.addLifeForm(h, 1, 1);
    MockWeapon w = new MockWeapon();
    e.addWeapon(w, 1, 1);
    h.pickUpWeapon(w);
    
    while(w.getCurrentAmmo() > 0) {
      w.fireShot();
    }
    
    AIContext a = new AIContext(h, e);
    OutOfAmmoState n = new OutOfAmmoState(a);
    a.setState(n);
    n.executeAction();
    
    assertEquals(100, w.getCurrentAmmo());
  }
  
  
  @Test
  public void testMovesToCorrectState() throws WeaponException {
    Environment e = Environment.getEnvironment(3, 3);
    e.clearBoard();
    Human h = new Human("Bob", 40, 0);
    e.addLifeForm(h, 1, 1);
    MockWeapon w = new MockWeapon();
    e.addWeapon(w, 1, 1);
    h.pickUpWeapon(w);
    
    while(w.getCurrentAmmo() > 0) {
      w.fireShot();
    }
    
    AIContext a = new AIContext(h, e);
    OutOfAmmoState n = new OutOfAmmoState(a);
    a.setState(n);
    n.executeAction();
    
    assertEquals(a.getCurrentState(), a.getHasWeapon());
  }
  
  @Test
  public void testOutOfAmmoButDead() throws WeaponException {
    Environment e = Environment.getEnvironment(3, 3);
    e.clearBoard();
    Human h = new Human("Bob", 40, 0);
    h.setCurrentLifePoints(0);
    e.addLifeForm(h, 1, 1);
    AIContext a = new AIContext(h, e);
    OutOfAmmoState n = new OutOfAmmoState(a);
    a.setState(n);
    assertEquals(n, a.getCurrentState());
    n.executeAction();
    assertEquals(a.getCurrentState(), a.getDead());
  }

}
