package algorithms.mazeGenerators;
 import java.util.Random;

public class Maze {
    private int[][] matrix;
    private int rows;
    private int cols;
    private Position S;
    private Position E;
    private Random rand;


    public Maze(){
        this.rows=1000;
        this.cols=1000;
        this.matrix= new int[this.rows][this.cols];
        this.rand=new Random();
        this.S =new Position(rand.nextInt((rows-1)/2)*2+1,rand.nextInt((cols-1)/2)*2+1);
        this.E =new Position(rand.nextInt((rows-1)/2)*2+1,rand.nextInt((cols-1)/2)*2+1);
    }

    public Maze(int rows, int cols){
        if(rows>0 && cols>0)
        {
        this.rows=rows;
        this.cols=cols;
        this.matrix= new int[this.rows][this.cols];
        this.rand=new Random();
        this.S =new Position(rand.nextInt((rows-1)/2)*2+1,rand.nextInt((cols-1)/2)*2+1);
        this.E =new Position(rand.nextInt((rows-1)/2)*2+1,rand.nextInt((cols-1)/2)*2+1);
        }
    }

    public int getRows(){
        return this.rows;
    }

    public int getCols(){
        return this.cols;
    }

    public int[][] getMatrix(){
        return this.matrix;
    }


    public void print(){
        System.out.println();
        for (int i=0;i<this.getRows();i++){
            for (int j=0;j<this.getCols();j++){
                if(i==this.S.getRowIndex()&&j==this.S.getColumnIndex()){
                    System.out.print("S");
                } else if (i==this.E.getRowIndex()&&j==this.E.getColumnIndex()) {
                    System.out.print("E");
                }
                else{
                    System.out.print(this.matrix[i][j]);
                }
            }
            System.out.println();
        }

    }

    public Position getStartPosition(){
        return this.S;
    }

    public Position getGoalPosition(){
        return this.E;
    }

    public void setStartPosition(Position start){
        this.S=start;
    }

    public void setGoalPosition(Position goal){
        this.E=goal;
    }

    public Position generateStartCell(){

        int startrow = 1 + this.rand.nextInt((rows - 2) / 2) * 2;
        int startcol = 1 + this.rand.nextInt((cols - 2) / 2) * 2;

        while(startrow!=0 && startrow!=this.getRows()-1 && startcol!=0 && startcol!=this.getCols()){
            startrow= 1 + this.rand.nextInt((rows - 2) / 2) * 2;
            startcol = 1 + this.rand.nextInt((cols - 2) / 2) * 2;
        }
        Position startcell =new Position(startrow,startcol);
        return startcell;
    }

    public Position generateGoalCell(){

        int goalrow = 1 + this.rand.nextInt((rows - 2) / 2) * 2;
        int goalcol = 1 + this.rand.nextInt((cols - 2) / 2) * 2;

        while(goalrow!=0 && goalrow!=this.getRows()-1 && goalcol!=0 && goalcol!=this.getCols()){
            goalrow= 1 + this.rand.nextInt((rows - 2) / 2) * 2;
            goalcol = 1 + this.rand.nextInt((cols - 2) / 2) * 2;
        }
        Position goalcell =new Position(goalrow,goalcol);
        return goalcell;
    }


}
