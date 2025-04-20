package algorithms.mazeGenerators;

import java.util.Random;


public class SimpleMazeGenerator extends AMazeGenerator {

    private Random rand;

    public SimpleMazeGenerator(){
        this.rand=new Random();
    }
    @Override
    public Maze generate(int rows, int cols) {

        Maze simple=new Maze(rows,cols);

        //generate a maze full of 1(walls);
        for(int i=0; i< simple.getRows();i++){
            for(int j=0;j< simple.getCols();j++){
                simple.getMatrix()[i][j]=1;
            }
        }
        simple.setStartPosition(simple.generateStartCell());
        Position startcell=simple.getStartPosition();

        simple.setGoalPosition(simple.generateGoalCell());
        Position goalcell=simple.getGoalPosition();

        simple.getMatrix()[goalcell.getRowIndex()][goalcell.getColumnIndex()]=0;

        int row=startcell.getRowIndex(),col=startcell.getColumnIndex(); //starting from start cell
        while (row!=goalcell.getRowIndex() || col!=goalcell.getColumnIndex()){
            simple.getMatrix()[row][col]=0;//make the cell a pass

            if(row<=goalcell.getRowIndex()){
                row++;
            } else if (row>=goalcell.getRowIndex()) {
                row--;
            } else if (col<goalcell.getColumnIndex()) {
                col++;
            } else if (col>goalcell.getColumnIndex()) {
                col--;
            }
            simple.getMatrix()[row][col]=0;
        }
        for(int i=0; i< simple.getRows();i++){
            for(int j=0;j< simple.getCols();j++){
                if(simple.getMatrix()[i][j]==1)//only if it is a wall try to break it to keep existing paths.
                {
                    simple.getMatrix()[i][j]=this.rand.nextBoolean() ? 1 : 0;
                }
            }
        }
        return simple;
    }

}
