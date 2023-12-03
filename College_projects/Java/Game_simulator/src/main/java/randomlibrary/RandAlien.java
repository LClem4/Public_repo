package randomlibrary;

import java.util.List;

import lifeform.Alien;

public class RandAlien implements Random<Alien> {
  List<String> nameChoices = List.of("E.T.", "Xenomorph", "Zoiberg", "Roger");
  
  /**
   * 
   */
  public Alien choose() {
    return new Alien(new FromList<>(nameChoices).choose(),
                     new RandInt(30, 50).choose(),
                     new RandRecovery().choose());
  }
}
