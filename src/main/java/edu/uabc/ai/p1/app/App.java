package edu.uabc.ai.p1;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import edu.uabc.ai.search.*;
import edu.uabc.util.StringUtil;

public class App {
    private static final String CITIES[] = new String[] {
        "A", "B", "C", "D", "E", "F", "G", "H", "I"
    };

    private static final List<String[]> CONNECTIONS= new ArrayList<String[]>() {{
        add(new String[] {"A", "B", "E", "H", "I"});
        add(new String[] {"A", "C", "D", "H", "I"});
        add(new String[] {"A", "D", "H", "I"});
        add(new String[] {"A", "E", "F", "I"});
        add(new String[] {"A", "E", "G", "I"});
        add(new String[] {"A", "D", "I"});
        add(new String[] {"A", "H", "I"});
    }};

    public static void main(String args[]) {
        Problem world= new World(CITIES, CONNECTIONS, "A");

        Search search= new Search(world, new State("I"));
        State path[]= search.solve();
        
        System.out.println("Solving...");
        System.out.println("Solution: "  + StringUtil.join(" -> ", path));
    }
}
