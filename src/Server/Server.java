package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This is the server side class.
 */
public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;
    public Configurations config;
    private Thread thread;


    /**
     * Constructor with parameters.
     * @param port the port the server listens to.
     * @param listeningIntervalMS the time in MS that the server is listening.
     * @param strategy that the server executes.
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.config=Configurations.getInstance();
        this.stop=false;
        this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(config.getProperty("threadPoolSize")));
        this.thread=new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        });
        ThreadPoolExecutor executor = (ThreadPoolExecutor) threadPool;
        System.out.println("Thread pool initialized with " + executor.getCorePoolSize() + " threads");
    }

    /**
     * This method is to invoke server and
     * open it for connection to clients.
     */
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Starting server at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client accepted: " + clientSocket);

                    threadPool.execute(() -> handleClient(clientSocket));

                } catch (SocketTimeoutException e) {
                    System.out.println("Socket timeout");
                }
            }
            serverSocket.close();
            threadPool.shutdown();
        } catch (IOException e) {
            System.out.println("IOException during server start");
        }
    }

    /**
     * This method is to invoke the thread itself.
     * the server can connect to multiple clients.
     */
    public void start(){
        thread.start();
    }

    /**
     * This method is to invoke client strategy and close it at the end.
     * @param clientSocket the connection to client
     */
    private void handleClient(Socket clientSocket) {
        try {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            System.out.println("Done handling client: " + clientSocket);
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException during client handling");
        }
    }

    /**
     * This method is for stopping the server from running.
     */
    public void stop() {
        System.out.println("Stopping server...");
        stop = true;
        threadPool.shutdown();
    }


}

