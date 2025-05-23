package Server;

import Server.IServerStrategy;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;
    public Configurations config;
    private Thread thread;


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

    public void start(){
        thread.start();
    }

    private void handleClient(Socket clientSocket) {
        try {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            System.out.println("Done handling client: " + clientSocket);
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException during client handling");
        }
    }

    public void stop() {
        System.out.println("Stopping server...");
        stop = true;
        threadPool.shutdown();
    }


}

