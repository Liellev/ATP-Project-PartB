package algorithms.search;
import algorithms.mazeGenerators.Position;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{

    public BestFirstSearch() {
        this.queue = new PriorityQueue<>(Comparator.comparingDouble(AState::getCost));
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return super.getNumberOfNodesEvaluated();
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }

    @Override
    protected void processNeighbor(AState currentState, AState neighbor) {
        double moveCost = computeMoveCost((MazeState) currentState, (MazeState) neighbor);
        neighbor.setCost(currentState.getCost() + moveCost);
        neighbor.setCameFrom(currentState);
        visited.add(neighbor);
        queue.add(neighbor);

    }
    private double computeMoveCost(MazeState from, MazeState to) {
        // init the 2 position states
        Position p1 = from.getMazeStatePosition();
        Position p2 = to.getMazeStatePosition();

        // calculate the difference in rows and columns
        int dr = Math.abs(p1.getRowIndex() - p2.getRowIndex());
        int dc = Math.abs(p1.getColumnIndex() - p2.getColumnIndex());

        if (dr + dc == 1) return 10;       // direct step
        if (dr == 1 && dc == 1) return 15; // diagonal step
        return Double.POSITIVE_INFINITY;  // invalid movement
    }
}
