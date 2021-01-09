package UI_seminarska;

import java.util.*;

public class BFS {

    public static void search(char[][] startConf, char[][] endConf)
    {
        // statistics
        int maxDepth = 0, processedNodes = 0, generatedNodes = 1;

        int l = 0;

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node(startConf, 0, 0));

        // path
        ArrayList<Integer> from = new ArrayList<>();

        Queue<Node> queue = new LinkedList<>();

        queue.add(nodes.get(0));
        from.add(-1);

        while(!queue.isEmpty())
        {
            // current node
            Node current = queue.remove();
            processedNodes++;

            // is current node the solution
            if(Main.compare(current.conf, endConf))
            {
                String rez = nodes.get(l).toString();

                do {
                    l = from.get(l);

                    maxDepth++;
                    rez = nodes.get(l).toString() + rez;
                } while (l >= 1);

                System.out.println("Ukazi: " + rez.substring(5));   // remove (0, 0)
                System.out.println("Število generiranih vozlišč: " + generatedNodes);
                System.out.println("Število obdelanih vozlišč: " + processedNodes);
                System.out.println("Največja preiskana globina: " + maxDepth);

                return;
            }

            // Newly generated configurations
            ArrayList<Node> generated = Main.generate(current.conf);

            // Add unique
            for (Node c : generated)
            {
                if (!Main.containsConf(nodes, c.conf))
                {
                    nodes.add(c);
                    queue.add(c);

                    from.add(l);
                    generatedNodes++;
                }
            }
            l++;
        }
    }

}
