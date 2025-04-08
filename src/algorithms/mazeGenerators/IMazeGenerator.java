package algorithms.mazeGenerators;


/**
 *
 */
public interface IMazeGenerator {
    /**
     *
     * @param rows
     * @param cols
     * @return
     */
    public Maze generate(int rows, int cols);

    /**
     *
     * @param rows
     * @param cols
     * @return
     */
    public long measureAlgorithmTimeMillis(int rows, int cols);
}
