package algorithms.maze3D;

import java.util.Objects;

/**
 * This is a class that defines a 3D position (depth,row,column).
 * Uses the program to define a location in a 3D matrix.
 */
public class Position3D {

    int row;
    int col;
    int depth;

    /**
     * Constructor with params.
     * @param row represents the row cell index
     * @param col represents the column cell index
     * @param depth represents the depth cell index
     */
    public Position3D(int depth,int row,int col){
        this.row=row;
        this.col=col;
        this.depth=depth;
    }

    /**
     * Getter for the cell's row index.
     * @return int of the row index.
     */
    public int getRowIndex(){
        return this.row;
    }

    /**
     * Getter for the cell's column index.
     * @return int of the column index.
     */
    public int getColumnIndex(){
        return this.col;
    }

    /**
     * Getter for the cell's depth index.
     * @return int of the depth index.
     */
    public int getDepthIndex(){
        return this.depth;
    }

    /**
     * helps to print the 3D position.
     * @return String that represents 3D position.
     */
    public String toString(){
        return "{"+ this.getDepthIndex() + "," + this.getRowIndex() + "," + this.getColumnIndex() + "}";
    }

    /**
     * to make sure that the hashcode is proper.
     * @return int of hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.depth,this.row,this.col);
    }

    /**
     * To make comparison able between 2 3D Positions.
     * @param obj that will represent to comparable object.
     * @return boolean. true if equal false if different.
     */
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
