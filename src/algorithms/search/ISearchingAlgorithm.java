package algorithms.search;

public interface ISearchingAlgorithm {

    int getNumberOfVisitedNodes();
    String getName();
    Solution solve(ISearchable s);
}
