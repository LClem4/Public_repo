package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import environment.Environment;
import exceptions.AttachmentException;
import exceptions.WeaponException;
import lifeform.Alien;
import lifeform.Direction;
import lifeform.Human;
import lifeform.LifeForm;
import randomlibrary.RandAlien;
import randomlibrary.RandHuman;
import randomlibrary.RandList;
import weapon.ChainGun;
import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.PowerBooster;
import weapon.Scope;
import weapon.Stabilizer;

public class GameBoard {
  public Environment environment;
  public int rows = 10;
  public int cols = 10;
  private int selectRow = 0;
  private int selectCol = 0;
  private JButton[][] cells;
  private JLabel cellStats;
  private JLabel round;
  private JFrame winScreen;
  
  /**
   * Creates an instance of an interactive game board
   * 
   * @throws WeaponException
   * @throws AttachmentException
   */
  public GameBoard() throws WeaponException, AttachmentException {
    JFrame master = new JFrame();
    master.setLayout(new BorderLayout());
    
    JPanel side = new JPanel();
    side.setLayout(new BorderLayout());
    
    environment = Environment.getEnvironment(rows, cols);
    JPanel legend = new JPanel();
    legend.setLayout(new BorderLayout());
    JLabel legendInfo;
    legendInfo = new JLabel(
        "<html>Legend<br/>" + "The shapes in the center of cells are LifeForms<br/>"
            + "Green Squares=Aliens<br/>" + "Green Circles=Humans<br/><br/>"
            + "The small black square at the side of the lifeform is the "
            + "direction the<br>lifeform is facing<br/><br/>"
            + "The Squares at the bottom of each cell are weapons, "
            + "no circles means<br>no weapons<br/>" + "Blue Squares are Pistols<br/>"
            + "Red Squares are ChainGuns<br/>" + "Yellow Squares are PlasmaCannons<br/><br/>"
            + "Weapons can have attachements, which are the smaller squares directly <br>"
            + "above the weapon in the top corner<br/><br/>" + "Pink Squares are PowerBoosters<br/>"
            + "Purple Squares are Stabilizers<br/>" + "Orange Squares are Scopes<br/><br/>"
            + "The Square selected will be highlighted green<br><br><html>");
    
    JPanel stats = new JPanel();
    stats.setLayout(new BorderLayout());
    cellStats = new JLabel();
    
    JPanel buttonGrid = new JPanel();
    cells = new JButton[rows][cols];
    
    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        int foo = j;
        int bar = i;
        cells[j][i] = new JButton(drawCell(createBlank()));
        cells[j][i].addActionListener(a -> {
          selectRow = foo;
          selectCol = bar;
          highlight();
          getCellStats(foo, bar);
        });
        buttonGrid.add(cells[j][i]);
      }
    }
    
    stats.setSize(500, 400);
    stats.add(cellStats, BorderLayout.NORTH);
    
    round = new JLabel();
    round.setSize(500, 50);
    stats.add(round, BorderLayout.CENTER);
    
    side.add(stats, BorderLayout.CENTER);
    
    legend.setSize(500, 500);
    legend.add(legendInfo, BorderLayout.NORTH);
    side.add(legend, BorderLayout.NORTH);
    
    master.add(side, BorderLayout.EAST);
    
    buttonGrid.setLayout(new GridLayout(rows, cols));
    // Cell Size scalars for row and column
    buttonGrid.setSize(100 * rows, 100 * cols);
    master.add(buttonGrid, BorderLayout.CENTER);
    
    master.setSize(1500, 1000);
    master.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    master.setVisible(true);
    
    updateBoard();
  }
  
  /**
   * Redraws the entire board
   */
  
  public void updateBoard() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        update(i, j);
        
      }
    }
  }
  
  public void updateRound(int r, int humanScore, int alienScore) {
    round.setText("Current Round: " + r + "                           Alien Score:  " + alienScore
        + "    Humans Score:  " + humanScore);
  }
  
  /**
   * Displays win screen
   * 
   * @param winText
   */
  public void displayWinScreen(String winText) {
    winScreen = new JFrame();
    winScreen.setLayout(new BorderLayout());
    winScreen.setSize(1200, 800);
    JLabel textDisplay = new JLabel(winText);
    textDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    textDisplay.setVerticalAlignment(SwingConstants.CENTER);
    textDisplay.setFont(new Font("Calibri", Font.BOLD, 120));
    winScreen.add(textDisplay, BorderLayout.CENTER);
    winScreen.setVisible(true);
  }
  
  /**
   * @return A black white BufferedImage cell
   */
  private BufferedImage createBlank() {
    BufferedImage exampleImage = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
    Graphics drawer = exampleImage.getGraphics();
    drawer.setColor(new Color(255, 255, 255));
    drawer.fillRect(0, 0, 100, 100);
    return exampleImage;
  }
  
  /**
   * @param exampleImage A cell BufferedImage
   * @return An ImageIcon that a JButton can hold
   */
  private ImageIcon drawCell(BufferedImage exampleImage) {
    return new ImageIcon(exampleImage);
  }
  
  /**
   * @param base
   * @return Adds a Human icon to base
   */
  private BufferedImage createHuman(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(0, 255, 0));
    drawer.fillOval(38, 40, 25, 25);
    return base;
  }
  
  /**
   * @param base
   * @return Adds an Alien icon to base
   */
  private BufferedImage createAlien(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(0, 0, 255));
    drawer.fillRect(38, 40, 25, 25);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a pistol icon to the left side of base
   */
  private BufferedImage createPistolLeft(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(0, 150, 255));
    drawer.fillRect(20, 70, 15, 15);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a chain gun icon to the left side of base
   */
  private BufferedImage createChainGunLeft(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 0, 0));
    drawer.fillRect(20, 70, 15, 15);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a plasma cannon icon to the left side of base
   */
  private BufferedImage createPlasmaCannonLeft(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 255, 0));
    drawer.fillRect(20, 70, 15, 15);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a pistol icon to the right side of base
   */
  private BufferedImage createPistolRight(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(0, 150, 255));
    drawer.fillRect(70, 70, 15, 15);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a chain gun icon to the right side of base
   */
  private BufferedImage createChainGunRight(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 0, 0));
    drawer.fillRect(70, 70, 15, 15);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a plasma cannon icon to the right side of base
   */
  private BufferedImage createPlasmaCannonRight(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 255, 0));
    drawer.fillRect(70, 70, 15, 15);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a scope icon to the top-left side of base
   */
  private BufferedImage createScopeLeftTop(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 150, 0));
    drawer.fillRect(15, 15, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a scope icon to the bottom-left side of base
   */
  private BufferedImage createScopeLeftBottom(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 150, 0));
    drawer.fillRect(15, 30, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a power booster icon to the top-left side of base
   */
  private BufferedImage createPowerBoosterLeftTop(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 0, 150));
    drawer.fillRect(15, 15, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a power booster icon to the bottom-left side of base
   */
  private BufferedImage createPowerBoosterLeftBottom(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 0, 150));
    drawer.fillRect(15, 30, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a stabilizer icon to the top-left side of base
   */
  private BufferedImage createStabilizerLeftTop(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(150, 0, 255));
    drawer.fillRect(15, 15, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a scope icon to the bottom-left side of base
   */
  private BufferedImage createStabilizerLeftBottom(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(150, 0, 255));
    drawer.fillRect(15, 30, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a scope icon to the top-right side of base
   */
  private BufferedImage createScopeRightTop(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 255, 0));
    drawer.fillRect(80, 15, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a scope icon to the bottom-right side of base
   */
  private BufferedImage createScopeRightBottom(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 255, 0));
    drawer.fillRect(80, 30, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a power booster icon to the top-right side of base
   */
  private BufferedImage createPowerBoosterRightTop(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 0, 150));
    drawer.fillRect(80, 15, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a power booster icon to the bottom-right side of base
   */
  private BufferedImage createPowerBoosterRightBottom(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(255, 0, 150));
    drawer.fillRect(80, 30, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a stabilizer icon to the top-right side of base
   */
  private BufferedImage createStabilizerRightTop(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(150, 0, 255));
    drawer.fillRect(80, 15, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a stabilizer icon to the bottom-right side of base
   */
  private BufferedImage createStabilizerRightBottom(BufferedImage base) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(150, 0, 255));
    drawer.fillRect(80, 30, 10, 10);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a north direction icon to base
   */
  private BufferedImage createDirectionNorth(BufferedImage base, LifeForm lf) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(0, 0, 0));
    double intermMath1 = lf.getMaxLifePoints();
    double intermMath2 = lf.getCurrentLifePoints();
    int newSize = (int) ((int) 30 * intermMath2 / intermMath1);
    drawer.fillRect(35, 30, newSize, 5);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a south direction icon to base
   */
  private BufferedImage createDirectionSouth(BufferedImage base, LifeForm lf) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(0, 0, 0));
    double intermMath1 = lf.getMaxLifePoints();
    double intermMath2 = lf.getCurrentLifePoints();
    int newSize = (int) ((int) 30 * intermMath2 / intermMath1);
    drawer.fillRect(35, 70, newSize, 5);
    return base;
  }
  
  /**
   * @param base
   * @return Adds a west direction icon to base
   */
  private BufferedImage createDirectionWest(BufferedImage base, LifeForm lf) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(0, 0, 0));
    double intermMath1 = lf.getMaxLifePoints();
    double intermMath2 = lf.getCurrentLifePoints();
    int newSize = (int) ((int) 30 * intermMath2 / intermMath1);
    drawer.fillRect(30, 38, 5, newSize);
    return base;
  }
  
  /**
   * @param base
   * @return Adds an east direction icon to base
   */
  private BufferedImage createDirectionEast(BufferedImage base, LifeForm lf) {
    Graphics drawer = base.getGraphics();
    drawer.setColor(new Color(0, 0, 0));
    double intermMath1 = lf.getMaxLifePoints();
    double intermMath2 = lf.getCurrentLifePoints();
    int newSize = (int) ((int) 30 * intermMath2 / intermMath1);
    drawer.fillRect(70, 38, 5, newSize);
    return base;
  }
  
  /**
   * Main method; run when GameBoard.java is executed
   * 
   * @param args
   * @throws WeaponException
   * @throws AttachmentException
   */
  public static void main(String[] args) throws WeaponException, AttachmentException {
    GameBoard g = new GameBoard();
    Remote r = new Remote(g);
    
    g.highlight();
    g.getCellStats(1, 1);
  }
  
  /**
   * Highlights the currently selected cell with a green border
   */
  public void highlight() {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        cells[i][j].setBorder(new LineBorder(Color.BLACK));
      }
    }
    cells[selectRow][selectCol].setBorder(new LineBorder(Color.GREEN));
  }
  
  /**
   * Get the stats of a given cell
   * 
   * @param row
   * @param col
   */
  public void getCellStats(int row, int col) {
    String currentCellStats = "<html><u>Lifeform Stats</u><br><br>";
    LifeForm lf = environment.getLifeForm(row, col);
    final int speed = lf == null ? 0 : lf.getCurrentSpeed();
    final int maxSpeed = lf == null ? 0 : lf.getMaxSpeed();
    final int currentHealth = lf == null ? 0 : lf.getCurrentLifePoints();
    final int maxHealth = lf == null ? 0 : lf.getMaxLifePoints();
    final String name = lf == null ? "None" : lf.getName();
    final int attackStrength = lf == null ? 0 : lf.getAttackStrength();
    final String weaponName = lf == null || lf.getWeapon() == null ? "None"
        : environment.getLifeForm(row, col).getWeapon().toString();
    final int ammo = lf == null || lf.getWeapon() == null ? 0
        : environment.getLifeForm(row, col).getWeapon().getCurrentAmmo();
    final int shotsLeft = lf == null || lf.getWeapon() == null ? 0
        : environment.getLifeForm(row, col).getWeapon().getShotsLeft();
    String speedStat = ("Speed: " + speed + "<br>");
    currentCellStats += speedStat;
    String maxSpeedStat = ("Max Speed: " + maxSpeed + "<br>");
    currentCellStats += maxSpeedStat;
    String currentHealthStat = ("Current HP: " + currentHealth + "<br>");
    currentCellStats += currentHealthStat;
    String maxHealthStat = ("Max HP: " + maxHealth + "<br>");
    currentCellStats += maxHealthStat;
    currentCellStats += ("Name: " + name + "<br>");
    String attackStrengthStat = ("Attack Strength: " + attackStrength + "<br><br>");
    currentCellStats += attackStrengthStat;
    String weaponNameStat = ("Weapon: " + weaponName + "<br>");
    currentCellStats += weaponNameStat;
    currentCellStats += "Weapon Ammo: " + ammo + "<br>";
    currentCellStats += "Shots Left: " + shotsLeft;
    currentCellStats += "</html>";
    cellStats.setText(currentCellStats);
  }
  
  /**
   * Visually update a given cell
   * 
   * @param row
   * @param col
   */
  public void update(int row, int col) {
    final String lf = environment.getLifeForm(row, col) == null ? "None"
        : environment.getLifeForm(row, col).getSpecies();
    final String weapon1 = environment.getWeapons(row, col)[0] == null ? "None"
        : environment.getWeapons(row, col)[0].toString();
    final String weapon2 = environment.getWeapons(row, col)[1] == null ? "None"
        : environment.getWeapons(row, col)[1].toString();
    final Direction direction = environment.getLifeForm(row, col) == null ? null
        : environment.getLifeForm(row, col).getDirection();
    BufferedImage img = createBlank();
    if (lf == "Alien") {
      img = createAlien(img);
    }
    if (lf == "Human") {
      img = createHuman(img);
    }
    if (weapon1.contains("Pistol")) {
      img = createPistolLeft(img);
    }
    if (weapon1.contains("ChainGun")) {
      img = createChainGunLeft(img);
      cells[row][col].setIcon(drawCell(img));
    }
    if (weapon1.contains("PlasmaCannon")) {
      img = createPlasmaCannonLeft(img);
    }
    if (weapon1.contains("+Scope")) {
      img = createScopeLeftTop(img);
      if (weapon1.contains("+Scope +Scope")) {
        img = createScopeLeftBottom(img);
      }
      if (weapon1.contains("+Scope +PowerBooster")) {
        img = createPowerBoosterLeftBottom(img);
      }
      if (weapon1.contains("+Scope +Stabilizer")) {
        img = createStabilizerLeftBottom(img);
      }
    }
    if (weapon1.contains("+PowerBooster")) {
      img = createPowerBoosterLeftTop(img);
      if (weapon1.contains("+PowerBooster +Scope")) {
        img = createScopeLeftBottom(img);
      }
      if (weapon1.contains("+PowerBooster +PowerBooster")) {
        img = createPowerBoosterLeftBottom(img);
      }
      if (weapon1.contains("+PowerBooster +Stabilizer")) {
        img = createStabilizerLeftBottom(img);
      }
    }
    if (weapon1.contains("+Stabilizer")) {
      img = createStabilizerLeftTop(img);
      if (weapon1.contains("+Stabilizer +Scope")) {
        img = createScopeLeftBottom(img);
      }
      if (weapon1.contains("+Stabilizer +PowerBooster")) {
        img = createPowerBoosterLeftBottom(img);
      }
      if (weapon1.contains("+Stabilizer +Stabilizer")) {
        img = createStabilizerLeftBottom(img);
      }
    }
    if (weapon2.contains("Pistol")) {
      img = createPistolRight(img);
    }
    if (weapon2.contains("ChainGun")) {
      img = createChainGunRight(img);
    }
    if (weapon2.contains("PlasmaCannon")) {
      img = createPlasmaCannonRight(img);
    }
    if (weapon2.contains("+Scope")) {
      img = createScopeRightTop(img);
      if (weapon2.contains("+Scope +Scope")) {
        img = createScopeRightBottom(img);
      }
      if (weapon2.contains("+Scope +PowerBooster")) {
        img = createPowerBoosterRightBottom(img);
      }
      if (weapon2.contains("+Scope +Stabilizer")) {
        img = createStabilizerRightBottom(img);
      }
    }
    if (weapon2.contains("+PowerBooster")) {
      img = createPowerBoosterRightTop(img);
      if (weapon2.contains("+PowerBooster +Scope")) {
        img = createScopeRightBottom(img);
      }
      if (weapon2.contains("+PowerBooster +PowerBooster")) {
        img = createPowerBoosterRightBottom(img);
      }
      if (weapon2.contains("+PowerBooster +Stabilizer")) {
        img = createStabilizerRightBottom(img);
      }
    }
    if (weapon2.contains("+Stabilizer")) {
      img = createStabilizerRightTop(img);
      if (weapon2.contains("+Stabilizer +Scope")) {
        img = createScopeRightBottom(img);
      }
      if (weapon2.contains("+Stabilizer +PowerBooster")) {
        img = createPowerBoosterRightBottom(img);
      }
      if (weapon2.contains("+Stabilizer +Stabilizer")) {
        img = createStabilizerRightBottom(img);
      }
    }
    if (direction == direction.NORTH) {
      img = createDirectionNorth(img, environment.getLifeForm(row, col));
    }
    cells[row][col].setIcon(drawCell(img));
    if (direction == direction.SOUTH) {
      img = createDirectionSouth(img, environment.getLifeForm(row, col));
    }
    cells[row][col].setIcon(drawCell(img));
    if (direction == direction.EAST) {
      img = createDirectionEast(img, environment.getLifeForm(row, col));
    }
    cells[row][col].setIcon(drawCell(img));
    if (direction == direction.WEST) {
      img = createDirectionWest(img, environment.getLifeForm(row, col));
    }
    cells[row][col].setIcon(drawCell(img));
  }
  
  /**
   * @return The number of rows
   */
  public int getRow() {
    return rows;
  }
  
  /**
   * @return The number of columns
   */
  public int getCol() {
    return cols;
  }
  
  /**
   * Set the selected row
   * 
   * @param row
   */
  public void setSRow(int row) {
    selectRow = row;
  }
  
  /**
   * Set the selected column
   * 
   * @param col
   */
  public void setSCol(int col) {
    selectCol = col;
  }
  
  /**
   * Set the selected coordinates
   * 
   * @param row
   * @param col
   */
  public void setCor(int row, int col) {
    selectRow = row;
    selectCol = col;
  }
  
  /**
   * @return The selected column
   */
  public int getSCol() {
    return selectCol;
  }
  
  /**
   * @return The selected row
   */
  public int getSRow() {
    return selectRow;
  }
  
  /**
   * @return The environment this GameBoard is displaying
   */
  public Environment getEnvironment() {
    return environment;
  }
  
  /**
   * @return The LifeForm at the currently selected cell
   */
  public LifeForm getLifeForm() {
    return environment.getLifeForm(selectRow, selectCol);
  }
  
  /**
   * @param row
   * @param col
   * @return The LifeForm at the cell of the given coordinates
   */
  public LifeForm getLifeForm(int row, int col) {
    return environment.getLifeForm(row, col);
  }
}
