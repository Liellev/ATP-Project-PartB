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
        int startrow= this.rand.nextInt((rows-1)/2)*2+1;
        int startcol= this.rand.nextInt((rows-1)/2)*2+1;
        Position startcell = mymaze.getStartPosition();
        mymaze.getMatrix()[startcell.getRowIndex()][startcell.getColumnIndex()]=0;
        Position wall_r= new Position(startrow,startcol+1);
        Position wall_l= new Position(startrow,startcol-1);
        Position wall_a= new Position(startrow+1,startcol);
        Position wall_b= new Position(startrow-1,startcol);

        this.walls.add(wall_a);
        this.walls.add(wall_b);
        this.walls.add(wall_r);
        this.walls.add(wall_l);

        while (this.walls.size()!=0){
            int i=this.rand.nextInt();
            Position pos=this.walls.get(i); //chose wall randomly from list
            if(mymaze.getMatrix()[pos.getRowIndex()][pos.getColumnIndex()]==1)//then not broken
            {

            }
        }

        return null;
    }

}





