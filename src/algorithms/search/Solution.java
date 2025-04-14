package algorithms.search;

import java.util.ArrayList;

public class Solution {

    private ArrayList<AState> path;

    public Solution(){
        this.path=new ArrayList<>();
    }

    public Solution(ArrayList<AState> path){
        this.path=new ArrayList<>();
        this.path.addAll(path);
    }

    public ArrayList<AState> getSolutionPath(){
        return this.path;
    }

}
