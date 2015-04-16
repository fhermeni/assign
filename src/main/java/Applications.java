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

    public Application add(String id, List<String> selections, boolean def) {
        if (!subjects.containsAll(selections)) {
            return null;
        }
        Application app = new Application(applications.size(), id, selections, def);
        applications.add(app);
        return app;
    }

    public Application add(String id, List<String> selections) {
        return add(id, selections, false);
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
        Applications apps = null;
        try (FileInputStream in = new FileInputStream(f)) {
            JSONObject jo = (JSONObject) p.parse(in);

            //Get all the subjects
            apps = new Applications(toString((JSONArray) jo.get("subjects")));

            //Get all the choices
            JSONObject choices = (JSONObject) jo.get("choices");
            for (String lbl : choices.keySet()) {
                if( choices.get(lbl) instanceof JSONArray) {
                    List<String> selection = toString((JSONArray) choices.get(lbl));
                    if (apps.add(lbl, selection) == null) {
                        throw new IOException("Application '" + lbl + "' contains unknown subjects");
                    }
                } else {
                    if (choices.get(lbl).equals("*")) {
                        apps.add(lbl, apps.getSubjects(), true);
                    }
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
