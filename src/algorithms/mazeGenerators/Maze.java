package algorithms.mazeGenerators;

public class Maze {
    private int[][] matrix;
    private int rows;
    private int cols;

    public Maze(){
        this.rows=1000;
        this.cols=1000;
        this.matrix= new int[this.rows][this.cols];
    }

    public Maze(int rows, int cols){
        if(rows>0 && cols>0)
        {
        this.rows=rows;
        this.cols=cols;
        this.matrix= new int[this.rows][this.cols];
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
}
