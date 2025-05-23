package test;
import Client.IClientStrategy;
import Server.*;
import Client.*;
import java.io.*;
import IO.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class RunCommunicateWithServers {
    public static void main(String[] args) {
//Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        //Server stringReverserServer = new Server(5402, 1000, newServerStrategyStringReverser());
//Starting servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();


//stringReverserServer.start();
//Communicating with servers
        CommunicateWithServer_MazeGenerating();
        CommunicateWithServer_SolveSearchProblem();
//        CommunicateWithServer_StringReverser();
//Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
//        stringReverserServer.stop();
    }
    private static void CommunicateWithServer_MazeGenerating() {

        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy()
            {

                        @Override
                        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                                toServer.flush();
                                int[] mazeDimensions = new int[]{50, 50};
                                toServer.writeObject(mazeDimensions); //send maze dimensions to server
                                toServer.flush();
                                byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server
                                InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                                byte[] decompressedMaze = new byte[50*50+12 /*CHANGESIZE ACCORDING TO YOU MAZE SIZE*/];
                                //allocating byte[] for the decompressedmaze -
                                is.read(decompressedMaze);
                                Maze maze = new Maze(decompressedMaze);
                                maze.print();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException  e) {
            e.printStackTrace();
        }
    }
    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new
                    IClientStrategy() {
                        @Override
                        public void clientStrategy(InputStream inFromServer,
                                                   OutputStream outToServer) {
                            try {
                                ObjectOutputStream toServer = new
                                        ObjectOutputStream(outToServer);
                                ObjectInputStream fromServer = new
                                        ObjectInputStream(inFromServer);
                                toServer.flush();
                                MyMazeGenerator mg = new MyMazeGenerator();
                                Maze maze = mg.generate(50, 50);
                                maze.print();
                                toServer.writeObject(maze); //send maze to server
                                toServer.flush();
                                Solution mazeSolution = (Solution)
                                        fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server%s", mazeSolution));
//Print Maze Solution retrieved from the server
                                System.out.println(String.format("Solution steps:"));
                               ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                                for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                                    System.out.println(String.format("%s. %s", i,
                                            mazeSolutionSteps.get(i).toString()));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
//    private static void CommunicateWithServer_StringReverser() {
//        try {
//            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
//                        @Override
//                        public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
//                            try {
//                                BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
//                                PrintWriter toServer = new PrintWriter(outToServer);
//                                String message = "Client Message";
//                                String serverResponse;
//                                toServer.write(message + "\n");
//                                toServer.flush();
//                                serverResponse = fromServer.readLine();
//                                System.out.println(String.format("Server response:%s", serverResponse));
//                                toServer.flush();
//                                fromServer.close();
//                                toServer.close();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//            });
//            client.communicateWithServer();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        Server mazeGeneratingServer = new Server(5400, 10000, new ServerStrategyGenerateMaze());
//        //Server solveSearchProblemServer = new Server(5401, 10000, new ServerStrategySolveSearchProblem());
//
//        // מריצים את השרתים ברקע (thread נפרד)
//        new Thread(() -> mazeGeneratingServer.start()).start();
//        //new Thread(() -> solveSearchProblemServer.start()).start();
//
//        try {
//            // מעט המתנה כדי שהשרתים יתחילו להאזין (100-500 מ"ש)
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // עכשיו אפשר להתחבר לשרת
//        try (Socket socket = new Socket("127.0.0.1", 5400)) {
//            System.out.println("Connected to server!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // קריאה לפונקציות תקשורת עם השרתים
//        CommunicateWithServer_MazeGenerating();
//        //CommunicateWithServer_SolveSearchProblem();
//
//        // עצירת השרתים
//        mazeGeneratingServer.stop();
//        //solveSearchProblemServer.stop();
//    }

}
