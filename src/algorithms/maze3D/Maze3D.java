package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Random;

public class Maze3D {
    private int[][][] matrix;
    private int rows;
    private int cols;
    private int depth;
    private Position3D S;
    private Position3D E;
    private Random rand;


    public Maze3D(){
        this.rows=1000;
        this.cols=1000;
        this.depth=1000;
        this.matrix= new int[this.depth][this.rows][this.cols];
        this.rand=new Random();

    }

    public Maze3D(int depth,int rows, int cols){
        if(rows>0 && cols>0 && depth>0)
        {
            this.rows=rows;
            this.cols=cols;
            this.matrix= new int[this.depth][this.rows][this.cols];
            this.rand=new Random();
        }
    }

    public int getRows(){
        return this.rows;
    }

    public int getCols(){
        return this.cols;
    }

    public int getDepth(){ return this.depth;}

    public int[][][] getMap(){
        return this.matrix;
    }


    public void print(){
        System.out.println();
        for (int i=0;i<this.getDepth();i++){
            for (int j=0;j<this.getRows();j++) {
                for( int k=0;k<this.getDepth();k++){
                    if(i==this.S.getRowIndex()&&j==this.S.getColumnIndex()&& k==this.S.getDepthIndex()){
                        System.out.print("S");
                    } else if (i==this.E.getRowIndex()&&j==this.E.getColumnIndex()&& k==this.S.getDepthIndex()) {
                        System.out.print("E");
                    }
                    else{
                        System.out.print(this.matrix[i][j][k]);
                    }
                }

            }
            System.out.println();
        }

    }

    public Position3D getStartPosition(){
        return this.S;
    }

    public Position3D getGoalPosition(){
        return this.E;
    }

    public void setStartPosition(Position3D start){
        this.S=start;
    }

    public void setGoalPosition(Position3D goal){
        this.E=goal;
    }

    public Position3D generateStartCell3D(){

        int edge = this.rand.nextInt(6);
        int row = 0, col = 0, dep=0;

        switch (edge) {
            case 0: row = 0; col = this.rand.nextInt(cols);dep= this.rand.nextInt(depth);
                break;
            case 1: row = rows - 1; col = this.rand.nextInt(cols);dep =this.rand.nextInt(depth);
                break;
            case 2: col = 0; row = this.rand.nextInt(rows);dep =this.rand.nextInt(depth);
                break;
            case 3: col = cols - 1; row = this.rand.nextInt(rows);dep= this.rand.nextInt(depth);
                break;
            case 4: dep=0; row = this.rand.nextInt(rows);col=this.rand.nextInt(cols);
                break;
            case 5: dep=depth-1; row = this.rand.nextInt(rows);col=this.rand.nextInt(cols);
                break;
        }
        Position3D startcell=new Position3D(depth,row, col);
        this.getMap()[startcell.getDepthIndex()][startcell.getRowIndex()][startcell.getColumnIndex()]=0;
        return startcell;
    }

    public Position3D generateGoalCell3D(){

        ArrayList<Position3D> edgePassages = new ArrayList<>();
        for(int dep= 0; dep< depth ;dep++) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    boolean isEdge = dep==0 || dep==depth-1 || row == 0 || row == rows - 1 || col == 0 || col == cols - 1;
                    boolean isPassage = this.getMap()[dep][row][col] == 0;
                    Position3D pos = new Position3D(dep,row, col);

                    if (isEdge && isPassage && !pos.equals(this.S)) {
                        edgePassages.add(pos);
                    }
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
