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
        if (depth <= 0 || rows <= 0 || cols <= 0) {
            Maze3D mymaze3D= new Maze3D(); // מחזיר מבוך ריק/ברירת מחדל אם הפרמטרים לא תקינים
        }

        Maze3D mymaze3D = new Maze3D(depth, rows, cols);
        ArrayList<Position3D> edgePassages = new ArrayList<>();

        // מילוי כל התאים כקירות
        for (int d = 0; d < depth; d++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    mymaze3D.getMap()[d][r][c] = 1;
                }
            }
        }

        // יצירת תא התחלה
        Position3D startcell = mymaze3D.generateStartCell3D();
        mymaze3D.setStartPosition(startcell);
        mymaze3D.getMap()[startcell.getDepthIndex()][startcell.getRowIndex()][startcell.getColumnIndex()] = 0;
        addWallsToList(startcell.getDepthIndex(), startcell.getRowIndex(), startcell.getColumnIndex(), mymaze3D);

        while (!walls.isEmpty()) {
            int i = rand.nextInt(walls.size());
            Position3D wall = walls.remove(i);

            int d = wall.getDepthIndex();
            int r = wall.getRowIndex();
            int c = wall.getColumnIndex();

            Position3D[] neighbors = getWallSides(wall, mymaze3D);
            if (neighbors == null) continue;

            Position3D neighbor1 = neighbors[0];
            Position3D neighbor2 = neighbors[1];

            boolean cell1_is_pass = mymaze3D.getMap()[neighbor1.getDepthIndex()][neighbor1.getRowIndex()][neighbor1.getColumnIndex()] == 0;
            boolean cell2_is_pass = mymaze3D.getMap()[neighbor2.getDepthIndex()][neighbor2.getRowIndex()][neighbor2.getColumnIndex()] == 0;

            if ((cell1_is_pass && !cell2_is_pass) || (!cell1_is_pass && cell2_is_pass)) {
                mymaze3D.getMap()[d][r][c] = 0;
                Position3D newcell = cell1_is_pass ? neighbor2 : neighbor1;
                mymaze3D.getMap()[newcell.getDepthIndex()][newcell.getRowIndex()][newcell.getColumnIndex()] = 0;

                if (isOnEdge(newcell, mymaze3D) && !newcell.equals(startcell)) {
                    edgePassages.add(newcell);
                }

                addWallsToList(newcell.getDepthIndex(), newcell.getRowIndex(), newcell.getColumnIndex(), mymaze3D);
            }
        }

        // בחר מטרה מתוך המעברים שעל המסגרת
        Position3D goal = null;
        if (!edgePassages.isEmpty()) {
            goal = edgePassages.get(rand.nextInt(edgePassages.size()));
        } else {
            // fallback: מציאת מיקום קצה שהוא מעבר ואינו התחלה
            ArrayList<Position3D> backup = new ArrayList<>();
            for (int d = 0; d < depth; d++) {
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        Position3D p = new Position3D(d, r, c);
                        if (isOnEdge(p, mymaze3D) && mymaze3D.getMap()[d][r][c] == 0 && !p.equals(startcell)) {
                            backup.add(p);
                        }
                    }
                }
            }
            if (!backup.isEmpty()) {
                goal = backup.get(rand.nextInt(backup.size()));
            }
        }

        if (goal == null || goal.equals(startcell)) {
            return generate(depth, rows, cols); // מנסה שוב
        }

        mymaze3D.setGoalPosition(goal);
        return mymaze3D;
    }





    private void addWallsToList(int dep, int row, int col, Maze3D maze3D) {
        int rowsnum = maze3D.getRows();
        int colsnum = maze3D.getCols();
        int depnum = maze3D.getDepth();

        ArrayList<Position3D> newWalls = new ArrayList<>();
        int[][][] matrix = maze3D.getMap();

        if (dep + 2 < depnum && matrix[dep + 2][row][col] == 1) {
            newWalls.add(new Position3D(dep + 1, row, col));
        }
        if (dep - 2 >= 0 && matrix[dep - 2][row][col] == 1) {
            newWalls.add(new Position3D(dep - 1, row, col));
        }

        if (row + 2 < rowsnum && matrix[dep][row + 2][col] == 1) {
            newWalls.add(new Position3D(dep, row + 1, col));
        }
        if (row - 2 >= 0 && matrix[dep][row - 2][col] == 1) {
            newWalls.add(new Position3D(dep, row - 1, col));
        }

        if (col + 2 < colsnum && matrix[dep][row][col + 2] == 1) {
            newWalls.add(new Position3D(dep, row, col + 1));
        }
        if (col - 2 >= 0 && matrix[dep][row][col - 2] == 1) {
            newWalls.add(new Position3D(dep, row, col - 1));
        }

        Collections.shuffle(newWalls, this.rand);
        this.walls.addAll(newWalls);
    }


    private Position3D[] getWallSides(Position3D wall, Maze3D maze3D) {
        int dep = wall.getDepthIndex();
        int row = wall.getRowIndex();
        int col = wall.getColumnIndex();

        // קיר בין שכבות (עומק)
        if (dep % 2 == 0 && row % 2 == 1 && col % 2 == 1) {
            int d1 = dep - 1;
            int d2 = dep + 1;
            if (isInBounds(d1, row, col, maze3D) && isInBounds(d2, row, col, maze3D)) {
                return new Position3D[]{
                        new Position3D(d1, row, col),
                        new Position3D(d2, row, col)
                };
            }
        }

        // קיר בין שורות
        else if (dep % 2 == 1 && row % 2 == 0 && col % 2 == 1) {
            int r1 = row - 1;
            int r2 = row + 1;
            if (isInBounds(dep, r1, col, maze3D) && isInBounds(dep, r2, col, maze3D)) {
                return new Position3D[]{
                        new Position3D(dep, r1, col),
                        new Position3D(dep, r2, col)
                };
            }
        }

        // קיר בין עמודות
        else if (dep % 2 == 1 && row % 2 == 1 && col % 2 == 0) {
            int c1 = col - 1;
            int c2 = col + 1;
            if (isInBounds(dep, row, c1, maze3D) && isInBounds(dep, row, c2, maze3D)) {
                return new Position3D[]{
                        new Position3D(dep, row, c1),
                        new Position3D(dep, row, c2)
                };
            }
        }

        return null;
    }


    private boolean isInBounds(int dep,int row, int col, Maze3D maze) {
        return row >= 0 && row < maze.getRows() && col >= 0 && col < maze.getCols()&& dep >= 0 && dep < maze.getDepth() ;
    }

    private boolean isOnEdge(Position3D pos, Maze3D maze)
    {
        if(pos.getRowIndex() == 0 || pos.getRowIndex() == maze.getRows()-1 || pos.getColumnIndex() ==0 || pos.getColumnIndex()==maze.getCols()-1 || pos.getDepthIndex() ==0 || pos.getDepthIndex()==maze.getDepth()-1){
            return true;
        }
        return false;
    }


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










