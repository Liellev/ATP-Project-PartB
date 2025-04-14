package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm{

    private Stack<AState> stack;
    private HashSet<AState> visited;

    public DepthFirstSearch(){
        this.stack= new Stack<>();
        this.visited= new HashSet<>();
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
            AState curr=this.stack.pop();
            System.out.println("Current Position: " + ((MazeState)curr).getMazeStatePosition());
            System.out.println("Goal Position: " + ((MazeState)s.getGoalState()).getMazeStatePosition());
            if (curr.equals(s.getGoalState())) {
                System.out.println("Found goal state: " + curr);
                return getSoluitonPath(curr);
            }
            else{
                System.out.println("Current: " + curr + " != Goal: " + s.getGoalState());
            }

            ArrayList<AState> neighbors =s.getAllPossibleStates(curr);
            System.out.println("Neighbors: " + neighbors.size());  // Debug print for neighbors
            for(AState neighbor :neighbors){
                if(!this.visited.contains(neighbor)){
                    neighbor.setCameFrom(curr);
                    this.stack.push(neighbor);
                    this.visited.add(neighbor);
                    System.out.println("Adding to stack: " + neighbor);  // Debug print for adding to stack
                }

            }
        }

        return null;
    }


    @Override
    public int getNumberOfNodesEvaluated() {
        return this.visited.size();
    }

}
