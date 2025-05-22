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

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;

    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;

        int threadPoolSize = loadThreadPoolSize();
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
        System.out.println("Thread pool initialized with " + threadPoolSize + " threads");
    }

    public void start() {
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
        } catch (IOException e) {
            System.out.println("IOException during server start");
        }
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

    private int loadThreadPoolSize() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
            String value = properties.getProperty("ThreadPoolSize");
            return Integer.parseInt(value);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not load ThreadPoolSize from config.properties. Using default = 5");
            return 5;
        }
    }
}

