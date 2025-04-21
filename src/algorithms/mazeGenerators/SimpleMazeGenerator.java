package algorithms.mazeGenerators;

import java.util.Random;


public class SimpleMazeGenerator extends AMazeGenerator {

    private Random rand;

    public SimpleMazeGenerator(){
        this.rand=new Random();
    }

public Maze generate(int rows, int cols) {
    Maze simple = new Maze(rows, cols);

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            simple.getMatrix()[i][j] = 1;
        }
    }

//    int startRow, startCol;
//    if (rand.nextBoolean()) {
//        startRow = rand.nextBoolean() ? 0 : rows - 1;
//        startCol = rand.nextInt(cols);
//    } else {
//        startCol = rand.nextBoolean() ? 0 : cols - 1;
//        startRow = rand.nextInt(rows);
//    }

    Position startcell = simple.generateStartCell();
    simple.setStartPosition(startcell);

//    int goalRow, goalCol;
//    do {
//        goalRow = 1 + rand.nextInt(rows - 2);
//        goalCol = 1 + rand.nextInt(cols - 2);
//    } while (goalRow == startcell.getRowIndex() && goalCol == startcell.getColumnIndex());

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
