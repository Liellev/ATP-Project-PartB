package algorithms.mazeGenerators;

import java.util.Random;
import java.util.ArrayList;

public class MyMazeGenerator extends AMazeGenerator{
    private Random rand;
    private ArrayList<Position> walls;


    public MyMazeGenerator(){
        this.rand=new Random();
        this.walls= new ArrayList<Position>();

    }
//    @Override
//    public Maze generate(int rows, int cols) {
//
//        Maze mymaze=new Maze(rows,cols);
//
//        for(int i=0; i< mymaze.getRows();i++){
//            for(int j=0;j< mymaze.getCols();j++){
//                mymaze.getMatrix()[i][j]=1;
//            }
//        }
//
//        Position startcell=mymaze.generateStartCell();
//        Position lastVisitedCell= startcell;
//        mymaze.setStartPosition(startcell);
//        mymaze.getMatrix()[startcell.getRowIndex()][startcell.getColumnIndex()]=0;
//        addWallsToList(startcell.getRowIndex(), startcell.getColumnIndex(),mymaze);
//
//        while (this.walls.size()!=0 ){
//            int i=this.rand.nextInt(this.walls.size());
//            Position wall=this.walls.remove(i); //choose wall randomly from list
//
//            int row=wall.getRowIndex();
//            int col=wall.getColumnIndex();
//
//            Position[] neighbors=getWallSides(wall,mymaze);
//            if(neighbors==null){ //no sides to wall
//                continue;
//            }
//            Position neighbor1= neighbors[0];
//            Position neighbor2= neighbors[1];
//
//            boolean cell1_is_pass = mymaze.getMatrix()[neighbor1.getRowIndex()][neighbor1.getColumnIndex()]==0;
//            boolean cell2_is_pass = mymaze.getMatrix()[neighbor2.getRowIndex()][neighbor2.getColumnIndex()]==0;
//
//            if ((cell1_is_pass && !cell2_is_pass) || (!cell1_is_pass && cell2_is_pass)){
//                mymaze.getMatrix()[row][col]=0; //break the wall and make it a pass.
//                Position newcell=cell1_is_pass ? neighbor2 : neighbor1;
//                lastVisitedCell = newcell;
//                mymaze.getMatrix()[newcell.getRowIndex()][newcell.getColumnIndex()]=0;//break the neighbor.
//                addWallsToList(newcell.getRowIndex(), newcell.getColumnIndex(), mymaze);
//            }
//
//        }
//
//        mymaze.setStartPosition(startcell);
//        mymaze.setGoalPosition(mymaze.generateGoalCell());
//        //mymaze.setGoalPosition(lastVisitedCell);
//
//
//        return mymaze;
//    }

