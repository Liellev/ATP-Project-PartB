package algorithms.search;
import java.util.*;

/**
 * This class extands ASearchingAlgorithm abstract class.
 * in this class DFS algorithm is used to solve problems.
 */
public class DepthFirstSearch extends ASearchingAlgorithm{

    private Stack<AState> stack;
    private HashSet<AState> visited;

    /**
     * Default constructor.
     */
    public DepthFirstSearch(){
        this.stack= new Stack<>();
        this.visited= new HashSet<>();
    }

    /**
     * Getter for the algorithm's name.
     * @return String of the name.
     */
    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

    /**
     * This method solves a problem using DFS algorithm.
     * @param s represent a searchable object (the problem needed to be solved)
     * @return Solution that contains the solution path.
     */
    @Override
    public Solution solve(ISearchable s) {
        if(s == null)
        {
            return null;
        }
        AState start_state= s.getStartState();
        this.stack.push(start_state);
        this.visited.add(start_state);

        while(!this.stack.isEmpty()){
            AState curr=this.stack.pop();
            if (curr.equals(s.getGoalState())) {
                return getSolutionPath(curr);
            }

            ArrayList<AState> neighbors =s.getAllPossibleStates(curr);
            for(AState neighbor :neighbors){
                if(!this.visited.contains(neighbor)){
                    neighbor.setCameFrom(curr);
                    this.stack.push(neighbor);
                    this.visited.add(neighbor);
                }

            }
        }

        return null;
    }

    /**
     * Getter for visited nodes during algorithm.
     * @return int of nodes count.
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return this.visited.size();
    }

}
