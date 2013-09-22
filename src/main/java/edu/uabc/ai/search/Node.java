package edu.uabc.ai.search;

/**
 * This represents an internal node for providing a solution to the problem
 */
class Node {
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
