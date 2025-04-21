package algorithms.maze3D;

import java.util.Random;
import java.util.ArrayList;

public class MyMaze3DGenerator extends AMaze3DGenerator{
    private Random rand;
    private ArrayList<Position3D> walls;


    public MyMaze3DGenerator(){
        this.rand=new Random();
        this.walls= new ArrayList<Position3D>();

    }
//    @Override
//    public Maze3D generate(int rows, int cols) {
//
//        Maze3D myMaze3D=new Maze3D(rows,cols);
//
//        for(int i=0; i< myMaze3D.getRows();i++){
//            for(int j=0;j< myMaze3D.getCols();j++){
//                myMaze3D.getMatrix()[i][j]=1;
//            }
//        }
//
//        Position3D startcell=myMaze3D.generateStartCell();
//        Position3D lastVisitedCell= startcell;
//        myMaze3D.setStartPosition3D(startcell);
//        myMaze3D.getMatrix()[startcell.getRowIndex()][startcell.getColumnIndex()]=0;
//        addWallsToList(startcell.getRowIndex(), startcell.getColumnIndex(),myMaze3D);
//
//        while (this.walls.size()!=0 ){
//            int i=this.rand.nextInt(this.walls.size());
//            Position3D wall=this.walls.remove(i); //choose wall randomly from list
//
//            int row=wall.getRowIndex();
//            int col=wall.getColumnIndex();
//
//            Position3D[] neighbors=getWallSides(wall,myMaze3D);
//            if(neighbors==null){ //no sides to wall
//                continue;
//            }
//            Position3D neighbor1= neighbors[0];
//            Position3D neighbor2= neighbors[1];
//
//            boolean cell1_is_pass = myMaze3D.getMatrix()[neighbor1.getRowIndex()][neighbor1.getColumnIndex()]==0;
//            boolean cell2_is_pass = myMaze3D.getMatrix()[neighbor2.getRowIndex()][neighbor2.getColumnIndex()]==0;
//
//            if ((cell1_is_pass && !cell2_is_pass) || (!cell1_is_pass && cell2_is_pass)){
//                myMaze3D.getMatrix()[row][col]=0; //break the wall and make it a pass.
//                Position3D newcell=cell1_is_pass ? neighbor2 : neighbor1;
//                lastVisitedCell = newcell;
//                myMaze3D.getMatrix()[newcell.getRowIndex()][newcell.getColumnIndex()]=0;//break the neighbor.
//                addWallsToList(newcell.getRowIndex(), newcell.getColumnIndex(), myMaze3D);
//            }
//
//        }
//
//        myMaze3D.setStartPosition3D(startcell);
//        myMaze3D.setGoalPosition3D(myMaze3D.generateGoalCell());
//        //myMaze3D.setGoalPosition3D(lastVisitedCell);
//
//
//        return myMaze3D;
//    }

