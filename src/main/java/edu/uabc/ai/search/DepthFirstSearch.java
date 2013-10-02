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
public class DepthFirstSearch extends Search {


    /**
     * Create a new search for a solution to a problem
     * * @param p The problem to be solved * @param f The desired solution state.
     */
    public DepthFirstSearch(Problem p, State f) {
        super(p, f);
    }

    @Override
    public State[] solve() {
        return this.solve(false);
    }

    /** 
     * Obtain a solution to the problem.
     */
    public State[] solve(boolean maximizeCost) {
        Stack s = new Stack();
        Set<Node> discovered =  new HashSet<Node>();
        Set<Node> explored = new HashSet<Node>();
        
        Node t;
        s.push(super.toRootNode(0));
        while (!s.empty()) {
            t= s.peek();
            if (isGoal(t))
                return t.toPath();
            
            if (successorsDiscovered(t))
                explored.add(t);

            Node nds[] = getSuccessors(t, false); toNode(t, expand(t.state));

            for(Node e : nds) {
                if (explored.contains(e))
                    continue;

                if (!discovered.contains(e)) {
                    discovered.add(e);
                    s.push(e);
                    break;
                }
            }
            s.pop();
        }

        return new State[0];
    }
}
