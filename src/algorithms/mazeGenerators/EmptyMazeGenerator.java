package algorithms.mazeGenerators;

/**
 * This is a class that creates Empty Mazes.
 * Using the AMazeGenerator class.
 */
public class EmptyMazeGenerator extends AMazeGenerator {
     public EmptyMazeGenerator(){
     }

    /**
     * This method is used to generate an empty maze (without walls).
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @return Maze object.
     */
    @Override
    public Maze generate(int rows, int cols) {
        if( rows<=0 || cols<=0){
            Maze empty_maze = new Maze();
            rows=empty_maze.getRows();
            cols= empty_maze.getCols();
        }
        Maze empty_maze= new Maze(rows,cols);
        for (int i=0;i<empty_maze.getRows();i++){
            for (int j=0 ;j<empty_maze.getCols();j++){
                empty_maze.getMatrix()[i][j]=0;
            }
        }
        empty_maze.setStartPosition(empty_maze.generateStartCell());
        empty_maze.setGoalPosition(empty_maze.generateGoalCell());
        return empty_maze;
    }
}
