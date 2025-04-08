package algorithms.mazeGenerators;

/**
 *
 */
public abstract class AMazeGenerator implements IMazeGenerator {
    /**
     *
     * @param rows
     * @param cols
     * @return
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
