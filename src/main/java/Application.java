import java.util.Collections;
import java.util.List;

/**
 * @author Fabien Hermenier
 */
public class Application {

    private List<String> choices;

    private int id;

    private String label;

    public Application(int id, String lbl, List<String> choices) {
        this.choices = Collections.unmodifiableList(choices);
        this.id =id;
        this.label = lbl;
    }

    @Override
    public String toString() {
        return "(" + id + ")" + label + " => " + choices;
    }

    public String label() {
        return label;
    }

    public int id() {
        return id;
    }

    public List<String> selections() {
        return choices;
    }
}
