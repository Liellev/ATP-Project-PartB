package algorithms.mazeGenerators;

import java.util.Objects;

/**
 * This is a class that defines a position (row,column).
 * Uses the program to define a location in a matrix.
 */
public class Position {

     int row;
     int col;

    /**
     * Constructor with params.
     * @param row represents the row cell index
     * @param col represents the column cel index
     */
    public Position(int row,int col){
        this.row=row;
        this.col=col;
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
     * helps to print the position.
     * @return String that represents position.
     */
    public String toString(){
       return "{"+ this.getRowIndex() + "," + this.getColumnIndex() + "}";
    }

    /**
     * to make sure thar the hashcode is proper.
     * @return int of hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.row,this.col);
    }

    /**
     * To make comparison able between to Positions.
     * @param obj that will represent to comparable object.
     * @return boolean. rue if equal false if different.
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
        Position other=(Position) obj;
        return (this.row== other.row && this.col== other.col);
    }

}
