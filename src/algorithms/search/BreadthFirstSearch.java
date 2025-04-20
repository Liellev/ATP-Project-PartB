package algorithms.search;
import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm{

    protected Queue<AState> queue = new LinkedList<>();
    protected HashSet<AState> visited = new HashSet<>();

    @Override
    public int getNumberOfNodesEvaluated() {
        return visited.size();
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    protected void processNeighbor(AState currentState, AState neighbor) {
        neighbor.setCameFrom(currentState);
        visited.add(neighbor);
        neighbor.setCost(currentState.getCost() + 1);// for each move , similar cost
        queue.add(neighbor);
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

}
