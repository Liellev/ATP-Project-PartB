package algorithms.search;

import java.util.ArrayList;

/**
 * This is an interface that defines what is required to make a searchable object.
 */
public interface ISearchable {

    /**
     * Getter for start state of the problem.
     * @return Astate of the state.
     */
    AState getStartState();

    /**
     * Getter for goal state of the problem.
     * @return Astate of the goal.
     */
    AState getGoalState();

    /**
     * Getter for all states that are reachable from current state.
     * @param state represent current state in the problem.
     * @return ArrayList<AState> contains all possible states.
     */
    ArrayList<AState> getAllPossibleStates(AState state);
}
