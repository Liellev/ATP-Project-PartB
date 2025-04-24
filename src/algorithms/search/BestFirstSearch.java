package algorithms.search;

import java.util.ArrayList;
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
    public Solution solve(ISearchable s) {
        if(s == null) return null;

        // init the first and the final stats
        AState startState = s.getStartState();
        AState goalState = s.getGoalState();

        // Pull the first state from the queue and mark it as visited
        queue.add(startState);
        visited.add(startState);

        while (!queue.isEmpty()) {
            AState currentState = queue.poll();
            // for the situation that the first state is the goal state also
            if (currentState.equals(goalState)) {
                return getSolutionPath(currentState);
            }

            else {
                ArrayList<AState> neighbors =s.getAllPossibleStates(currentState);
                for (AState neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        processNeighbor(currentState, neighbor);
                    }
                }

            }
        }
        return null;
    }


    public void processNeighbor(AState currentState, AState neighbor,ISearchable s) {
        double moveCost = s.computeMoveCost(currentState, neighbor);
        neighbor.setCost(currentState.getCost() + moveCost);
        neighbor.setCameFrom(currentState);
        visited.add(neighbor);
        queue.add(neighbor);
    }




}
