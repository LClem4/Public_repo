package randomlibrary;

import lifeform.LifeForm;

public class RandLifeForm {
  public LifeForm choose() {
    return new RandBool().choose() 
        ? new RandAlien().choose() : new RandHuman().choose();
  }
}
