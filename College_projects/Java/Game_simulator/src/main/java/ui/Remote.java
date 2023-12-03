package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import commands.AttackCommand;
import commands.Command;
import commands.DropCommand;
import commands.MoveCommand;
import commands.MoveOneSpaceCommand;
import commands.PickUpCommand;
import commands.ReloadCommand;
import commands.RotateEastCommand;
import commands.RotateNorthCommand;
import commands.RotateSouthCommand;
import commands.RotateWestCommand;
import commands.RoundUpdateCommand;

import exceptions.EnvironmentException;
import exceptions.WeaponException;

public class Remote extends JFrame {
  
  private enum Action {
      ATTACK,
      RELOAD,
      TURN_NORTH,
      TURN_EAST,
      TURN_SOUTH,
      TURN_WEST,
      MOVE_MAX,
      MOVE_ONE,
      ACQUIRE,
      DROP,
      INCREMENT_ROUND
  }
  
  private GameBoard gb;
  private Command[] commands;
  final Color cream = new Color(253, 236, 166);
  final Color brown = new Color(176, 94, 48);
  
  /**
   * Creates & displays a remote instance
   */
  public Remote(GameBoard g) {
    // Initialize fields when GameBoard & Commands are ready
    gb = g;
    commands = new Command[] {
        new AttackCommand(g),
        new ReloadCommand(g),
        new RotateNorthCommand(g),
        new RotateEastCommand(g),
        new RotateSouthCommand(g),
        new RotateWestCommand(g),
        new MoveCommand(g),
        new MoveOneSpaceCommand(g),
        new PickUpCommand(g),
        new DropCommand(g),
        new RoundUpdateCommand(g)
    };
    
    // Building the UI
    setSize(400, 700);
    setLayout(new BorderLayout());
    
    JPanel upper = new JPanel();
    upper.setLayout(new BorderLayout());
    upper.setPreferredSize(new Dimension(400, 100));
    
    JButton attack = new JButton("Attack");
    attack.setPreferredSize(new Dimension(180, 90));
    attack.addActionListener(a -> buttonPressed(Action.ATTACK));
    attack.setBackground(cream);
    upper.add(attack, BorderLayout.WEST);
    
    JButton reload = new JButton("Reload");
    reload.setPreferredSize(new Dimension(180, 90));
    reload.setBackground(cream);
    reload.addActionListener(a -> buttonPressed(Action.RELOAD));
    upper.add(reload, BorderLayout.EAST);
    
    add(upper, BorderLayout.NORTH);
    
    JPanel middle = new JPanel();
    middle.setLayout(new BorderLayout());
    middle.setPreferredSize(new Dimension(400, 400));
    
    JButton north = new JButton("North");
    north.setPreferredSize(new Dimension(90, 90));
    north.setBackground(Color.LIGHT_GRAY);
    north.addActionListener(a -> buttonPressed(Action.TURN_NORTH));
    middle.add(north, BorderLayout.NORTH);
    
    JButton east = new JButton("East");
    east.setPreferredSize(new Dimension(90, 90));
    east.setBackground(Color.LIGHT_GRAY);
    east.addActionListener(a -> buttonPressed(Action.TURN_EAST));
    middle.add(east, BorderLayout.EAST);
    
    JButton south = new JButton("South");
    south.setPreferredSize(new Dimension(90, 90));
    south.setBackground(Color.LIGHT_GRAY);
    south.addActionListener(a -> buttonPressed(Action.TURN_SOUTH));
    middle.add(south, BorderLayout.SOUTH);
    
    JButton west = new JButton("West");
    west.setPreferredSize(new Dimension(90, 90));
    west.setBackground(Color.LIGHT_GRAY);
    west.addActionListener(a -> buttonPressed(Action.TURN_WEST));
    middle.add(west, BorderLayout.WEST);
    
    JPanel midder = new JPanel(); // Even more mid than middle
    midder.setLayout(new BorderLayout());
    midder.setPreferredSize(new Dimension(200, 200));
    
    JButton move = new JButton("Move Max");
    move.setPreferredSize(new Dimension(200, 100));
    move.setBackground(Color.DARK_GRAY);
    move.setForeground(Color.WHITE);
    move.addActionListener(a -> buttonPressed(Action.MOVE_MAX));
    midder.add(move, BorderLayout.NORTH);
    
    JButton move2 = new JButton("Move One");
    move2.setPreferredSize(new Dimension(200, 100));
    move2.setBackground(Color.DARK_GRAY);
    move2.setForeground(Color.WHITE);
    move2.addActionListener(a -> buttonPressed(Action.MOVE_ONE));
    midder.add(move2, BorderLayout.SOUTH);
    
    middle.add(midder, BorderLayout.CENTER);
    
    add(middle, BorderLayout.CENTER);
    
    JPanel lower = new JPanel();
    lower.setLayout(new BorderLayout());
    lower.setPreferredSize(new Dimension(400, 200));
    
    JButton acquire = new JButton("Acquire");
    acquire.setPreferredSize(new Dimension(180, 90));
    acquire.addActionListener(a -> buttonPressed(Action.ACQUIRE));
    acquire.setBackground(brown);
    lower.add(acquire, BorderLayout.WEST);
    
    JButton drop = new JButton("Drop");
    drop.setPreferredSize(new Dimension(180, 90));
    drop.setBackground(brown);
    drop.addActionListener(a -> buttonPressed(Action.DROP));
    lower.add(drop, BorderLayout.EAST);
    
    JButton round = new JButton("Increment Round");
    round.setPreferredSize(new Dimension(180, 90));
    round.setBackground(Color.LIGHT_GRAY);
    round.addActionListener(a -> buttonPressed(Action.INCREMENT_ROUND));
    lower.add(round, BorderLayout.SOUTH);
    
    add(lower, BorderLayout.SOUTH);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  
  /**
   * Performs an action on the Game Board
   * @param action The action to be performed
   */
  public void buttonPressed(Action action) {
    // TODO: write buttonPressed method when GameBoard & Commands are ready
    try {
      commands[action.ordinal()].execute();
      gb.getCellStats(gb.getSRow(), gb.getSCol());
    } catch (EnvironmentException e) {
      System.err.println("EnvironmentException: " + e.getMessage());
    } catch (WeaponException e) {
      System.err.println("WeaponException: " + e.getMessage());
    } catch (NullPointerException e) {
      // Do nothing
    }
  }
}
