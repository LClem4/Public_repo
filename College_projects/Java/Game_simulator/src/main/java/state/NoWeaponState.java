package state;

import lifeform.Direction;

import java.util.Random;

import environment.Environment;
import lifeform.LifeForm;
import randomlibrary.RandInt;
import weapon.Weapon;

public class NoWeaponState extends ActionState {
  
 
  AIContext action;
  
  public NoWeaponState(AIContext a) {
    super(a);
    this.action = a;
  }

  @Override
  public void executeAction() {
    // TODO: write this method
    
    // Checks if LifeForm is Dead or if there is a weapon in the Cell it can pick up
    // If there is a weapon, state changes to HasWeaponState
    // If there is no weapon, the lifeForm changes to a random direction and moves

    if (action.getLifeForm().getCurrentLifePoints() != 0) {
      action.getEnvironment().swapWeapon(action.getLifeForm());
    }
    
    if (action.getLifeForm().getCurrentLifePoints() == 0) {
      
      action.setState(action.getDead());
      
    } else if (action.getLifeForm().hasWeapon() == true) {
      
      action.aquireWeapon();
      action.setState(action.getHasWeapon());
      
    } else if (action.getLifeForm().hasWeapon() == false) {

      action.search();
        
    }
  }

}
