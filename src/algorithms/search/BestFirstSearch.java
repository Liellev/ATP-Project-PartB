package algorithms.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;



/**
 * This class implements the Best First Search algorithm by extending BreadthFirstSearch,
    So we use the polymorphism rules.
 */
public class BestFirstSearch extends BreadthFirstSearch{
    /**
     * Default constructor.
     */
    public BestFirstSearch() {
        // Initializes the queue as a PriorityQueue where states are ordered by their cost.
        this.queue = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));

    }

    /**
     * @return the number of nodes evaluated during the search
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return super.getNumberOfNodesEvaluated();
    }

    /**
     *
     * @return the name of the algorithm
     */
    @Override
    public String getName() {
        return "BestFirstSearch";
    }

    /**
     * This method solves a problem using Best first search algorithm.
     * @param s represent a searchable object (the problem needed to be solved)
     * @return a solution of the problem from the start state to the goal state
     */
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
                        processNeighbor(currentState, neighbor,s);
                    }
                }

            }
        }
        return null;
    }

    /**
     * This method takes a state and its neighbor, sets its move cost
     * and adds it to priority queue accordingly.
     * @param currentState The current state being expanded
     * @param neighbor A neighbor of the current state
     */
    public void processNeighbor(AState currentState, AState neighbor, ISearchable s) {
        double moveCost = s.computeMoveCost(currentState, neighbor);
        neighbor.setCost(currentState.getCost() + moveCost);
        neighbor.setCameFrom(currentState);
        visited.add(neighbor);
        queue.add(neighbor);
    }
}
