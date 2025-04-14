package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

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

            }
        }

        return null;
    }

    public Solution getSoluitonPath(AState goal){
        ArrayList<AState> path= new ArrayList<>();
        while (goal!=null){
            path.add(goal);
            goal=goal
        }
    }
}
