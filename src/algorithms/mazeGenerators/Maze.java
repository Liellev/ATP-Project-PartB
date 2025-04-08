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
        System.out.println("{"+ this.getRows() + "," + this.getCols() + "}");
    }

    public Position getStartPosition(){
        return this.S;
    }

    public Position getGoalPosition(){
        return this.E;
    }
}
