package algorithms.mazeGenerators;

import java.util.Random;

/**
 * This is a class that creates Random Mazes.
 * extends the AMazeGenerator class.
 */
public class SimpleMazeGenerator extends AMazeGenerator {

    private Random rand;

    /**
     * Default constructor.
     */
    public SimpleMazeGenerator(){
        this.rand=new Random();
    }

    /**
     * This method is used to generate a random maze with possible
     * solutions.First, it builds a maze with just walls.
     * It takes start and goal positions and breaks
     * walls between. Afterwords it randomly breaks other walls to
     * make more solutions.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @return Maze object with possible solutions.
     */
    public Maze generate(int rows, int cols) {
            if(rows<=0 || cols <=0){
                Maze simple=new Maze();
                rows= simple.getRows();;
                cols= simple.getCols();
            }
            Maze simple = new Maze(rows, cols);

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    simple.getMatrix()[i][j] = 1;
                }
            }

            Position startcell = simple.generateStartCell();
            simple.setStartPosition(startcell);

            Position goalCell =  simple.generateGoalCell();
            simple.setGoalPosition(goalCell);

            int row = startcell.getRowIndex();
            int col = startcell.getColumnIndex();

            simple.getMatrix()[row][col] = 0;

            while (row != goalCell.getRowIndex()) {
                row += (goalCell.getRowIndex() > row) ? 1 : -1;
                simple.getMatrix()[row][col] = 0;
            }

            while (col != goalCell.getColumnIndex()) {
                col += (goalCell.getColumnIndex() > col) ? 1 : -1;
                simple.getMatrix()[row][col] = 0;
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (simple.getMatrix()[i][j] == 1 && rand.nextDouble() < 0.3) {
                        simple.getMatrix()[i][j] = 0;
                    }
                }
            }

        return simple;
    }

}
