package algorithms.maze3D;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;

public class Maze3DState extends AState {
    private Position3D state; //using position as it is from maze generators.
    private double cost;


    public Maze3DState(Position3D state){
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
        Maze3DState other= (Maze3DState) obj;
        if(this.state.equals(other.state))
        {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.state!= null ? this.state.hashCode() : 0;
    }

    public Position3D getMaze3DStatePosition(){
        return this.state;
    }

    @Override
    public String toString() {
        return state.toString();
    }

}

