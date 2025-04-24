package algorithms.maze3D;

import algorithms.maze3D.IMaze3DGenerator;

/**
 * This is an abstract class that represent all kinds of 3D mazes.
 * Using the IMaze3DGenerator interface.
 */
public abstract class AMaze3DGenerator implements IMaze3DGenerator {

    /**
     * This method is used to generate a 3D maze.
     * @param depth This is the depth of maze matrix.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @return Maze3D object
     */
    @Override
    public abstract Maze3D generate(int depth, int rows, int cols);

    /**
     * This method is used to measure the time it takes to build a 3D maze.
     * It is identical to all 3D mazes.
     * @param depth This is the depth of the matrix.
     * @param rows This is the rows number in maze matrix.
     * @param cols This is the columns number in maze matrix.
     * @return long that represent the time in ms.
     */
    @Override
    public long measureAlgorithmTimeMillis(int depth, int rows, int cols) {
        long before=System.currentTimeMillis();
        generate(depth,rows,cols);
        long after=System.currentTimeMillis();
        long diff= after-before;
        return diff;
    }

}

