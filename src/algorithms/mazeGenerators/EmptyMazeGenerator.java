package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
     public EmptyMazeGenerator(){
     }

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
