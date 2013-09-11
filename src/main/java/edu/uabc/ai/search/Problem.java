package edu.uabc.ai.search;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

import edu.uabc.util.StringUtil;

public class Problem {

    public final Set<State> states= new HashSet<State>();
    public final Map<String, Operator> operators= new HashMap<String, Operator>();
    public final Map<State[], Integer> cost= new HashMap<State[], Integer>();
    private State currentState;

    /**  
     * Create a new problem that has no cost to transition between states.
     */
    public Problem(Set<State> states, Map<String, Operator> operators, State q0) {
        this.states.addAll(states);
        this.operators.putAll(operators);
        this.currentState= q0;
        
        State sa[]= states.toArray(new State[states.size()]), 
              sb[]= states.toArray(new State[states.size()]);

        for(State a : sa) 
            for (State b : sb)
                if (!a.equals(b))
                    this.cost.put(new State[]{a, b}, 0);
    }

    /**
     * Create a new problem that has a cost to transition from states.
     */
    public Problem(Set<State> states, State q0, 
                   Map<String, Operator> operators, Map<State[], Integer> costs) {

        this.states.addAll(states);
        this.operators.putAll(operators);
        this.currentState= q0;
        this.cost.putAll(costs);
    }

    /**
     * Obtain the current state of the problem.
     */
    public State getState() {
        return currentState;
    }

    /**
     * This function transitions the problem to a state, 
     * if possible.
     *
     * @param s The state to which we will transition.
     * @return True if the transition was successful, false otherwise
     */
    public boolean transition(State s) {
        for(Operator o : this.operators.values())
            if (transition(s, o)) {
                this.currentState= s;
                return true;
            }

        return false;
    }

    public boolean transition(State s, Operator op) {
        return op.apply(this.getState(), s);
    }

    /**
     * Obtain the cost to transition from a given state to another.
     *
     * @param a The starting state.
     * @param b The next state.
     * @return An integer that represents the cost to transition from 
     *         one state to another.
     */
    public int cost(State a, State b) {
        for(Map.Entry<State[], Integer> e : this.cost.entrySet())
            if (e.getKey()[0].equals(a) && e.getKey()[1].equals(b))
                return e.getValue().intValue();
    
        throw new RuntimeException(
            String.format("There is no cost from %s to %s.", a.toString(), b.toString())
        );
    }

    /**
     * Obtaint the total cost of walking the given path.
     *
     * @param path The sequece of states that will be traversed.
     * @return An integer that represents the sum of the cost 
     *         of traversing the given path.
     */
    public int cost(State ... path) {
        int c= 0;
        if (path.length < 2)
                throw new RuntimeException("There are no tranistions on the given path");
        for(int i=1; i < path.length; i++)
            c += cost(path[i - 1], path[i]);

        return c;
    }

    /**
     * This function calculates all the possible states to which the problem 
     * can transition
     */
    public State[] expand(State s) { 
        List<State> states= new ArrayList<State>();
        for(Map.Entry<String, Operator> o : this.operators.entrySet()) { 
            for(State b : this.states) {
                if (!s.equals(b)) {
                    if (o.getValue().apply(s, b))
                        states.add(b);
                }
            }
        }
        System.out.println(getClass().getName() + ": Expanded: " + 
                           StringUtil.join(", ", states));
        return states.toArray(new State[states.size()]);
    }


    /**
     * Add a new state to the problem
     * @param s The state to be added.
     */
    protected void addState(State s) {
        this.states.add(s);
    };
    

    /**
     * Defines an operator to be applied.
     */
    public static interface Operator {
        public boolean apply(State a, State b);
    }
}
