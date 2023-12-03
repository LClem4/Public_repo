package gameplay;

import java.util.ArrayList;

public interface Timer {
  ArrayList<TimerObserver> observerArray = new ArrayList<>();

  static void addTimeObserver(TimerObserver newTimerObserver) {
    observerArray.add(newTimerObserver);
  }

  static void timeChanged() {
    observerArray.forEach(ob -> ob.updateTime(0));
  }

  static void removeTimeObserver(TimerObserver newTimerObserver) {
    observerArray.remove(newTimerObserver);
  }
}
