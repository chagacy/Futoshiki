/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import progcw.FutoshikiGrid;
import progcw.FutoshikiSquare;

/**
 *
 * @author ca335
 */
public class FutoshikiGridTest {

    public FutoshikiGridTest() {
    }

    @Test
    public void setSquare() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.setSquare(0, 0, 5);
        int tmp = grid.getSquare(0, 0).getNumber();
        assertEquals(5, tmp);
    }

    @Test
    public void setRowConstraint() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.setRowConstraint(0, 0, "<");
        String tmp = grid.getRowConstraint(0, 0).constraint();
        assertEquals("<", tmp);
    }

    @Test
    public void setColConstraint() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.setColConstraint(0, 0, "^");
        String tmp = grid.getColConstraint(0, 0).constraint();
        assertEquals("^", tmp);
    }
    
    @Test
    public void isFull() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        assertEquals(false, grid.isFull());
    }

    @Test
    public void fillPuzzle() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.fillPuzzle();
        int testNum = 0;
        boolean fillTest = false;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid.getSquare(i, j).getNumber() > 0 && grid.getSquare(i, j).getNumber() <= 5) {
                    testNum++;
                }
                if (testNum > 0) {
                    fillTest = true;
                }
            }
        }
        assertEquals(true, fillTest);

        int testCol = 0;
        boolean fillColTest = false;
        for (int i = 0; i < 5 - 1; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid.getColConstraint(i, j).constraint().equals("^") || grid.getColConstraint(i, j).constraint().equals("V")) {
                    testCol++;
                }
                if (testCol > 0) {
                    fillColTest = true;
                }
            }
        }
        assertEquals(true, fillColTest);

        int testRow = 0;
        boolean fillRowTest = false;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5 - 1; j++) {
                if (grid.getRowConstraint(i, j).constraint().equals("<") || grid.getRowConstraint(i, j).constraint().equals(">")) {
                    testRow++;
                }
                if (testRow > 0) {
                    fillRowTest = true;
                }
            }
        }
        assertEquals(true, fillRowTest);
    }

    @Test
    public void isLegalRowNum() {
        FutoshikiGrid gridNotCorrect = new FutoshikiGrid(5);
        gridNotCorrect.setSquare(0, 0, 1);
        gridNotCorrect.setSquare(0, 1, 2);
        gridNotCorrect.setSquare(0, 2, 3);
        gridNotCorrect.setSquare(0, 3, 4);
        gridNotCorrect.setSquare(0, 4, 5);
        String tmp = gridNotCorrect.checkRowNum();
        assertEquals("", tmp);
        gridNotCorrect.setSquare(0, 0, 1);
        gridNotCorrect.setSquare(0, 1, 1);
        gridNotCorrect.setSquare(0, 2, 1);
        gridNotCorrect.setSquare(0, 3, 1);
        gridNotCorrect.setSquare(0, 4, 1);
        String tmp1 = gridNotCorrect.checkRowNum();
        assertEquals("Row 1 has 4 too many 1's \n", tmp1);
    }

    @Test
    public void isLegalVertNum() {
        FutoshikiGrid gridCorrect = new FutoshikiGrid(5);
        gridCorrect.setSquare(0, 0, 1);
        gridCorrect.setSquare(1, 0, 2);
        gridCorrect.setSquare(2, 0, 3);
        gridCorrect.setSquare(3, 0, 4);
        gridCorrect.setSquare(4, 0, 5);
        String tmp = gridCorrect.checkColNum();
        assertEquals("", tmp);

        FutoshikiGrid gridNotCorrect = new FutoshikiGrid(5);
        gridNotCorrect.setSquare(0, 0, 1);
        gridNotCorrect.setSquare(1, 0, 5);
        gridNotCorrect.setSquare(2, 0, 5);
        gridNotCorrect.setSquare(3, 0, 5);
        gridNotCorrect.setSquare(4, 0, 1);
        String tmp1 = gridNotCorrect.checkColNum();
        assertEquals("Column 1 has 1 too many 1's \nColumn 1 has 2 too many 5's \n", tmp1);
    }

    @Test
    public void isLegalRowConstraints() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.setSquare(0, 0, 1);
        grid.setSquare(0, 1, 2);
        grid.setRowConstraint(0, 0, "<");
        String tmp = grid.checkRowConstraints();
        assertEquals("", tmp);

        grid.setSquare(1, 0, 1);
        grid.setSquare(1, 1, 2);
        grid.setRowConstraint(1, 0, ">");
        String tmp1 = grid.checkRowConstraints();
        assertEquals("In row 2, no number can be less than 1\n" + "In row 2, 1 is not greater than 2\n", tmp1);

        grid.setSquare(4, 3, 1);
        grid.setSquare(4, 4, 1);
        grid.setRowConstraint(4, 3, ">");
        String tmp2 = grid.checkRowConstraints();
        assertEquals("In row 2, no number can be less than 1\n" + "In row 2, 1 is not greater than 2\n"
                + "In row 5, no number can be less than 1\n" + "In row 5, 1 is not greater than 1\n", tmp2);
    }

    @Test
    public void isLegalColConstraints() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.setSquare(0, 0, 1);
        grid.setSquare(1, 0, 2);
        grid.setColConstraint(0, 0, "^");
        String tmp = grid.checkColConstraints();
        assertEquals("", tmp);

        grid.setSquare(1, 4, 1);
        grid.setSquare(2, 4, 2);
        grid.setColConstraint(1, 4, "V");
        String tmp1 = grid.checkColConstraints();
        assertEquals("In column 5, no number can be less than 1\n" + "In column 5, 1 is not greater than 2\n", tmp1);

        grid.setSquare(3, 4, 5);
        grid.setSquare(4, 4, 5);
        grid.setColConstraint(3, 4, "^");
        String tmp2 = grid.checkColConstraints();
        assertEquals("In column 5, no number can be less than 1\n" + "In column 5, 1 is not greater than 2\n" + 
                "In column 5, no number can be greater than 5\n" + "In column 5, 5 is not less than 5\n", tmp2);
    }

    @Test
    public void getProblems() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.setSquare(0, 0, 1);
        grid.setSquare(0, 1, 3);
        grid.setRowConstraint(0, 0, "<");
        String tmp = grid.getProblems();
        assertEquals("Legal puzzle", tmp);

        FutoshikiGrid grid1 = new FutoshikiGrid(5);
        grid1.setSquare(3, 4, 5);
        grid1.setSquare(4, 4, 5);
        grid1.setColConstraint(3, 4, "^");
        grid1.setSquare(1, 2, 1);
        grid1.setSquare(1, 3, 2);
        grid1.setRowConstraint(1, 2, ">");
        grid1.setSquare(3, 0, 4);
        grid1.setSquare(3, 1, 4);
        grid1.setSquare(3, 3, 4);
        String tmp1 = grid1.getProblems();
        assertEquals("Row 4 has 2 too many 4's \n" + "In row 2, no number can be less than 1\n" + "In row 2, 1 is not greater than 2\n"
                + "Column 5 has 1 too many 5's \n" + "In column 5, no number can be greater than 5\n" + "In column 5, 5 is not less than 5\n", tmp1);
    }

    @Test
    public void isLegal() {
        FutoshikiGrid gridCorrect = new FutoshikiGrid(5);
        gridCorrect.setSquare(0, 0, 1);
        gridCorrect.setSquare(0, 1, 3);
        gridCorrect.setRowConstraint(0, 0, "<");
        gridCorrect.setSquare(2, 4, 3);
        gridCorrect.setSquare(3, 4, 1);
        gridCorrect.setColConstraint(2, 4, "V");
        boolean tmp = gridCorrect.isLegal();
        assertEquals(true, tmp);
        FutoshikiGrid gridNotCorrect = new FutoshikiGrid(5);
        gridNotCorrect.setSquare(3, 4, 5);
        gridNotCorrect.setSquare(4, 4, 5);
        gridNotCorrect.setColConstraint(3, 4, "^");
        gridNotCorrect.setSquare(1, 2, 1);
        gridNotCorrect.setSquare(1, 3, 2);
        gridNotCorrect.setRowConstraint(1, 2, ">");
        gridNotCorrect.setSquare(3, 0, 4);
        gridNotCorrect.setSquare(3, 1, 4);
        gridNotCorrect.setSquare(3, 3, 4);
        boolean tmp1 = gridNotCorrect.isLegal();
        assertEquals(false, tmp1);
    }

    @Test
    public void toStringTest() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        String tmp = grid.toString();
        assertEquals("--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n\n", tmp);
        grid.setSquare(3, 4, 5);
        grid.setSquare(4, 4, 5);
        grid.setColConstraint(3, 4, "^");
        grid.setSquare(1, 2, 1);
        grid.setSquare(1, 3, 2);
        grid.setRowConstraint(1, 2, ">");
        grid.setSquare(3, 0, 4);
        grid.setSquare(3, 1, 4);
        grid.setSquare(3, 3, 4);
        String tmp1 = grid.toString();
        assertEquals("--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | |1|>|2| | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n|4| |4| | | |4| |5|\n--- --- --- --- --- \n                 ^  \n"
                + "--- --- --- --- --- \n| | | | | | | | |5|\n--- --- --- --- --- \n\n", tmp1);
    }
    
    @Test
    public void getEmpty() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        int tmp = grid.getEmpty().getNumber();
        assertEquals(grid.getSquare(0, 0).getNumber(), tmp);
    }
    
    @Test
    public void solution() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        boolean tmp = grid.solution();
        assertEquals(true, tmp);
        
        FutoshikiGrid grid1 = new FutoshikiGrid(5);
        grid1.setSquare(3, 0, 4);
        grid1.setSquare(3, 1, 4);
        grid1.setSquare(3, 3, 4);
        boolean tmp1 = grid1.solution();
        assertEquals(false, tmp1);
    }
    
    @Test
    public void solvable() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        boolean tmp = grid.solvable();
        assertEquals(true, tmp);
        int tmp2 = grid.getSquare(0, 0).getNumber();
        assertEquals(0,tmp2);
        
        FutoshikiGrid grid1 = new FutoshikiGrid(5);
        grid1.setSquare(3, 0, 4);
        grid1.setSquare(3, 1, 4);
        grid1.setSquare(3, 3, 4);
        boolean tmp1 = grid1.solution();
        assertEquals(false, tmp1);
    }
}



