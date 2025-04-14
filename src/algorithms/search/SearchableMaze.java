package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze {
    private Maze maze;

    public ArrayList<AState> getAllPossibleStates(AState state) {
        int[][] directions = {
                {-1, 0}, // above
                {1, 0},  // below
                {0, -1}, // left
                {0, 1},   // right
                {-1, -1}, //left above
                {1, -1}, //right above
                {1, 1}, // right below
                {-1, 1} // left below
        };
        ArrayList<AState> successors = new ArrayList<>();
        MazeState mazeState = (MazeState) state;
        int row = mazeState.getMazeStatePosition().getRowIndex();
        int col = mazeState.getMazeStatePosition().getColumnIndex();

        Position[] positions=new Position[8];
        //change that to for loop
        for(int[]dir : directions) {
            int new_row = row + dir[0];
            int new_col = col + dir[1];
            Position new_pos = new Position(new_row, new_col);
            if (!valid(new_pos)) {
                continue;
            }
            //check diagonals
            if (dir[0] != 0 && dir[1] != 0) {
                Position vertical = new Position(row + dir[0], col);
                Position horizontal = new Position(row, col + dir[1]);
                if (!valid(vertical) || !valid(horizontal)) { //if one of the cells in the way to diagonal are invalid then there is no point to check diagonal.
                    continue;
                }
                if (this.maze.getMatrix()[vertical.getRowIndex()][vertical.getColumnIndex()] == 1 ||
                        this.maze.getMatrix()[horizontal.getRowIndex()][horizontal.getColumnIndex()] == 1) {
                    continue;
                }
                if (this.maze.getMatrix()[new_row][new_col] == 0) {
                    MazeState neighbor = new MazeState(new_pos);
                    neighbor.setCameFrom(state);
                    successors.add(neighbor);
                }
            }
        }
        return successors;
    }

    public boolean valid(Position pos) {
        if (pos.getColumnIndex() < 0 || pos.getRowIndex() < 0) {
            return false;
        }
        return this.maze.getRows() > pos.getRowIndex() && this.maze.getCols() > pos.getColumnIndex();
    }
}
