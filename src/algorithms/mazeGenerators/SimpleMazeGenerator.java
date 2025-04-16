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

        int startrow = 1 + this.rand.nextInt((rows - 2) / 2) * 2;
        int startcol = 1 + this.rand.nextInt((cols - 2) / 2) * 2;
        Position startcell =new Position(startrow,startcol);
        simple.setStartPosition(startcell);

        int goalRow = 1 + rand.nextInt((rows - 2) / 2) * 2;
        int goalCol = 1 + rand.nextInt((cols - 2) / 2) * 2;
        Position goalcell = new Position(goalRow, goalCol);
        simple.setGoalPosition(goalcell);
        simple.getMatrix()[goalRow][goalCol]=0;

        int row=startrow,col=startcol; //starting from start cell
        while (row!=goalRow || col!=goalCol){
            simple.getMatrix()[row][col]=0;//make the cell a pass

            if(row<goalRow){
                row++;
            } else if (row>goalRow) {
                row--;
            } else if (col<goalCol) {
                col++;
            } else if (col>goalCol) {
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
