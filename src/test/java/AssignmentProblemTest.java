import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @author Fabien Hermenier
 */
public class AssignmentProblemTest {

    @Test
    public void test() {
          List<String> subjects = new ArrayList<String>();
          for (int i = 0; i < 12; i++) {
              subjects.add("subject " + i);
          }

          Applications a = new Applications(subjects);
          List<Application> apps = new ArrayList<Application>();
          for (int i = 0; i < 10; i++) {
              apps.add(a.add(pick(subjects, 3)));
          }

            AssignmentProblem p = new AssignmentProblem(a);
            p.maxConcurrency(4);
            p.penaltyPolicy(new LinearPenalty());
        Assignment as = p.compute();
        as.printStatistics();
        Assert.fail();
    }

    private static Random rnd = new Random();
    private List<String> pick(List<String> s, int nb){
        Set<String> res = new HashSet<String>(nb);
        while (res.size() != nb) {
            int idx = rnd.nextInt(s.size());
            res.add(s.get(idx));
        }
        return new ArrayList<String>(res);
        //return Arrays.asList("subject 1", "subject 2", "subject 3");
    }
}
