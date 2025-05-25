package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is an interface that holds the clientStrategy,
 */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}

