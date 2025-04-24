package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;

/**
 * This class is used to link between maze to a generic searchable problem.
 * It implements ISearchable interface.
 */
public class SearchableMaze implements ISearchable{

    private Maze maze;

    /**
     * Constructor with params.
     * @param maze represent a maze to solve.
     */
    public SearchableMaze(Maze maze){
        if(maze!=null){
            this.maze=maze;
        }
    }

    /**
     * Getter for all states that are reachable from current state.
     * It uses the structure of a maze.
     * @param state represent current state in the problem.
     * @return ArrayList<AState> contains all possible states.
     */
    public ArrayList<AState> getAllPossibleStates(AState state) {
        if (state==null){
            return null;
        }
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
            }
            if (this.maze.getMatrix()[new_row][new_col] == 0) {
                MazeState neighbor = new MazeState(new_pos);
                neighbor.setCameFrom(state);
                successors.add(neighbor);
            }
        }
        return successors;
    }

    /**
     * Helper method to check if a position is in matrix of the maze.
     * @param pos represents position to check.
     * @return boolean . true if in maze, false if not.
     */
    private boolean valid(Position pos) {
        if(pos==null){
            return false;
        }
        if (pos.getColumnIndex() < 0 || pos.getRowIndex() < 0) {
            return false;
        }
        return this.maze.getRows() > pos.getRowIndex() && this.maze.getCols() > pos.getColumnIndex();
    }

    /**
     * This is a wrapper from MazeState start position to Astate.
     * @return Astate that represent a mazestate.
     */
    @Override
    public AState getStartState() {
        MazeState sstate=new MazeState(this.maze.getStartPosition());
        return (AState) sstate;
    }

    /**
     * This is a wrapper from MazeState goal position to Astate.
     * @return Astate that represent a mazestate.
     */
    @Override
    public AState getGoalState() {
        MazeState gstate=new MazeState(this.maze.getGoalPosition());
        return (AState) gstate;
    }

    public double computeMoveCost(AState from, AState to) {
        if(from==null || to==null){
            return Double.POSITIVE_INFINITY;
        }
        Position loc1 = stringToPosition(from.getState());
        Position loc2 = stringToPosition(to.getState());


        // calculate the difference in rows and columns
        int dr = Math.abs(loc1.getRowIndex() - loc2.getRowIndex());
        int dc = Math.abs(loc1.getColumnIndex() - loc2.getColumnIndex());

        if (dr + dc == 1) return 10;       // direct step
        if (dr == 1 && dc == 1) return 15; // diagonal step
        return Double.POSITIVE_INFINITY;  // invalid movement
    }


    private Position stringToPosition(String str){
        str = str.replaceAll("[{}\\s]", "");
        String[] pos_parts = str.split(",");
        int row=Integer.parseInt(pos_parts[0]);
        int col=Integer.parseInt(pos_parts[1]);
        return new Position(row,col);
    }
}
