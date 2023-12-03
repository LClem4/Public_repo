package randomlibrary;

import java.util.ArrayList;
import java.util.List;

public class RandList<A> implements Random<List<A>> {
  private Random<A> ra; 
  private int numberOfItems;
  
  public RandList(Random<A> r, int m) { 
    ra = r; 
    numberOfItems = m; 
  }
  /**
   * Creates a random List of items from the given random presets
   */
  
  public List<A> choose() {
    List<A> ans = new ArrayList<A>();
    for (int i = 0; i < numberOfItems; i++) {
      ans.add(ra.choose());
    }
    return ans;
  }
}
