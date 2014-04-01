/**
 * @author Fabien Hermenier
 */
public class ExponentialPenalty implements PenaltyPolicy {

    private int i;
    public ExponentialPenalty() {
        i = 0;
    }

    @Override
    public int next() {
        int old = i;
        i = 2 * i + 1;
        return old;
    }

    @Override
    public void reset() {
        i = 0;
    }

    @Override
    public int notSelectedPenalty() {
        return Integer.MAX_VALUE / 100;
    }
}
