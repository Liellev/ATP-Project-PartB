package algorithms.search;
import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm{

    Queue<AState> queue = new LinkedList<>();
    HashSet<AState> visited = new HashSet<>();

    @Override
    public int getNumberOfNodesEvaluated() {
        return visited.size();
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    @Override
    public Solution solve(ISearchable s) {
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
                        neighbor.setCameFrom(currentState);
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Solution getSolutionPath(AState goal) {
        Solution Solutions = new Solution();
        return null;
    }

//    @Override
//    public ArrayList<AState> getAllPossibleStates(AState state){
//        return null;
//    }
}
