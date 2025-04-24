package algorithms.maze3D;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.ISearchable;
import java.util.ArrayList;

/**
 * This class is used to link between a 3D maze to a generic searchable problem.
 * It implements ISearchable interface.
 */
public class SearchableMaze3D implements ISearchable {

    private Maze3D maze;

    /**
     * Constructor with params.
     * @param maze represent a 3D maze to solve.
     */
    public SearchableMaze3D(Maze3D maze){
        if(maze!=null){
            this.maze=maze;
        }

    }

    /**
     * Getter for all states that are reachable from current state.
     * It uses the structure of a 3D maze.
     * @param state represent current state in the problem.
     * @return ArrayList<AState> contains all possible states.
     */
    public ArrayList<AState> getAllPossibleStates(AState state) {
        int[][] directions = {
                {0, 0, 1},
                {0, 0, -1},
                {0, 1, 0},
                {0, -1, 0},
                {1, 0, 0},
                {-1, 0, 0}
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

    /**
     * Helper method to check if a 3D position is in matrix of the 3D maze.
     * @param pos represents position to check.
     * @return boolean . true if in maze, false if not.
     */
    public boolean valid(Position3D pos) {
        if (pos.getColumnIndex() < 0 || pos.getRowIndex() < 0 || pos.getDepthIndex()<0) {
            return false;
        }
        return this.maze.getRows() > pos.getRowIndex() && this.maze.getCols() > pos.getColumnIndex() && this.maze.getDepth() > pos.getDepthIndex();
    }

    /**
     * This is a wrapper from Maze3DState start position to Astate.
     * @return Astate that represent a maze3Dstate.
     */
    @Override
    public AState getStartState() {
        Maze3DState sstate=new Maze3DState(this.maze.getStartPosition());
        return (AState) sstate;
    }

    /**
     * This is a wrapper from Maze3DState goal position to Astate.
     * @return Astate that represent a maze3Dstate.
     */
    @Override
    public AState getGoalState() {
        Maze3DState gstate=new Maze3DState(this.maze.getGoalPosition());
        return (AState) gstate;
    }

    @Override
    public double computeMoveCost(AState from, AState to) {
        if(from==null || to==null){
            return Double.POSITIVE_INFINITY;
        }
        Position3D loc1 = stringToPosition(from.getState());
        Position3D loc2 = stringToPosition(to.getState());


        // calculate the difference in rows and columns
        int dr = Math.abs(loc1.getRowIndex() - loc2.getRowIndex());
        int dc = Math.abs(loc1.getColumnIndex() - loc2.getColumnIndex());
        int dp = Math.abs(loc1.getDepthIndex() - loc2.getDepthIndex());
        if (dr + dc +dp== 1) return 10;       // direct step
        return Double.POSITIVE_INFINITY;  // invalid movement
    }

    private Position3D stringToPosition(String sposition){
        String[] pos_parts=sposition.split(",");
        int row=Integer.parseInt(pos_parts[0]);
        int col=Integer.parseInt(pos_parts[1]);
        int dep=Integer.parseInt(pos_parts[2]);
        return new Position3D(dep,row,col);
    }
}
