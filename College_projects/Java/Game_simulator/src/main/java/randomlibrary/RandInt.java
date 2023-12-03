package randomlibrary;

public class RandInt implements Random<Integer> {
  private int lo; 
  private int hi; 
  private static int seed = 9;
  
  public static java.util.Random seeded = new java.util.Random(seed);
  public static java.util.Random unseeded = new java.util.Random();
  public static boolean useSeed = true;
  
  public RandInt(int l, int h) { 
    lo = l; 
    hi = h;
  }
  
  public Integer choose() {
    Integer randomNum = (useSeed ? seeded : unseeded).nextInt(hi - lo) + lo;
    return randomNum;
  }
}
