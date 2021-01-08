package UI_seminarska;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AStar {

    /** calculate 'h' value */
    private static int incorrectlyPlaced(char[][] conf, char[][] end) {
        int n = 0;
        for (int y = 0; y < conf.length; y++)
            for (int x = 0; x < conf[y].length; x++)
                if (conf[y][x] != end[y][x])
                    n++;
        return n;
    }

    /** return index of the best node to visit next */
    private static int best(ArrayList<Conf> nodes)
    {
        if (nodes.size() < 2) return 0;

        int min = 0;
        for (int i = 1; i < nodes.size(); i++)
        {
            Conf node = nodes.get(i);
            int sum = node.g + node.h;

            if (sum < min)
                min = i;
        }
        return min;
    }

    public static void search(char[][] start, char[][] end)
    {
        // nodes to visit
        ArrayList<Conf> nodes = new ArrayList<>();

        // set start node
        Conf startNode = new Conf(start, 0, 0);
        startNode.g = 0;
        startNode.h = incorrectlyPlaced(start, end);
        nodes.add(startNode);

        // visited nodes
        ArrayList<Conf> visited = new ArrayList<>();

        // path
        Map<char[][], Conf> map = new HashMap<>();

        int generatedNodes = 0;

        // while there are unvisited nodes
        while (!nodes.isEmpty())
        {
            // get next best node
            int index = best(nodes);
            Conf current = nodes.remove(index);
            generatedNodes++;

            // is current node the solution
            if (Main.compare(current.conf, end))
            {
                // statistics
                System.out.println("Maksimalna globina: " + current.g);
                System.out.println("Obdelana vozlišča: " + (current.g + 1));
                System.out.println("Generirana vozlišča: " + generatedNodes);

                // get path
                StringBuilder s = new StringBuilder();
                current = map.get(current.conf);
                while (map.containsKey(current.conf))
                {
                    s.append(current.toString());
                    current = map.get(current.conf);
                }
                s.append(current.toString());
                System.out.println("Izpis ukazov (od desne proti levi): " + s.toString());

                return;
            }

            // add current node to visited
            visited.add(current);

            // generate nodes neighbours (that have not already been visited)
            ArrayList<Conf> neighbours = Main.generate(current.conf);
            for (Conf neighbour : neighbours) {
                if (!Main.containsConf(visited, neighbour.conf))
                {
                    // add neighbour
                    neighbour.g = current.g + 1;
                    neighbour.h = incorrectlyPlaced(neighbour.conf, end);
                    nodes.add(neighbour);

                    map.put(neighbour.conf, new Conf(current.conf, neighbour.p, neighbour.r));
                }
            }
        }
    }

}
