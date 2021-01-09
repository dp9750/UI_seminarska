package UI_seminarska;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class IDDFS {

    public static void search(char[][] start, char[][] end)
    {
        Stack<Node> stack = new Stack<>();              // nodes to check
        Node startNode = new Node(start, 0, 0);
        stack.push(startNode);

        ArrayList<Node> visited = new ArrayList<>();    // already visited nodes

        ArrayList<Node> parents = new ArrayList<>();    // parent nodes
        parents.add(startNode);                             // add root

        Map<char[][], Node> map = new HashMap<>();      // path

        int depth = 0;  // current depth to check, starts with 0 (root)
        int generatedNodes = 1; // statistics. 1, including root node

        while (!stack.isEmpty())
        {
            // get current node
            Node current = stack.pop();

            // is current node the solution
            if (Main.compare(current.conf, end))
            {
                System.out.println("Maksimalna globina: " + depth);
                System.out.println("Obdelana vozlišča: " + (visited.size() + 1));
                System.out.println("Generirana vozlišča: " + generatedNodes);
                System.out.print("Izpis ukazov (desne proti levi): ");

                // Print path
                StringBuilder s = new StringBuilder();
                current = map.get(current.conf);
                while (map.containsKey(current.conf))
                {
                    s.append(current.toString());
                    current = map.get(current.conf);
                }
                s.append(current.toString());
                System.out.println(s.toString());

                return;
            }

            // add current node to visited
            visited.add(current);

            // if no more nodes on this level, go lower
            if (stack.isEmpty())
            {
                depth++;

                ArrayList<Node> tmp = new ArrayList<>();

                // for each parent node generate its 'children' nodes
                for (Node parent : parents)
                {
                    ArrayList<Node> neighbours = Main.generate(parent.conf);

                    // only the not already visited ones
                    for (Node neighbour : neighbours) {
                        if (!Main.containsConf(visited, neighbour.conf))
                        {
                            stack.push(neighbour);
                            tmp.add(neighbour);

                            generatedNodes++;
                            map.put(neighbour.conf, new Node(parent.conf, neighbour.p, neighbour.r));
                        }
                    }
                }
                parents = tmp;
            }

        }
    }

}
