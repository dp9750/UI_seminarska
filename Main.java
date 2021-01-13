package UI_seminarska;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    /**
     * @param filename name of the input file
     * @return 2d char array
     */
    private static char[][] readFile(String filename) {
        ArrayList<char[]> input = new ArrayList<>();

        // If file not found, exit
        try {
            Scanner sc = new Scanner(new File(filename));
            // Read file
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().replace("'", "").split(",");
                char[] lineChar = new char[line.length];
                for (int i = 0; i < line.length; i++)
                    lineChar[i] = line[i].charAt(0);
                input.add(lineChar);
            }

            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Datoteka " + filename + " ni bila najdena.");
            System.exit(2);
        }

        // Array dimensions
        int P = 0, N = 0;

        // Get dimensions
        try {
            P = input.size();
            N = input.get(0)[0];
        } catch (Exception ex) {
            System.out.println("Error reading files");
            System.exit(2);
        }

        // Convert ArrayList to char array
        char[][] out = new char[P][N];
        for (int i = 0; i < P; i++)
            out[i] = input.get(i);
        return out;
    }

    /** Print 2d char array */
    public static void print2DArray(char[][] conf) {
        for (char[] chars : conf) {
            for (char c : chars)
                System.out.print(c + ",");
            System.out.println();
        }
    }

    /**
     * Take top box from column p and move to top of column r
     * Changes the input array
     * @param conf 2d char configuration array
     * @param p where to take from
     * @param r where to move
     */
    private static void move(char[][] conf, int p, int r) {
        if (r < 1 || p < 1 || p == r) return;

        // Index
        p--; r--;

        char top = ' ';

        // Find top of column p
        for (int y = 0; y < conf.length; y++) {
            if (conf[y][p] != ' ') {
                top = conf[y][p];
                conf[y][p] = ' ';
                break;
            }
        }

        // If there are no blocks in column p
        if (top == ' ') return;

        // Put on top of column r
        for (int y = conf.length - 1; y >= 0; y--) {
            if (conf[y][r] == ' ') {
                conf[y][r] = top;
                break;
            }
        }
    }

    /** Is array a equal to array b */
    public static boolean compare(char[][] a, char[][] b) {
        if (a.length != b.length) return false;
        if (a.length == 0) return true;
        if (a[0].length != b[0].length) return false;

        for (int y = 0; y < a.length; y++)
            for (int x = 0; x < a[y].length; x++)
                if (a[y][x] != b[y][x])
                    return false;
        return true;
    }

    /** Copy 2d array */
    private static char[][] copy(char[][] a) {
        char[][] c = new char[a.length][a[0].length];
        for (int y = 0; y < a.length; y++)
            System.arraycopy(a[y], 0, c[y], 0, a[y].length);
        return c;
    }

    /**
     * Does ArrayList of configurations contain the given configuration
     * @param nodes ArrayList configurations
     * @param conf given config
     * @return true or false
     */
    public static boolean containsConf(ArrayList<Node> nodes, char[][] conf) {
        for (Node value : nodes)
            if (compare(conf, value.conf))
                return true;
        return false;
    }

    /**
     * Generate all possible configurations from given conf
     * and store parameters on how to get them.
     * @param conf given start configuration
     * @return ArrayList of all configurations & parameters
     */
    public static ArrayList<Node> generate(char[][] conf) {
        final int cols = conf[0].length;

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node(conf, 0,0));

        // Try placing blocks to the right of current block
        for (int y = 0; y < cols; y++) {
            for (int x = 1; x < cols; x++) {
                char[][] tmpConf = copy(conf);
                move(tmpConf, y+1, x+1);

                if (!containsConf(nodes, tmpConf))
                    nodes.add(new Node(tmpConf, y+1, x+1));
            }
        }

        // Try placing blocks to the left of current block
        for (int y = cols-1; y >= 0; y--) {
            for (int x = cols-2; x >= 0; x--) {
                char[][] tmpConf = copy(conf);
                move(tmpConf, y+1, x+1);

                if (!containsConf(nodes, tmpConf))
                    nodes.add(new Node(tmpConf, y+1, x+1));
            }
        }

        return nodes;
    }

    /**
     * Does stack contain given configuration
     * @param stack given stack
     * @param node given configuration
     * @return true or false
     */
    public static boolean stackContains(Stack<Node> stack, Node node) {
        for (Node value : stack)
            if (compare(node.conf, value.conf))
                return true;
        return false;
    }

    public static void main(String[] args) {

        char[][] startConf = readFile("primer3_zacetna.txt");
        char[][] endConf   = readFile("primer3_koncna.txt");

        //DFS.search(startConf, endConf);
        //BFS.search(startConf, endConf);
        //IDDFS.search(startConf, endConf);
        //AStar.search(startConf, endConf);
        new IDAStar().find(startConf, endConf);
    }

}
