package state;

import environment.Environment;
import exceptions.EnvironmentException;
import exceptions.WeaponException;
import lifeform.LifeForm;

public abstract class ActionState {
  private AIContext context;
  private LifeForm lifeform;
  private static Environment env;
  
  /**
   * Creates an instance of an ActionState
   * @param a The AIContext this ActionState belongs to
   */
  public ActionState(AIContext a) {
    setContext(a);
    setLifeform(a.getLifeForm());
    setEnv(a.getEnvironment());
  }
  
  /**
   * Executes an action on the LifeForm depending on its
   * current state & surrounding environment
   * @throws EnvironmentException 
   * @throws WeaponException 
   */
  public abstract void executeAction() throws WeaponException, EnvironmentException;

  public AIContext getContext() {
    return context;
  }

  public void setContext(AIContext context) {
    this.context = context;
  }

  public LifeForm getLifeform() {
    return lifeform;
  }

  public void setLifeform(LifeForm lifeform) {
    this.lifeform = lifeform;
  }

  public Environment getEnv() {
    return env;
  }

  public void setEnv(Environment env) {
    this.env = env;
  }
}
