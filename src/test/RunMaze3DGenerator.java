package test;

import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.Position3D;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMazeGenerator;

public class RunMaze3DGenerator {
    public static void main(String[] args) {

        testMaze3DGenerator(new MyMaze3DGenerator());
    }
    private static void testMaze3DGenerator(IMaze3DGenerator mazeGenerator) {
// prints the time it takes the algorithm to run
        System.out.println(String.format("3D Maze generation time(ms): %s", mazeGenerator.measureAlgorithmTimeMillis(10/*depth*/,10/*rows*/, 10/*columns*/)));
// generate another maze
        Maze3D maze = mazeGenerator.generate(10/*depth*/,10/*rows*/, 10/*columns*/);
// prints the maze
        maze.print();
// get the maze entrance
        Position3D startPosition = maze.getStartPosition();
// print the start position
        System.out.println(String.format("Start Position: %s", startPosition)); // format "{row,column}"
// prints the maze exit position
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }
}
