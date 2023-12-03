package commands;

import lifeform.Direction;
import ui.GameBoard;

public class RotateEastCommand extends RotateCommand {
  public RotateEastCommand(GameBoard gb) {
    super(gb);
    direction = Direction.EAST;
  }
}
