package progcw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

/**
 * This is the class which put all the classes together to make a working
 * futoshiki puzzle. In this class the filling printing and checking are all
 * done.
 *
 * @author ca335
 */
public class FutoshikiGrid implements Serializable {

    private FutoshikiSquare[][] numGrid;
    private Constraint[][] colGrid;
    private Constraint[][] rowGrid;
    private final int gridSize;
    private Random random = new Random();

    /*
    public static void main(String[] args) {
        FutoshikiGrid x = new FutoshikiGrid(5);
        x.fillPuzzle();
        System.out.println(x);
        x.isLegal();
    }
     */
    /**
     * Constructor which initializes all the fields and assigns an appropriate
     * object to every cell of the arrays
     *
     * @param gridSize the size you want the grid to be.
     */
    public FutoshikiGrid(int gridSize) {
        this.gridSize = gridSize;
        numGrid = new FutoshikiSquare[gridSize][gridSize];
        colGrid = new Constraint[gridSize - 1][gridSize];
        rowGrid = new Constraint[gridSize][gridSize - 1];

        reset();
    }

    /**
     * Returns the value stored in a square of the grid as long as it within the
     * indexed size.
     *
     * @param row is the indexed row of the square that you want.
     * @param col is the indexed column of the square that you want.
     * @return returns the number in the square where the row and col intersect.
     */
    public FutoshikiSquare getSquare(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Not in range of the indexed grid");
        }
        return numGrid[row][col];
    }

    /**
     * @return the 2D array numGrid
     */
    public FutoshikiSquare[][] getNumGrid() {
        return numGrid;
    }

    /**
     * Returns the constraint stored in a square of the row constraint grid
     * (rowGrid) as long as it within the indexed size.
     *
     * @param row is the indexed row of the constraint that you want.
     * @param col is the indexed column of the constraint that you want.
     * @return the constraint in the square where the row and col intersect.
     */
    public Constraint getRowConstraint(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Not in range of the indexed grid");
            return null;
        } else {
            return rowGrid[row][col];
        }
    }

    /**
     * @return the size of the grid
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Returns the constraint stored in a square of the column constraint grid
     * (colGrid)as long as it within the indexed size.
     *
     * @param row is the indexed row of the constraint that you want.
     * @param col is the indexed column of the constraint that you want.
     * @return returns the constraint in the square where the row and col
     * intersect.
     */
    public Constraint getColConstraint(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Not in range of the indexed grid");
            return null;
        } else {
            return colGrid[row][col];
        }
    }

    /*
     * This method will return teh numbers of a specified square that the square can not contain.
     *
     * @return a HashSet of the NotNumbers. 
     */
    public HashSet getNotNumbers(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Not in range of the indexed grid");
            return null;
        } else {
            return getSquare(row, col).getNotNumber();
        }
    }

    /**
     * Sets a square you choose with the number you want It has to meet the
     * conditions which makes sure it lies within the size of the grid
     *
     * @param row is the indexed row of the square that you want to put it in.
     * @param col is the indexed col of the square that you want to put it in.
     * @param input is the number you want to be put in the square at (row,
     * col), it has to be greater than 0 and less than gridSize.
     */
    public void setSquare(int row, int col, int input) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Out of range of the indexed grid");
            throw new IllegalArgumentException();
        } else if (input < 0 || input > gridSize) { // equal to 0 so you can make it so a box is empty again
            System.err.println("Your input has to be a postiive number and lower than " + gridSize);
            numGrid[row][col].setNumber(0);
            throw new IllegalArgumentException();
        } else {
            numGrid[row][col].setNumber(input);
        }
    }

    /**
     * Adds a number to the ArrayList in a square about which numbers the square
     * can not be. It has to meet the conditions which make sure it lies within
     * the size of the grid and the notNum has to be greater than 1 and less
     * than gridSize.
     *
     * @param row is the indexed row of the square that you want to add the
     * number it cant be to.
     * @param col is the indexed col of the square that you want to add the
     * number it cant be to.
     * @param notNum is the number you think it can not be.
     */
    public void setNotNumbers(int row, int col, int notNum) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Out of range of the indexed grid");
        } else if (notNum < 1 || notNum > gridSize) {
            System.err.println("Your input has to be a postiive number and lower than");
        } else {
            numGrid[row][col].setNotNumber(notNum);

        }
    }

    /**
     * Adds a constraint to a square in the row constraints grid. It has to meet
     * the conditions which make sure it lies within the size of the grid.
     * Depending on the constraint it will make either a GreaterThan or LessThan
     * object.
     *
     * @param row is the indexed row of the square that you want add the number
     * it cant be to.
     * @param col is the indexed col of the square that you want add the number
     * it cant be to.
     * @param constraint the constraint you want to set either ">" or "<".
     *
     */
    public void setRowConstraint(int row, int col, String constraint) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {// equal to 0 so you can make it so a box is empty
            System.err.println("Out of range of the indexed grid");
        } else if (constraint.equals("<")) {
            rowGrid[row][col] = new LessThan(getSquare(row, col), getSquare(row, col + 1));
        } else if (constraint.equals(">")) {
            rowGrid[row][col] = new GreaterThan(getSquare(row, col), getSquare(row, col + 1));
        } else {
            System.out.println("Your constraint is not valid");
        }
    }

    /**
     * Adds a constraint to a square in the row constraints grid. It has to meet
     * the conditions which make sure it lies within the size of the grid.
     *
     * @param row is the indexed row of the square that you want add the number
     * it cant be to.
     * @param col is the indexed col of the square that you want add the number
     * it cant be to.
     * @param constraint the constraint you want to set either "^" or "V".
     */
    public void setColConstraint(int row, int col, String constraint) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {// equal to 0 so you can make it so a box is empty
            System.err.println("Out of range of the indexed grid");
        } else if (constraint.equals("^")) {
            colGrid[row][col] = new LessThan(getSquare(row, col), getSquare(row + 1, col));
        } else if (constraint.equals("V")) {
            colGrid[row][col] = new GreaterThan(getSquare(row, col), getSquare(row + 1, col));
        } else {
            System.err.println("Your constraint is not valid");
        }
    }

    /**
     * Checks to see if the number grid is full
     *
     * @return returns true if every square of numGrid is full
     */
    public boolean isFull() {
        boolean bool = true;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize - 1; j++) {
                if (numGrid[i][j].getNumber() == 0) {
                    bool = false;
                }
            }
        }
        return bool;
    }

    /**
     * resets the puzzles square back to original state
     */
    public void reset() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                numGrid[i][j] = new FutoshikiSquare(i, j);
            }
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize - 1; j++) {
                rowGrid[i][j] = new GreaterThan(numGrid[0][0], numGrid[0][0]);
            }
        }
        for (int i = 0; i < gridSize - 1; i++) {
            for (int j = 0; j < gridSize; j++) {
                colGrid[i][j] = new GreaterThan(numGrid[0][0], numGrid[0][0]);
            }
        }
    }

    /**
     * Adds the relevant values to each square. It give numbers between 1 and 5
     * for the numbers and different constraints for the constraints. it picks a
     * number of random squares adds it to them
     */
    public void fillPuzzle() {
        reset();

        for (int i = 0; i < gridSize / 1.5; i++) {
            int a = random.nextInt(gridSize);
            int b = random.nextInt(gridSize);
            int c = random.nextInt(gridSize + 1);
            while (c == 0) {
                c = random.nextInt(gridSize + 1);
            }
            setSquare(a, b, c);
            if (getSquare(a, b).getNumber() != 0) {
                getSquare(a, b).setInitial();
            }
        }
        for (int i = 0; i < gridSize / 3; i++) {

            int rConRand = random.nextInt(3);
            if (rConRand <= 0) {
                setRowConstraint(random.nextInt(gridSize), random.nextInt(gridSize - 1), "<");
            } else {
                setRowConstraint(random.nextInt(gridSize), random.nextInt(gridSize - 1), ">");
            }

            int cConRand = random.nextInt(3);
            if (rConRand <= 1) {
                setColConstraint(random.nextInt(gridSize - 1), random.nextInt(gridSize), "V");
            } else {
                setColConstraint(random.nextInt(gridSize - 1), random.nextInt(gridSize), "^");
            }
        }

    }

    /**
     * Checks that all the number in the numGrid horizontally are unique. If
     * there is duplicates it will print out what number is duplicated and how
     * many times it is duplicated.
     *
     * @return all the duplicate numbers in the rows.
     */
    public String checkRowNum() {

        String problem = "";
        FutoshikiSquare[][] tmp = new FutoshikiSquare[gridSize][gridSize];  // use this so that when a number is checked it is
        //turned to 0 so it is not checked again, doing this without effecting the orignal grid
        // also allows to count up how many cuplicates of each number

        for (int a = 0; a < gridSize; a++) {
            for (int b = 0; b < gridSize; b++) {
                tmp[a][b] = numGrid[a][b];
            }
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (tmp[i][j].getNumber() != 0) {
                    int value = tmp[i][j].getNumber();
                    int tooMany = 0;
                    for (int k = j + 1; k < gridSize; k++) {
                        if (value == tmp[i][k].getNumber()) {
                            tooMany++;
                            tmp[i][k] = new FutoshikiSquare(i, k);
                        }
                    }
                    if (tooMany > 0) {
                        problem += "Row " + (i + 1) + " has " + tooMany + " too many " + getSquare(i, j).getNumber() + "'s \n";
                    }
                }
            }
        }
        return problem;
    }

    /**
     * Checks that all the number in the numGrid vertically are unique. If there
     * is duplicates it will print out what number is duplicated and how many
     * times it is duplicated.
     *
     * @return all the duplicate numbers in the columns.
     */
    public String checkColNum() {
        String problem = "";
        FutoshikiSquare[][] tmp = new FutoshikiSquare[gridSize][gridSize];  // use this so that when a number is checked it is
        //turned to 0 so it is checked again, do this without effecting the orignal grid

        for (int a = 0; a < gridSize; a++) {
            for (int b = 0; b < gridSize; b++) {
                tmp[a][b] = numGrid[a][b];
            }
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (tmp[j][i].getNumber() != 0) {
                    int value = tmp[j][i].getNumber();
                    int tooMany = 0;
                    for (int k = j + 1; k < gridSize; k++) {
                        if (value == tmp[k][i].getNumber()) {
                            tooMany++;
                            tmp[k][i] = new FutoshikiSquare(k, i);
                        }
                    }
                    if (tooMany > 0) {
                        problem += "Column " + (i + 1) + " has " + tooMany + " too many " + getSquare(j, i).getNumber() + "'s \n";
                    }
                }
            }
        }
        return problem;
    }

    /**
     * Checks that all the row constraints are met and if any aren't then it
     * will keep record of the problems.
     *
     * @return all the problems where the constraints aren't met in the rows.
     */
    public String checkRowConstraints() {
        String problem = "";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize - 1; j++) {
                if ((getRowConstraint(i, j).constraint() == ("<") && getSquare(i, j).getNumber() == gridSize)
                        || // check if a problem where asking for something bigger than the biggest number
                        (getRowConstraint(i, j).constraint() == (">") && getSquare(i, j + 1).getNumber() == gridSize)) {
                    problem += "In row " + (i + 1) + ", no number can be greater than " + gridSize + "\n";
                }
                if (getRowConstraint(i, j).constraint() == (">") && getSquare(i, j).getNumber() == 1
                        || getRowConstraint(i, j).constraint() == ("<") && getSquare(i, j + 1).getNumber() == 1) {
                    problem += "In row " + (i + 1) + ", no number can be less than 1" + "\n";
                }
                if (getRowConstraint(i, j) != null && getSquare(i, j).getNumber() != 0 && getSquare(i, j + 1).getNumber() != 0) {
                    int tooMany = 0;
                    if (!getRowConstraint(i, j).isCorrect()) {
                        tooMany++;
                    }
                    if (tooMany > 0) {
                        problem += "In row " + (i + 1) + ", " + getSquare(i, j).getNumber() + " is not " + getRowConstraint(i, j).stringRep() + " " + getSquare(i, j + 1).getNumber() + "\n";
                    }
                }
            }
        }
        return problem;
    }

    /**
     * Checks that all the column constraints are met and if any aren't then it
     * will keep record of all the problems.
     *
     * @return all the problems where the constraints aren't met in the rows.
     */
    public String checkColConstraints() {
        String problem = "";
        for (int j = 0; j < gridSize; j++) {
            for (int i = 0; i < gridSize - 1; i++) {
                if ((getColConstraint(i, j).constraint() == ("^") && getSquare(i, j).getNumber() == gridSize)
                        || // check if  asking for something bigger than the biggest number
                        (getColConstraint(i, j).constraint() == ("V") && getSquare(i + 1, j).getNumber() == gridSize)) {
                    problem += "In column " + (j + 1) + ", no number can be greater than " + gridSize + "\n";
                }
                if (getColConstraint(i, j).constraint() == ("V") && getSquare(i, j).getNumber() == 1
                        || //check if were asking for something less than 1
                        getColConstraint(i, j).constraint() == ("^") && getSquare(i + 1, j).getNumber() == 1) {
                    problem += "In column " + (j + 1) + ", no number can be less than 1" + "\n";
                }
                if (getSquare(i, j).getNumber() != 0 && getSquare(i + 1, j).getNumber() != 0 && getColConstraint(i, j) != null) {
                    int tooMany = 0;
                    if (!(getColConstraint(i, j).isCorrect())) {
                        tooMany++;
                    }
                    if (tooMany > 0) {
                        problem += "In column " + (j + 1) + ", " + getSquare(i, j).getNumber() + " is not " + getColConstraint(i, j).stringRep() + " " + getSquare(i + 1, j).getNumber() + "\n"; //sort out signs when printing
                    }
                }
            }
        }
        return problem;
    }

    /**
     * It will return all the problems with the grid at once if there are any
     * otherwise it will print legal puzzle.
     *
     * @return a string of all the problems from the four methods.
     */
    public String getProblems() {
        if ("".equals(checkRowNum() + checkRowConstraints() + checkColNum() + checkColConstraints())) {
            System.out.println("Legal puzzle \n");
            return "Legal puzzle";
        } else {
            System.out.println(checkRowNum() + checkRowConstraints() + checkColNum() + checkColConstraints());
            return checkRowNum() + checkRowConstraints() + checkColNum() + checkColConstraints();
        }
    }

    /**
     * If the problems field is empty then it means that there are no problems
     * and it is a legal puzzle, otherwise it means that there are problems and
     * it will return the problems.
     *
     * @return a string of all the problems if there is any otherwise "Legal
     * puzzle" is printed.
     */
    public boolean isLegal() {
        return getProblems().equals("Legal puzzle");
    }

    /**
     * This will print out the grid in a suitable representation in an actual
     * gird. It will print all the numbers and Constraints in the correct place.
     * The 0 will be printed as a space
     *
     * @return all the grids together in one string.
     */
    @Override
    public String toString() {

        String print = "";
        int n = 0;
        if (gridSize < 10) {
            for (int i = 0; i < gridSize; i++) {
                for (int a = 0; a < gridSize; a++) {
                    print += "--- ";
                }

                print += "\n";
                for (int j = 0; j < gridSize; j++) {
                    if (getSquare(i, j).getNumber() == 0) {
                        print += "| |";
                    } else {
                        print += "|" + getSquare(i, j).getNumber() + "|";
                    }
                    if (j < gridSize - 1) {
                        print += getRowConstraint(i, j).constraint();
                    }
                }

                print += "\n";
                for (int a = 0; a < gridSize; a++) {
                    print += "--- ";
                }

                print += "\n";

                for (int k = 0; k < gridSize; k++) {

                    if (i < gridSize - 1) {
                        print += " " + getColConstraint(i, k).constraint() + "  ";
                    }
                }
                print += "\n";
            }
        } else {
            for (int i = 0; i < gridSize; i++) {
                for (int a = 0; a < gridSize; a++) {
                    print += "----- ";
                }

                print += "\n";
                for (int j = 0; j < gridSize; j++) {
                    if (getSquare(i, j).getNumber() == 0) {
                        print += "|   |";
                    } else if (getSquare(i, j).getNumber() < 10) {
                        print += "| " + getSquare(i, j) + " |";
                    } else {
                        print += "| " + getSquare(i, j) + "|";
                    }
                    if (j < gridSize - 1) {
                        print += getRowConstraint(i, j).constraint();
                    }
                }

                print += "\n";
                for (int a = 0; a < gridSize; a++) {
                    print += "----- ";
                }

                print += "\n";

                for (int k = 0; k < gridSize; k++) {

                    if (i < gridSize - 1) {
                        print += "  " + getColConstraint(i, k).constraint() + "   ";
                    }
                }
                print += "\n";
            }
        }
        return print;
    }

    /**
     * Prints out the grid in a grid layout
     */
    public void print() {
        System.out.println(toString());
    }

    /**
     * Finds the next empty square
     * @return the next empty square object
     */
    public FutoshikiSquare getEmpty() {
        FutoshikiSquare tmp = null;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (getSquare(i, j).getNumber() == 0) {
                    tmp = getSquare(i, j);
                }
            }
        }
        return tmp;
    }

    /**
     * solves the puzzle and put into the grid if it solvable
     * @return true if it can be solved
     */
    public boolean solution() {
        if (isFull() && isLegal()) {
            return true;
        }

        FutoshikiSquare tmp = getEmpty();

        for (int k = 1; k <= gridSize; k++) {
            tmp.setNumber(k);
            if (isLegal()) {
                if (solution()) {
                    return true;
                }
            }
            tmp.setNumber(0);
        }
        return false;
    }

    /**
     * Checks to see if the puzzle is solvable without changing the grid
     * @return true if it is solvable
     */
    public boolean solvable() {
        if (solution()) {
            for (int a = 0; a < gridSize; a++) {
                for (int b = 0; b < gridSize; b++) {
                    if (getSquare(a, b).getInitial() == false) {
                        setSquare(a, b, 0);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
