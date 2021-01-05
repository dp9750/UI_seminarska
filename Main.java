package UI_seminarska;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

    /**
     * Print 2d char array
     * @param conf 2d char configuration array
     */
    private static void print2DArray(char[][] conf) {
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
    public static void move(char[][] conf, int p, int r) {
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

    /**
     * Začetno in končno konfiguracijo prejme iz args ali standard inputa?
     *
     * Začetna: args[0]
     * Končna:  args[1]
     */
    public static void main(String[] args) {
        /*
        if (args.length < 2) {
            System.out.println("Podaj poti do konfiguracij");
            System.exit(1);
        }

        char[][] zacetna = readFile(args[0]);
        char[][] koncna = readFile(args[1]);
         */

        // Začasno:
        char[][] zacetna = readFile("primer1_zacetna.txt");
        char[][] koncna = readFile("primer1_koncna.txt");

        print2DArray(zacetna);
        System.out.println();
        move(zacetna, 1, 2);
        print2DArray(zacetna);


    }

}
