/**
 * To express penalties.
 *
 * @author Fabien Hermenier
 */
public interface PenaltyPolicy {


    int notSelectedPenalty();

    /**
     * get the penalty for the current element and
     * prepare for the next one
     *
     * @return the current penalty
     */
    int next();

    /**
     * Reset the penalty counter.
     */
    void reset();
}
