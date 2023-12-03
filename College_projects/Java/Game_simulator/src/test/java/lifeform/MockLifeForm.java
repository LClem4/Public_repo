package lifeform;

public class MockLifeForm extends LifeForm {
  public MockLifeForm(String name, int points) {
    super(name, points);
  }

  public MockLifeForm(String name, int points, int attack) {
    super(name, points);
    this.setAttackStrength(attack);
  }
}