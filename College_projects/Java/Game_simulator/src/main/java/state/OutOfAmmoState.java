package state;

import exceptions.WeaponException;
import lifeform.LifeForm;
import weapon.Weapon;

public class OutOfAmmoState extends ActionState {
  
  protected AIContext action;
  
  public OutOfAmmoState(AIContext a) {
    super(a);
    this.action = a;
  }

  @Override
  public void executeAction() {
    
    /**
     * If Dead: sets current State to Dead
     * 
     * else: reload LifeForms weapon and sets Current State to HasWeapon
     */
    if (action.getLifeForm().getCurrentLifePoints() == 0) {
      
      action.setState(action.getDead());
      
    } else {
      
      action.getLifeForm().getWeapon().reload();
      action.setState(action.getHasWeapon());
      
    }
  }
  
  
  
  
  

}
