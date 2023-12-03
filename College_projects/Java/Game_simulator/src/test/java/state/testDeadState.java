package state;

import static org.junit.Assert.*;

import org.junit.Test;

import environment.Environment;
import exceptions.EnvironmentException;
import exceptions.WeaponException;
import lifeform.Human;
import lifeform.LifeForm;
import lifeform.MockLifeForm;
import weapon.Pistol;

public class testDeadState {

  @Test
  public void testDeadWeapon() throws WeaponException, EnvironmentException {
    Environment environment = Environment.getEnvironment(3, 3);
    environment.resizeEnvironment(3, 3);
    environment.clearBoard();
    Human human = new Human("Bob", 40, 0);
    human.setCurrentLifePoints(0);
    environment.addLifeForm(human, 1, 1);
    Pistol gun = new Pistol();
    human.pickUpWeapon(gun);
    AIContext action = new AIContext(human, environment);
    ActionState dead= action.getDead();
    ActionState no = action.getNoWeapon();
    action.setState(dead);
    assertEquals(dead, action.getCurrentState());
    dead.executeAction();
    Boolean lifeformRespawned = null;
    Boolean gunOnBoard= true;
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        if(action.getEnvironment().getLifeForm(i, j)== human) {
          lifeformRespawned=true;
        }
      }
    }
    assertTrue(lifeformRespawned);
    assertTrue(gunOnBoard);
    assertEquals(40, action.getLifeForm().getCurrentLifePoints());
    assertEquals(no, action.getCurrentState());
  }
  
  @Test
  public void testDeadNoWeapon() throws WeaponException, EnvironmentException {
    Environment environment = Environment.getEnvironment(3, 3);
    environment.resizeEnvironment(3, 3);
    environment.clearBoard();
    Human human = new Human("Bob", 40, 0);
    human.setCurrentLifePoints(0);
    environment.addLifeForm(human, 1, 1);
    AIContext action = new AIContext(human, environment);
    ActionState dead= action.getDead();
    ActionState no = action.getNoWeapon();
    action.setState(dead);
    assertEquals(dead, action.getCurrentState());
    dead.executeAction();
    Boolean testComplete = null;
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        if(action.getEnvironment().getLifeForm(i, j)== human) {
          testComplete=true;
        }
      }
    }
    assertTrue(testComplete);
    assertEquals(40, action.getLifeForm().getCurrentLifePoints());
    assertEquals(no, action.getCurrentState());
  }

}
