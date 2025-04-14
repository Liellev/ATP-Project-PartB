
package test;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;
import java.util.ArrayList;

public class RunSearchOnMaze {
    public static void main(String[] args) {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(5, 5);
        System.out.println(String.format("Start Position: %s", maze.getStartPosition())); // format "{row,column}"
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition())); // format "{row,column}"
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        //solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        //solveProblem(searchableMaze, new BestFirstSearch());
    }
    private static <AState> void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
//Solve a searching problem with a searcher
        long before=System.currentTimeMillis();
        Solution solution = searcher.solve(domain); System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
        long after=System.currentTimeMillis();
        long diff= after-before;
        System.out.println(diff);
//Printing Solution Path
                System.out.println("Solution path:");

        ArrayList<AState> solutionPath = (ArrayList<AState>) solution.getSolutionPath();
        if(solutionPath==null){
            System.out.println("solution is null");
            return;
        }
        int size=solutionPath.size();
        System.out.println(size);
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
        }
    }
}