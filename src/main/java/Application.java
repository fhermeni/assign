import java.util.List;

/**
 * @author Fabien Hermenier
 */
public class Application {

    private List<String> choices;

    private int id;
    public Application(int id, List<String> choices) {
        this.choices = choices;
        this.id =id;
    }

    @Override
    public String toString() {
        return id + "=>" + choices;
    }

    public int id() {
        return id;
    }

    public List<String> selections() {
        return choices;
    }
}
