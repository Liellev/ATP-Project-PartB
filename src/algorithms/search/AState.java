package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * This is an abstract class to define a general state
 * that is not linked to a specific puzzle.
 */
public abstract class AState {

    private AState cameFrom;
    protected double cost;
    protected String state;

    /**
     * This method is to get the origin of current state.
     *
     * @return Astate that represent the state's origin.
     */
    public AState getCameFrom() {
        return this.cameFrom;
    }

    /**
     * Setter for this state's origin.
     *
     * @param cameFrom represent the state that is the origin of the current state.
     */
    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    /**
     * getter for cost's field.
     *
     * @return double of cost.
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * Setter for cost field.
     *
     * @param cost represent the cost that needed to be set.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * To make sure hashcode is properly represented.
     *
     * @return int of hashcode.
     */
    @Override
    public abstract int hashCode();

    /**
     * To make comparison able between 2 states.
     *
     * @param obj that will represent to comparable object.
     * @return boolean. true if equal false if different.
     */
    @Override
    public abstract boolean equals(Object obj);

    public abstract Object getLocation();

}