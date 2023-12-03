package commands;

import exceptions.EnvironmentException;
import exceptions.WeaponException;
import ui.GameBoard;

public class RoundUpdateCommand extends Command {

  public RoundUpdateCommand(GameBoard gb) {
    super(gb);
  }
  
  public void execute() {
    // gb.getEnvironment().incrementRound();
  }
}
