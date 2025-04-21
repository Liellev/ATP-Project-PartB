package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

import java.util.Objects;

public class Position3D {
    int row;
    int col;
    int depth;

    public Position3D(int depth,int row,int col){
        this.row=row;
        this.col=col;
        this.depth=depth;
    }

    public int getRowIndex(){
        return this.row;
    }

    public int getColumnIndex(){
        return this.col;
    }

    public int getDepthIndex(){
        return this.depth;
    }

    public String toString(){
        return "{"+ this.getDepthIndex() + "," + this.getRowIndex() + "," + this.getColumnIndex() + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.depth,this.row,this.col);
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
        Position3D other=(Position3D) obj;
        return (this.row== other.row && this.col== other.col&& this.depth==other.depth);
    }

}
