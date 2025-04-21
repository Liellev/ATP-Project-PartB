package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;

public interface IMaze3DGenerator {
    public Maze3D generate(int depth, int rows, int cols);

    /**
     *
     * @param rows
     * @param cols
     * @return
     */
    public long measureAlgorithmTimeMillis(int depth,int rows, int cols);
}
