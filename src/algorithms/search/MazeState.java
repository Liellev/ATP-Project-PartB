package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState{

    private Position state; //using position as it is from maze generators.
    private double cost;

    public MazeState(Position state){
        this.state=state;
    }

    @Override
    public AState getCameFrom() {
        return super.getCameFrom();
    }

    @Override
    public void setCameFrom(AState cameFrom) {
        super.setCameFrom(cameFrom);
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public void setCost(double cost) {
        this.cost=cost;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){return true;}
        if (obj==null || getClass() != obj.getClass()){return false;}
        MazeState other= (MazeState) obj;
        if(this.state.equals(other.state))
        {
           return true;
        }
       return false;
    }

    @Override
    public int hashCode() {
        return this.state!= null ? this.state.hashCode() : 0;
        //return this.state.hashCode();
    }

    public Position getMazeStatePosition(){
        return this.state;
    }


}
