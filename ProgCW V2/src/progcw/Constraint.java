package progcw; 

import java.io.Serializable;

/**
 * Abstract class to provide basic methods for constraint classes and other
 * methods that they will need.
 *
 * @author chayagacy
 */
public abstract class Constraint implements Serializable{

    /**
     * Allows you to store the futoshiki square you are using to later use for other methods
     */
    protected FutoshikiSquare square1;

    /**
     * Allows you to store the futoshiki square you are using to later use for other methods
     */
    protected FutoshikiSquare square2;
    private boolean rowConstraint;
    private boolean colConstraint;

    /**
     * Constructor which will initialise the fields. It also checks to see whether 
     * you are making a row constraint or column constraint.
     * 
     * @param first the first futoshiki square of the constraint.
     * @param second the second futoshiki square of the constraint.
     */
    public Constraint(FutoshikiSquare first, FutoshikiSquare second) {
        this.square1 = first;
        this.square2 = second;

        if ((first.getRow() == second.getRow()) && (second.getCol() == first.getCol()+1)) {
            rowConstraint = true;
        } else if ((second.getRow() == first.getRow()+1) && (first.getCol() == second.getCol())) {
            colConstraint = true;
        } else {
            rowConstraint = false;
            colConstraint = false;
        }
    }

    /**
     * Returns the rowConstraint for retrieval of the field
     * 
     * @return rowConstraint for retrieval of the field
     */
    public boolean isRowConstraint() {
        return rowConstraint;
    }

    /**
     * Returns the rowConstraint for retrieval of the field
     * 
     * @return rowConstraint for retrieval of the field
     */
    public boolean isColConstraint() {
        return colConstraint;
    }

    /**
     * Method to check that the futoshiki squares meet the constraints.
     * 
     * @return true if they do meet the constraint.
     */
    public abstract boolean isCorrect();
    
    /**
     * Returns the symbol of the constraint that has been assigned.
     * 
     * @return symbol of the constraint.
     */
    public abstract String constraint();

    /**
     * Returns a word representation of the constraint.
     * 
     * @return word representation of the constraint.
     */
    public abstract String stringRep();
}
