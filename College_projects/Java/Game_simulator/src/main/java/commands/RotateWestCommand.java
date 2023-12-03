package commands;

import lifeform.Direction;
import ui.GameBoard;

public class RotateWestCommand extends RotateCommand {
  public RotateWestCommand(GameBoard gb) {
    super(gb);
    direction = Direction.WEST;
  }
}
