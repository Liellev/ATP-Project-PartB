package algorithms.search;

import java.util.ArrayList;

public class Solution {

    private ArrayList<AState> path;

    public Solution(){
        this.path=new ArrayList<>();
    }

    public Solution(ArrayList<AState> path){
        this.path=new ArrayList<>();
        for (int i=0;i< path.size();i++){
            this.path.add(path.get(i));
        }
    }

}
