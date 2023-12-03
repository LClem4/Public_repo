package environment;

import exceptions.EnvironmentException;
import exceptions.WeaponException;

import gameplay.SimpleTimer;
import gameplay.TimerObserver;

import java.util.ArrayList;

import lifeform.LifeForm;

import weapon.Weapon;

public class Environment {
  
  private static volatile Environment env;
  Cell[][] gridOfCells;
  int rows;
  int cols;
  
  /**
   * Creates a "Grid" of cells to the specified row x column dimensions, stores
   * dimensions to be referenced later
   * 
   * @param rows ,row position
   * @param cols ,column position
   */
  private Environment(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    gridOfCells = new Cell[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        gridOfCells[i][j] = new Cell();
      }
    }
  }
  
  /**
   * Returns a singleton instance of the environment. On first call, a new
   * instance will be created based on rows & cols. On further calls, the original
   * instance will be returned, regardless of what the new values of rows & cols
   * are.
   * 
   * @param rows Number of rows
   * @param cols Number of columns
   * @return The instance of the environment
   */
  public static synchronized Environment getEnvironment(int rows, int cols) {
    if (env == null) {
      env = new Environment(rows, cols);
    }
    return env;
  }
  
  /**
   * Resize environment
   * 
   * @param rows
   * @param cols
   */
  public void resizeEnvironment(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    gridOfCells = new Cell[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        gridOfCells[i][j] = new Cell();
      }
    }
  }
  
  /**
   * @param row ,row position
   * @param col ,column position
   * @return (LifeForm) Object at specified location using row and column
   *         parameters
   */
  public LifeForm getLifeForm(int row, int col) {
    return gridOfCells[row][col].getLifeForm();
  }
  
  /**
   * @param row ,row position
   * @param col ,column position
   * @return (boolean) true if lifeForm is in that position, else returns false
   */
  public boolean hasLifeForm(int row, int col) {
    return gridOfCells[row][col].hasLifeForm();
  }
  
  /**
   * @param row ,row position
   * @param col ,column position
   */
  public boolean outOfBounds(int row, int col) {
    if (this.rows <= row || this.cols <= col || row < 0 || col < 0) {
      return true;
    }
    return false;
  }
  
  /**
   * @param entity ,lifeForm to be added
   * @param row    ,row position
   * @param col    ,column position
   * @return (boolean) true if lifeForm was successfully added to the board,
   *         returns false false if the position is out of bounds or there is
   *         already a lifeForm assigned to that cell
   */
  
  public boolean addLifeForm(LifeForm entity, int row, int col) {
    if (outOfBounds(row, col)) {
      return false;
    }
    if (gridOfCells[row][col].hasLifeForm()) {
      return false;
    } else {
      entity.setLocation(row, col);
      gridOfCells[row][col].addLifeForm(entity);
      // timer.addTimeObserver(entity);
      return true;
    }
    
  }
  
  /**
   * Removes LifeForm from cell, sets to null, blank return if position is out of
   * bounds
   * 
   * @param row , row position
   * @param col , column position
   */
  
  public void removeLifeForm(int row, int col) {
    if (outOfBounds(row, col)) {
      return;
    }
    // timer.removeTimeObserver(gridOfCells[row][col].getLifeForm());
    gridOfCells[row][col].removeLifeForm();
  }
  
  /**
   * Move a LifeForm one space
   * 
   * @param entity The LifeForm to be moved
   * @return The new coordinates of the LifeForm
   */
  public int[] moveLifeForm(LifeForm entity) {
    boolean added = false;
    if (entity.getCurrentSpeed() > 0) {
      switch (entity.getDirection()) {
        case NORTH:
          added = addLifeForm(entity, entity.getRow(), entity.getCol() - 1);
          if (added) {
            removeLifeForm(entity.getRow(), entity.getCol() + 1);
          }
          break;
        case SOUTH:
          added = addLifeForm(entity, entity.getRow(), entity.getCol() + 1);
          if (added) {
            removeLifeForm(entity.getRow(), entity.getCol() - 1);
          }
          break;
        case EAST:
          added = addLifeForm(entity, entity.getRow() + 1, entity.getCol());
          if (added) {
            removeLifeForm(entity.getRow() - 1, entity.getCol());
          }
          break;
        case WEST:
          added = addLifeForm(entity, entity.getRow() - 1, entity.getCol());
          if (added) {
            removeLifeForm(entity.getRow() + 1, entity.getCol());
          }
          break;
        default:
          break; // Do nothing
      }
      
      if (added == true) {
        entity.decrementSpeed();
      }
    }
    
    int[] newCordinates = { entity.getRow(), entity.getCol() };
    
    return newCordinates;
  }
  
  /**
   * Move a LifeForm the max amount of spaces it is able in one direction
   * 
   * @param entity The LifeForm to be moved
   * @return The LifeForm's new coordinates
   */
  public int[] moveLifeFormMax(LifeForm entity) {
    boolean added = false;
    int i = entity.getCurrentSpeed();
    while (i > 0 && entity.getCurrentSpeed() > 0) {
      switch (entity.getDirection()) {
        case NORTH:
          added = addLifeForm(entity, entity.getRow(), entity.getCol() - i);
          if (added) {
            removeLifeForm(entity.getRow(), entity.getCol() + i);
            i = 0;
          }
          break;
        case SOUTH:
          added = addLifeForm(entity, entity.getRow(), entity.getCol() + i);
          if (added) {
            removeLifeForm(entity.getRow(), entity.getCol() - i);
            i = 0;
          }
          break;
        case EAST:
          added = addLifeForm(entity, entity.getRow() + i, entity.getCol());
          if (added) {
            removeLifeForm(entity.getRow() - i, entity.getCol());
            i = 0;
          }
          break;
        case WEST:
          added = addLifeForm(entity, entity.getRow() - i, entity.getCol());
          if (added) {
            removeLifeForm(entity.getRow() + i, entity.getCol());
            i = 0;
          }
          break;
        default:
          break; // Do nothing
      }
      i--;
    }
    
    entity.setSpeed0();
    int[] newCordinates = { entity.getRow(), entity.getCol() };
    
    return newCordinates;
  }
  
  /**
   * @return The number of rows in this environment
   */
  public int getNumRows() {
    return rows;
  }
  
  /**
   * @return The number of columns in this environment
   */
  public int getNumCols() {
    return cols;
  }
  
  /**
   * Attempts to add a weapon to the cell at given coordinates
   * 
   * @param w   The weapon to be added
   * @param row Row position
   * @param col Column position
   * @return True if the weapon was successfully added, otherwise false
   */
  public boolean addWeapon(Weapon w, int row, int col) {
    return gridOfCells[row][col].addWeapon(w);
  }
  
  /**
   * Attempts to remove a weapon from the cell at given coordinates
   * 
   * @param w   The weapon to be removed
   * @param row Row position
   * @param col Column position
   * @return The weapon removed if it was successfully removed, otherwise null
   */
  public Weapon removeWeapon(Weapon w, int row, int col) {
    return gridOfCells[row][col].removeWeapon(w);
  }
  
  /**
   * Swap weapons
   */
  public void swapWeapon(LifeForm entity) {
    Weapon weapon = null;
    if (getWeapons(entity.getRow(), entity.getCol())[0] != null) {
      weapon = removeWeapon(getWeapons(entity.getRow(), entity.getCol())[0], entity.getRow(),
          entity.getCol());
      if (entity.hasWeapon()) {
        addWeapon(entity.dropWeapon(), entity.getRow(), entity.getCol());
      }
    } else if (getWeapons(entity.getRow(), entity.getCol())[1] != null) {
      weapon = removeWeapon(getWeapons(entity.getRow(), entity.getCol())[1], entity.getRow(),
          entity.getCol());
      if (entity.hasWeapon()) {
        addWeapon(entity.dropWeapon(), entity.getRow(), entity.getCol());
      }
    }
    if (weapon != null) {
      entity.pickUpWeapon(weapon);
    }
  }
  
  /**
   * Drop Weapon
   */
  public void dropWeapon(LifeForm entity) {
    if (getWeapons(entity.getRow(), entity.getCol())[0] == null) {
      addWeapon(entity.dropWeapon(), entity.getRow(), entity.getCol());
    } else if (getWeapons(entity.getRow(), entity.getCol())[1] == null) {
      addWeapon(entity.dropWeapon(), entity.getRow(), entity.getCol());
    }
  }
  
  /**
   * Find nearest enemy
   * 
   * @throws EnvironmentException
   * @throws WeaponException
   */
  public LifeForm findNearestEnemy(LifeForm entity) throws WeaponException, EnvironmentException {
    
    switch (entity.getDirection()) {
      case NORTH:
        for (int i = entity.getCol() - 1; i >= 0; i--) {
          if (hasLifeForm(entity.getRow(), i)) {
            if (!entity.getSpecies().equals(getLifeForm(entity.getRow(), i).getSpecies())) {
              return getLifeForm(entity.getRow(), i);
            }
          }
        }
        return null;
      
      case SOUTH:
        for (int i = entity.getCol() + 1; i < cols; i++) {
          if (hasLifeForm(entity.getRow(), i)) {
            if (!entity.getSpecies().equals(getLifeForm(entity.getRow(), i).getSpecies())) {
              return getLifeForm(entity.getRow(), i);
            }
          }
        }
        return null;
      
      case EAST:
        for (int i = entity.getRow() + 1; i < rows; i++) {
          if (hasLifeForm(i, entity.getCol())) {
            if (!entity.getSpecies().equals(getLifeForm(i, entity.getCol()).getSpecies())) {
              return getLifeForm(i, entity.getCol());
            }
          }
        }
        return null;
      
      case WEST:
        for (int i = entity.getRow() - 1; i >= 0; i--) {
          if (hasLifeForm(i, entity.getCol())) {
            if (!entity.getSpecies().equals(getLifeForm(i, entity.getCol()).getSpecies())) {
              return getLifeForm(i, entity.getCol());
            }
          }
        }
        return null;
      
      default:
        return null;
    }
  }
  
  /**
   * AttackNearest Enemy
   * 
   * @throws EnvironmentException
   * @throws WeaponException
   */
  
  public void attackNearestEnemy(LifeForm entity) throws WeaponException, EnvironmentException {
    if (entity != null && findNearestEnemy(entity) != null) {
      if (!entity.getSpecies().equals(findNearestEnemy(entity).getSpecies())) {
        entity.attack(findNearestEnemy(entity),
            (int) getDistance(entity, findNearestEnemy(entity)));
      }
    }
  }
  
  /**
   * @param row Row position
   * @param col Column position
   * @return An array of the weapons at the cell at given coordinates
   */
  public Weapon[] getWeapons(int row, int col) {
    Cell c = gridOfCells[row][col];
    return new Weapon[] { c.getWeapon1(), c.getWeapon2() };
  }
  
  /**
   * Clears the Environment of all LifeForms and Weapons
   */
  public void clearBoard() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        gridOfCells[i][j] = new Cell();
      }
    }
  }
  
  /**
   * @param row1 Row position of Cell 1
   * @param col1 Column position of Cell 1
   * @param row2 Row position of Cell 2
   * @param col2 Row position of Cell 2
   * @return The pythagorean distance between Cell 1 and Cell 2. Each cell is a 5
   *         unit square.
   * @throws EnvironmentException if any coordinates are invalid
   */
  public double getDistance(int row1, int col1, int row2, int col2) throws EnvironmentException {
    // Check for invalid coordinates
    int[][] allCoords = { { row1, col1 }, { row2, col2 } };
    int[] envDims = { rows, cols };
    for (int r = 0; r < 2; r++) {
      for (int c = 0; c < 2; c++) {
        if (allCoords[r][c] < 0 || allCoords[r][c] >= envDims[c]) {
          throw new EnvironmentException("Invalid input coordinates");
        }
      }
    }
    // Actual calculation
    return 5 * Math.sqrt(Math.pow(row1 - row2, 2) + Math.pow(col1 - col2, 2));
  }
  
  /**
   * @param l1 LifeForm 1
   * @param l2 LifeForm 2
   * @return The pythagorean distance between the two lifeforms. Each cell is a 5
   *         unit square.
   * @throws EnvironmentException
   */
  public double getDistance(LifeForm l1, LifeForm l2) throws EnvironmentException {
    if (l2 != null) {
      return getDistance(l1.getRow(), l1.getCol(), l2.getRow(), l2.getCol());
    } else {
      return 0.0;
    }
  }
  
  public int getWeaponsCount(int row, int col) {
    return gridOfCells[row][col].getWeaponsCount();
  }
  
  /*
   * public void incrementRound() { timer.timeChanged(); }
   */
  
}
