import java.util.*;

public class GraphRouter {
    public static List<Person> findPath(Person start, Person end, SocialGraph graph) {
        Queue<Person> queue = new LinkedList<>();
        Map<Person, Person> parent = new HashMap<>();
        Set<Person> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Person current = queue.poll();
            if (current.equals(end)) {
                List<Person> path = new ArrayList<>();
                while (current != null) {
                    path.add(0, current);
                    current = parent.get(current);
                }
                return path;
            }

            for (Person neighbor : graph.adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }
}
```
