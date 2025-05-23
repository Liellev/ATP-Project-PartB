package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            String maze_file="MazeFromClient";
            OutputStream outputStream= new FileOutputStream(maze_file);

            Maze maze = (Maze) fromClient.readObject();
            byte[] maze_byte=maze.toByteArray();
            SearchableMaze s_maze=new SearchableMaze(maze);
            BestFirstSearch algorithm= new BestFirstSearch();
            Solution solution=algorithm.solve(s_maze);

            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(outputStream);
            compressorOutputStream.write(maze_byte);
            InputStream infile= new FileInputStream(maze_file);
            byte[] compMazeByte=infile.readAllBytes();
            toClient.writeObject(compMazeByte);
            toClient.flush();

            fromClient.close();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
