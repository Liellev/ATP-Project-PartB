package algorithms.mazeGenerators;

import java.util.Random;


public class SimpleMazeGenerator extends AMazeGenerator {

    private Random rand;

    public SimpleMazeGenerator(){
        this.rand=new Random();
    }

public Maze generate(int rows, int cols) {
        if(rows<=0 || cols <=0){
            Maze simple=new Maze();
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
