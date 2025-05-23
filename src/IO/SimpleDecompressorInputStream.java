package IO;

import java.io.IOException;
import java.io.InputStream;


/**
 * This class is for decompressing input streams.
 * it will use us to read the compressed object (with the method of counting 0 and 1).
 * This class extends class InputStream.
 */
public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;


    /**
     * Constructor with parameters.
     * @param in represents the input stream
     */
    public SimpleDecompressorInputStream(InputStream in) {
        this.in=in;
    }

    /**
     * This methood reads the next byte in input stream.
     * @return int that represent the valur of the byte from 0 to 255.
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
        return this.in.read();
    }


    /**
     * This method reads byte array (with the method of counting 0 and 1) to the input stream.
     * @param b   the buffer into which the data is read.
     * @return int that represent the value of the given byte array.
     * @throws IOException
     */
    @Override
    public int read(byte[] b) throws IOException {
        int index = 0;

        for (int i = 0; i < 12; i++) {
            b[index++] = (byte) in.read();
        }

        int length;
        byte currentValue = 0;
        while ((length = in.read()) != -1 && index < b.length) {
            for (int i = 0; i < length && index < b.length; i++) {
                b[index++] = currentValue;
            }
            currentValue = (byte) (1 - currentValue); // flip between 0 and 1
        }

        return index;
    }

}
