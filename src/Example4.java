import java.util.*;

public class Example4 {

    private static String search(Map<String, List<String>> graph, String startPosition) {
        final List<String> queue = new ArrayList<>();

        queue.add(startPosition);

        while (queue.size() > 0) {
            String nextPerson = queue.remove(0);

            if (isPersonASeller(nextPerson)) {
                return nextPerson;
            } else {
                queue.addAll(graph.get(nextPerson));
            }
        }

        return "Not Found";
    }

    private static Boolean isPersonASeller(String person) {
        return person.endsWith("m");
    }

    public static void main(String[] args) {
        final Map<String, List<String>> graph = new HashMap<>();

        graph.put("you", Arrays.asList("alice", "bob", "claire"));
        graph.put("bob", Arrays.asList("anuj", "peggy"));
        graph.put("alice", Collections.singletonList("peggy"));
        graph.put("claire", Arrays.asList("thom", "jonny"));
        graph.put("anuj", new ArrayList<>());
        graph.put("peggy", new ArrayList<>());
        graph.put("thom", new ArrayList<>());
        graph.put("jonny", new ArrayList<>());

        System.out.println(search(graph, "you"));
        System.out.println(search(graph, "bob"));
    }
}
