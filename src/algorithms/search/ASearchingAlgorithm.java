package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    @Override
    public abstract int getNumberOfNodesEvaluated() ;

    @Override
    public abstract Solution solve(ISearchable s) ;

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
