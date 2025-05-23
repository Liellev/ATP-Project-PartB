package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class is for decompressing input streams.
 * it will use us to read the compressed object.
 * This class extends class InputStream.
 */
public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    /**
     * Constructor with parameters.
     * @param in represents the input stream
     */
    public MyDecompressorInputStream(InputStream in){
        this.in=in;
    }

    /**
     * This method reads byte array to the input stream.
     * @param dest   the buffer into which the data is read.
     * @return int that represent the value of the given byte array.
     * @throws IOException
     */
    @Override
    public int read(byte[] dest) throws IOException {
        byte[] compressed = in.readAllBytes();

        if (compressed.length < 12) {
            System.arraycopy(compressed, 0, dest, 0, compressed.length);
            return compressed.length;
        }

        System.arraycopy(compressed, 0, dest, 0, 12);

        int destIndex = 12;
        for (int i = 12; i < compressed.length; i++) {
            byte b = compressed[i];
            for (int bit = 0; bit < 8 && destIndex < dest.length; bit++) {
                int value = (b >> (7 - bit)) & 1;
                dest[destIndex++] = (byte) value;
            }
        }

        return destIndex;
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
}
