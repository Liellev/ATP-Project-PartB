package algorithms.mazeGenerators;

import java.util.Random;
import java.util.ArrayList;

public class MyMazeGenerator extends AMazeGenerator{
    private Random rand;
    private ArrayList<int[]> walls;


    public MyMazeGenerator(){
        this.rand=new Random();
        this.walls= new ArrayList<int[]>();

    }
    @Override
    public Maze generate(int rows, int cols) {

        Maze mymaze=new Maze(rows,cols);
        for(int i=0; i< mymaze.getRows();i++){
            for(int j=0;j< mymaze.getCols();j++){
                mymaze.getMatrix()[i][j]=1;
            }
        }
        int startrow= rand.nextInt((rows-1)/2)*2+1;
        int startcol= rand.nextInt((rows-1)/2)*2+1;
        int startcell = mymaze.getMatrix()[startrow][startcol];
        startcell=0;
        int wall_r =mymaze.getMatrix()[startrow][startcol+1];
        int wall_l =mymaze.getMatrix()[startrow][startcol-10];


        return null;
    }

}





