package UI_seminarska;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DFS {

    public static void search(char[][] start, char[][] end) {
        Stack<char[][]> stack = new Stack<>(); // Nodes to check

        ArrayList<char[][]> visited = new ArrayList<>();  // Already visited nodes
        ArrayList<Conf> possibleMoves;  // Possible moves from current node
                                        // and how to get them

        Map<char[][], char[][]> map = new HashMap<>();  // Find previous step

        // Key: newConfiguration, Value: curConf & parameter p r
        Map<char[][], Conf> betterMap = new HashMap<>();

        // Add start node to stack
        stack.push(start);

        while (!stack.isEmpty())
        {
            // Current node
            char[][] currentNode = stack.remove(0);

            // Is current node the solution
            if (Main.compare(currentNode, end)) {

                while (betterMap.containsKey(currentNode))
                {
                    System.out.println(betterMap.get(currentNode).toString());

                    currentNode = betterMap.get(currentNode).conf;
                }
                return;
            }

            // Add current to visited
            visited.add(currentNode);

            // Generate possible moves from current node
            possibleMoves = Main.generate(currentNode);

            for (Conf move : possibleMoves) {
                if (!Main.contains(visited, move.conf)) {
                    stack.push(move.conf);

                    betterMap.put(move.conf, new Conf(currentNode, move.p, move.r));
                }
            }
        }

        System.out.println("Cannot find solution. ");
    }

}
