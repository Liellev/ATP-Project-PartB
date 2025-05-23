package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            String maze_file="MazeForClient";
            OutputStream outputStream= new FileOutputStream(maze_file);

            int[] maze_param = (int[]) fromClient.readObject();
            int rows=maze_param[0];
            int cols=maze_param[1];
            MyMazeGenerator maze= new MyMazeGenerator();
            Maze my_maze=maze.generate(rows,cols);
            byte[] maze_byte=my_maze.toByteArray();
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
