package state;

import static org.junit.Assert.*;

import environment.Environment;
import lifeform.*;
import org.junit.Test;

public class TestAIContext {

  Environment e = Environment.getEnvironment(10, 10);
  LifeForm bob = new Human("Bob", 40, 10);
  AIContext a = new AIContext(bob, e);
  
  @Test
  public void testChangeActiveState() {
    assertTrue(a.getCurrentState() instanceof NoWeaponState);
    a.setState(a.getHasWeapon());
    assertTrue(a.getCurrentState() instanceof HasWeaponState);
  }

  @Test
  public void testGetStates() {
    assertTrue(a.getNoWeapon() instanceof NoWeaponState);
    assertTrue(a.getHasWeapon() instanceof HasWeaponState);
    assertTrue(a.getOutOfAmmo() instanceof OutOfAmmoState);
    assertTrue(a.getDead() instanceof DeadState);
  }
}
