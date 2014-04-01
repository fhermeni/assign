import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Fabien Hermenier
 */
public class Applications {

    private List<String> subjects;

    private List<Application> applications;

    public Applications(List<String> subjects) {
        this.subjects = Collections.unmodifiableList(subjects);
        this.applications = new ArrayList<>();
    }

    public Application add(String id, List<String> selections) {
        if (!subjects.containsAll(selections)) {
            return null;
        }
        Application app = new Application(applications.size(), id, selections);
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

    public static Applications fromJSON(File f) throws IOException, ParseException {
        JSONParser p = new JSONParser(JSONParser.MODE_RFC4627);
        List<String> subjects = new ArrayList<String>();
        Applications apps = null;
        try (FileInputStream in = new FileInputStream(f)) {
            JSONObject jo = (JSONObject) p.parse(in);

            //Get all the subjects
            apps = new Applications(toString((JSONArray) jo.get("subjects")));

            //Get all the choices
            JSONArray choices = (JSONArray) jo.get("choices");
            for (Object o : choices) {
                String lbl = ((JSONObject) o).get("label").toString();
                List<String> selection = toString((JSONArray) ((JSONObject) o).get("selection"));
                if (apps.add(lbl, selection) == null) {
                    throw new IOException("Application '" + lbl + "' contains unknown subjects");
                }
            }
        }
        return apps;
    }

    private static List<String> toString(JSONArray a) {
        List<String> l = new ArrayList<>(a.size());
        for (Object o : a) {
            l.add(o.toString());
        }
        return l;
    }
}
