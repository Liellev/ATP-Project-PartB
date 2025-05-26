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
     */
    @Override
    public int read(byte[] dest) throws IOException {
        byte[] compressed = in.readAllBytes();
        decompress(compressed, dest);
        return dest.length;
    }

    /**
     * Decompresses a packed byte array into original binary form
     * @param compressed compressed byte array from input stream
     * @param dest       decompressed output buffer
     */
    private void decompress(byte[] compressed, byte[] dest) {
        if (compressed.length < 12) {
            System.arraycopy(compressed, 0, dest, 0, compressed.length);
            return;
        }

        System.arraycopy(compressed, 0, dest, 0, 12);  // header

        int destIndex = 12;
        for (int i = 12; i < compressed.length; i++) {
            byte b = compressed[i];
            for (int bit = 0; bit < 8 && destIndex < dest.length; bit++) {
                dest[destIndex++] = (byte) ((b >> (7 - bit)) & 1);
            }
        }
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
