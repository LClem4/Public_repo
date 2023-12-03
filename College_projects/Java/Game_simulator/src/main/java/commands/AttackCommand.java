package commands;

import exceptions.EnvironmentException;
import exceptions.WeaponException;
import ui.GameBoard;

public class AttackCommand extends Command {

  public AttackCommand(GameBoard gb) {
    super(gb);
  }
  
  @Override
  public void execute() throws WeaponException, EnvironmentException {
    gb.getEnvironment().attackNearestEnemy(gb.getEnvironment().getLifeForm(
        gb.getSRow(),gb.getSCol()
    ));
  }
}
