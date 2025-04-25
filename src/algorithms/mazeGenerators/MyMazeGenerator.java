package algorithms.mazeGenerators;

import java.util.*;

/**
 * This is a class that creates Random Mazes using the Prim's algorithm.
 * extends the AMazeGenerator class.
 */
public class MyMazeGenerator extends AMazeGenerator {

    private Random rand;
    private ArrayList<Position> walls;

    /**
     * Default constructor.
     */
    public MyMazeGenerator() {
        this.rand = new Random();
        this.walls = new ArrayList<>();
    }

    /**
     * This is the main method of this class.
     * It generates a new maze using Prim's algorithm.
     * first, sets all cells to walls and then breaks
     * them according to valid neighbors of a passage.
     * At the end it sets a valid goal position.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @return Maze object that has at lease 1 solution.
     */
    @Override
    public Maze generate(int rows, int cols) {
        if (rows <= 1 || cols <= 1) {
            Maze maze=new Maze();
            rows=maze.getRows();
            cols= maze.getCols();
        }

        Maze maze = new Maze(rows, cols);
        int[][] grid = maze.getMatrix();

        // Fill matrix with walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 1;
            }
        }

        // set random starting point
        int startRow = rand.nextInt(rows);
        int startCol = rand.nextInt(cols);
        grid[startRow][startCol] = 0;

        List<Position> walls = getNeighboringWalls(startRow, startCol, grid);
        while (!walls.isEmpty()) {
            Position wall = walls.remove(rand.nextInt(walls.size()));
            if (canBePassage(wall, grid)) {
                grid[wall.getRowIndex()][wall.getColumnIndex()] = 0;
                walls.addAll(getNeighboringWalls(wall.getRowIndex(), wall.getColumnIndex(), grid));
            }
        }

        // Choose valid edge to set start and goal cells
        ArrayList<Position> edgePassages = getEdgePassages(grid);
        if (edgePassages.size() < 2) return generate(rows, cols); // fallback

        Collections.shuffle(edgePassages, rand);
        Position start = edgePassages.get(0);
        Position goal = edgePassages.get(1);
        maze.setStartPosition(start);
        maze.setGoalPosition(goal);

        return maze;
    }

    /**
     * This is a private helper method.
     * It uses generate method to get the list of walls
     * that is near the current cell (in each direction).
     * @param row represent the cell's row index.
     * @param col represent the cell's column index.
     * @param grid represent the maze matrix.
     * @return ArrayList<Position> of neighbors that are walls.
     */
    private ArrayList<Position> getNeighboringWalls(int row, int col, int[][] grid) {
        ArrayList<Position> walls = new ArrayList<>();
        int rows = grid.length, cols = grid[0].length;
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : directions) {
            int r = row + d[0], c = col + d[1];
            if (r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c] == 1) {
                walls.add(new Position(r, c));
            }
        }
        return walls;
    }

    /**
     * This is a private helper method.
     * It uses generate method to decide if a cell can
     * be a passage or not.
     * It takes a wall and checks if there is a pass anywhere around it.
     * If there is, the method will return true.
     * @param wall position of the current wall cell.
     * @param grid represent the maze matrix.
     * @return boolean true if can be a pass, false if not.
     */
    private boolean canBePassage(Position wall, int[][] grid) {
        int row = wall.getRowIndex();
        int col = wall.getColumnIndex();
        int rows = grid.length, cols = grid[0].length;
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        int passages = 0;
        for (int[] d : directions) {
            int r = row + d[0], c = col + d[1];
            if (r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c] == 0) {
                passages++;
            }
        }
        return passages == 1;
    }

    /**
     * This is a private helper method.
     * It uses generate method to set a valid goal and start position.
     * It scans the maze matrix and creates a list with all
     * passes (value of o) that are on maze's frame.
     * Then, generate method will use it to randomly choose a start and goal cell.
     * @param grid represent the maze matrix.
     * @return ArrayList<Position> of possible start and goal cells.
     */
    private ArrayList<Position> getEdgePassages(int[][] grid) {
        ArrayList<Position> edges = new ArrayList<>();
        int rows = grid.length, cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            //check if edges of column already broken
            if (grid[i][0] == 0) edges.add(new Position(i, 0));
            if (grid[i][cols - 1] == 0) edges.add(new Position(i, cols - 1));
        }
        for (int j = 0; j < cols; j++) {
            //check if edges of rows already broken
            if (grid[0][j] == 0) edges.add(new Position(0, j));
            if (grid[rows - 1][j] == 0) edges.add(new Position(rows - 1, j));
        }
        return edges;
    }

}
