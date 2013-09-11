package edu.uabc.ai.p2;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import edu.uabc.ai.search.*;
import static edu.uabc.ai.search.State.S;
import static edu.uabc.ai.search.State.States;
import edu.uabc.util.StringUtil;

public class P2 {
    public static void main(String args[]) {
        Problem P = P2.getProblem(CITIES, "A", CONNECTIONS, COSTS);
        State q0 = new State("I");

        Search search= new Search(P, q0);
        State path[]= search.solve();
        int costs[]= new int[path.length];
        for(int i=1; i < path.length; i++)
                costs[i]= P.cost(path[i - 1], path[i]);

        System.out.println("------------------------");
        System.out.println("Solving...");
        System.out.println("Solution: "  + StringUtil.join(" -> ", path));

        System.out.println(String.format("Cost: %s =  %d", StringUtil.join(" + ", costs), P.cost(path)));
        System.out.println("------------------------");
    }

    // The available "cities" (states)
    private static final String CITIES[] = new String[] {
        "A", "B", "C", "D", "E", "F", "G", "H", "I"
    };

    // The connection between states.
    private static final List<String[]> CONNECTIONS= new ArrayList<String[]>() {{
        add(new String[] {"A", "B", "E", "H", "I"});
        add(new String[] {"A", "E", "G", "I"});
        add(new String[] {"A", "C", "E", "I"});
        add(new String[] {"A", "D", "I"});
    }};

    // The costs to transition between states
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
        put(States("E", "I"), 40);
    }};

    /**
     */
    public static Problem getProblem (String cities[], String start, 
                  List<String[]> connections, Map<State[], Integer> costs) {
        Set<State> states =  new HashSet<State>();
        Map<String, Problem.Operator> operators = new HashMap<String, Problem.Operator>();

        for(String city : cities) 
            states.add(new State(city));

        for(String c[] : connections) {
            for(int i=1; i < c.length; i++) {
                final String k= c[i - 1];
                final String v= c[i];

                final State sa= S(k);
                final State sb= S(v);
                
                operators.put(
                    String.format("From%sTo%s", k, v), 
                    new Problem.Operator() { 
                        @Override
                        public boolean apply(State a, State b) {
                           return sa.equals(a) && sb.equals(b);
                        }
                    }
                );
            }
        }

        return new Problem(states, new State(start), operators, costs);
    }

}
