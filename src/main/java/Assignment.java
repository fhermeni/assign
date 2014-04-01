import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabien Hermenier
 */
public class Assignment {

    private List<String> subjects;

    private List<Application> apps;

    private List<String> selected;

    public Assignment(List<String> subjects) {
        this.subjects = subjects;
        this.apps = new ArrayList<Application>();
        this.selected = new ArrayList<String>();
    }

    public void add(Application a, String choice) {
        apps.add(a);
        this.selected.add(choice);
    }

    public String selected(Application a) {
        return selected.get(a.id());
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Application a : apps) {
            b.append("Group ").append(a.id()).append(": among").append(a.selections()).append(" got ").append(selected.get(a.id())).append("\n");
        }
        return b.toString();
    }

    public void printStatistics() {
        int nbPos = 0;
        for (Application a: apps) {
             nbPos = Math.max(a.selections().size(), nbPos);
        }
        int [] cdf = new int[nbPos];
        int [] cards = new int[subjects.size()];
        for (int i = 0; i < apps.size(); i++) {
            int pos = apps.get(i).selections().indexOf(selected.get(i));
            cdf[pos]++;
            cards[subjects.indexOf(selected.get(i))]++;
        }
        System.out.println("Nb groups: " + apps.size());
        System.out.println("Nb of selections with penalty <= x : ");
        int s = 0;
        for (int i = 0; i < cdf.length; i++) {
            int p = cdf[i];
            s+=p;
            System.out.println(i + ": " + s);
        }
        System.out.println("Distribution of the selection: ");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println(subjects.get(i) + ": " + cards[i]);
        }
    }
}
