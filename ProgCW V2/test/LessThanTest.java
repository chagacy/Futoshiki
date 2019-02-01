/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;
import progcw.Constraint;
import progcw.LessThan;
import progcw.FutoshikiGrid;
import progcw.FutoshikiSquare;

/**
 *
 * @author chayagacy
 */
public class LessThanTest {

    @Test
    public void constraintTest() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        Constraint up = new LessThan(grid.getSquare(0, 0), grid.getSquare(1, 0));
        String tmp = up.constraint();
        assertEquals("^", tmp);
    }

    @Test
    public void stringTestTest() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        Constraint up = new LessThan(grid.getSquare(0, 0), grid.getSquare(1, 0));
        String tmp = up.stringRep();
        assertEquals("less than", tmp);
    }

    @Test
    public void rowConstraintBoolean() {
        FutoshikiSquare square1 = new FutoshikiSquare(0,0);
        square1.setNumber(5);
        FutoshikiSquare square2 = new FutoshikiSquare(0,1);
        square2.setNumber(1);
        Constraint right = new LessThan(square1, square2);
        boolean correct = right.isRowConstraint();
        assertEquals(true, correct);
        
        FutoshikiSquare square3 = new FutoshikiSquare(0,0);
        square1.setNumber(5);
        FutoshikiSquare square4 = new FutoshikiSquare(1,0);
        square2.setNumber(1);
        Constraint down = new LessThan(square3, square4);
        boolean notCorrect = down.isRowConstraint();
        assertEquals(false, notCorrect);
    }

    @Test
    public void colConstraintBoolean() {
        FutoshikiSquare square1 = new FutoshikiSquare(0,0);
        square1.setNumber(5);
        FutoshikiSquare square2 = new FutoshikiSquare(1,0);
        square2.setNumber(1);
        Constraint up = new LessThan(square1, square2);
        boolean correct = up.isColConstraint();
        assertEquals(true, correct);
        
        FutoshikiSquare square3 = new FutoshikiSquare(0,0);
        square1.setNumber(5);
        FutoshikiSquare square4 = new FutoshikiSquare(0,1);
        square2.setNumber(1);
        Constraint right = new LessThan(square3, square4);
        boolean notCorrect = right.isColConstraint();
        assertEquals(false, notCorrect);
    }

    @Test
    public void isCorrect() {
        FutoshikiSquare square1 = new FutoshikiSquare(0,0);
        square1.setNumber(2);
        FutoshikiSquare square2 = new FutoshikiSquare(1,0);
        square2.setNumber(5);
        Constraint correct = new LessThan(square1, square2);
        boolean tmp = correct.isCorrect();
        assertEquals(true, tmp);

        FutoshikiSquare square3 = new FutoshikiSquare(0,0);
        square3.setNumber(4);
        FutoshikiSquare square4 = new FutoshikiSquare(1,0);
        square4.setNumber(3);
        Constraint notCorrect = new LessThan(square3, square4);
        boolean tmp1 = notCorrect.isCorrect();
        assertEquals(false, tmp1);
    }
}
