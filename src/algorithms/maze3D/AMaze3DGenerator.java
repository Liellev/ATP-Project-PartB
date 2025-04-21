package algorithms.maze3D;

import algorithms.mazeGenerators.IMazeGenerator;

public abstract class AMaze3DGenerator implements IMaze3DGenerator {
    /**
     *
     * @param rows
     * @param cols
     * @return
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

