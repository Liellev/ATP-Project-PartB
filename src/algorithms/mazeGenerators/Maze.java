package algorithms.mazeGenerators;
import java.util.ArrayList;
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
        if(rows>=0 && cols>=0)
        {
            this.rows=rows;
            this.cols=cols;
            this.matrix= new int[this.rows][this.cols];
            this.rand=new Random();
            if(rows==0&&cols==0){
                this.S=new Position(0,0);
                this.E=new Position(0,0);
            }
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
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                if (i == this.S.getRowIndex() && j == this.S.getColumnIndex()) {
                    System.out.print("S");
                } else if (i == this.E.getRowIndex() && j == this.E.getColumnIndex()) {
                    System.out.print("E");
                } else {
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

    public Position generateStartCell() {
        ArrayList<Position> edgeCells = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            if (getMatrix()[i][0] == 1) edgeCells.add(new Position(i, 0));
            if (getMatrix()[i][cols - 1] == 1) edgeCells.add(new Position(i, cols - 1));
        }

        for (int j = 0; j < cols; j++) {
            if (getMatrix()[0][j] == 1) edgeCells.add(new Position(0, j));
            if (getMatrix()[rows - 1][j] == 1) edgeCells.add(new Position(rows - 1, j));
        }

        if (edgeCells.isEmpty()) return new Position(0, 0); // fallback
        Position start = edgeCells.get(rand.nextInt(edgeCells.size()));
        getMatrix()[start.getRowIndex()][start.getColumnIndex()] = 0;
        return start;
    }

    public Position generateGoalCell() {
        ArrayList<Position> edgePassages = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            if (matrix[i][0] == 0 && !(i == S.getRowIndex() && 0 == S.getColumnIndex()))
                edgePassages.add(new Position(i, 0));
            if (matrix[i][cols - 1] == 0 && !(i == S.getRowIndex() && cols - 1 == S.getColumnIndex()))
                edgePassages.add(new Position(i, cols - 1));
        }

        for (int j = 0; j < cols; j++) {
            if (matrix[0][j] == 0 && !(0 == S.getRowIndex() && j == S.getColumnIndex()))
                edgePassages.add(new Position(0, j));
            if (matrix[rows - 1][j] == 0 && !(rows - 1 == S.getRowIndex() && j == S.getColumnIndex()))
                edgePassages.add(new Position(rows - 1, j));
        }

        if (edgePassages.isEmpty()) {
            // fallback: אם אין קצה פתוח, מחזיר את נקודת ההתחלה
            return S;
        }
        return edgePassages.get(new Random().nextInt(edgePassages.size()));
    }

}
