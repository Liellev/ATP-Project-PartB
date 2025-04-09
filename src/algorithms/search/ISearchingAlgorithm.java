package algorithms.search;

public interface ISearchingAlgorithm {

    Astate search(ISearchable s);
    int getNumberOfVisitedNodes();
}
