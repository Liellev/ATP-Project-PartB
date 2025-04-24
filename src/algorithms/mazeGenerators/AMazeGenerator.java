package algorithms.mazeGenerators;

/**
 * This is an abstract class that represent all kinds of mazes.
 * Using the IMazeGenerator interface.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * This method is used to generate a maze.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @return Maze object.
     */
    @Override
    public abstract Maze generate(int rows, int cols);

    /**
     * This method is used to measure the time it takes to build a maze.
     * It is identical to all mazes.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @return long that represent the time in ms.
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int cols) {
        long before=System.currentTimeMillis();
        generate(rows,cols);
        long after=System.currentTimeMillis();
        long diff= after-before;
        return diff;
    }


}
