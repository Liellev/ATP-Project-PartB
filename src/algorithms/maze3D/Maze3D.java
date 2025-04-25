package algorithms.maze3D;

import java.util.Random;

/**
 * This is class Maze3D. it has all necessary fields and method to generate a 3D maze of all types.
 */
public class Maze3D {

    private int[][][] matrix;
    private int rows;
    private int cols;
    private int depth;
    private Position3D S;
    private Position3D E;
    private Random rand;


    /**
     * Default constructor. creates a 100*100*100 3D maze matrix.
     */
    public Maze3D(){
        this.rows=100;
        this.cols=100;
        this.depth=100;
        this.matrix= new int[this.depth][this.rows][this.cols];
        this.rand=new Random();

    }

    /**
     * Constructor with params. to let the user create a 3D maze
     * in any valid size.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @param depth This is the depth of maze matrix.
     */
    public Maze3D(int depth,int rows, int cols){
        if(rows>0 && cols>0 && depth>0)
        {
            this.rows=rows;
            this.cols=cols;
            this.depth=depth;
            this.matrix= new int[this.depth][this.rows][this.cols];
            this.rand=new Random();
        }
    }

    /**
     * Getter for rows number.
     * @return int rows count
     */
    public int getRows(){
        return this.rows;
    }

    /**
     * Getter for columns number.
     * @return int columns count
     */
    public int getCols(){
        return this.cols;
    }

    /**
     * Getter for depth number.
     * @return int depth count
     */
    public int getDepth(){ return this.depth;}

    /**
     * Getter for the maze matrix.
     * @return int[][][] matrix.
     */
    public int[][][] getMap(){
        return this.matrix;
    }


    /**
     * This method prints the 3D maze matrix with S to define start position
     * and E to define goal position. 1 are walls, 0 are passages.
     * I also displays each layer separately.
     */
    public void print(){
        System.out.println();
        for (int i=0;i<this.getDepth();i++){
            System.out.println("Layer"+" "+ i);
            for (int j=0;j<this.getRows();j++) {
                for( int k=0;k<this.getCols();k++){
                    if(i==this.S.getDepthIndex()&&j==this.S.getRowIndex()&& k==this.S.getColumnIndex()){
                        System.out.print("S");
                    } else if (i==this.E.getDepthIndex()&&j==this.E.getRowIndex()&& k==this.E.getColumnIndex()) {
                        System.out.print("E");
                    }
                    else{
                        System.out.print(this.matrix[i][j][k]);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }

    }

    /**
     * Getter for start position.
     * @return Position start cell in maze.
     */
    public Position3D getStartPosition(){
        return this.S;
    }

    /**
     * Getter for goal position.
     * @return Position goal cell in maze.
     */
    public Position3D getGoalPosition(){
        return this.E;
    }

    /**
     * Setter for start 3D position.
     * @param start position to set as start
     */
    public void setStartPosition(Position3D start){
        this.S=start;
    }

    /**
     * Setter for goal 3D position.
     * @param goal position to set as goal
     */
    public void setGoalPosition(Position3D goal){
        this.E=goal;
    }

}
