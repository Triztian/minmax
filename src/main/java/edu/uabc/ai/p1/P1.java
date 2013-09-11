package edu.uabc.ai.p1;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import edu.uabc.ai.search.*;
import static edu.uabc.ai.search.State.S;
import static edu.uabc.ai.search.State.States;

import edu.uabc.util.StringUtil;

public class P1 {
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

    /**
     * Obtain the problem
     */
    public static Problem getProblem(String cities[], List<String[]> connections, String start) {
        Set<State> states= new HashSet<State>();
        
        for(String s : cities)
            states.add(new State(s));
        
        
        Map<String, Problem.Operator> operators= new HashMap<String, Problem.Operator>();
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

        return new Problem(states, operators, new State(start));
    }

    public static void main(String args[]) {
        Problem world= P1.getProblem(CITIES, CONNECTIONS, "A");

        Search search= new Search(world, new State("I"));
        State path[]= search.solve();
        
        System.out.println("Solving...");
        System.out.println("Solution: "  + StringUtil.join(" -> ", path));
    }
}
