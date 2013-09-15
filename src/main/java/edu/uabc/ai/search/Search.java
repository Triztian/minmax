package edu.uabc.ai.search;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Iterator;

import edu.uabc.util.StringUtil;
/** 
 * This class performs a search and returns the path
 * to the specified goal state.
 */
public class Search {

    public static final State FAIL[]= null;
    private Problem p;
    private State f;

    /**
     * Create a new search for a solution to a problem
     * 
     * @param p The problem to be solved
     * @param f The desired solution state.
     */
    public Search(Problem p, State f) {
        this.p= p;
        this.f= f;
    }

    /** 
     * Obtain a solution to the problem.
     */
    public State[] solve() {
        LinkedList<Node> nodes= new LinkedList<Node>(Arrays.asList(
            new Node(p.getState(), Node.ROOT, 0, 0)));

        Set<Node> visited= new HashSet<Node>();
        
        Node n;
        while(true) {
            if (nodes.peek() == null) 
                return Search.FAIL;

            n= nodes.pop();
            System.out.println(this.getClass().getName() + 
                               ": Testing node: " + n.toString());
            if (isGoal(n.state))
                break;
            
            visited.add(n);

            Node nds[] = toNode(n, p.expand(n.state));
            System.out.println(this.getClass().getName() + ": Neighbors: " +
                               StringUtil.join(", ", nds));
            Arrays.sort(nds, new Node.CostComparator());
            System.out.println(this.getClass().getName() + ": Sorted Neighbors: " +
                               StringUtil.join(", ", nds));
            
            for(Node nn : nds)
                if (!visited.contains(nn))
                    nodes.offer(nn);
        }
        System.out.println(n);
        return Search.getTransitions(n);
    }

    /**
     * Function that determines if a given state is the objective
     * 
     * @param s The state to be tested.
     * @return true if the state is the goal, false otherwise.
     */
    private boolean isGoal(State s) {
        return s.equals(this.f);
    }

    /**
     * Create an array of nodes from a set of states.
     *
     * @param parent The parent node of all the states.
     * @param states The states to from which we will create the nodes.
     * 
     * @return An array of nodes.
     */
    public Node[] toNode(Node parent, State ... states) {
        List<Node> nodes= new ArrayList<Node>();
        for(State s : states) {
            nodes.add(new Node(s, parent, parent.depth + 1, this.p.cost(parent.state, s)));
        }

        return nodes.toArray(new Node[nodes.size()]);
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
    private static State[] getTransitions(Search.Node n) {
        LinkedList<State> states= new LinkedList<State>();
        do {
            System.out.println("Node: " + n.toString());
            states.offer(n.state);
            n= n.parent;
        } while(n.parent  != null);
        states.offer(n.state);

        State s[] = new State[states.size()];
        Iterator<State> rIter= states.descendingIterator();
        for(int i=0; rIter.hasNext(); i++) 
            s[i]= rIter.next();
        return s;
    }

    /**
     * This represents an internal node for providing a solution to the problem
     */
    public static class Node {
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

        public static class CostComparator implements Comparator<Node> {
            @Override 
            public int compare(Node a, Node b) {
                return a.cost == b.cost ? 0 : (a.cost > b.cost ? 1 : -1);
            }
        }
    }
}
