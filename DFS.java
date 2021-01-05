package UI_seminarska;

import java.util.ArrayList;
import java.util.Stack;

public class DFS {

    public static void search(char[][] start, char[][] end) {
        Stack<char[][]> stack = new Stack<>(); // Nodes to check

        ArrayList<char[][]> visited = new ArrayList<>();  // Already visited nodes
        ArrayList<char[][]> possibleMoves;  // Possible moves from current node

        // Add start node to stack
        stack.push(start);

        while (!stack.isEmpty())
        {
            // Current node
            char[][] currentNode = stack.remove(0);

            // Is current node the solution
            if (Main.compare(currentNode, end)) {
                Main.print2DArray(currentNode);
                return;
            }

            // Add current to visited
            visited.add(currentNode);

            // Generate possible moves from current node
            possibleMoves = Main.generate(currentNode);

            // If not in visited, check neighbour node
            for (char[][] node : possibleMoves)
                if (!Main.contains(visited, node))
                    stack.push(node);
        }

        System.out.println("Cannot find solution. ");
    }

}
