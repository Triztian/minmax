package edu.uabc.ai.search;

import java.util.Set;
import java.util.Stack;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collections;

import edu.uabc.ai.search.Node;
import edu.uabc.util.StringUtil;
/** 
 * This class performs a search and returns the path
 * to the specified goal state.
 */
public class DepthFirstSearch {

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

    public State[] solve() {
        return this.solve(false);
    }

    /** 
     * Obtain a solution to the problem.
     */
    public State[] solve(boolean maximizeCost) {
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

}
