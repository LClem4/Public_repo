package commands;

import lifeform.Direction;
import ui.GameBoard;

public class RotateCommand extends Command {
  Direction direction = Direction.NORTH;
  
  public RotateCommand(GameBoard gb) {
    super(gb);
  }
  
  public void execute() {
    gb.getLifeForm().rotate(direction);
    gb.update(gb.getSRow(), gb.getSCol());
  }
}
