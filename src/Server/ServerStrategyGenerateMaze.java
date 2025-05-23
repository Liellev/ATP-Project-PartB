package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    public Configurations config;

    public ServerStrategyGenerateMaze(){
        this.config=Configurations.getInstance();
    }


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
