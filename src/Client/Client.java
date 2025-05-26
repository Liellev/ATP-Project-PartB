package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


/**
 * This class is for the client's side in order to connect to server.
 */
public class Client {

    private final InetAddress serverIP;
    private final int serverPort;
    private final IClientStrategy strategy;

    /**
     * Constructor with parameters.
     * @param serverIP the IP of the server we want to connect to.
     * @param serverPort the port of the server we want to connect to.
     * @param strategy the strategy we want to invoke.
     */
    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    /**
     * This method is for connecting to server.
     */
    public void communicateWithServer(){
        try {
            Socket serverSocket = new Socket(serverIP, serverPort);

            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
