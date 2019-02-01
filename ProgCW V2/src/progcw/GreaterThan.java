package progcw;

/**
 * Class to represent the functionality of a row constraint.
 *
 * @author chayagacy
 */
public class GreaterThan extends Constraint {

    /**
     * passes the parameters up to the super class.
     *
     * @param square1 the square that you want to be apart of the constraint.
     * @param square2 the square that you want to be apart of the constraint.
     */
    public GreaterThan(FutoshikiSquare square1, FutoshikiSquare square2) {
        super(square1, square2);
    }

    @Override
    public boolean isCorrect() {
        if ((isRowConstraint() || isColConstraint()) && square1.getNumber() != 0 && square2.getNumber() != 0) {
            return square1.getNumber() > square2.getNumber();
        } else {
            return true;
        }
    }

    @Override
    public String constraint() {
        if (isRowConstraint()) {
            return ">";
        } else if (isColConstraint()) {
            return "V";
        } else {
            return " ";
        }
    }

    @Override
    public String stringRep() {
        return "greater than";
    }
}
