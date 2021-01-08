package UI_seminarska;

import java.util.*;

public class BFS {

    public static void search(char[][] startConf, char[][] endConf)
    {
        // statistics
        int maxDepth = 0, processedNodes = 0, generatedNodes = 1;

        int l = 0;

        ArrayList<Conf> confs = new ArrayList<>();
        confs.add(new Conf(startConf, 0, 0));

        // path
        ArrayList<Integer> from = new ArrayList<>();

        Queue<Conf> queue = new LinkedList<>();

        queue.add(confs.get(0));
        from.add(-1);

        while(!queue.isEmpty())
        {
            // current node
            Conf current = queue.remove();
            processedNodes++;

            // is current node the solution
            if(Main.compare(current.conf, endConf))
            {
                String rez = confs.get(l).toString();

                do {
                    l = from.get(l);

                    maxDepth++;
                    rez = confs.get(l).toString() + rez;
                } while (l >= 1);

                System.out.println("Ukazi: " + rez.substring(5));   // remove (0, 0)
                System.out.println("Število generiranih vozlišč: " + generatedNodes);
                System.out.println("Število obdelanih vozlišč: " + processedNodes);
                System.out.println("Največja preiskana globina: " + maxDepth);

                return;
            }

            // Newly generated configurations
            ArrayList<Conf> generated = Main.generate(current.conf);

            // Add unique
            for (Conf c : generated)
            {
                if (!Main.containsConf(confs, c.conf))
                {
                    confs.add(c);
                    queue.add(c);

                    from.add(l);
                    generatedNodes++;
                }
            }
            l++;
        }
    }

}
