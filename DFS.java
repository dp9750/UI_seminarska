package UI_seminarska;

import java.util.ArrayList;
import java.util.Stack;

public class DFS {

    public static ArrayList<Integer> search(int[][] graph, int startNode, int endNode)
    {
        // Statistics
        int maxDepth = 0, generatedNodes = 0;
        boolean[] marked = new boolean[graph.length]; // checked nodes
        int[] from = new int[graph.length];           // backtrack path

        Stack<Integer> stack = new Stack<>();         // nodes to check

        ArrayList<Integer> path = new ArrayList<>();  // path to solution

        from[startNode] = -1;
        marked[startNode] = true;
        stack.push(startNode);

        while(!stack.isEmpty())
        {
            // Current node
            int curNode = stack.peek();

            // If current node is the end node
            // Create and return path
            if (endNode == curNode)
            {
                path.add(curNode);

                while (true)
                {
                    curNode = from[curNode];
                    if (curNode != -1)
                        path.add(curNode);
                    else
                        break;
                }

                System.out.println("Maksimlna globina: " + maxDepth);
                System.out.println("Generirana vozlišča: " + generatedNodes);
                System.out.println("Obdelana vozlišča: " + path.size());

                return path;
            }

            maxDepth++;

            // find unvisited neighbour
            boolean found = false;
            for (int nextNode = 0; nextNode < graph[curNode].length; nextNode++)
            {
                if (graph[curNode][nextNode] == 1 && !marked[nextNode])
                {
                    marked[nextNode] = true;
                    from[nextNode] = curNode;
                    stack.push(nextNode);

                    generatedNodes++;

                    found = true;
                    break;
                }
            }

            if (!found) stack.pop();
        }

        return null;
    }

    /*
    public static void search(char[][] start, char[][] end) {

        // Statistics
        int processedNodes = 0, maxDepth = 0, generatedNodes = 0;

        Stack<char[][]> stack = new Stack<>(); // Nodes to check

        ArrayList<char[][]> visited = new ArrayList<>();  // Already visited nodes
        ArrayList<Conf> possibleMoves;  // Possible moves from current node
                                        // and how to get them

        // Key: newConfiguration, Value: curConf & parameter p r
        Map<char[][], Conf> map = new HashMap<>();

        // Add start node to stack
        stack.push(start);

        while (!stack.isEmpty())
        {
            // Current node
            char[][] currentNode = stack.remove(0);
            processedNodes++;

            // Is current node the solution
            if (Main.compare(currentNode, end)) {

                System.out.println("Največja globina: " + maxDepth);
                System.out.println("Število obdelanih vozlišč: " + processedNodes);
                System.out.println("Število generiranih vozlišč: " + generatedNodes);
                System.out.println("Zaporedje ukazov (od spodaj navzgor): ");
                while (map.containsKey(currentNode))
                {
                    System.out.println(map.get(currentNode).toString());

                    currentNode = map.get(currentNode).conf;
                }
                return;
            }

            // Add current to visited
            visited.add(currentNode);

            // Generate possible moves from current node
            possibleMoves = Main.generate(currentNode);
            generatedNodes += possibleMoves.size();
            maxDepth++;

            for (Conf move : possibleMoves) {
                if (!Main.contains(visited, move.conf)) {
                    stack.push(move.conf);

                    map.put(move.conf, new Conf(currentNode, move.p, move.r));
                }
            }
        }

        System.out.println("Cannot find solution. ");
    }
    */
}
