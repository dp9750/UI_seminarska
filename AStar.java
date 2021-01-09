package UI_seminarska;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AStar {

    /** calculate h value */
    private static int incorrectlyPlaced(char[][] conf, char[][] end) {
        int h = 0;
        for (int y = 0; y < conf.length; y++)
            for (int x = 0; x < conf[y].length; x++)
                if (conf[y][x] != end[y][x])
                    h++;
        return h;
    }

    /** return index of the best node to visit next */
    private static int best(ArrayList<Node> nodes)
    {
        if (nodes.size() < 2) return 0;

        int min = 0;
        for (int i = 1; i < nodes.size(); i++)
        {
            // g + h value of the current node
            Node curNode = nodes.get(i);
            int curSum = curNode.g + curNode.h;

            // g + h value of the current minimum
            Node minNode = nodes.get(i);
            int minSum = minNode.g + minNode.h;

            // set new minimum
            if (curSum < minSum)
                min = i;
        }
        return min;
    }

    public static void search(char[][] start, char[][] end)
    {
        // nodes to visit
        ArrayList<Node> nodes = new ArrayList<>();

        // set start node
        Node startNode = new Node(start, 0, 0);
        startNode.g = 0;
        startNode.h = incorrectlyPlaced(start, end);
        nodes.add(startNode);

        // visited nodes
        ArrayList<Node> visited = new ArrayList<>();

        // path
        Map<char[][], Node> map = new HashMap<>();

        int generatedNodes = 0;

        // while there are unvisited nodes
        while (!nodes.isEmpty())
        {
            // get next best node
            int index = best(nodes);
            Node current = nodes.remove(index);
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
            ArrayList<Node> neighbours = Main.generate(current.conf);
            for (Node neighbour : neighbours) {
                if (!Main.containsConf(visited, neighbour.conf))
                {
                    // add neighbour
                    neighbour.g = current.g + 1;
                    neighbour.h = incorrectlyPlaced(neighbour.conf, end);
                    nodes.add(neighbour);

                    map.put(neighbour.conf, new Node(current.conf, neighbour.p, neighbour.r));
                }
            }
        }
    }

}
