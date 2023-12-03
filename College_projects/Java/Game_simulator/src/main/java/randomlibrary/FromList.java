package randomlibrary;

import java.util.List;

public class FromList<A> implements Random<A> {
  List<A> choices;
  
  public FromList(List<A> l) {
    choices = l;
  }
  
  public A choose() {
    return choices.get(new RandInt(0, choices.size()).choose());
  }
}
