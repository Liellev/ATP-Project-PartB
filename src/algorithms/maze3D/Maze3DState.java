package algorithms.maze3D;

import algorithms.maze3D.Position3D;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;

/**
 * This class represent a state in 3D maze. it extends Astate.
 * Each Maze state contains a 3D position.
 */
public class Maze3DState extends AState {

    private Position3D state; //using position as it is from maze generators.

    /**
     * Constructor with params.
     *
     * @param state reresents a 3D Position of the current state.
     */
    public Maze3DState(Position3D state) {
        this.state = state;
    }


    /**
     * To make comparison able between 2 3DMaze States.
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
        Maze3DState other = (Maze3DState) obj;
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
     * getter for 3D maze state position.
     *
     * @return Position3D of the current state.
     */
    public Position3D getMaze3DStatePosition() {
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

    @Override
    public Object getLocation() {
        Position3D position = this.state;
        return new int[]{position.getRowIndex(), position.getColumnIndex()};
    }
}