    public Maze3D generate(int depth, int rows, int cols) {
        if( rows<=0 || cols<=0){
            Maze3D myMaze3D = new Maze3D();
        }
        Maze3D mymaze3D = new Maze3D(depth,rows, cols);
        ArrayList<Position3D> edgePassages = new ArrayList<>();

        // Initialize all cells as walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mymaze3D.getMatrix()[i][j] = 1;
            }
        }

        // Generate start cell
        Position3D startcell = mymaze3D.generateStartCell();
        mymaze3D.setStartPosition3D(startcell);
        mymaze3D.getMatrix()[startcell.getRowIndex()][startcell.getColumnIndex()] = 0;
        addWallsToList(startcell.getRowIndex(), startcell.getColumnIndex(), mymaze3D);

        while (!walls.isEmpty()) {
            int i = rand.nextInt(walls.size());
            Position3D wall = walls.remove(i);

            int row = wall.getRowIndex();
            int col = wall.getColumnIndex();

            Position3D[] neighbors = getWallSides(wall, mymaze3D);
            if (neighbors == null) continue;

            Position3D neighbor1 = neighbors[0];
            Position3D neighbor2 = neighbors[1];

            boolean cell1_is_pass = mymaze3D.getMatrix()[neighbor1.getRowIndex()][neighbor1.getColumnIndex()] == 0;
            boolean cell2_is_pass = mymaze3D.getMatrix()[neighbor2.getRowIndex()][neighbor2.getColumnIndex()] == 0;

            if ((cell1_is_pass && !cell2_is_pass) || (!cell1_is_pass && cell2_is_pass)) {
                mymaze3D.getMatrix()[row][col] = 0;
                Position3D newcell = cell1_is_pass ? neighbor2 : neighbor1;
                mymaze3D.getMatrix()[newcell.getRowIndex()][newcell.getColumnIndex()] = 0;

                // Only add if it's on edge and not the start
                if (isOnEdge(newcell, mymaze3D) && !newcell.equals(startcell)) {
                    edgePassages.add(newcell);
                }

                addWallsToList(newcell.getRowIndex(), newcell.getColumnIndex(), mymaze3D);
            }
        }


        // Pick a goal from edgePassages
        Position3D goal = null;
        if (!edgePassages.isEmpty()) {
            goal = edgePassages.get(rand.nextInt(edgePassages.size()));
        } else {
            // Fallback: find *any* different cell on edge that is a passage
            ArrayList<Position3D> backup = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Position3D p = new Position3D(i, j);
                    if (isOnEdge(p, mymaze3D) && mymaze3D.getMatrix()[i][j] == 0 && !p.equals(startcell)) {
                        backup.add(p);
                    }
                }
            }
            if (!backup.isEmpty()) {
                goal = backup.get(rand.nextInt(backup.size()));
            }

        }

        if (goal == null || goal.equals(startcell)) {
            return generate(rows, cols); // regenerate the Maze3D
        }
        mymaze3D.setGoalPosition3D(goal);
        return mymaze3D;
    }




    private void addWallsToList(int row, int col, Maze3D maze3D){

        int rowsnum= maze3D.getRows();
        int colsnum= maze3D.getCols();

        ArrayList<Position3D> newWalls = new ArrayList<>();

        if(row + 2 >= 0 && row+2 < rowsnum && Maze3D.getMatrix()[row +2 ][col]==1){
            newWalls.add(new Position3D(row+1,col));
        }
        if(row - 2 >= 0 && row-2 < rowsnum && Maze3D.getMatrix()[row-2][col]==1){
            newWalls.add(new Position3D(row-1,col));
        }
        if(col+2 >= 0 && col+2 < colsnum && Maze3D.getMatrix()[row][col+2]==1){
            newWalls.add(new Position3D(row,col+1));
        }
        if(col-2 >= 0 && col-2 < colsnum && Maze3D.getMatrix()[row][col-2]==1){
            newWalls.add(new Position3D(row,col-1));

            java.util.Collections.shuffle(newWalls, this.rand);
            this.walls.addAll(newWalls);
        }

    }

    private Position3D[] getWallSides(Position3D wall, Maze3D Maze3D)
    {
        int row= wall.getRowIndex();
        int col= wall.getColumnIndex();

        if (row%2==0 && col%2==1) {
            int r1 = row - 1;
            int r2 = row + 1;
            if (isInBounds(r1, col, Maze3D) && isInBounds(r2, col, Maze3D)) {
                return new Position3D[]{new Position3D(r1, col), new Position3D(r2, col)};
            }
            else{
                return null;
            }


        } else if (row%2==1 && col%2==0) {
            int c1 = col - 1;
            int c2 = col + 1;
            if (isInBounds(row, c1, Maze3D) && isInBounds(row, c2, Maze3D)) {
                return new Position3D[]{new Position3D(row, c1), new Position3D(row, c2)};
            }
            else {
                return null;
            }

        }
        return null;
    }

    private boolean isInBounds(int row, int col, Maze3D Maze3D) {
        return row >= 0 && row < Maze3D.getRows() && col >= 0 && col < Maze3D.getCols();
    }

    private boolean isOnEdge(Position3D pos, Maze3D Maze3D)
    {
        if(pos.getRowIndex() == 0 || pos.getRowIndex() == Maze3D.getRows()-1 || pos.getColumnIndex() ==0 || pos.getColumnIndex()==Maze3D.getCols()-1){
            return true;
        }
        return false;
    }


    public Maze3D createTrickyMaze3D() {
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

        Maze3D Maze3D = new Maze3D(8, 9);  // rows=8, cols=9
        int[][] matrix = Maze3D.getMatrix();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = template[i][j];
            }
        }

        Maze3D.setStartPosition3D(new Position3D(0, 0));
        Maze3D.setGoalPosition3D(new Position3D(0, 8));
        return Maze3D;
    }
}










