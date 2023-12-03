package state;

import randomlibrary.RandInt;
import weapon.Weapon;
import gameplay.Simulator;

public class DeadState extends ActionState {
  AIContext action;
  
  public DeadState(AIContext a) {
    super(a);
    this.action = a;
  }
  
  @Override
  public void executeAction() {
    if (action.getLifeForm().getSpecies().contentEquals("Human")) {
      Simulator.alienScore++;
    }
    if (action.getLifeForm().getSpecies().contentEquals("Alien")) {
      Simulator.humanScore++;
    }
    if (action.getLifeForm().getWeapon() != null) {
      Weapon w = action.getLifeForm().dropWeapon();
      while (w != null) {
        int weaponRespawnRow = new RandInt(0, action.getEnvironment().getNumRows()).choose();
        int weaponRespawnCol = new RandInt(0, action.getEnvironment().getNumCols()).choose();
        if (action.getEnvironment().getWeapons(weaponRespawnRow, weaponRespawnRow)[0] == null
            || action.getEnvironment().getWeapons(weaponRespawnRow, weaponRespawnCol)[1] == null) {
          action.getEnvironment().addWeapon(w, weaponRespawnRow, weaponRespawnCol);
          w = null;
        }
      }
    }
    action.getEnvironment().removeLifeForm(action.getLifeForm().getRow(),
        action.getLifeForm().getCol());
    action.getLifeForm().setCurrentLifePoints(action.getLifeForm().getMaxLifePoints());
    boolean respawn = false;
    while (respawn == false) {
      int respawnRow = new RandInt(0, action.getEnvironment().getNumRows()).choose();
      int respawnCol = new RandInt(0, action.getEnvironment().getNumCols()).choose();
      if (action.getEnvironment().getLifeForm(respawnRow, respawnCol) == null) {
        action.getEnvironment().addLifeForm(action.getLifeForm(), respawnRow, respawnCol);
        respawn = true;
      }
    }
    action.setState(action.getNoWeapon());
  }
  
}
