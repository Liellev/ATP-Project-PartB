package algorithms.mazeGenerators;

import java.util.Objects;

public class Position {
     int row;
     int col;

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

    @Override
    public int hashCode() {
        return Objects.hash(this.row,this.col);
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if (obj==null || obj.getClass()!=this.getClass())
        {
            return false;
        }
        Position other=(Position) obj;
        return (this.row== other.row && this.col== other.col);
    }

}
