package gameplay;

import static org.junit.Assert.*;

import java.awt.HeadlessException;

import org.junit.Test;

import exceptions.AttachmentException;
import exceptions.WeaponException;
import ui.GameBoard;
import state.NoWeaponState;
import environment.Environment;

public class TestSimulator {
  
  @Test
  public void testPopulate() throws WeaponException, AttachmentException {
    try {
      Environment env = Environment.getEnvironment(10, 10);
      env.clearBoard();
      env.resizeEnvironment(10, 10);
      int numHumans = 0;
      int numAliens = 0;
      int numWeapons = 0;
      Simulator simulator = new Simulator(new GameBoard(), new SimpleTimer(), 10, 10);
      for (int i = 0; i < simulator.gameboard.getRow(); i++) {
        for (int j = 0; j < simulator.gameboard.getCol(); j++) {
          if (simulator.gameboard.getLifeForm(i, j) != null) {
            if (simulator.gameboard.getLifeForm(i, j).getSpecies().equals("Human")) {
              numHumans++;
            }
            if (simulator.gameboard.getLifeForm(i, j).getSpecies().equals("Alien")) {
              numAliens++;
            }
          }
          numWeapons += simulator.gameboard.getEnvironment().getWeaponsCount(i, j);
        }
      }
      assertEquals(10, numHumans);
      assertEquals(10, numAliens);
      assertEquals(20, numWeapons);
      
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 1: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  
  @Test
  public void testUpdate() throws WeaponException, AttachmentException {
    
    try {
      Simulator simulator = new Simulator(new GameBoard(), new SimpleTimer(), 10, 10);
      boolean stateChange = false;
      for (int i = 0; i < simulator.humanAis.length; i++) {
        assertTrue(simulator.humanAis[i].getCurrentState() instanceof NoWeaponState);
      }
      simulator.timer.timeChanged();
      simulator.timer.timeChanged();
      simulator.timer.timeChanged();
      simulator.timer.timeChanged();
      simulator.timer.timeChanged();
      for (int i = 0; i < simulator.humanAis.length; i++) {
        if (!(simulator.humanAis[i].getCurrentState() instanceof NoWeaponState)) {
          stateChange = true;
        }
      }
      assertTrue(stateChange);
      
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 1: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  
}
