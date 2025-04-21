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

        int edge = this.rand.nextInt(4); // 0=top, 1=bottom, 2=left, 3=right
        int row = 0, col = 0;

        switch (edge) {
            case 0: row = 0; col = this.rand.nextInt(cols);
                    break;
            case 1: row = rows - 1; col = this.rand.nextInt(cols);
                    break;
            case 2: col = 0; row = this.rand.nextInt(rows);
                    break;
            case 3: col = cols - 1; row = this.rand.nextInt(rows);
                    break;
        }
        Position startcell=new Position(row, col);
        this.getMatrix()[startcell.getRowIndex()][startcell.getColumnIndex()]=0;
        return startcell;
    }

    public Position generateGoalCell(){

        ArrayList<Position> edgePassages = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean isEdge = row == 0 || row == rows - 1 || col == 0 || col == cols - 1;
                boolean isPassage = this.getMatrix()[row][col] == 0;
                Position pos = new Position(row, col);

                if (isEdge && isPassage && !pos.equals(this.S)) {
                    edgePassages.add(pos);
                }
            }
        }

        if (edgePassages.isEmpty()) {
            // fallback: return any passage not equal to S, or just S if none
            return this.S;
        }

        return edgePassages.get(rand.nextInt(edgePassages.size()));
    }


}
