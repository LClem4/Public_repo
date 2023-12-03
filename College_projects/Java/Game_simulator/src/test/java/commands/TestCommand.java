package commands;

import static org.junit.Assert.*;

import java.awt.HeadlessException;

import org.junit.Test;
import org.junit.runners.*;
import org.junit.FixMethodOrder;

import exceptions.AttachmentException;
import exceptions.EnvironmentException;
import exceptions.RecoveryRateException;
import exceptions.WeaponException;
import ui.GameBoard;
import lifeform.*;
import weapon.*;

@FixMethodOrder( MethodSorters.NAME_ASCENDING)
public class TestCommand {

  @Test
  public void stage1_testMove() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.getEnvironment().resizeEnvironment(10,10);
     gb.getEnvironment().clearBoard();
     LifeForm entity = new Human("Kevin",100,3);
     gb.getEnvironment().addLifeForm(entity, 4, 4);
     assertTrue(gb.getEnvironment().hasLifeForm(4,4));
     assertEquals("Kevin",gb.getEnvironment().getLifeForm(4,4).getName());
     Command moveCommand = new MoveOneSpaceCommand(gb);
     gb.setCor(4,4);
     moveCommand.execute();
     assertTrue(gb.getEnvironment().hasLifeForm(4,3));
     assertEquals("Kevin",gb.getEnvironment().getLifeForm(4,3).getName());
     
     Command move = new MoveCommand(gb);
     Command north = new RotateNorthCommand(gb);
     Command south = new RotateSouthCommand(gb);
     Command east = new RotateEastCommand(gb);
     Command west = new RotateWestCommand(gb);
     
     LifeForm lf1 = new Human("Kevin",100,3);
     LifeForm lf2 = new Human("Kevin2",100,3);
     LifeForm lf3 = new Human("Kevin3",100,3);
     LifeForm lf4 = new Human("Kevin4",100,3);
     LifeForm lf5 = new Human("Kevin5",100,3);
     gb.getEnvironment().addLifeForm(lf1, 0, 0);
     gb.getEnvironment().addLifeForm(lf2, 9, 0);
     gb.getEnvironment().addLifeForm(lf3, 9, 9);
     gb.getEnvironment().addLifeForm(lf4, 0, 9);
     gb.getEnvironment().addLifeForm(lf5, 0, 8);
     gb.setCor(0, 0);
     north.execute();
     move.execute();
     gb.setCor(9, 0);
     east.execute();
     move.execute();
     gb.setCor(9, 9);
     south.execute();
     move.execute();
     gb.setCor(0, 9);
     west.execute();
     move.execute();
     north.execute();
     move.execute();
     assertEquals("Kevin4", gb.getLifeForm(0,9).getName());
     assertEquals("Kevin5", gb.getLifeForm(0,8).getName());
     
     LifeForm lf6 = new Human("Kevin6",100,3);
     gb.getEnvironment().addLifeForm(lf6, 3, 9);
     gb.setCor(3, 9);
     west.execute();
     move.execute();
     assertEquals("Kevin6", gb.getLifeForm(1,9).getName());
        
