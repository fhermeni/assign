import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A solution to an {@link AssignmentProblem}.
 * @author Fabien Hermenier
 */
public class Assignment {

    private List<String> subjects;

    private Map<Application, String> assigns;

    /**
     * New assignment.
     *
     * @param subjects the possible subjects.
     */
    public Assignment(List<String> subjects) {
        this.subjects = subjects;
        assigns = new HashMap<>();
    }

    /**
     * Declare a partial assignment.
     * @param a the application
     * @param choice the selected option. Shoud belong to {@code a#options()}
     */
    public void add(Application a, String choice) {
        assigns.put(a, choice);
    }

    /**
     * Get the selected option
     * @param a the application
     * @return the assigned option
     */
    public String selected(Application a) {
        return assigns.get(a);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<Application, String>  e : assigns.entrySet()) {
            b.append("Equipe '").append(e.getKey().label()).append("': among ").append(e.getKey().selections()).append(" => ").append(e.getValue()).append("\n");
        }
        return b.toString();
    }

    public Set<Application> getApps() {
        return assigns.keySet();
    }


    public void printPenaltiesCDF() {
        int nbPos = 0;
        for (Application a : assigns.keySet()) {
            nbPos = Math.max(a.selections().size(), nbPos);
        }
        int[] cdf = new int[assigns.size()];

        for (Application a : assigns.keySet()) {
            int pos = a.selections().indexOf(getSelection(a));
            if (pos < 0) {
                System.err.println(a + " " + getSelection(a) + " " + a.selections());
            }
            cdf[pos]++;
        }
        System.out.println("#assignments: " + assigns.size());
        System.out.println("Nb of assignments with choice ranking <= x : ");
        int s = 0;
        for (int i = 0; i < cdf.length; i++) {
            if (s == assigns.size()) {
                break;
            }
            int p = cdf[i];
            s += p;
            System.out.println(i + ": " + s);
        }
    }

    public void printDistribution() {
        int[] cards = new int[subjects.size()];
        for (Application a : assigns.keySet()) {
            String s = getSelection(a);
            int pos = subjects.indexOf(s);
            cards[pos]++;
        }

        System.out.println("Distribution of the selection: ");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println(subjects.get(i) + ": " + cards[i]);
        }
    }

    public String toJSONString() {
        JSONObject o = new JSONObject();
        o.put("subjects", subjects);
        JSONObject s = new JSONObject();
        for (Map.Entry<Application, String> e : assigns.entrySet()) {
            s.put(e.getKey().label(), e.getValue());
        }
        o.put("selections", s);
        return o.toJSONString();
    }

    public String getSelection(Application app) {
        return assigns.get(app);
    }
}
