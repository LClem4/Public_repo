package randomlibrary;

public class RandBool implements Random<Boolean> {
  public Boolean choose() {
    return new RandInt(0, 2).choose() == 0 ? true : false;
  }
}
