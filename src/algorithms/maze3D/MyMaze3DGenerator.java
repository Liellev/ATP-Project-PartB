package algorithms.maze3D;

import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;


/**
 * This is a class that creates Random 3D Mazes using the Prim's algorithm.
 * extends the AMaze3DGenerator class.
 */
public class MyMaze3DGenerator extends AMaze3DGenerator {

    private Random rand;
    private ArrayList<Position3D> walls;


    /**
     * Default constructor.
     */
    public MyMaze3DGenerator() {
        this.rand = new Random();
        this.walls = new ArrayList<Position3D>();

    }

    /**
     * This method is used to generate a 3D maze.
     *
     * @param depth This is the depth of maze matrix.
     * @param rows  This is the rows number in maze matrix.
     * @param cols  This is the columns number in maze matrix.
     * @return Maze3D object
     */
    @Override
    public Maze3D generate(int depth, int rows, int cols) {
        if (depth <= 1 || rows <= 1 || cols <= 1) {
            Maze3D defaultMaze = new Maze3D();
            depth = defaultMaze.getDepth();
            rows = defaultMaze.getRows();
            cols = defaultMaze.getCols();
        }

        Maze3D maze = new Maze3D(depth, rows, cols);
        int[][][] grid = maze.getMap();

        // Fill grid with walls
        for (int z = 0; z < depth; z++)
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    grid[z][i][j] = 1;

        // Random starting point
        int startZ = rand.nextInt(depth);
        int startRow = rand.nextInt(rows);
        int startCol = rand.nextInt(cols);
        grid[startZ][startRow][startCol] = 0;

        ArrayList<Position3D> walls = getNeighboringWalls3D(startZ, startRow, startCol, grid);
        while (!walls.isEmpty()) {
            Position3D wall = walls.remove(rand.nextInt(walls.size()));
            if (canBePassage3D(wall, grid)) {
                grid[wall.getDepthIndex()][wall.getRowIndex()][wall.getColumnIndex()] = 0;
                walls.addAll(getNeighboringWalls3D(wall.getDepthIndex(), wall.getRowIndex(), wall.getColumnIndex(), grid));
            }
        }

        // Choose valid edge start and goal
        ArrayList<Position3D> edgePassages = getEdgePassages3D(grid);
        if (edgePassages.size() < 2) return generate(depth, rows, cols); // fallback

        Collections.shuffle(edgePassages, rand);
        Position3D start = edgePassages.get(0);
        Position3D goal = edgePassages.get(1);
        maze.setStartPosition(start);
        maze.setGoalPosition(goal);

        return maze;
    }

    /**
     * This is a private helper method.
     * It uses generate method to get the list of walls
     * that is near the current cell (in each direction).
     *
     * @param z    represent the cell's depth index.
     * @param row  represent the cell's row index.
     * @param col  represent the cell's column index.
     * @param grid represent the maze matrix.
     * @return ArrayList<Position> of neighbors that are walls.
     */
    private ArrayList<Position3D> getNeighboringWalls3D(int z, int row, int col, int[][][] grid) {
        ArrayList<Position3D> walls = new ArrayList<>();
        int depth = grid.length, rows = grid[0].length, cols = grid[0][0].length;
        int[][] directions = {
                {1, 0, 0}, {-1, 0, 0},
                {0, 1, 0}, {0, -1, 0},
                {0, 0, 1}, {0, 0, -1}
        };

        for (int[] d : directions) {
            int dz = z + d[0], dr = row + d[1], dc = col + d[2];
            if (dz >= 0 && dz < depth && dr >= 0 && dr < rows && dc >= 0 && dc < cols && grid[dz][dr][dc] == 1) {
                walls.add(new Position3D(dz, dr, dc));
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
     *
     * @param wall position of the current wall cell.
     * @param grid represent the maze matrix.
     * @return boolean true if can be pass, false if not.
     */
    private boolean canBePassage3D(Position3D wall, int[][][] grid) {
        int z = wall.getDepthIndex();
        int row = wall.getRowIndex();
        int col = wall.getColumnIndex();
        int depth = grid.length, rows = grid[0].length, cols = grid[0][0].length;

        int[][] directions = {
                {1, 0, 0}, {-1, 0, 0},
                {0, 1, 0}, {0, -1, 0},
                {0, 0, 1}, {0, 0, -1}
        };

        int passages = 0;
        for (int[] d : directions) {
            int dz = z + d[0], dr = row + d[1], dc = col + d[2];
            if (dz >= 0 && dz < depth && dr >= 0 && dr < rows && dc >= 0 && dc < cols && grid[dz][dr][dc] == 0) {
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
     *
     * @param grid represent the maze matrix.
     * @return ArrayList<Position3D> of possible start and goal cells.
     */
    private ArrayList<Position3D> getEdgePassages3D(int[][][] grid) {
        ArrayList<Position3D> edges = new ArrayList<>();
        int depth = grid.length, rows = grid[0].length, cols = grid[0][0].length;

        for (int z = 0; z < depth; z++) {
            for (int i = 0; i < rows; i++) {
                if (grid[z][i][0] == 0) edges.add(new Position3D(z, i, 0));
                if (grid[z][i][cols - 1] == 0) edges.add(new Position3D(z, i, cols - 1));
            }
            for (int j = 0; j < cols; j++) {
                if (grid[z][0][j] == 0) edges.add(new Position3D(z, 0, j));
                if (grid[z][rows - 1][j] == 0) edges.add(new Position3D(z, rows - 1, j));
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[0][i][j] == 0) edges.add(new Position3D(0, i, j));
                if (grid[depth - 1][i][j] == 0) edges.add(new Position3D(depth - 1, i, j));
            }
        }

        return edges;
    }

}


















