package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This abstract class is implements ISearchingAlgorithm interface.
 * It represents all kinds of searching algorithms.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    /**
     * This is an abstract method that all algorithms will implement.
     * @return int of nodes evaluated.
     */
    @Override
    public abstract int getNumberOfNodesEvaluated() ;

    /**
     * This is an abstract method that all algorithms will implement.
     * The algorithm solves the given problem.
     * @param s represent a searchable object (the problem needed to be solved)
     * @return Solution for the given problem.
     */
    @Override
    public abstract Solution solve(ISearchable s) ;

    /**
     * This method is relevant for all algorithms.
     * It takes a given state and go back all the way
     * to where ut came from.
     * @param goal represents a current state.
     * @return solution that contains the solution path.
     */
    public Solution getSolutionPath(AState goal) {
        ArrayList<AState> path = new ArrayList<>();
        AState curr = goal;

        while (curr != null) {
            path.add(curr);
            curr = curr.getCameFrom();
        }
        Collections.reverse(path);
        Solution sol = new Solution(path);
        return sol;
    }


}
