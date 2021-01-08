package UI_seminarska;

/**
 * A structure to contain a configuration and parameters p & r,
 * that determine, how did we create it from the previous
 * configuration.
 */

public class Conf {

    public char[][] conf;   // Configuration
    public int p, r;        // p : move from, r : move to

    public int g, h;        // AStar

    public Conf(char[][] conf, int p, int r) {
        this.conf = conf;
        this.p = p;
        this.r = r;
    }

    @Override
    public String toString() {
        return String.format("(%s %s) ", p, r);
    }
}