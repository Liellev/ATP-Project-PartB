package algorithms.mazeGenerators;

public abstract class AMazeGenerator {
    public AMazeGenerator() {
        System.out.println("AMazeGenerator constructor called");
    }

    public void printSomething() {
        System.out.println("Hello from AMazeGenerator!");
    }

    public abstract void generateMaze();
}
