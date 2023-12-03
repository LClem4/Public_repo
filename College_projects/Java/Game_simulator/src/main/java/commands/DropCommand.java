package commands;

import ui.GameBoard;

public class DropCommand extends Command {
  
  public DropCommand(GameBoard gb) {
    super(gb);
  }
  
  public void execute() {
    gb.getEnvironment().dropWeapon(gb.getLifeForm());
    gb.update(gb.getSRow(), gb.getSCol());
  }
}
