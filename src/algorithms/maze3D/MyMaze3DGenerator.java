package algorithms.maze3D;

import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class MyMaze3DGenerator extends AMaze3DGenerator{
    private Random rand;
    private ArrayList<Position3D> walls;


    public MyMaze3DGenerator(){
        this.rand=new Random();
        this.walls= new ArrayList<Position3D>();

    }
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

    private ArrayList<Position3D> getNeighboringWalls3D(int z, int row, int col, int[][][] grid) {
        ArrayList<Position3D> walls = new ArrayList<>();
        int depth = grid.length, rows = grid[0].length, cols = grid[0][0].length;
        int[][] directions = {
                {1,0,0},{-1,0,0},   // קדימה ואחורה
                {0,1,0},{0,-1,0},   // למעלה ולמטה
                {0,0,1},{0,0,-1}    // ימינה ושמאלה
        };

        for (int[] d : directions) {
            int dz = z + d[0], dr = row + d[1], dc = col + d[2];
            if (dz >= 0 && dz < depth && dr >= 0 && dr < rows && dc >= 0 && dc < cols && grid[dz][dr][dc] == 1) {
                walls.add(new Position3D(dz, dr, dc));
            }
        }
        return walls;
    }

    private boolean canBePassage3D(Position3D wall, int[][][] grid) {
        int z = wall.getDepthIndex();
        int row = wall.getRowIndex();
        int col = wall.getColumnIndex();
        int depth = grid.length, rows = grid[0].length, cols = grid[0][0].length;

        int[][] directions = {
                {1,0,0},{-1,0,0},
                {0,1,0},{0,-1,0},
                {0,0,1},{0,0,-1}
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







//    private void addWallsToList(int dep, int row, int col, Maze3D maze3D) {
//        int rowsnum = maze3D.getRows();
//        int colsnum = maze3D.getCols();
//        int depnum = maze3D.getDepth();
//
//        ArrayList<Position3D> newWalls = new ArrayList<>();
//        int[][][] matrix = maze3D.getMap();
//
//        if (dep + 2 < depnum && matrix[dep + 2][row][col] == 1) {
//            newWalls.add(new Position3D(dep + 1, row, col));
//        }
//        if (dep - 2 >= 0 && matrix[dep - 2][row][col] == 1) {
//            newWalls.add(new Position3D(dep - 1, row, col));
//        }
//
//        if (row + 2 < rowsnum && matrix[dep][row + 2][col] == 1) {
//            newWalls.add(new Position3D(dep, row + 1, col));
//        }
//        if (row - 2 >= 0 && matrix[dep][row - 2][col] == 1) {
//            newWalls.add(new Position3D(dep, row - 1, col));
//        }
//
//        if (col + 2 < colsnum && matrix[dep][row][col + 2] == 1) {
//            newWalls.add(new Position3D(dep, row, col + 1));
//        }
//        if (col - 2 >= 0 && matrix[dep][row][col - 2] == 1) {
//            newWalls.add(new Position3D(dep, row, col - 1));
//        }
//
//        Collections.shuffle(newWalls, this.rand);
//        this.walls.addAll(newWalls);
//    }
//
//
//    private Position3D[] getWallSides(Position3D wall, Maze3D maze3D) {
//        int dep = wall.getDepthIndex();
//        int row = wall.getRowIndex();
//        int col = wall.getColumnIndex();
//
//        // קיר בין שכבות (עומק)
//        if (dep % 2 == 0 && row % 2 == 1 && col % 2 == 1) {
//            int d1 = dep - 1;
//            int d2 = dep + 1;
//            if (isInBounds(d1, row, col, maze3D) && isInBounds(d2, row, col, maze3D)) {
//                return new Position3D[]{
//                        new Position3D(d1, row, col),
//                        new Position3D(d2, row, col)
//                };
//            }
//        }
//
//        // קיר בין שורות
//        else if (dep % 2 == 1 && row % 2 == 0 && col % 2 == 1) {
//            int r1 = row - 1;
//            int r2 = row + 1;
//            if (isInBounds(dep, r1, col, maze3D) && isInBounds(dep, r2, col, maze3D)) {
//                return new Position3D[]{
//                        new Position3D(dep, r1, col),
//                        new Position3D(dep, r2, col)
//                };
//            }
//        }
//
//        // קיר בין עמודות
//        else if (dep % 2 == 1 && row % 2 == 1 && col % 2 == 0) {
//            int c1 = col - 1;
//            int c2 = col + 1;
//            if (isInBounds(dep, row, c1, maze3D) && isInBounds(dep, row, c2, maze3D)) {
//                return new Position3D[]{
//                        new Position3D(dep, row, c1),
//                        new Position3D(dep, row, c2)
//                };
//            }
//        }
//
//        return null;
//    }
//
//
//    private boolean isInBounds(int dep,int row, int col, Maze3D maze) {
//        return row >= 0 && row < maze.getRows() && col >= 0 && col < maze.getCols()&& dep >= 0 && dep < maze.getDepth() ;
//    }
//
//    private boolean isOnEdge(Position3D pos, Maze3D maze)
//    {
//        if(pos.getRowIndex() == 0 || pos.getRowIndex() == maze.getRows()-1 || pos.getColumnIndex() ==0 || pos.getColumnIndex()==maze.getCols()-1 || pos.getDepthIndex() ==0 || pos.getDepthIndex()==maze.getDepth()-1){
//            return true;
//        }
//        return false;
//    }


//    public Maze3D createTrickyMaze3D() {
//        int[][] template = {
//                {0,0,0,1,0,0,0,0,0},
//                {1,1,0,1,0,1,1,0,1},
//                {0,0,0,1,0,0,0,0,1},
//                {0,1,1,1,0,1,1,0,1},
//                {0,0,0,0,0,0,0,0,1},
//                {1,0,1,1,1,1,0,1,1},
//                {1,0,0,0,0,0,0,0,0},
//                {1,1,1,1,1,1,1,1,1}
//        };
//
//        Maze3D Maze3D = new Maze3D(8, 9);  // rows=8, cols=9
//        int[][] matrix = Maze3D.getMatrix();
//
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 9; j++) {
//                matrix[i][j] = template[i][j];
//            }
//        }
//
//        Maze3D.setStartPosition3D(new Position3D(0, 0));
//        Maze3D.setGoalPosition3D(new Position3D(0, 8));
//        return Maze3D;
//    }

}










