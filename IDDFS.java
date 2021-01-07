package UI_seminarska;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class IDDFS {

    public static void search(Conf start, char[][] end)
    {
        Stack<Conf> stack = new Stack<>();              // nodes to check
        stack.push(start);

        ArrayList<Conf> visited = new ArrayList<>();    // already visited nodes

        ArrayList<Conf> parents = new ArrayList<>();    // parent nodes
        parents.add(start);                             // add root

        Map<char[][], Conf> map = new HashMap<>();      // path

        int depth = 0;  // current depth to check, starts with 0 (root)
        int generatedNodes = 1; // statistics. 1, including root node

        while (!stack.isEmpty())
        {
            // get current node
            Conf current = stack.pop();

            // is current node the solution
            if (Main.compare(current.conf, end))
            {
                System.out.println("Maksimalna globina: " + depth);
                System.out.println("Obdelana vozlišča: " + (visited.size() + 1));
                System.out.println("Generirana vozlišča: " + generatedNodes);
                System.out.println("Izpis ukazov (od spodaj navzdgor): ");

                // Print path
                current = map.get(current.conf);
                while (map.containsKey(current.conf))
                {
                    System.out.println(current.toString());
                    current = map.get(current.conf);
                }
                System.out.println(current.toString());

                return;
            }

            // add current node to visited
            visited.add(current);

            // if no more nodes on this level, go lower
            if (stack.isEmpty())
            {
                depth++;

                ArrayList<Conf> tmp = new ArrayList<>();

                // for each parent node generate its 'children' nodes
                for (Conf parent : parents)
                {
                    ArrayList<Conf> neighbours = Main.generate(parent.conf);

                    // only the not already visited ones
                    for (Conf neighbour : neighbours) {
                        if (!Main.containsConf(visited, neighbour.conf))
                        {
                            stack.push(neighbour);
                            tmp.add(neighbour);

                            generatedNodes++;
                            map.put(neighbour.conf, new Conf(parent.conf, neighbour.p, neighbour.r));
                        }
                    }
                }
                parents = tmp;
            }

        }
    }

}
