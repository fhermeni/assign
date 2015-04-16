import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author Fabien Hermenier
 */
public class Projects {

    private Assignment [] assigns;

    public Projects(int nbGrps) {
        this.assigns = new Assignment[nbGrps];
    }

    public boolean add(int grp, Assignment a) {
        if (assigns[grp] != null) {
            return false;
        }
        assigns[grp] = a;
        return true;
    }

    public void generateAccounts(String path) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(path))) {
            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<students>");
            int grp = 1;
            for (Assignment a : assigns) {
                for (Application app : a.getApps()) {
                    String [] emails = app.label().split(",");
                    for (String email : emails) {
                        out.println("<student email='" + email + "' group='" + grp + "'>");
                        out.println("<password>" + mkPassword(8) + "</password>");
                        out.println("</student>");
                    }
                }
                grp++;
            }
            out.println("</students>");
        }
    }

    public void generateDashboards(String path) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(path))) {
            out.println("<?xml version='1.0' encoding='utf-8'?>");
            out.println("<projects>");
            int id = 1;
            for (Assignment a : assigns) {
                for (Application app : a.getApps()) {
                    out.println("<project id='" + id++ + "'>");
                    out.println("<subject>" + a.getSelection(app) + "</subject>");
                    out.println("<students>");
                    String [] emails = app.label().split(",");
                    for (String email : emails) {
                        out.println("<student email='" + email + "'/>");
                    }
                    out.println("</students>");
                    out.println("</project>");
                }
            }
            out.println("</projects>");
        }
    }

    public void printEmails() {
        for (Assignment a : assigns) {
            for (Application app : a.getApps()) {
                String [] emails = app.label().split(",");
                for (String email : emails) {
                    System.out.println(email);
                }
            }
        }
        System.out.flush();
    }

    private static String chars = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN1234567890";

    private static Random rnd = new Random();
    private static String mkPassword(int len) {
        StringBuilder b = new StringBuilder(len);
        for(int i = 0; i < len; i++) {
            int idx = rnd.nextInt(chars.length());
            b.append(chars.charAt(idx));
        }
        return b.toString();
    }

}
