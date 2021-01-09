package UI_seminarska;

import java.util.ArrayList;

public class IDAStar
{
    private char[][] end;   // end configuration
    private boolean found;  // solution found?

    private ArrayList<Node> path;

    // statistics
    private int generatedNodes = 0;
    private int processedNodes = 0;

    /** Calculate h value for given configuration */
    private static int incorrectlyPlaced(char[][] conf, char[][] end)
    {
        int h = 0;
        for (int y = 0; y < conf.length; y++)
            for (int x = 0; x < conf[y].length; x++)
                if (conf[y][x] != end[y][x])
                    h++;
        return h;
    }

    private int search(int gScore, int bound)
    {
        generatedNodes++;

        // get current node
        Node curNode = path.get(0);

        // calculate fScore : g + h
        int fScore = gScore + incorrectlyPlaced(curNode.conf, end);

        if (fScore > bound)
            return fScore;

        processedNodes++;

        // if current node is the solution
        if (Main.compare(curNode.conf, end))
        {
            found = true;
            return fScore;
        }

        int min = Integer.MAX_VALUE;

        // generate neighbours
        ArrayList<Node> generated = Main.generate(curNode.conf);

        // add unique
        for (Node c : generated)
        {
            if (!Main.containsConf(path, c.conf))
            {
                path.add(0, c);
                int res = search(gScore + 1, bound);

                if (found)
                    return res;

                if (res < min)
                    min = res;

                path.remove(0);
            }
        }

        return min;
    }

    public void find(char[][] startConf, char[][] endConf)
    {
        // init variables
        end = endConf;
        found = false;

        // add start node to path
        path = new ArrayList<>();
        path.add(new Node(startConf, 0, 0));

        // h value
        int bound = incorrectlyPlaced(startConf, endConf);

        // search
        while (true)
        {
            int res = search(0, bound);

            if (found)
            {
                System.out.println("Maksimalna globina: " + res);
                System.out.println("Obdelana vozlišča: " + processedNodes);
                System.out.println("Generirana vozlišča: " + generatedNodes);

                System.out.print("Izpis ukazov (od desne proti levi): ");

                for (int i = 0; i < path.size() - 1; i++)
                    System.out.print(path.get(i).toString());
                break;
            }

            if (res == Integer.MAX_VALUE)
            {
                System.out.println("Iz zacetnega vozlisca ni mozno priti do nobenega ciljnega vozlisca!");
                break;
            }

            bound = res;
        }
    }
}