import java.util.Collections;
import java.util.List;

/**
 * @author Fabien Hermenier
 */
public class Application {

    private List<String> choices;

    private int id;

    private String label;

    private boolean def;

    public Application(int id, String lbl, List<String> choices, boolean def) {
        this.choices = Collections.unmodifiableList(choices);
        this.id =id;
        this.label = lbl;
        this.def  = def;
    }

    @Override
    public String toString() {
        return "(" + id + ")" + label + " => " + choices;
    }


    public boolean isDefault() {
        return def;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;
        return (id == that.id());
    }

    @Override
    public int hashCode() {
        return id;
    }
}
