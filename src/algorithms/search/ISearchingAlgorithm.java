package algorithms.search;

/**
 * This is an interface to define all required methods that a
 * searching algorithm must have.
 */
public interface ISearchingAlgorithm {

    /**
     * To have the number of nodes visited during the akgorithm.
     * @return int of nodes count.
     */
    int getNumberOfNodesEvaluated();

    /**
     * Getter for algorithm's name.
     * @return String of the name.
     */
    String getName();

    /**
     * The algorithm solves the given problem.
     * @param s represent a searchable object (the problem needed to be solved)
     * @return Solution for the given problem.
     */
    Solution solve(ISearchable s);
}
