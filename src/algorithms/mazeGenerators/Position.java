package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int col;

    public Position(int row,int col){
        this.row=row;
        this.col=col;
    }

    public int getRowIndex(){
        return this.row;
    }

    public int getColumnIndex(){
        return this.col;
    }

    public String toString(){
       return "{"+ this.getRowIndex() + "," + this.getColumnIndex() + "}";
    }
}
