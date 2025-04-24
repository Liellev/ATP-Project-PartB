package algorithms.maze3D;

/**
 * This is an interface to combine all necessary method to create a 3D maze.
 */
public interface IMaze3DGenerator {

    /**
     * This method is used to generate a 3D maze.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @param depth This is the depth of maze matrix.
     * @return Maze3D object.
     */
    public Maze3D generate(int depth, int rows, int cols);


    /**
     * This method is used to measure the time it takes to build a maze.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @param depth This is the depth of maze matrix.
     * @return long that represent the time in ms.
     */
    public long measureAlgorithmTimeMillis(int depth,int rows, int cols);
}
