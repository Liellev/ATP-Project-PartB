package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

/**
 * This class is for a server that generates mazes.
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {

    public Configurations config;

    /**
     * Default constructor.
     */
    public ServerStrategyGenerateMaze(){
        this.config=Configurations.getInstance();
    }


    /**
     * This method is for choosing the algorithm to create a maze.
     * @param rows num of rows of maze.
     * @param cols num of cols of maze.
     * @return the created maze
     */
    private Maze generateMazeSelection(int rows, int cols){
        String maze_geberte_strategy= config.getProperty("mazeGeneratingAlgorithm");
        AMazeGenerator mazeGenerator;
        Maze maze=null;
        switch (maze_geberte_strategy){
            case("MyMazeGenerator"):
                mazeGenerator=new MyMazeGenerator();
                maze=mazeGenerator.generate(rows,cols);
                break;

            case ("EmptyMazeGenerator"):
                mazeGenerator=new EmptyMazeGenerator();
                maze=mazeGenerator.generate(rows,cols);
                break;

            case ("SimpleMazeGenerator"):
                mazeGenerator=new SimpleMazeGenerator();
                maze=mazeGenerator.generate(rows,cols);
                break;

        }
        return maze;
    }

    /**
     * This method to create a maze and send it to client.
     * @param inFromClient the input stream from client
     * @param outToClient the output stream to client.
     */
    @Override
    public void serverstrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();


            String maze_file="MazeForClient";
            OutputStream outputStream= new FileOutputStream(maze_file);

            int[] maze_param = (int[]) fromClient.readObject();
            int rows=maze_param[0];
            int cols=maze_param[1];

            Maze my_maze=generateMazeSelection(rows,cols);
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
