package progcw;

/**
 * Class to represent the functionality of a column constraint.
 *
 * @author chayagacy
 */
public class LessThan extends Constraint {

    /**
     * @param square1 the square that you want to be apart of the constraint
     * @param square2 the square that you want to be apart of the constraint
     */
    public LessThan(FutoshikiSquare square1, FutoshikiSquare square2) {
        super(square1, square2);
    }

    @Override
    public boolean isCorrect() {
        if ((isRowConstraint() || isColConstraint()) && square1.getNumber() != 0 && square2.getNumber() != 0) {
            return square1.getNumber() < square2.getNumber();
        } else {
            return true;
        }
    }

    @Override
    public String constraint() {
        if (isRowConstraint()) {
            return "<";
        } else if (isColConstraint()) {
            return "^";
        } else {
            return " ";
        }
    }

    @Override
    public String stringRep() {
        return "less than";
    }
}
