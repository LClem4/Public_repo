package gameplay;

import java.util.ArrayList;
import java.lang.Thread;

public class SimpleTimer extends Thread implements Timer {
  ArrayList<TimerObserver> observerArray = new ArrayList<>();
  int currentRound = 0;
  int endOfGame = 100;
  long delay;

  public SimpleTimer() {
  }

  public SimpleTimer(int sleep) {
    this.delay = sleep;
  }

  public int getRound() {
    return this.currentRound;
  }

  public int getNumObservers() {
    return this.observerArray.size();
  }

  @Override
  public void run() {
    while (currentRound < endOfGame) {
      try {
        sleep(this.delay);
      } catch (InterruptedException e) {
        System.out.println("Fail");
      }
      timeChanged();
    }
  }

  public void addTimeObserver(TimerObserver newTimerObserver) {
    observerArray.add(newTimerObserver);
  }

  public void timeChanged() {
    currentRound++;
    observerArray.forEach(ob -> ob.updateTime(this.currentRound));
  }

  public void removeTimeObserver(TimerObserver newTimerObserver) {
    observerArray.remove(newTimerObserver);
  }
}
