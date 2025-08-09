import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialGraph {
    private Map<Person, List<Person>> adjacencyList;

    public SocialGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addPerson(Person person) {
        adjacencyList.putIfAbsent(person, new ArrayList<>());
    }

    public void connect(Person a, Person b) {
        adjacencyList.get(a).add(b);
        adjacencyList.get(b).add(a); 
    }
    public Map<Person, List<Person>> getAdjacencyList() {
        return adjacencyList;
    }    
}