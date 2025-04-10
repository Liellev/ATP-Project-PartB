package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {
    MazeState getStartState();
    MazeState getGoalState();
    ArrayList<MazeState> getAllSuccessors(MazeState mazestate);
}
