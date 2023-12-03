package environment;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.runners.*;
import org.junit.Test;

import exceptions.EnvironmentException;
import lifeform.LifeForm;
import lifeform.Alien;
import lifeform.MockLifeForm;
import lifeform.Direction;
import weapon.MockWeapon;

/**
 * Previous labs: Source code and tests written by Andrew Abruzzese. 
 * Lab5 Source code and tests written by Christian Honicker.
 */

/**
 * NOTE: Since all tests use the same static singleton, test order is important.
 * Tests are executed in order of stage number (stage1, stage2, etc), not the order
 * they appear in this file.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEnvironment {
  int row = 3;
  int col = 3;

  /**
   * The following tests were written or edited for Lab 5
   */
  
  /**
   * Move a lifeforms north w or w/o obstacles
   */
  
  @Test
  public void stageC_testMoveLifeFormNorth() {
    Environment x = Environment.getEnvironment(row, col);
    x.clearBoard();
    x.resizeEnvironment(4,4);
    LifeForm entity1 = new Alien("Bob1",100);
    LifeForm entity2 = new Alien("Bob2",100);
    LifeForm entity3 = new Alien("Bob3",100);
    LifeForm entity4 = new Alien("Bob4",100);
    LifeForm entity5 = new Alien("Bob5",100);
    LifeForm entity6 = new Alien("Bob6",100);
    LifeForm entity7 = new Alien("Bob7",100);
    LifeForm entity8 = new Alien("Bob8",100);
    x.addLifeForm(entity1, 0, 3);
    x.addLifeForm(entity2, 1, 3);
    x.addLifeForm(entity3, 2, 3);
    x.addLifeForm(entity4, 3, 3);
    x.addLifeForm(entity5, 1, 2);
    x.addLifeForm(entity6, 2, 1);
    x.addLifeForm(entity7, 3, 1);
    x.addLifeForm(entity8, 3, 2);
    x.moveLifeFormMax(entity1);
    x.moveLifeFormMax(entity2);
    x.moveLifeFormMax(entity3);
    x.moveLifeFormMax(entity4);
    assertEquals("Bob1",x.getLifeForm(0, 1).getName());
    assertEquals("Bob2",x.getLifeForm(1, 1).getName());
    assertEquals("Bob3",x.getLifeForm(2, 2).getName());
    assertEquals("Bob4",x.getLifeForm(3, 3).getName());
  }
  
  /**
   * Move a lifeforms south w or w/o obstacles
   */
  @Test
  public void stageD_testMoveLifeFormSouth() {
    Environment x = Environment.getEnvironment(row, col);
    x.clearBoard();
    x.resizeEnvironment(4,4);
    LifeForm entity1 = new Alien("Bob1",100);
    LifeForm entity2 = new Alien("Bob2",100);
    LifeForm entity3 = new Alien("Bob3",100);
    LifeForm entity4 = new Alien("Bob4",100);
    LifeForm entity5 = new Alien("Bob5",100);
    LifeForm entity6 = new Alien("Bob6",100);
    LifeForm entity7 = new Alien("Bob7",100);
    LifeForm entity8 = new Alien("Bob8",100);
    x.addLifeForm(entity1, 0, 0);
    x.addLifeForm(entity2, 1, 0);
    x.addLifeForm(entity3, 2, 0);
    x.addLifeForm(entity4, 3, 0);
    x.addLifeForm(entity5, 1, 1);
    x.addLifeForm(entity6, 2, 2);
    x.addLifeForm(entity7, 3, 1);
    x.addLifeForm(entity8, 3, 2);
    entity1.rotate(Direction.SOUTH);
    entity2.rotate(Direction.SOUTH);
    entity3.rotate(Direction.SOUTH);
    entity4.rotate(Direction.SOUTH);
    x.moveLifeFormMax(entity1);
    x.moveLifeFormMax(entity2);
    x.moveLifeFormMax(entity3);
    x.moveLifeFormMax(entity4);
    assertEquals("Bob1",x.getLifeForm(0, 2).getName());
    assertEquals("Bob2",x.getLifeForm(1, 2).getName());
    assertEquals("Bob3",x.getLifeForm(2, 1).getName());
    assertEquals("Bob4",x.getLifeForm(3, 0).getName());
  }
  
  
  /**
   * Move a lifeforms west w or w/o obstacles
   */
  @Test
  public void stageE_testMoveLifeFormWest() {
    Environment x = Environment.getEnvironment(row, col);
    x.clearBoard();
    x.resizeEnvironment(4,4);
    LifeForm entity1 = new Alien("Bob1",100);
    LifeForm entity2 = new Alien("Bob2",100);
    LifeForm entity3 = new Alien("Bob3",100);
    LifeForm entity4 = new Alien("Bob4",100);
    LifeForm entity5 = new Alien("Bob5",100);
    LifeForm entity6 = new Alien("Bob6",100);
    LifeForm entity7 = new Alien("Bob7",100);
    LifeForm entity8 = new Alien("Bob8",100);
    x.addLifeForm(entity1, 3, 0);
    x.addLifeForm(entity2, 3, 1);
    x.addLifeForm(entity3, 3, 2);
    x.addLifeForm(entity4, 3, 3);
    x.addLifeForm(entity5, 1, 1);
    x.addLifeForm(entity6, 2, 2);
    x.addLifeForm(entity7, 1, 3);
    x.addLifeForm(entity8, 2, 3);
    entity1.rotate(Direction.WEST);
    entity2.rotate(Direction.WEST);
    entity3.rotate(Direction.WEST);
    entity4.rotate(Direction.WEST);
    x.moveLifeFormMax(entity1);
    x.moveLifeFormMax(entity2);
    x.moveLifeFormMax(entity3);
    x.moveLifeFormMax(entity4);
    assertEquals("Bob1",x.getLifeForm(1, 0).getName());
    assertEquals("Bob2",x.getLifeForm(2, 1).getName());
    assertEquals("Bob3",x.getLifeForm(1, 2).getName());
    assertEquals("Bob4",x.getLifeForm(3, 3).getName());
  }
  
  
  /**
   * Move a lifeforms east w or w/o obstacles
   */
  @Test
  public void stageF_testMoveLifeFormEast() {
    Environment x = Environment.getEnvironment(row, col);
    x.clearBoard();
    x.resizeEnvironment(4,4);
    LifeForm entity1 = new Alien("Bob1",100);
    LifeForm entity2 = new Alien("Bob2",100);
    LifeForm entity3 = new Alien("Bob3",100);
    LifeForm entity4 = new Alien("Bob4",100);
    LifeForm entity5 = new Alien("Bob5",100);
    LifeForm entity6 = new Alien("Bob6",100);
    LifeForm entity7 = new Alien("Bob7",100);
    LifeForm entity8 = new Alien("Bob8",100);
    x.addLifeForm(entity1, 0, 0);
    x.addLifeForm(entity2, 0, 1);
    x.addLifeForm(entity3, 0, 2);
    x.addLifeForm(entity4, 0, 3);
    x.addLifeForm(entity5, 1, 1);
    x.addLifeForm(entity6, 2, 2);
    x.addLifeForm(entity7, 1, 3);
    x.addLifeForm(entity8, 2, 3);
    entity1.rotate(Direction.EAST);
    entity2.rotate(Direction.EAST);
    entity3.rotate(Direction.EAST);
    entity4.rotate(Direction.EAST);
    x.moveLifeFormMax(entity1);
    x.moveLifeFormMax(entity2);
    x.moveLifeFormMax(entity3);
    x.moveLifeFormMax(entity4);
    assertEquals("Bob1",x.getLifeForm(2, 0).getName());
    assertEquals("Bob2",x.getLifeForm(2, 1).getName());
    assertEquals("Bob3",x.getLifeForm(1, 2).getName());
    assertEquals("Bob4",x.getLifeForm(0, 3).getName());
  }
  
  /**
   * Move a lifeforms north w or w/o obstacles
   */
  @Test
  public void stageG_testMoveLifeFormEdge() {
    Environment x = Environment.getEnvironment(row, col);
    x.clearBoard();
    x.resizeEnvironment(4,4);
    LifeForm entity1 = new Alien("Bob1",100);
    LifeForm entity2 = new Alien("Bob2",100);
    LifeForm entity3 = new Alien("Bob3",100);
    LifeForm entity4 = new Alien("Bob4",100);
    x.addLifeForm(entity1, 0, 0);
    x.addLifeForm(entity2, 3, 0);
    x.addLifeForm(entity3, 3, 3);
    x.addLifeForm(entity4, 0, 3);
    entity1.rotate(Direction.NORTH);
    entity2.rotate(Direction.EAST);
    entity3.rotate(Direction.SOUTH);
    entity4.rotate(Direction.WEST);
    x.moveLifeFormMax(entity1);
    x.moveLifeFormMax(entity2);
    x.moveLifeFormMax(entity3);
    x.moveLifeFormMax(entity4);
    assertEquals("Bob1",x.getLifeForm(0, 0).getName());
    assertEquals("Bob2",x.getLifeForm(3, 0).getName());
    assertEquals("Bob3",x.getLifeForm(3, 3).getName());
    assertEquals("Bob4",x.getLifeForm(0, 3).getName());
  }

  /**
   * test initialize (as Singleton)
   */
  @Test
  public void stage1_testInitialization() {

    Environment x = Environment.getEnvironment(row, col);
    x.resizeEnvironment(row, col);
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        assertNull(x.gridOfCells[i][j].getLifeForm());
      }
    }

  }

  /**
   * can add a weapon to a location
   */
  @Test
  public void stage5_testAddWeapon() {
    Environment x = Environment.getEnvironment(row, col);
    MockWeapon w = new MockWeapon();
    assertTrue(x.addWeapon(w, 1, 1));
  }

  /**
   * can remove a weapon from a location
   */
  @Test
  public void stage6_testRemoveWeapon() {
    Environment x = Environment.getEnvironment(row, col);
    MockWeapon w = new MockWeapon();
    x.addWeapon(w, 2, 2);
    assertEquals(w, x.removeWeapon(w, 2, 2));
  }

  /**
   * get distance along same row
   */
  @Test
  public void stage7_testGetDistanceAlongRow() {
    Environment x = Environment.getEnvironment(row, col);
    LifeForm bob = x.getLifeForm(1, 1);
    LifeForm bub = new MockLifeForm("Bub", 40);
    x.addLifeForm(bub, 1, 0); // Bub is one Cell to the left of Bob
    try {
      assertEquals(5, x.getDistance(bob, bub), 0.01);
    } catch (EnvironmentException e) {
      fail(e.getMessage());
    }
  }

  /**
   * get distance along sam column
   */
  @Test
  public void stage8_testGetDistanceAlongCol() {
    Environment x = Environment.getEnvironment(row, col);
    LifeForm fred = new MockLifeForm("Fred", 40);
    LifeForm ferd = new MockLifeForm("Ferd", 40);
    x.addLifeForm(fred, 2, 2);
    x.addLifeForm(ferd, 0, 2); // Ferd is two Cells above Fred
    try {
      assertEquals(10, x.getDistance(fred, ferd), 0.01);
    } catch (EnvironmentException e) {
      fail(e.getMessage());
    }
  }

  /**
   * get distance not along same row or column diagonal
   */
  @Test
  public void stage9_testGetDistanceAlongDiagonal() {
    Environment x = Environment.getEnvironment(row, col);
    LifeForm bub = x.getLifeForm(1, 0);
    LifeForm fred = x.getLifeForm(2, 2); // Fred is one Cell below & two Cells to the right of Bub
    try {
      assertEquals(Math.sqrt(125), x.getDistance(fred, bub), 0.01);
    } catch (EnvironmentException e) {
      fail(e.getMessage());
    }
  }

  /**
   * get distance not along same row or column coords
   */
  @Test
  public void stageA_testGetDistanceWithCoords() {
    Environment x = Environment.getEnvironment(row, col);
    try {
      assertEquals(Math.sqrt(200), x.getDistance(0, 0, 2, 2), 0.01);
    } catch (EnvironmentException e) {
      fail(e.getMessage());
    }
  }

  /**
   * get distance not along same row or column invalid coords
   */
  @Test
  public void stageB_testGetDistanceWithInvalidCoords() {
    Environment x = Environment.getEnvironment(row, col);
    try {
      double d = x.getDistance(-1, -1, 2, 2);
      fail();
    } catch (EnvironmentException e) {
      // Expected
    }
    try {
      double d = x.getDistance(0, 0, 3, 3);
      fail();
    } catch (EnvironmentException e) {
      // Expected
    }
  }

  /**
   * This is where tests from previous labs begin
   */

  /**
   * Test add lifeform
   */
  @Test
  public void stage2_addLifeFormTest() {
    Environment x = Environment.getEnvironment(row, col);
    LifeForm bob = new MockLifeForm("Bob", 40);
    LifeForm fred = new MockLifeForm("fred", 40);
    assertTrue(x.addLifeForm(fred, 2, 2));
    assertTrue(x.addLifeForm(bob, 1, 1));

  }

  /**
   * tests out of bounds
   */
  @Test
  public void stage3_outOfBoundsTest() {
    Environment x = Environment.getEnvironment(row, col);
    int outOfBoundsRow = 4;
    int outOfBoundsCol = 4;
    LifeForm bob = new MockLifeForm("Bob", 40);
    System.out.print(x.outOfBounds(4, 2));
    System.out.print(" " + x.addLifeForm(bob, outOfBoundsRow, outOfBoundsCol));

  }

  /**
   * tests remove lifeform
   */
  @Test
  public void stage4_removeLifeFormTest() {
    Environment x = Environment.getEnvironment(row, col);
    x.removeLifeForm(2, 2);
    assertFalse(x.hasLifeForm(2, 2));
  }


}