    public Maze generate(int rows, int cols) {
        if( rows<=0 || cols<=0){
            Maze mymaze = new Maze();
        }
        Maze mymaze = new Maze(rows, cols);
        ArrayList<Position> edgePassages = new ArrayList<>();

        // Initialize all cells as walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mymaze.getMatrix()[i][j] = 1;
            }
        }

        // Generate start cell
        Position startcell = mymaze.generateStartCell();
        mymaze.setStartPosition(startcell);
        mymaze.getMatrix()[startcell.getRowIndex()][startcell.getColumnIndex()] = 0;
        addWallsToList(startcell.getRowIndex(), startcell.getColumnIndex(), mymaze);

        while (!walls.isEmpty()) {
            int i = rand.nextInt(walls.size());
            Position wall = walls.remove(i);

            int row = wall.getRowIndex();
            int col = wall.getColumnIndex();

            Position[] neighbors = getWallSides(wall, mymaze);
            if (neighbors == null) continue;

            Position neighbor1 = neighbors[0];
            Position neighbor2 = neighbors[1];

            boolean cell1_is_pass = mymaze.getMatrix()[neighbor1.getRowIndex()][neighbor1.getColumnIndex()] == 0;
            boolean cell2_is_pass = mymaze.getMatrix()[neighbor2.getRowIndex()][neighbor2.getColumnIndex()] == 0;

            if ((cell1_is_pass && !cell2_is_pass) || (!cell1_is_pass && cell2_is_pass)) {
                mymaze.getMatrix()[row][col] = 0;
                Position newcell = cell1_is_pass ? neighbor2 : neighbor1;
                mymaze.getMatrix()[newcell.getRowIndex()][newcell.getColumnIndex()] = 0;

                // Only add if it's on edge and not the start
                if (isOnEdge(newcell, mymaze) && !newcell.equals(startcell)) {
                    edgePassages.add(newcell);
                }

                addWallsToList(newcell.getRowIndex(), newcell.getColumnIndex(), mymaze);
            }
        }


        // Pick a goal from edgePassages
        Position goal = null;
        if (!edgePassages.isEmpty()) {
            goal = edgePassages.get(rand.nextInt(edgePassages.size()));
        } else {
            // Fallback: find *any* different cell on edge that is a passage
            ArrayList<Position> backup = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Position p = new Position(i, j);
                    if (isOnEdge(p, mymaze) && mymaze.getMatrix()[i][j] == 0 && !p.equals(startcell)) {
                        backup.add(p);
                    }
                }
            }
            if (!backup.isEmpty()) {
                goal = backup.get(rand.nextInt(backup.size()));
            }

        }

        if (goal == null || goal.equals(startcell)) {
            return generate(rows, cols); // regenerate the maze
        }
        mymaze.setGoalPosition(goal);
        return mymaze;
    }




    private void addWallsToList(int row, int col, Maze maze){

        int rowsnum= maze.getRows();
        int colsnum= maze.getCols();

        ArrayList<Position> newWalls = new ArrayList<>();

        if(row + 2 >= 0 && row+2 < rowsnum && maze.getMatrix()[row +2 ][col]==1){
            newWalls.add(new Position(row+1,col));
        }
        if(row - 2 >= 0 && row-2 < rowsnum && maze.getMatrix()[row-2][col]==1){
            newWalls.add(new Position(row-1,col));
        }
        if(col+2 >= 0 && col+2 < colsnum && maze.getMatrix()[row][col+2]==1){
            newWalls.add(new Position(row,col+1));
        }
        if(col-2 >= 0 && col-2 < colsnum && maze.getMatrix()[row][col-2]==1){
            newWalls.add(new Position(row,col-1));

            java.util.Collections.shuffle(newWalls, this.rand);
            this.walls.addAll(newWalls);
        }

    }

    private Position[] getWallSides(Position wall, Maze maze)
    {
        int row= wall.getRowIndex();
        int col= wall.getColumnIndex();

        if (row%2==0 && col%2==1) {
            int r1 = row - 1;
            int r2 = row + 1;
            if (isInBounds(r1, col, maze) && isInBounds(r2, col, maze)) {
                return new Position[]{new Position(r1, col), new Position(r2, col)};
            }
            else{
                return null;
            }


        } else if (row%2==1 && col%2==0) {
            int c1 = col - 1;
            int c2 = col + 1;
            if (isInBounds(row, c1, maze) && isInBounds(row, c2, maze)) {
                return new Position[]{new Position(row, c1), new Position(row, c2)};
            }
            else {
                return null;
            }

        }
        return null;
    }

    private boolean isInBounds(int row, int col, Maze maze) {
        return row >= 0 && row < maze.getRows() && col >= 0 && col < maze.getCols();
    }

    private boolean isOnEdge(Position pos, Maze maze)
    {
        if(pos.getRowIndex() == 0 || pos.getRowIndex() == maze.getRows()-1 || pos.getColumnIndex() ==0 || pos.getColumnIndex()==maze.getCols()-1){
            return true;
        }
        return false;
    }


    public Maze createTrickyMaze() {
        int[][] template = {
                {0,0,0,1,0,0,0,0,0},
                {1,1,0,1,0,1,1,0,1},
                {0,0,0,1,0,0,0,0,1},
                {0,1,1,1,0,1,1,0,1},
                {0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,0,1,1},
                {1,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,1}
        };

        Maze maze = new Maze(8, 9);  // rows=8, cols=9
        int[][] matrix = maze.getMatrix();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = template[i][j];
            }
        }

        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(0, 8));
        return maze;
    }
}









