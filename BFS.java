package UI_seminarska;

import java.util.*;

public class BFS {

    public static void search(char[][] start, char[][] end)
    {
        ArrayList<Conf> visited = new ArrayList<>();    // Already visited nodes/configurations

        Queue<Conf> queue = new LinkedList<>(); // Nodes to check
        queue.add(new Conf(start, 0,0));

        Map<char[][], Conf> map = new HashMap<>();

        while (!queue.isEmpty())
        {
            Conf currentNode = queue.remove();

            // If currentNode is the solution
            if (Main.compare(currentNode.conf, end))
            {
                while (map.containsKey(currentNode.conf))
                {
                    System.out.println(map.get(currentNode.conf).toString());
                    
                    currentNode = map.get(currentNode.conf);
                }

                return;
            }

            // Add current node to check ones
            visited.add(currentNode);

            // Generate neighbours
            ArrayList<Conf> neighbours = Main.generate(currentNode.conf);

            // Check neighbours
            for (Conf neighbour : neighbours)
            {
                // Check only the new ones
                if (!Main.containsConf(visited, neighbour.conf))
                {
                    queue.add(neighbour);

                    map.put(neighbour.conf, new Conf(currentNode.conf, neighbour.p, neighbour.r));
                }
            }
        }
    }

}
