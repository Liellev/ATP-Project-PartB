package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm{

    private int vnodes; //num of visited nodes
    private Stack<AState> stack;
    private HashSet<AState> visited;

    public DepthFirstSearch(){
        this.vnodes=0;
        this.stack= new Stack<>();
        this.visited= new HashSet<>();
    }

    @Override
    public int getNumberOfVisitedNodes() {
        return super.getNumberOfVisitedNodes();
    }

    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

    @Override
    public Solution solve(ISearchable s) {

        AState start_state= s.getStartState();
        this.stack.push(start_state);
        this.visited.add(start_state);

        while(!this.stack.isEmpty()){
            AState curr=this.stack.peek();
            if(curr==s.getGoalState()){
                break; //return solution
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

        return getSoluitonPath(s.getGoalState());
    }

    public Solution getSoluitonPath(AState goal) {
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
