package edu.uabc.ai.search;

<<<<<<< HEAD
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
=======
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

>>>>>>> 1830c788ad0ef87285be9287ddb0d56457fa86af
import edu.uabc.ai.search.Node;

public abstract class Search {

    public static final State FAIL[]= null;

    protected Problem p;
    protected State g;
    
    protected Search(Problem p, State g) {
        this.p= p; 
        this.g= g;
    }
    
    protected abstract State[] solve();

    /**
     * Function that determines if a given state is the objective
     * 
     * @param s The state to be tested.
     * @return true if the state is the goal, false otherwise.
     */
    private boolean isGoal(Node n) {
        return n.state.equals(this.g);
    }

    /**
     */
    protected Node[] getSuccessors(Node n, boolean maximizeCost) {
        Node nds[] = toNode(n, p.expand(n.state));
        Arrays.sort(nds, new Node.CostComparator());
        if (maximizeCost) { 
            List<Node> tmp = Arrays.asList(nds);
            Collections.reverse(tmp);
            nds= tmp.toArray(new Node[tmp.size()]);
        }
        return nds;
    }

    /**
     * Create an array of nodes from a set of states.
     *
     * @param parent The parent node of all the states.
     * @param states The states to from which we will create the nodes.
     * 
     * @return An array of nodes.
     */
    protected final Node[] toNode(Node parent, State ... states) {
        List<Node> nodes= new ArrayList<Node>();
        for(State s : states) {
            nodes.add(new Node(s, parent, parent.depth + 1, this.p.cost(parent.state, s)));
        }

        return nodes.toArray(new Node[nodes.size()]);
    }

    /**
     * Obtain the root node for the problem.
     * @param cost The starting cost of the root node.
     *
     * @return The root node.
     */
    protected final Node getRootNode(int cost) {
        return new Node(this.g, Node.ROOT, 0, cost);
    }
}
