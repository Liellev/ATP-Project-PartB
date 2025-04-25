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
    void timeToSolve(){
        BestFirstSearch best = new BestFirstSearch();
        IMazeGenerator mg = new MyMazeGenerator();

        long t1 = System.currentTimeMillis();
        Maze maze = mg.generate(1000, 1000);
        long t2 = System.currentTimeMillis();
        long generationTime = t2 - t1;

        SearchableMaze searchableMaze = new SearchableMaze(maze);

        long t3 = System.currentTimeMillis();
        Solution s = best.solve(searchableMaze);
        long t4 = System.currentTimeMillis();
        long solvingTime = t4 - t3;
        assertNotNull(s);
        assertTrue(generationTime<60000);
        assertTrue(solvingTime<60000);
    }

    @Test
    void rowsAndColmsAreEqualSimple() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        IMazeGenerator mg = new SimpleMazeGenerator();
        Maze maze = mg.generate(20, 20);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Solution s = best.solve(searchableMaze);
        assertTrue(best.getNumberOfNodesEvaluated() > 0, "Number of nodes evaluated should be greater than 0");
        assertNotNull(s , "Solution should be not null");
        assertFalse(s.getSolutionPath().isEmpty(), "Solution path should be not empty");
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

    @Test
    void checkNullInSearchableMaze() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        Exception e = assertThrows(NullPointerException.class, () -> {
            Solution s =  best.solve(null);
        });
        assertEquals("Null searchable", e.getMessage());
       assertTrue(best.getNumberOfNodesEvaluated() == 0, "Number of nodes evaluated should be greater than 0");


    }
    @Test
    void processNeighborForDirectStep() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        IMazeGenerator mg = new EmptyMazeGenerator();
        Maze maze = mg.generate(5, 5);

        maze.setStartPosition(new Position(2, 2));
        maze.setGoalPosition(new Position(4, 4));

        SearchableMaze searchableMaze = new SearchableMaze(maze);
        best.solve(searchableMaze);

        MazeState start = new MazeState(maze.getStartPosition());
        MazeState next = new MazeState(new Position(2, 3));

        start.setState(start.getMazeStatePosition().toString());
        next.setState(next.getMazeStatePosition().toString());

        best.processNeighbor(start, next,searchableMaze);
        assertEquals(start, next.getCameFrom(), "CameFrom should be the start state");
        assertEquals(10.0, next.getCost(), "Cost for direct step should be 10");
    }

    @Test
    public void  processNeighborDiagonalStepCost() throws Exception {
        BestFirstSearch best = new BestFirstSearch();
        IMazeGenerator mg = new EmptyMazeGenerator();
        Maze maze = mg.generate(5, 5);

        maze.setStartPosition(new Position(2, 2));
        maze.setGoalPosition(new Position(4, 4));

        SearchableMaze searchableMaze = new SearchableMaze(maze);
        best.solve(searchableMaze);

        MazeState start = new MazeState(maze.getStartPosition());
        MazeState nextDiagonal = new MazeState(new Position(3, 3));

        start.setState(start.getMazeStatePosition().toString());
        nextDiagonal.setState(nextDiagonal.getMazeStatePosition().toString());

        best.processNeighbor(start, nextDiagonal,searchableMaze);
        assertEquals(start, nextDiagonal.getCameFrom(), "CameFrom should be the start state");
        assertEquals(15.0, nextDiagonal.getCost(), "Cost for diagonal step should be 15");
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
        assertNull(s , "Solution should not be null");   }
}
