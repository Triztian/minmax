package edu.uabc.ai.p1;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import edu.uabc.ai.search.*;
import edu.uabc.util.StringUtil;

/**
 * This class represents the world
 */
class World extends Problem {

    public final List<String> cities= new ArrayList<String>();
    private final Map<String, String> connections = new HashMap<String, String>();
    
    /**
     * Create a new world.
     */
    public World (String cities[], List<String[]> connections, String start) {
        super(new HashSet<State>(), new HashMap<String, Problem.Operator>(), new State(start));
        this.cities.addAll(Arrays.asList(cities));
        for(String city : this.cities) 
            super.addState(new State(city));

        for(String c[] : connections) {
            for(int i=1; i < c.length; i++) {
                final String k= c[i - 1];
                final String v= c[i];

                final State sa= this.getState(k);
                final State sb= this.getState(v);
                
                this.connections.put(k, v);
                this.operators.put(
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


    }

    /**
     * Add a new city to this world. Without any roads
     *
     * @param city The city to be added.
     */
    private void addCity(String city) {
        this.cities.add(city);
        this.states.add(new State(city));
    }
    
    /**
     * Connect a list of cities one after another.
     */
    private void connect(String ... cities) {
        if (cities.length < 2)
            return;

        for(int c= 1; c < cities.length; c++)
            if (this.cities.contains(cities[c - 1]) && 
                this.cities.contains(cities[c]))
                this.connections.put(cities[c - 1], cities[c]);
    }

    /**
     * Obtain the corresponding state for the given value.
     *
     * @param value The value searched.
     */
    public State getState(String value) {
        return new State(cities.get(cities.indexOf(value)));
    }
}
