package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    @Override
    public abstract int getNumberOfNodesEvaluated() ;

    @Override
    public abstract Solution solve(ISearchable s) ;


}
