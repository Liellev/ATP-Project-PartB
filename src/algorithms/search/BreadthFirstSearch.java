package algorithms.search;
import java.util.*;

/**
 * This class implements the Breadth First Search algorithm (BFS).
 * BFS explores the search space level by level using a FIFO queue.
 * It extends the abstract class ASearchingAlgorithm.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm{

    protected Queue<AState> queue = new LinkedList<>();
    // using hashSet to keep the visited states
    protected HashSet<AState> visited = new HashSet<>();

    /**
     * Getter for visited nodes during algorithm.
     * @return int of nodes count.
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return visited.size();
    }

    /**
     * Getter for the algorithm's name.
     * @return String of the name.
     */
    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    /**
     * This method takes a state and its neighbor, sets its move cost
     * and adds it to priority queue accordingly.
     * @param currentState The current state being expanded
     * @param neighbor A neighbor of the current state
     */
    protected void processNeighbor(AState currentState, AState neighbor) {
        neighbor.setCameFrom(currentState);
        visited.add(neighbor);
        neighbor.setCost(currentState.getCost() + 1);// for each move , similar cost
        queue.add(neighbor);
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
                        processNeighbor(currentState, neighbor);
                    }
                }

            }
        }
        return null;
    }

}
