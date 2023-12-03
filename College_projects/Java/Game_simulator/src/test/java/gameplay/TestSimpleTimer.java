package gameplay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import gameplay.SimpleTimer;
import gameplay.Timer;
import gameplay.TimerObserver;
import lifeform.Alien;

public class TestSimpleTimer {

  @Test
  public void testInitalization() {
    try {
      SimpleTimer t1 = new SimpleTimer(300);
    } catch (Exception e) {
      fail();
    }

  }

  @Test
  public void testTimeAccuracyr() throws InterruptedException {
    SimpleTimer st = new SimpleTimer(1000);
    st.start();
    Thread.sleep(250); // So we are 1/4th a second different
    for (int x = 0; x < 5; x++) {
      assertEquals(x, st.getRound()); // assumes round starts at 0
      Thread.sleep(1000); // wait for the next time change
    }
  }

  @Test
  public void testThreeObservers() {
    SimpleTimer timer = new SimpleTimer(50);

    Alien alOne = new Alien("Bob", 40);
    Alien alTwo = new Alien("Tony", 40);
    Alien alThree = new Alien("Tim", 40);

    try {
      timer.addTimeObserver(alOne);
      timer.addTimeObserver(alTwo);
      timer.addTimeObserver(alThree);

      timer.start();
      System.out.println(timer.getNumObservers());
      timer.sleep(500);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void testUpdateToObservers() {
    SimpleTimer timer = new SimpleTimer(50);
    Alien alOne = new Alien("Bob", 40);
    timer.addTimeObserver(alOne);

    timer.start();
    System.out.println(timer.getNumObservers());
    try {
      timer.sleep(500);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void testSimpleTimerAsThread() throws InterruptedException {
    SimpleTimer st = new SimpleTimer(1000);
    st.start();
    Thread.sleep(250); // So we are 1/4th a second different
    for (int x = 0; x < 5; x++) {
      assertEquals(x, st.getRound()); // assumes round starts at 0
      Thread.sleep(1000); // wait for the next time change
    }
  }

}
