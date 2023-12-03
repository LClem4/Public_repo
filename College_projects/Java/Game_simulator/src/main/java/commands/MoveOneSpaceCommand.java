package commands;

import ui.GameBoard;

public class MoveOneSpaceCommand extends Command {
  
  public  MoveOneSpaceCommand(GameBoard gb) {
    super(gb);
  }
  
  @Override
  public void execute() {
    int[] updateCordinates = gb.getEnvironment().moveLifeForm(
      gb.getEnvironment().getLifeForm(gb.getSRow(),gb.getSCol())
    );
    gb.update(gb.getSRow(),gb.getSCol()); // updates the previous cell that was selected
    gb.update(updateCordinates[0],updateCordinates[1]); // updates new location cell of lifeform
    gb.setCor(updateCordinates[0],updateCordinates[1]); // Sets the new selected cell location
    gb.highlight();
  }
}
