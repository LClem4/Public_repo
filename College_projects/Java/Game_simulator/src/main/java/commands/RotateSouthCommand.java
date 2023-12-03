package commands;

import lifeform.Direction;
import ui.GameBoard;

public class RotateSouthCommand extends RotateCommand {
  public RotateSouthCommand(GameBoard gb) {
    super(gb);
    direction = Direction.SOUTH;
  }
}
