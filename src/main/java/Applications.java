import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabien Hermenier
 */
public class Applications {

    private List<String> subjects;

    private List<Application> applications;

    public Applications(List<String> subjects) {
        this.subjects = subjects;
        this.applications = new ArrayList<Application>();
    }

    public Application add(List<String> selections) {
        if (!subjects.containsAll(selections)) {
            return null;
        }
        Application app = new Application(applications.size(), selections);
        applications.add(app);
        return app;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public List<Application> getApplications() {
        return applications;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("Subject(s): ");
        b.append(subjects);
        b.append('\n');
        b.append("Groups:\n");
        for (Application g : applications) {
            b.append(g).append('\n');
        }
        return b.toString();
    }
}
