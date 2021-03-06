package UI_seminarska;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DFS {

    public static void search(char[][] start, char[][] end)
    {
        Stack<Node> stack = new Stack<>();          // nodes to visit
        ArrayList<Node> visited = new ArrayList<>();// already visited nodes
        Map<char[][], Node> map = new HashMap<>();  // path

        // Statistics
        int maxDepth = 0, generatedNodes = 0;

        // Add start to checked nodes
        stack.push(new Node(start, 0, 0));

        // While there are nodes to check
        while (!stack.isEmpty())
        {
            // Check last element in stack
            Node current = stack.pop();

            // If current node is the end node
            if (Main.compare(current.conf, end))
            {
                System.out.println("Obdelana vozlišča: " + (visited.size() + 1));
                System.out.println("Generirana vozlišča: " + generatedNodes);

                // Print path
                System.out.print("Izpis ukazov (od desne proti levi): ");
                StringBuilder s = new StringBuilder();
                current = map.get(current.conf);
                while (map.containsKey(current.conf))
                {
                    s.append(current.toString());
                    current = map.get(current.conf);

                    maxDepth++;
                }
                s.append(current.toString());
                System.out.println(s.toString());

                System.out.println("Maksimalna globina: " + maxDepth);

                return;
            }

            // add current node to visited
            visited.add(current);

            // Neighbours of the current node
            ArrayList<Node> neighbours = Main.generate(current.conf);

            // Check unchecked neighbours
            for (Node neighbour : neighbours) {
                if (!Main.containsConf(visited, neighbour.conf) &&
                        !Main.stackContains(stack, neighbour)) {
                    stack.push(neighbour);

                    map.put(neighbour.conf, new Node(current.conf, neighbour.p, neighbour.r));
                    generatedNodes++;
                }
            }
        }
    }
}
