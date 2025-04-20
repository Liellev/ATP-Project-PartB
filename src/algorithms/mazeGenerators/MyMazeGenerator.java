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
    @Override
    public Maze generate(int rows, int cols) {

        Maze mymaze=new Maze(rows,cols);

        for(int i=0; i< mymaze.getRows();i++){
            for(int j=0;j< mymaze.getCols();j++){
                mymaze.getMatrix()[i][j]=1;
            }
        }
        int startrow = 1 + this.rand.nextInt((rows - 2) / 2) * 2;
        int startcol = 1 + this.rand.nextInt((cols - 2) / 2) * 2;
        Position startcell =new Position(startrow,startcol);
        Position lastVisitedCell= startcell;
        mymaze.setStartPosition(startcell);
        mymaze.getMatrix()[startcell.getRowIndex()][startcell.getColumnIndex()]=0;
        addWallsToList(startrow,startcol,mymaze);

        while (this.walls.size()!=0){
            int i=this.rand.nextInt(this.walls.size());
            Position wall=this.walls.remove(i); //choose wall randomly from list

            int row=wall.getRowIndex();
            int col=wall.getColumnIndex();

            Position[] neighbors=getWallSides(wall,mymaze);
            if(neighbors==null){ //no sides to wall
                continue;
            }
            Position neighbor1= neighbors[0];
            Position neighbor2= neighbors[1];

            boolean cell1_is_pass = mymaze.getMatrix()[neighbor1.getRowIndex()][neighbor1.getColumnIndex()]==0;
            boolean cell2_is_pass = mymaze.getMatrix()[neighbor2.getRowIndex()][neighbor2.getColumnIndex()]==0;

            if ((cell1_is_pass && !cell2_is_pass) || (!cell1_is_pass && cell2_is_pass)){
                mymaze.getMatrix()[row][col]=0; //break the wall and make it a pass.
                Position newcell=cell1_is_pass ? neighbor2 : neighbor1;
                lastVisitedCell = newcell;
                mymaze.getMatrix()[newcell.getRowIndex()][newcell.getColumnIndex()]=0;//break the neighbor.
                addWallsToList(newcell.getRowIndex(), newcell.getColumnIndex(), mymaze);
            }

        }
        mymaze.setStartPosition(startcell);
        mymaze.setGoalPosition(lastVisitedCell);


        return mymaze;
    }

    private void addWallsToList(int row, int col, Maze maze){

        int rowsnum= maze.getRows();
        int colsnum= maze.getCols();
        if(row + 2 >= 0 && row+2 < rowsnum && maze.getMatrix()[row +2 ][col]==1){
            this.walls.add(new Position(row+1,col));
        }
        if(row - 2 >= 0 && row-2 < rowsnum && maze.getMatrix()[row-2][col]==1){
            this.walls.add(new Position(row-1,col));
        }
        if(col+2 >= 0 && col+2 < colsnum && maze.getMatrix()[row][col+2]==1){
            this.walls.add(new Position(row,col+1));
        }
        if(col-2 >= 0 && col-2 < colsnum && maze.getMatrix()[row][col-2]==1){
            this.walls.add(new Position(row,col-1));
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

    public void blablo()
    {
        return;
    }
}









