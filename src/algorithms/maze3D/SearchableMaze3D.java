package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {
    private Maze3D maze;

    public SearchableMaze3D(Maze3D maze){
        if(maze!=null){
            this.maze=maze;
        }

    }

    public ArrayList<AState> getAllPossibleStates(AState state) {
        int[][] directions = {
                {0, 0, 1},   // ימינה (col + 1)
                {0, 0, -1},  // שמאלה (col - 1)
                {0, 1, 0},   // למטה (row + 1)
                {0, -1, 0},  // למעלה (row - 1)
                {1, 0, 0},   // קדימה בעומק (depth + 1)
                {-1, 0, 0}   // אחורה בעומק (depth - 1)
        };
        ArrayList<AState> successors = new ArrayList<>();
        Maze3DState mazeState = (Maze3DState) state;
        int row = mazeState.getMaze3DStatePosition().getRowIndex();
        int col = mazeState.getMaze3DStatePosition().getColumnIndex();
        int depth= mazeState.getMaze3DStatePosition().getDepthIndex();

        for(int[]dir : directions) {
            int new_dep= depth+dir[2];
            int new_row = row + dir[0];
            int new_col = col + dir[1];
            Position3D new_pos = new Position3D(new_dep,new_row, new_col);
            if (!valid(new_pos)) {
                continue;
            }

            if (this.maze.getMap()[new_dep][new_row][new_col] == 0) {
                Maze3DState neighbor = new Maze3DState(new_pos);
                neighbor.setCameFrom(state);
                successors.add(neighbor);
            }
        }
        return successors;
    }

    public boolean valid(Position3D pos) {
        if (pos.getColumnIndex() < 0 || pos.getRowIndex() < 0 || pos.getDepthIndex()<0) {
            return false;
        }
        return this.maze.getRows() > pos.getRowIndex() && this.maze.getCols() > pos.getColumnIndex() && this.maze.getDepth() > pos.getDepthIndex();
    }

    @Override
    public AState getStartState() {
        Maze3DState sstate=new Maze3DState(this.maze.getStartPosition());
        return (AState) sstate;
    }

    @Override
    public AState getGoalState() {
        Maze3DState gstate=new Maze3DState(this.maze.getGoalPosition());
        return (AState) gstate;
    }
}
