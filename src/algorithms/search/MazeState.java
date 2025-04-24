package algorithms.search;
import algorithms.mazeGenerators.Position;

/**
 * This class represent a state in maze. it extends Astate.
 * Each Maze state contains a position.
 */
public class MazeState extends AState {

    private Position state; //using position as it is from maze generators.

    /**
     * Constructor with params.
     *
     * @param pos is a position in a maze.
     */
    public MazeState(Position pos) {

        this.state = pos;
        this.astate=pos.toString();
    }

    /**
     * To make comparison able between 2 Maze States.
     *
     * @param obj that will represent to comparable object.
     * @return boolean. true if equal false if different.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MazeState other = (MazeState) obj;
        if (this.state.equals(other.state)) {
            return true;
        }
        return false;
    }

    /**
     * To make sure hashcode is properly represented.
     *
     * @return int of hashcode.
     */
    @Override
    public int hashCode() {
        return this.state != null ? this.state.hashCode() : 0;
    }

    /**
     * getter for maze state position.
     *
     * @return Position of the current state.
     */
    public Position getMazeStatePosition() {
        return this.state;
    }

    /**
     * To help printing the maze state.
     *
     * @return String represent the state position.
     */
    @Override
    public String toString() {
        return state.toString();
    }


}