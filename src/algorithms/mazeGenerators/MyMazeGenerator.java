package algorithms.mazeGenerators;

import java.util.*;

/**
 * This is a class that creates Random Mazes using the Prim's algorithm.
 * extends the AMazeGenerator class.
 */

public class MyMazeGenerator extends AMazeGenerator {
    private Random rand;
    private ArrayList<Position> walls;

    public MyMazeGenerator() {
        this.rand = new Random();
        this.walls = new ArrayList<>();
    }
    @Override
    public Maze generate(int rows, int cols) {
        if (rows <= 1 || cols <= 1) {
            Maze maze=new Maze();
            rows=maze.getRows();
            cols= maze.getCols();
        }

        Maze maze = new Maze(rows, cols);
        int[][] grid = maze.getMatrix();

        // Fill grid with walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 1;
            }
        }

        // Random starting point
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

        // Choose valid edge start and goal
        ArrayList<Position> edgePassages = getEdgePassages(grid);
        if (edgePassages.size() < 2) return generate(rows, cols); // fallback

        Collections.shuffle(edgePassages, rand);
        Position start = edgePassages.get(0);
        Position goal = edgePassages.get(1);
        maze.setStartPosition(start);
        maze.setGoalPosition(goal);

        return maze;
    }

    private List<Position> getNeighboringWalls(int row, int col, int[][] grid) {
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

    private ArrayList<Position> getEdgePassages(int[][] grid) {
        ArrayList<Position> edges = new ArrayList<>();
        int rows = grid.length, cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            if (grid[i][0] == 0) edges.add(new Position(i, 0));
            if (grid[i][cols - 1] == 0) edges.add(new Position(i, cols - 1));
        }
        for (int j = 0; j < cols; j++) {
            if (grid[0][j] == 0) edges.add(new Position(0, j));
            if (grid[rows - 1][j] == 0) edges.add(new Position(rows - 1, j));
        }
        return edges;
    }

}
