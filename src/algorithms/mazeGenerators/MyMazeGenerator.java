package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {
    private Random rand;
    private ArrayList<Position> walls;

    public MyMazeGenerator() {
        this.rand = new Random();
        this.walls = new ArrayList<>();
    }
    @Override
    public Maze generate(int rows, int cols) {
        if (rows <= 1 || cols <= 1)
            return new Maze(); // fallback על מבוך ריק אם קלט שגוי

        Maze maze = new Maze(rows, cols);
        this.walls = new ArrayList<>();
        Random rand = new Random();

        // אתחול כל התאים כקירות
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                maze.getMatrix()[i][j] = 1;

        // יצירת נקודת התחלה חוקית על הקצה
        Position start = maze.generateStartCell();
        maze.setStartPosition(start);
        maze.getMatrix()[start.getRowIndex()][start.getColumnIndex()] = 0;

        addWallsToList(start.getRowIndex(), start.getColumnIndex(), maze);

        // יצירת המבוך עם קירות
        while (!walls.isEmpty()) {
            int i = rand.nextInt(walls.size());
            Position wall = walls.remove(i);
            Position[] neighbors = getWallSides(wall, maze);
            if (neighbors == null) continue;

            Position cell1 = neighbors[0], cell2 = neighbors[1];
            boolean pass1 = maze.getMatrix()[cell1.getRowIndex()][cell1.getColumnIndex()] == 0;
            boolean pass2 = maze.getMatrix()[cell2.getRowIndex()][cell2.getColumnIndex()] == 0;

            if (pass1 ^ pass2) {
                maze.getMatrix()[wall.getRowIndex()][wall.getColumnIndex()] = 0;
                Position newcell = pass1 ? cell2 : cell1;
                maze.getMatrix()[newcell.getRowIndex()][newcell.getColumnIndex()] = 0;
                addWallsToList(newcell.getRowIndex(), newcell.getColumnIndex(), maze);
            }
        }

        // יצירת נקודת סיום חוקית על הקצה (שונה מהתחלה)
        Position goal = null;
        int maxTries = 100;
        for (int tries = 0; tries < maxTries; tries++) {
            Position candidate = maze.generateGoalCell();
            if (candidate != null && !candidate.equals(start)) {
                goal = candidate;
                break;
            }
        }

        // fallback נוסף - סריקת כל הקצוות
        if (goal == null || goal.equals(start)) {
            ArrayList<Position> fallbackGoals = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                if (maze.getMatrix()[i][0] == 0 && !(i == start.getRowIndex() && 0 == start.getColumnIndex()))
                    fallbackGoals.add(new Position(i, 0));
                if (maze.getMatrix()[i][cols - 1] == 0 && !(i == start.getRowIndex() && cols - 1 == start.getColumnIndex()))
                    fallbackGoals.add(new Position(i, cols - 1));
            }
            for (int j = 0; j < cols; j++) {
                if (maze.getMatrix()[0][j] == 0 && !(0 == start.getRowIndex() && j == start.getColumnIndex()))
                    fallbackGoals.add(new Position(0, j));
                if (maze.getMatrix()[rows - 1][j] == 0 && !(rows - 1 == start.getRowIndex() && j == start.getColumnIndex()))
                    fallbackGoals.add(new Position(rows - 1, j));
            }
            if (!fallbackGoals.isEmpty()) {
                goal = fallbackGoals.get(rand.nextInt(fallbackGoals.size()));
            }
        }

        // fallback קיצוני - אם אין נקודה בכלל, בחר תא פנימי
        if (goal == null || goal.equals(start)) {
            outer:
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (maze.getMatrix()[i][j] == 0 && !(i == start.getRowIndex() && j == start.getColumnIndex())) {
                        goal = new Position(i, j);
                        break outer;
                    }
                }
            }
        }

        maze.setGoalPosition(goal);

        return maze;
    }


    private void addWallsToList(int row, int col, Maze maze) {
        ArrayList<Position> newWalls = new ArrayList<>();
        int rows = maze.getRows(), cols = maze.getCols();

        if (row + 2 < rows && maze.getMatrix()[row + 2][col] == 1)
            newWalls.add(new Position(row + 1, col));
        if (row - 2 >= 0 && maze.getMatrix()[row - 2][col] == 1)
            newWalls.add(new Position(row - 1, col));
        if (col + 2 < cols && maze.getMatrix()[row][col + 2] == 1)
            newWalls.add(new Position(row, col + 1));
        if (col - 2 >= 0 && maze.getMatrix()[row][col - 2] == 1)
            newWalls.add(new Position(row, col - 1));

        Collections.shuffle(newWalls, this.rand);
        this.walls.addAll(newWalls);
    }

    private Position[] getWallSides(Position wall, Maze maze) {
        int row = wall.getRowIndex();
        int col = wall.getColumnIndex();

        if (row % 2 == 0 && col % 2 == 1) {
            int r1 = row - 1;
            int r2 = row + 1;
            if (isInBounds(r1, col, maze) && isInBounds(r2, col, maze)) {
                return new Position[]{new Position(r1, col), new Position(r2, col)};
            }
        } else if (row % 2 == 1 && col % 2 == 0) {
            int c1 = col - 1;
            int c2 = col + 1;
            if (isInBounds(row, c1, maze) && isInBounds(row, c2, maze)) {
                return new Position[]{new Position(row, c1), new Position(row, c2)};
            }
        }
        return null;
    }

    private boolean isInBounds(int row, int col, Maze maze) {
        return row >= 0 && row < maze.getRows() && col >= 0 && col < maze.getCols();
    }
}
