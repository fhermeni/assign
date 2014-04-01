/**
 * @author Fabien Hermenier
 */
public class LinearPenalty implements PenaltyPolicy {

    private int i;
    public LinearPenalty() {
        i = 0;
    }

    @Override
    public int next() {
        return i++;
    }

    @Override
    public void reset() {
        i = 0;
    }

    @Override
    public int notSelectedPenalty() {
        return 10;
    }
}
