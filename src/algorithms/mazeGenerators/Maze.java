package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This is class Maze. it has all necessary fields and method to generate a maze of all types.
 */
public class Maze {

    private int[][] matrix;
    private int rows;
    private int cols;
    private Position S;
    private Position E;
    private Random rand;

    /**
     * Default constructor. creates a 100*100 maze matrix.
     */
    public Maze(){
        this.rows=100;
        this.cols=100;
        this.matrix= new int[this.rows][this.cols];
        this.rand=new Random();
    }

    /**
     * Constructor with params. to let the user create a maze
     * in any valid size.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     */
    public Maze(int rows, int cols){
        if(rows>=0 && cols>=0)
        {
            this.rows=rows;
            this.cols=cols;
            this.matrix= new int[this.rows][this.cols];
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
     * Getter for the maze matrix.
     * @return int[][] matrix.
     */
    public int[][] getMatrix(){
        return this.matrix;
    }


    /**
     * This method prints the maze matrix with S to define start position
     * and E to define goal position. 1 are walls, 0 are passages.
     */
    public void print(){
        System.out.println();
        for (int i=0;i<this.getRows();i++){
            for (int j=0;j<this.getCols();j++){
                boolean isStart = i == this.S.getRowIndex() && j == this.S.getColumnIndex();
                boolean isGoal = i == this.E.getRowIndex() && j == this.E.getColumnIndex();

                if (isStart && isGoal) {
                    System.out.print("X"); // Start and Goal at same spot
                } else if (isStart) {
                    System.out.print("S");
                } else if (isGoal) {
                    System.out.print("E");
                } else {
                    System.out.print(this.matrix[i][j]);
                }
            }
            System.out.println();
        }
}


    /**
     * Getter for start position.
     * @return Position start cell in maze.
     */
    public Position getStartPosition(){
        return this.S;
    }

    /**
     * Getter for goal position.
     * @return Position goal cell in maze.
     */
    public Position getGoalPosition(){
        return this.E;
    }

    /**
     * Setter for start position.
     * @param start position to set as start
     */
    public void setStartPosition(Position start){
        this.S=start;
    }

    /**
     * Setter for goal position.
     * @param goal position to set as goal
     */
    public void setGoalPosition(Position goal){
        this.E=goal;
    }

    /**
     * This is a helping method. it is being used to generate a
     * valid start position that should be on maze's frame and random.
     * @return Position of the start cell.
     */
    public Position generateStartCell() {
        ArrayList<Position> edgeCells = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            edgeCells.add(new Position(i, 0));
            edgeCells.add(new Position(i, cols - 1));
        }
        for (int j = 1; j < cols - 1; j++) {
            edgeCells.add(new Position(0, j));
            edgeCells.add(new Position(rows - 1, j));
        }

        Collections.shuffle(edgeCells, rand);
        for (Position pos : edgeCells) {
            matrix[pos.getRowIndex()][pos.getColumnIndex()] = 0;
            return pos;
        }

        return new Position(0, 0); // fallback
    }


    /**
     * This is a helping method. it is being used to generate a
     * valid goal position that should be on maze's frame, random and
     * different from start cell.
     * @return Position of the goal cell.
     */
    public Position generateGoalCell() {
        ArrayList<Position> edgePassages = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            if (matrix[i][0] == 0 && !(i == S.getRowIndex() && 0 == S.getColumnIndex()))
                edgePassages.add(new Position(i, 0));
            if (matrix[i][cols - 1] == 0 && !(i == S.getRowIndex() && cols - 1 == S.getColumnIndex()))
                edgePassages.add(new Position(i, cols - 1));
        }

        for (int j = 1; j < cols - 1; j++) {
            if (matrix[0][j] == 0 && !(0 == S.getRowIndex() && j == S.getColumnIndex()))
                edgePassages.add(new Position(0, j));
            if (matrix[rows - 1][j] == 0 && !(rows - 1 == S.getRowIndex() && j == S.getColumnIndex()))
                edgePassages.add(new Position(rows - 1, j));
        }

        if (!edgePassages.isEmpty()) {
            return edgePassages.get(rand.nextInt(edgePassages.size()));
        }

        for (int i = 0; i < rows; i++) {
            if (i == S.getRowIndex()) continue;
            matrix[i][0] = 0;
            return new Position(i, 0);
        }

        matrix[rows - 1][cols - 1] = 0;
        return new Position(rows - 1, cols - 1);
    }

    /**
     *
     * @return
     */

    public byte[] toByteArray(){
        byte[] bytes = new byte[12 + rows * cols];

        //Meta data of the maze
        //rows:
        bytes[0] = (byte)(rows/256);
        bytes[1] = (byte)(rows%256);

        //colms
        bytes[2] = (byte)(cols/256);
        bytes[3] = (byte)(cols%256);

        //Start position
        bytes[4] = (byte)(start.getRow() / 256);
        bytes[5] = (byte)(start.getRow() % 256);
        bytes[6] = (byte)(start.getCol() / 256);
        bytes[7] = (byte)(start.getCol() % 256);

        //Goal Position
        bytes[8]  = (byte)(goal.getRow() / 256);
        bytes[9]  = (byte)(goal.getRow() % 256);
        bytes[10] = (byte)(goal.getCol() / 256);
        bytes[11] = (byte)(goal.getCol() % 256);

        //Contact (0,1) copy from the maze to the array
        int index = 12;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                bytes[index++] = (byte)(maze[i][j]);

        return bytes;
    }
    /**
     * Constructor for maze from byte array
     */

    public Maze(byte[] bytes){
         int rows = bytes[0] * 256+bytes[1];
         int cols = bytes[2] * 256+bytes[3];

         this.rows = rows;
         this.cols = cols;

         //determinate the start position
         int startRow = bytes[4] * 256+bytes[5];
         int startCol = bytes[6] * 256+bytes[7];
         this.S = new Position(startRow, startCol);

        //determinate the goal position
        int goalRow = bytes[8] * 256+bytes[9];
        int goalCol = bytes[10] * 256+bytes[11];
        this.E = new Position(goalRow, goalCol);

        //fill the maze, recover the maze from the array
        this.matrix = new int[rows][cols];
        int index = 12;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.matrix[i][j] = bytes[index++];
            }
    }
}
}
