package state;

import exceptions.EnvironmentException;
import exceptions.WeaponException;
import lifeform.Direction;
import lifeform.LifeForm;

public class HasWeaponState extends ActionState {
  AIContext action;
  
  public HasWeaponState(AIContext a) {
    super(a);
    this.action = a;
  }

  @Override
  public void executeAction() throws WeaponException, EnvironmentException {
    LifeForm attacker = action.getLifeForm();
    LifeForm defender = action.getEnvironment().findNearestEnemy(attacker);
    if (action.getLifeForm().getCurrentLifePoints() == 0) {
      action.setState(action.getDead());
    }
    if (attacker.getWeapon().getShotsLeft() > 0 && defender != null) {
      if (attacker.getSpecies() != defender.getSpecies()) {
        attacker.attack(defender, (int) action.getEnvironment().getDistance(attacker, defender));
      }
    }
    if (attacker.getWeapon().getShotsLeft() == 0 || attacker.getWeapon().getCurrentAmmo() == 0) {
      action.setState(action.getOutOfAmmo());
    }
    if (defender == null) {
      action.search();
    }
  }
}
