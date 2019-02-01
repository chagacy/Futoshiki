/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;
import progcw.FutoshikiSquare;

/**
 *
 * @author chayagacy
 */
public class FutoshikiSquareTest {

    @Test
    public void setNumber() {
        FutoshikiSquare square = new FutoshikiSquare(0, 0);
        square.setNumber(3);
        assertEquals(3, square.getNumber());
    }

    @Test
    public void setNotNumber() {
        FutoshikiSquare square = new FutoshikiSquare(0, 0);
        assertEquals(0, square.getNotNumber().size());
        square.setNotNumber(1);
        assertEquals(1, square.getNotNumber().size());
        square.setNotNumber(1); // insert same number twice should still be size off 1
        assertEquals(1, square.getNotNumber().size());
    }

    @Test
    public void removeNotNumber() {
        FutoshikiSquare square = new FutoshikiSquare(0, 0);

        square.setNotNumber(3);
        assertEquals(1, square.getNotNumber().size());
        square.removeNotNumber(3);
        assertEquals(0, square.getNotNumber().size());
    }
    
    @Test
    public void initial() {
        FutoshikiSquare square = new FutoshikiSquare(0, 0);
        boolean tmp = square.getInitial();
        assertEquals(false, tmp);
        square.setInitial();
        boolean tmp1 = square.getInitial();
        assertEquals(true, tmp1);
    }

}
