package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is for a server that solves mazes.
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {


    public Configurations config;
    String tempDirectoryPath = System.getProperty("java.io.tmpdir");

    /**
     * Default constructor.
     */
    public ServerStrategySolveSearchProblem(){
        this.config=Configurations.getInstance();
    }

    /**
     * This method is for choosing the algorithm to solve a maze.
     * @param sMaze the maze that is needed to be solved.
     * @return the solution of the maze.
     */
    private Solution searchingAlgorithmSelection(SearchableMaze sMaze){
        String maze_searching_strategy= config.getProperty("mazeSearchingAlgorithm");
        Solution solution=null;

        switch (maze_searching_strategy){
            case("BestFirstSearch"):
                BestFirstSearch best=new BestFirstSearch();
                solution=best.solve(sMaze);
                break;

            case ("BreadthFirstSearch"):
                BreadthFirstSearch bfs=new BreadthFirstSearch();
                solution=bfs.solve(sMaze);
                break;

            case ("DepthFirstSearch"):
                DepthFirstSearch dfs=new DepthFirstSearch();
                solution=dfs.solve(sMaze);
                break;

        }
        return solution;
    }

    /**
     * This method is for creating am ID for the maze.
     * @param maze_byte the maze in bytes.
     * @return String that represent the maze ID.
     */
    public String hashMaze(byte[] maze_byte)  {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashcode=md.digest(maze_byte);
            BigInteger bigInt=new BigInteger(1,hashcode);
            return bigInt.toString(16);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("hash algo not found",e);
        }
    }

    /**
     * This method to solve a maze and send the solution to client.
     * @param inFromClient the input stream from client -the maze.
     * @param outToClient the output stream to client - the solution.
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            Maze maze = (Maze) fromClient.readObject();
            byte[] maze_byte=maze.toByteArray();
            String mazeHash=hashMaze(maze_byte);
            Solution solution;
            File sol_file=new File(tempDirectoryPath,mazeHash+".sol");

            if(sol_file.exists()){ //if solution is already done before
                ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(sol_file));
                solution=(Solution)objectInputStream.readObject();
            }
            else{
                ObjectOutputStream solution_output=new ObjectOutputStream(new FileOutputStream(sol_file));
                SearchableMaze s_maze=new SearchableMaze(maze);
                solution=searchingAlgorithmSelection(s_maze);
                solution_output.writeObject(solution);
            }

            toClient.writeObject(solution);
            toClient.flush();
            fromClient.close();
            toClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
