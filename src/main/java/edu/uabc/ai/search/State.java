package edu.uabc.ai.search;

public class State {
    public final Object value;

    public State(Object value) {
        if (value == null) 
            throw new RuntimeException("State value cannot be null");

        this.value = value;
    }
    
    @Override
    public String toString() {
        return String.format("S(%s)", this.value.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof State))
            return false;

        return this.value.equals(((State)o).value);
    }

    /**
     *  Convenience function to create states.
     *  @param v The value of the state
     *  @param The created state
     */
    public static State S(Object v) {
        return new State(v);
    }

    /**
     * Convenience function to create a set of states.
     *
     * @param vs The values of each state.
     * @return An array containing the created states.
     */
    public static State[] States(Object ... vs) {
        State ss[]= new State[vs.length];
        for(int i= 0; i<vs.length; i++)
            ss[i]= new State(vs[i]);
                
        return ss;
    }
}
