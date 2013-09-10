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
}
