package randomlibrary;

import java.util.List;

import lifeform.Human;

public class RandHuman implements Random<Human> {
  List<String> nameChoices = List.of("Alice", "Bob", "Chad", "Denise");
  
  /**
   * Creates a human from a random name and with a health value from 30 to 50
   * and an armor value from 0 to 10
   */
  public Human choose() {
    return new Human(new FromList<>(nameChoices).choose(),
                     new RandInt(30, 50).choose(),
                     new RandInt(0, 10).choose());
  }
}
