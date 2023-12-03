package commands;

import exceptions.EnvironmentException;
import exceptions.WeaponException;

public interface CommandInterface {
  /**
   * Executes a command.
   * @throws WeaponException
   * @throws EnvironmentException
   */
  public void execute() throws WeaponException, EnvironmentException;
}
