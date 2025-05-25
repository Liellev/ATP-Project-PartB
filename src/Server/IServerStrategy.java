package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is an interface that holds the serverStrategy,
 */
public interface IServerStrategy {
    void applyStrategy(InputStream inFromClient, OutputStream outToClient);
}