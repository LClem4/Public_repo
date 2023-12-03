package state;

import environment.Environment;
import exceptions.EnvironmentException;
import exceptions.WeaponException;
import gameplay.TimerObserver;
import lifeform.LifeForm;
import randomlibrary.RandInt;
import lifeform.Direction;

public class AIContext implements TimerObserver {
  private ActionState currentState;
  private ActionState noWeapon = new NoWeaponState(this);
  private ActionState hasWeapon = new HasWeaponState(this);
  private ActionState outOfAmmo = new OutOfAmmoState(this);
  private ActionState dead = new DeadState(this);
  private LifeForm lifeform;
  private Environment env;
  
  /**eadState.getLifeform().getWeapon()
   * Creates an AIContext that controls a LifeForm in an environment
   * @param l The LifeForm to be controlled
   * @param e The Environment it inhabits
   */
  public AIContext(LifeForm l, Environment e) {
    lifeform = l;
    env = e;
    setState(noWeapon);
  }
  
  /**
   * @return The LifeForm this AIContext is controlling
   */
  public LifeForm getLifeForm() {
    return lifeform;
  }
  
  /**
   * @return The Environment this AIContext is operating within
   */
  public Environment getEnvironment() {
    return env;
  }
  
  /**
   * @return This AI's current state
   */
  public ActionState getCurrentState() {
    return currentState;
  }
  
  /**
   * @return A HasWeaponState instance for this AI
   */
  public ActionState getHasWeapon() {
    return hasWeapon;
  }
  
  /**
   * @return A NoWeaponState instance for this AI
   */
  public ActionState getNoWeapon() {
    return noWeapon;
  }
  
  /**
   * @return An OutOfAmmoState instance for this AI
   */
  public ActionState getOutOfAmmo() {
    return outOfAmmo;
  }
  
  /**
   * @return A DeadState instance for this AI
   */
  public ActionState getDead() {
    return dead;
  }
  
  /**
   * @param a The new ActionState to set this AI to
   */
  public void setState(ActionState a) {
    currentState = a;
  }
  
  /**
   * turn random direction and move max speed
   */
  public void search() {
    
    Direction a = lifeform.getDirection();
    Direction d = a;
    
    while (a == d) {
      int randDirection = new RandInt(0, 4).choose();
      
      if (randDirection == 0) {
        d = d.NORTH;
      } else if (randDirection == 1) {
        d = d.SOUTH;
      } else if (randDirection == 2) {
        d = d.EAST;
      } else if (randDirection == 3) {
        d = d.WEST;
      }
    }
    
    lifeform.rotate(d);
    int randMove = new RandInt(0, 1).choose();
    
    if (randMove == 0) {
      env.moveLifeFormMax(lifeform);
    }
  }
  
  public void aquireWeapon() {
    env.swapWeapon(lifeform);
  }
  
  @Override
  public void updateTime(int time) {
    lifeform.updateTime(time);
    try {
      currentState.executeAction();
    } catch (WeaponException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (EnvironmentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

