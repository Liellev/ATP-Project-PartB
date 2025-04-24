package algorithms.search;

import java.util.ArrayList;

/**
 * This class represents a solution of a given problem.
 * The solution is actually a list of states that solves the problem.
 */
public class Solution {

    private ArrayList<AState> path;

    /**
     * Default constructor.
     */
    public Solution(){
        this.path=new ArrayList<>();
    }

    /**
     * Constructor with prams.
     * @param path represents the states of a solution.
     */
    public Solution(ArrayList<AState> path){
        this.path=new ArrayList<>();
        this.path.addAll(path);
    }

    /**
     * Getter for solution path.
     * @return ArrayList<AState> represents a list of states to solve a problem.
     */
    public ArrayList<AState> getSolutionPath(){
        return this.path;
    }

}
