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
        if (i == 0) {
            i = 1;
            return 0;
        }
        i *= 2;
        return i/2;
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
