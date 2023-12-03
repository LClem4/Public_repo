package commands;

import lifeform.Direction;
import ui.GameBoard;

public class RotateNorthCommand extends RotateCommand {
  public RotateNorthCommand(GameBoard gb) {
    super(gb);
    direction = Direction.NORTH;
  }
}
