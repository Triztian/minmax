package edu.uabc.ai.search;

import java.util.Comparator;
/**
 * This represents an internal node for providing a solution to the problem
 */
public class Node {
    public static Node ROOT= null;

    public final State state;
    public final Node parent;
    public final int depth;
    public final int cost;

    public Node(State s, Node parent, int depth, int cost) {
        this.state= s;
        this.parent= parent;
        this.depth= depth;
        this.cost= cost;
    }
    
    @Override
    public String toString() {
        return String.format("N(%s, %d, %d):%s", 
            parent != null ? parent.state.toString() : "ROOT", 
            this.depth, this.cost, this.state.value.toString()
        );
    }

    /**
     * Obtain the array of states that derive from the node
     * and its parents.
     * 
     * @param n The node from where the state transitions will
     *          be derived
     * @return An array of states in which the first elements is
     *         the intitial state of the problem.
     */
    public State[] toPath() {
        LinkedList<State> states= new LinkedList<State>();
        Node n= this;
        do {
            states.offer(n.state);
            n= n.parent;
        } while(n.parent  != null);
        states.offer(n.state);

        State s[] = new State[states.size()];
        Iterator<State> iter= states.descendingIterator();
        for(int i=0; iter.hasNext(); i++) 
            s[i]= iter.next();

        return s;
    }

    public static class CostComparator implements Comparator<Node> {
        @Override 
        public int compare(Node a, Node b) {
            return a.cost == b.cost ? 0 : (a.cost > b.cost ? 1 : -1);
        }
    }

    
}
