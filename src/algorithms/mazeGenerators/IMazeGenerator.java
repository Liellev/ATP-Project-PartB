
package algorithms.mazeGenerators;

/**
 * This is an interface to combine all necessary method to create a maze.
 */
public interface IMazeGenerator {
    /**
     * This method is used to generate a maze.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @return Maze object.
     */
    public Maze generate(int rows, int cols);

    /**
     * This method is used to measure the time it takes to build a maze.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @return long that represent the time in ms.
     */
    public long measureAlgorithmTimeMillis(int rows, int cols);
}
