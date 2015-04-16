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
        as.printPenaltiesCDF();

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
        res.printPenaltiesCDF();

        ap = new AssignmentProblem(apps);
        ap.penaltyPolicy(new ExponentialPenalty());
        ap.maxConcurrency(1);
        res = ap.compute();
        Assert.assertNotNull(res);
        //System.out.println(res);
        res.printPenaltiesCDF();
    }

    private Assignment assign(String path, int c) throws Exception {
        Applications apps = Applications.fromJSON(new File(path));
        AssignmentProblem ap = new AssignmentProblem(apps);
        ap.maxConcurrency(c);
        return ap.compute();
    }

    private void testConcurrencies(String path, int lb, int ub) throws Exception {
        for (int i = lb; i < ub; i++) {
            System.out.println("-- Concurrency " + i + " --");
            Assignment res = assign(path, i);
            if (res == null) {
                System.out.println("\tNo Solution");
            } else {
                res.printPenaltiesCDF();
            }
       }
    }

    @Test
    public void assignG3() throws Exception {
        testConcurrencies("/Users/fhermeni/Documents/Teaching/this/iai/projets/g3.json", 1, 5);
    }

/*    @Test
    public void assignG1() throws Exception {
        testConcurrencies("/Users/fhermeni/Teaching/this/IaI/etc/choices_g1.json", 1, 5);
    }

    @Test
    public void assignG2() throws Exception {
        testConcurrencies("/Users/fhermeni/Teaching/this/IaI/etc/choices_g2.json", 1, 5);
    }*/

    @Test
    public void makeStuff() throws Exception {
        String root = "/Users/fhermeni/Documents/Teaching/this/iai/projets/";

        System.out.println("-- Groupe 1 --");
        Assignment g1 = assign(root + "g1.json", 2);
        System.out.println(g1);
        g1.printDistribution();
        g1.printPenaltiesCDF();

        System.out.println("-- Groupe 2 --");
        Assignment g2 = assign(root + "g2.json", 2);
        System.out.println(g2);
        g2.printDistribution();
        g2.printPenaltiesCDF();


        System.out.println("-- Groupe 3 --");
        Assignment g3 = assign(root + "g3.json", 2);
        System.out.println(g3);
        g3.printDistribution();
        g3.printPenaltiesCDF();

        System.out.println("-- Groupe 4 --");
        Assignment g4 = assign(root + "g4.json", 2);
        System.out.println(g4);
        g4.printDistribution();
        g4.printPenaltiesCDF();

        Projects p = new Projects(4);
        p.add(0, g1);
        p.add(1, g2);
        p.add(2, g3);
        p.add(3, g4);
        p.generateAccounts("students.xml");
        p.generateDashboards("projects.xml");
        p.printEmails();
        Assert.fail();
    }
}
