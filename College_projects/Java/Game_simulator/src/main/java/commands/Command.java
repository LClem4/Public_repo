package commands;

import exceptions.EnvironmentException;
import exceptions.WeaponException;
import ui.GameBoard;

public abstract class Command implements CommandInterface {
  GameBoard gb;
    
  public Command(GameBoard gb) {
    this.gb = gb;
  }
    
  public abstract void execute() throws WeaponException, EnvironmentException;
}
