package commands;

import ui.GameBoard;

public class PickUpCommand extends Command {

  public PickUpCommand(GameBoard gb) {
    super(gb);
  }

  public void execute() {
    gb.getEnvironment().swapWeapon(gb.getLifeForm());
    gb.update(gb.getSRow(),gb.getSCol());
  }
}
