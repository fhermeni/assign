import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;

/**
 * @author Fabien Hermenier
 */
public class AssignmentProblemTest {

    @Test
    public void test() {
        List<String> subjects = new ArrayList<>();
          for (int i = 0; i < 12; i++) {
              subjects.add("subject " + i);
          }

        Applications a = new Applications(subjects);

        List<Application> apps = new ArrayList<>();
          for (int i = 0; i < 10; i++) {
              apps.add(a.add("group" + i, pick(subjects, 3)));
          }

            AssignmentProblem p = new AssignmentProblem(a);
            p.maxConcurrency(4);
            p.penaltyPolicy(new LinearPenalty());
        Assignment as = p.compute();
        System.out.println(as);
        as.printStatistics();

        System.out.println(as.toJSONString());
        Assert.fail();
    }

    private static Random rnd = new Random();
    private List<String> pick(List<String> s, int nb){
        Set<String> res = new HashSet<>(nb);
        while (res.size() != nb) {
            int idx = rnd.nextInt(s.size());
            res.add(s.get(idx));
        }
        return new ArrayList<>(res);
    }

    @Test
    public void test2() throws Exception {
        Applications apps = Applications.fromJSON(new File("src/test/resources/sample_input.json"));
        AssignmentProblem ap = new AssignmentProblem(apps);
        ap.maxConcurrency(1);
        Assignment res = ap.compute();
        Assert.assertNotNull(res);
        //System.out.println(res);
        res.printStatistics();

        ap = new AssignmentProblem(apps);
        ap.penaltyPolicy(new ExponentialPenalty());
        ap.maxConcurrency(1);
        res = ap.compute();
        Assert.assertNotNull(res);
        //System.out.println(res);
        res.printStatistics();
    }
}
