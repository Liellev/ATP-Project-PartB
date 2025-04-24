package algorithms.search;
import algorithms.mazeGenerators.Position;

/**
 * This class represent a state in maze. it extends Astate.
 * Each Maze state contains a position.
 */
public class MazeState extends AState{

    private Position state; //using position as it is from maze generators.

    /**
     * Constructor with params.
     * @param state is a position in a maze.
     */
    public MazeState(Position state){
        this.state=state;
    }

//    /**
//     * This method is to get the origin of current state.
//     * @return Astate that represent the state's origin.
//     */
//    @Override
//    public AState getCameFrom() {
//        return super.getCameFrom();
//    }
//
//    /**
//     * Setter for this state's origin.
//     * @param cameFrom represent the state that is the origin of the current state.
//     */
//    @Override
//    public void setCameFrom(AState cameFrom) {
//        super.setCameFrom(cameFrom);
//    }
//
//    /**
//     * getter for cost's field.
//     * @return double of cost.
//     */
//    @Override
//    public double getCost() {
//        return this.cost;
//    }
//
//    @Override
//    public void setCost(double cost) {
//        this.cost=cost;
//
//    }

    /**
     * To make comparison able between 2 Maze States.
     * @param obj that will represent to comparable object.
     * @return boolean. true if equal false if different.
     */
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

    /**
     * To make sure hashcode is properly represented.
     * @return int of hashcode.
     */
    @Override
    public int hashCode() {
        return this.state!= null ? this.state.hashCode() : 0;
    }

    /**
     * getter for maze state position.
     * @return Position of the current state.
     */
    public Position getMazeStatePosition(){
        return this.state;
    }

    /**
     * To help printing the maze state.
     * @return String represent the state position.
     */
    @Override
    public String toString() {
        return state.toString();
    }

}
