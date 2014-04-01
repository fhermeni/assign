/**
 * @author Fabien Hermenier
 */
public interface PenaltyPolicy {

    int notSelectedPenalty();

    int next();

    void reset();
}
