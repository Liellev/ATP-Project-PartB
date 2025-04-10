package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    @Override
    public int getNumberOfVisitedNodes() {
        return 0;
    }

    @Override
    public Solution solve(ISearchable s) {
        return null;
    }


}