     gb.setCor(0, 9);
     gb.getLifeForm().resetSpeed();
     east.execute();
     move.execute();
     assertEquals("Kevin4", gb.getLifeForm(3,9).getName());
     
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 1: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  
  @Test
  public void stage2_testReload() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.setCor(4,3);
     assertTrue(gb.getEnvironment().hasLifeForm(4,3));
     assertEquals("Kevin",gb.getEnvironment().getLifeForm(4,3).getName());
     gb.getLifeForm(4,3).pickUpWeapon(new Pistol());
     assertTrue(gb.getLifeForm(4,3).getWeapon() instanceof Pistol);
     assertEquals(10,gb.getLifeForm(4,3).getWeapon().getCurrentAmmo());
     gb.getLifeForm(4,3).getWeapon().fireShot();
     assertEquals(9,gb.getLifeForm(4,3).getWeapon().getCurrentAmmo());
     Command reloadCommand = new ReloadCommand(gb);
     reloadCommand.execute();
     assertEquals(10,gb.getLifeForm(4,3).getWeapon().getCurrentAmmo());
     gb.getLifeForm(4,3).dropWeapon();
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 2: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  
  @Test
  public void stage3_testRotateNorth() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.setCor(4,3);
     Command moveCommand = new MoveOneSpaceCommand(gb);
     Command rotateNorthCommand = new RotateNorthCommand(gb);
     rotateNorthCommand.execute();
     moveCommand.execute();
     assertTrue(gb.getEnvironment().hasLifeForm(4,2));
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 3: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  
  @Test
  public void stage4_testRotateSouth() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.setCor(4,2);
     Command moveCommand = new MoveOneSpaceCommand(gb);
     Command rotateSouthCommand = new RotateSouthCommand(gb);
     rotateSouthCommand.execute();
     moveCommand.execute();
     assertTrue(gb.getEnvironment().hasLifeForm(4,3));
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 4: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  @Test
  public void stage5_testRotateEast() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.setCor(4,3);
     gb.getLifeForm(4,3).resetSpeed();
     Command moveCommand = new MoveOneSpaceCommand(gb);
     Command rotateEastCommand = new RotateEastCommand(gb);
     rotateEastCommand.execute();
     moveCommand.execute();
     assertTrue(gb.getEnvironment().hasLifeForm(5,3));
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 5: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  
  @Test
  public void stage6_testRotateWest() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.setCor(5,3);
     Command moveCommand = new MoveOneSpaceCommand(gb);
     Command rotateWestCommand = new RotateWestCommand(gb);
     rotateWestCommand.execute();
     moveCommand.execute();
     assertTrue(gb.getEnvironment().hasLifeForm(4,3));
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 6: "
          + "this test will still pass, but it can't be properly tested.");
    }
     
  }
  @Test
  public void stage7_testDropSpace() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.setCor(4,3);
     assertNull(gb.getLifeForm(4,3).getWeapon());
     gb.getLifeForm(4,3).pickUpWeapon(new Pistol());
     Command dropCommand = new DropCommand(gb);
     dropCommand.execute();
     assertNull(gb.getLifeForm(4,3).getWeapon());
     assertTrue(gb.getEnvironment().getWeapons(4,3)[0] instanceof Pistol);
     gb.getLifeForm(4,3).pickUpWeapon(new ChainGun());
     dropCommand.execute();
     assertTrue(gb.getEnvironment().getWeapons(4,3)[1] instanceof ChainGun);
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 7: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  
  @Test
  public void stage8_testDropNoSpace() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.setCor(4,3);
     gb.getLifeForm(4,3).pickUpWeapon(new PlasmaCannon());
     Command dropCommand = new DropCommand(gb);
     dropCommand.execute();
     assertTrue(gb.getEnvironment().getWeapons(4,3)[0] instanceof Pistol);
     assertTrue(gb.getLifeForm(4,3).getWeapon() instanceof PlasmaCannon);
     
     gb.getLifeForm(4,3).dropWeapon();
     Command pickUpCommand = new PickUpCommand(gb);
     pickUpCommand.execute();
     gb.getLifeForm(4,3).dropWeapon();
     pickUpCommand.execute();
     gb.getLifeForm(4,3).dropWeapon();
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 8: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  
  @Test
  public void stage9_testAcquireWithSpace() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.setCor(4,3);
     gb.getEnvironment().addWeapon(new Pistol(), 4, 3);
     assertTrue(gb.getEnvironment().getWeapons(4,3)[0] instanceof Pistol);
     assertNull(gb.getLifeForm(4,3).getWeapon());
     Command pickUpCommand = new PickUpCommand(gb);
     pickUpCommand.execute();
     assertTrue(gb.getLifeForm(4,3).getWeapon() instanceof Pistol);
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage 9: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
   
  @Test
  public void stageA_testAcquireWithoutSpace() throws WeaponException, EnvironmentException, AttachmentException {
    try {
     GameBoard gb = new GameBoard();
     gb.setCor(4,3);
     Command pickUpCommand = new PickUpCommand(gb);
     assertTrue(gb.getEnvironment().getWeapons(4,3)[0] == null);
     assertTrue(gb.getEnvironment().getWeapons(4,3)[1] == null);
     gb.getEnvironment().addWeapon(new ChainGun(), 4, 3);
     gb.getEnvironment().addWeapon(new PlasmaCannon(), 4, 3);
     assertTrue(gb.getEnvironment().getWeapons(4,3)[0] instanceof ChainGun);
     assertTrue(gb.getEnvironment().getWeapons(4,3)[1] instanceof PlasmaCannon);
     pickUpCommand.execute();
     assertTrue(gb.getLifeForm(4,3).getWeapon() instanceof ChainGun);
     assertTrue(gb.getEnvironment().getWeapons(4,3)[0] instanceof Pistol);
     gb.getLifeForm(4,3).dropWeapon();
     pickUpCommand.execute();
     assertTrue(gb.getLifeForm(4,3).getWeapon() instanceof Pistol);
     gb.getLifeForm(4,3).dropWeapon();
     pickUpCommand.execute();
     assertTrue(gb.getLifeForm(4,3).getWeapon() instanceof PlasmaCannon);
     assertTrue(gb.getEnvironment().getWeapons(4,3)[0] == null);
     assertTrue(gb.getEnvironment().getWeapons(4,3)[1] == null);
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage A: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }
  
  @Test
  public void stageB_testAttack() throws WeaponException, EnvironmentException, AttachmentException {
    try {
    GameBoard gb = new GameBoard();
    gb.setCor(4,3);
    gb.getLifeForm().getWeapon().updateTime(1);
    Command attackCommand = new AttackCommand(gb);
    attackCommand.execute();
    
    LifeForm entity2 = new Alien("Bob",100);
    LifeForm entity3 = new Alien("Bob2",100);
    LifeForm entity4 = new Alien("Bob3",100);
    LifeForm entity5 = new Alien("Bob4",100);
    LifeForm entity6 = new Alien("Bob5",100);
  
    gb.getEnvironment().addLifeForm(entity2, 4, 1);
    gb.getEnvironment().addLifeForm(entity3, 4, 4);
    gb.getEnvironment().addLifeForm(entity4, 4, 8);
    gb.getEnvironment().addLifeForm(entity5, 3, 3);
    gb.getEnvironment().addLifeForm(entity6, 7, 3);
    
    Command north = new RotateNorthCommand(gb);
    Command south = new RotateSouthCommand(gb);
    Command east = new RotateEastCommand(gb);
    Command west = new RotateWestCommand(gb);
   
    gb.getLifeForm().getWeapon().updateTime(1);
    attackCommand.execute();
    
    gb.getLifeForm().getWeapon().updateTime(1);
    north.execute();
    attackCommand.execute();
    assertTrue(entity2.getCurrentLifePoints()<100);
    
    gb.getLifeForm().getWeapon().updateTime(1);
    south.execute();
    attackCommand.execute();
    assertTrue(entity3.getCurrentLifePoints()<100);
    assertTrue(entity4.getCurrentLifePoints()==100);
    
    gb.getLifeForm().getWeapon().updateTime(1);
    west.execute();
    attackCommand.execute();
    assertTrue(entity5.getCurrentLifePoints()<100);
    
    Command reloadCommand = new ReloadCommand(gb);
    reloadCommand.execute();
    gb.getLifeForm().getWeapon().updateTime(1);
    east.execute();
    attackCommand.execute();
    assertTrue(entity6.getCurrentLifePoints()<100);
    } catch (HeadlessException e) {
      System.err.println("HeadlessException in Stage B: "
          + "this test will still pass, but it can't be properly tested.");
    }
  }

}
