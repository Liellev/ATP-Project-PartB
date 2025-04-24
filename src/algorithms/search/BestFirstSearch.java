package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

/*/
This class is extending from BreadthFirstSearch, because the implication is similar
So we use the polymorphism rules.
 */
public class BestFirstSearch extends BreadthFirstSearch{
    /*
    Default constructor
     */
    public BestFirstSearch() {
        // best
        this.queue = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return super.getNumberOfNodesEvaluated();
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }

    @Override
    public void processNeighbor(AState currentState, AState neighbor) {
        double moveCost = computeMoveCost(currentState, neighbor);
        neighbor.setCost(currentState.getCost() + moveCost);
        neighbor.setCameFrom(currentState);
        visited.add(neighbor);
        queue.add(neighbor);
    }

    public double computeMoveCost(AState from, AState to) {
        int[] loc1 = (int[]) from.getLocation();
        int[] loc2 = (int[]) to.getLocation();

        if(loc1 == null || loc2 == null){
            return Double.POSITIVE_INFINITY;
        }

        // calculate the difference in rows and columns
        int dr = Math.abs(loc1[0] - loc2[0]);
        int dc = Math.abs(loc1[1] - loc2[1]);

        if (dr + dc == 1) return 10;       // direct step
        if (dr == 1 && dc == 1) return 15; // diagonal step
        return Double.POSITIVE_INFINITY;  // invalid movement
    }
}
