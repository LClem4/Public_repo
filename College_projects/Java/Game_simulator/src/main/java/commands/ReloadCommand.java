package commands;

import ui.GameBoard;

public class ReloadCommand extends Command {
   
  public ReloadCommand(GameBoard gb) {
    super(gb);
  }
  
  public void execute() {
    gb.getLifeForm().getWeapon().reload();
  }
}
