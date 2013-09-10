package edu.uabc.ai.p1;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import edu.uabc.ai.search.*;
import static edu.uabc.ai.search.State.S;
import static edu.uabc.ai.search.State.States;
import edu.uabc.util.StringUtil;

public class App {
    private static final String CITIES[] = new String[] {
        "A", "B", "C", "D", "E", "F", "G", "H", "I"
    };

    private static final List<String[]> CONNECTIONS= new ArrayList<String[]>() {{
        add(new String[] {"A", "B", "E", "H", "I"});
        add(new String[] {"A", "E", "G", "I"});
        add(new String[] {"A", "C", "E", "I"});
        add(new String[] {"A", "D", "I"});
    }};

    private static final Map<State[], Integer> COSTS= new HashMap<State[], Integer>(){{
        put(States("A", "B"), 50);
        put(States("A", "C"), 20);
        put(States("A", "D"), 50);
        put(States("A", "E"), 50);
        put(States("B", "C"), 35);
        put(States("C", "D"), 35);
        put(States("D", "E"), 40);
        put(States("E", "F"), 60);
        put(States("F", "G"), 25);
        put(States("G", "H"), 50);
        put(States("H", "I"), 70);
    }};

    public static void main(String args[]) {
        Problem world= new World(CITIES, "A", CONNECTIONS, COSTS);

        Search search= new Search(world, new State("I"));
        State path[]= search.solve();
        
        System.out.println("Solving...");
        System.out.println("Solution: "  + StringUtil.join(" -> ", path));
    }
}
