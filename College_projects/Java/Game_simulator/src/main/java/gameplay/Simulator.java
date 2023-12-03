package gameplay;

import ui.GameBoard;

import exceptions.AttachmentException;
import exceptions.EnvironmentException;
import exceptions.WeaponException;
import lifeform.Human;
import lifeform.Alien;
import lifeform.LifeForm;
import randomlibrary.RandAlien;
import randomlibrary.RandHuman;
import randomlibrary.RandInt;
import randomlibrary.RandList;
import randomlibrary.RandWeapon;
import weapon.Weapon;

import state.AIContext;

import java.util.List;

public class Simulator implements TimerObserver {
  int numHumans;
  int numAliens;
  AIContext[] humanAis;
  AIContext[] alienAis;
  SimpleTimer timer;
  GameBoard gameboard;
  int randomListLength = 100000; // Length of list of randomly generated numbers
  int curRandomInt = 0; // The current index of the list
  static int simulationSpeed = 1000;
  public static int humanScore = 0;
  public static int alienScore = 0;
  
  /**
   * Create simulator
   * 
   * @param gb
   * @param s
   * @param numH
   * @param numA
   */
  public Simulator(GameBoard gb, SimpleTimer s, int numH, int numA) {
    gameboard = gb;
    
    int rows = gameboard.getRow();
    int cols = gameboard.getCol();
    int boardSize = rows * cols;
    
    timer = s;
    numHumans = Math.max(0, Math.min(numH, boardSize));
    numAliens = Math.max(0, Math.min(numA, boardSize - numHumans));
    humanAis = new AIContext[numHumans];
    alienAis = new AIContext[numAliens];
    
    RandList<Human> listHumansTemplate = new RandList<Human>(new RandHuman(), numHumans);
    List<Human> listHumans = listHumansTemplate.choose();
    RandList<Alien> listAliensTemplate = new RandList<Alien>(new RandAlien(), numAliens);
    List<Alien> listAliens = listAliensTemplate.choose();
    
    for (int i = 0; i < humanAis.length; i++) {
      humanAis[i] = new AIContext(listHumans.get(i), gameboard.getEnvironment());
      timer.addTimeObserver(humanAis[i]);
    }
    for (int i = 0; i < alienAis.length; i++) {
      alienAis[i] = new AIContext(listAliens.get(i), gameboard.getEnvironment());
      timer.addTimeObserver(alienAis[i]);
    }
    
    RandList<Integer> listIntTemplate = new RandList<Integer>(new RandInt(0, rows), randomListLength);
    List<Integer> listRows = listIntTemplate.choose();
    List<Integer> listCols = listIntTemplate.choose();
    RandList<Weapon> listWeaponTemplate = new RandList<Weapon>(new RandWeapon(),
        numAliens + numHumans);
    List<Weapon> listWeapons = listWeaponTemplate.choose();
    
    // Adds Humans
    int i = 0;
    while (i < numHumans && i < boardSize) {
      int row = listRows.get(curRandomInt).intValue(); // Gets next random row
      int col = listCols.get(curRandomInt).intValue(); // Next random col
      LifeForm entity = humanAis[i].getLifeForm(); // Next random human
      
      if (!gameboard.getEnvironment().hasLifeForm(row, col)) { // attempt to add
        gameboard.getEnvironment().addLifeForm(entity, row, col);
        gameboard.update(row, col); // updates cell
        i++;
      }
      
      if (curRandomInt < randomListLength - 1) {
        curRandomInt++;
      } else {
        curRandomInt = 0;
      }
      
    }
    
    // Adds Aliens with comments same as Human
    i = 0;
    while (i < numAliens && i < boardSize - numHumans) {
      int row = listRows.get(curRandomInt).intValue();
      int col = listCols.get(curRandomInt).intValue();
      LifeForm entity = alienAis[i].getLifeForm();
      
      if (!gameboard.getEnvironment().hasLifeForm(row, col)) {
        gameboard.getEnvironment().addLifeForm(entity, row, col);
        gameboard.update(row, col);
        i++;
      }
      
      if (curRandomInt < randomListLength - 1) {
        curRandomInt++;
      } else {
        curRandomInt = 0;
      }
    }
    
    // Adds weapons
    i = 0;
    while (i < numAliens + numHumans && i < boardSize) {
      int row = listRows.get(curRandomInt).intValue();
      int col = listCols.get(curRandomInt).intValue();
      Weapon weapon = listWeapons.get(i);
      
      boolean added = gameboard.getEnvironment().addWeapon(weapon, row, col);
      if (added) {
        i++;
        gameboard.update(row, col);
      }
      
      if (curRandomInt < randomListLength - 1) {
        curRandomInt++;
      } else {
        curRandomInt = 0;
      }
    }
    
    timer.addTimeObserver(this);
  }
  
  public void updateTime(int time) {
    gameboard.updateBoard();
    gameboard.updateRound(time, humanScore, alienScore);
  }
  
  /**
   * Where the program is run
   * 
   * @param args
   */
  public static void main(String[] args) {
    
    try {
      Simulator simulator = new Simulator(new GameBoard(), new SimpleTimer(simulationSpeed), 10,
          10);
      simulator.timer.run();
      
      String winText = "";
      if (alienScore > humanScore) {
        winText = "Aliens Win!";
      } else if (humanScore > alienScore) {
        winText = "Humans Win!";
      } else {
        winText = "It's a Tie!";
      }
      
      simulator.gameboard.displayWinScreen(winText);
    } catch (WeaponException e) {
      e.printStackTrace();
    } catch (AttachmentException e) {
      e.printStackTrace();
    }
    
  }
  
}
