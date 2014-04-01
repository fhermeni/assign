import solver.ResolutionPolicy;
import solver.Solver;
import solver.constraints.ICF;
import solver.variables.IntVar;
import solver.variables.VF;
import util.ESat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fabien Hermenier
 */
public class AssignmentProblem {

    private Applications apps;

    private Solver solver;

    private Map<String, Integer> subjectId;

    private IntVar[] assigns;

    private IntVar[] costs;

    private PenaltyPolicy pp;

    private int maxConcurrency;

    public AssignmentProblem(Applications apps) {
        pp = new LinearPenalty();
        this.apps = apps;
        subjectId = new HashMap<>();
        int i = 0;
        for (String s : apps.getSubjects()) {
            subjectId.put(s, i++);
        }
        solver = new Solver();
        makeCore();
        maxConcurrency = subjectId.size();
    }

    public AssignmentProblem penaltyPolicy(PenaltyPolicy p) {
        this.pp = p;
        return this;
    }

    public AssignmentProblem maxConcurrency(int m) {
        maxConcurrency = m;
        return this;
    }

    private void makeCore() {
        assigns = new IntVar[apps.getApplications().size()];

        for (Application c : apps.getApplications()) {
            int i = c.id();
            int [] selections = toArray(c.selections());
            assigns[i] = VF.enumerated("assign(" + i + ")", Arrays.copyOf(selections, selections.length), solver);
        }
    }

    private void makePenalties() {
        costs = new IntVar[assigns.length];
        for (Application c : apps.getApplications()) {
            int [] penalties = new int[subjectId.size()];
            Arrays.fill(penalties, pp.notSelectedPenalty());
            int i = c.id();
            int [] selections = toArray(c.selections());
            costs[i] = VF.bounded("penalty(" + i+")", 0, pp.notSelectedPenalty(), solver);
            for (int x : selections) {
                penalties[x] = pp.next();
            }
            pp.reset();
            solver.post(ICF.element(costs[i], penalties, assigns[i]));
        }
    }
    public Assignment compute() {
        makePenalties();
        makeConcurrency();
        IntVar obj = VF.bounded("penalties", 0, Integer.MAX_VALUE / 1000, solver);
        solver.post(ICF.sum(costs, obj));
        solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, obj);
        if (solver.isFeasible().equals(ESat.FALSE)) {
            return null;
        }
        Assignment res = new Assignment(apps.getSubjects());
        for (Application a : apps.getApplications()) {
            int selected = assigns[a.id()].getLB();
            res.add(a, apps.getSubjects().get(selected));
        }
        return res;
    }

    private void makeConcurrency() {
        if (maxConcurrency == 1) {
            solver.post(ICF.alldifferent(assigns,"AC"));
        } else if(maxConcurrency < subjectId.size()) {
            IntVar max = VF.bounded("maxConcurrency", 0, maxConcurrency, solver);
            solver.post(ICF.nvalues(assigns, max, "at_most_BC"));
        }
    }

    private int[] toArray(List<String> choices) {
        int[] values = new int[choices.size()];
        int i = 0;
        for (String c : choices) {
            values[i++] = subjectId.get(c);
        }
        return values;
    }
}
