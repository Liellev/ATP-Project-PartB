package algorithms.search;

import algorithms.mazeGenerators.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    @Test
    //testing for the name of algorithm
    void getName() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        assertEquals("BestFirstSearch", best.getName());
    }

    @Test
    void rowsAndColmsAreEqual() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(20, 20);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Solution s = best.solve(searchableMaze);
        assertTrue(best.getNumberOfNodesEvaluated() > 0, "Number of nodes evaluated should be greater than 0");
        assertNotNull(s , "Solution should be not null");
        assertFalse(s.getSolutionPath().isEmpty(), "Solution path should be not empty");
    }

    @Test
    void rowsAndColmsAreNotEqual() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(100, 20);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Solution s = best.solve(searchableMaze);
        assertTrue(best.getNumberOfNodesEvaluated() > 0, "Number of nodes evaluated should be greater than 0");
        assertNotNull(s , "Solution should be not null");
        assertFalse(s.getSolutionPath().isEmpty(), "Solution path should be not empty");
    }

    @Test
    void rowsAndColmsAreOdd() throws Exception {
        BestFirstSearch best1 = new BestFirstSearch();
        IMazeGenerator mg1 = new MyMazeGenerator();
        Maze maze1 = mg1.generate(121, 121);
        SearchableMaze searchableMaze1 = new SearchableMaze(maze1);
        Solution s1 = best1.solve(searchableMaze1);
        assertTrue(best1.getNumberOfNodesEvaluated() > 0, "Number of nodes evaluated should be greater than 0");
        assertNotNull(s1 , "Solution should be not null");
        assertFalse(s1.getSolutionPath().isEmpty(), "Solution path should be not empty");
    }

    @Test
    void rowsAndColmsAre0() throws Exception {
        BestFirstSearch best1 = new BestFirstSearch();
        IMazeGenerator mg1 = new MyMazeGenerator();
        Maze maze1 = mg1.generate(0, 0);
        SearchableMaze searchableMaze1 = new SearchableMaze(maze1);
        Solution s1 = best1.solve(searchableMaze1);
        assertTrue(best1.getNumberOfNodesEvaluated() > 0, "Number of nodes evaluated should be greater than 0");
        assertNotNull(s1 , "Solution should be not null");
        assertFalse(s1.getSolutionPath().isEmpty(), "Solution path should be not empty");
    }

//    @Test
//    void checkNullInSearchableMaze() throws Exception {
//        BestFirstSearch best = new BestFirstSearch();
//        IMazeGenerator mg = new MyMazeGenerator();
//        Maze maze = null;
//        SearchableMaze searchableMaze = new SearchableMaze(maze);
//        Solution s = best.solve(searchableMaze);
//        assertTrue(best.getNumberOfNodesEvaluated() > 0, "Number of nodes evaluated should be greater than 0");
//        assertNotNull(s , "Solution should be not null");
//        assertFalse(s.getSolutionPath().isEmpty(), "Solution path should be not empty");
//    }

    @Test
    void processNeighbor() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        IMazeGenerator mg = new EmptyMazeGenerator();
        Maze maze = mg.generate(5, 5);

        maze.setStartPosition(new Position(2,2));
        maze.setGoalPosition(new Position(4,4));

        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Solution s = best.solve(searchableMaze);

        MazeState start = new MazeState(maze.getStartPosition());
        //checking the cost of direct step
        MazeState next = new MazeState(new Position(2,3));
        assertNotNull(s , "Solution should be not null");

        //checking the cost of direct step
        best.processNeighbor(start,next);
        assertEquals(10.0,next.getCost());

        //checking the cost of diagonal step
        MazeState nextDiagonal = new MazeState(new Position(3,3));
        best.processNeighbor(start,nextDiagonal);
        assertEquals(start,nextDiagonal.getCameFrom());
        best.processNeighbor(start, nextDiagonal);
        assertEquals(15.0, nextDiagonal.getCost());
    }


    @Test
    void checkingNull() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
            Solution s = best.solve(null);
            assertNull(s , "Solution should null");

    }
   @Test
    void checkingSolveWithoutSolution() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        IMazeGenerator mg = new EmptyMazeGenerator();
        Maze maze = mg.generate(5, 5);
        for (int i=0;i<5;i++) {
            for (int j=0;j<5;j++) {
                maze.getMatrix()[i][j]=1;
            }
        }
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Solution s  = best.solve(searchableMaze);
       //assertTrue(s.getSolutionPath().isEmpty(), "Expected empty path when maze is completely blocked");
   }
}
