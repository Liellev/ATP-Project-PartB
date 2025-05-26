package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is for compressing output streams.
 * it will use us to reduce memory use with mazes.
 * This class extends class OutputStream.
 */
public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;


    /**
     * Constructor with parameters.
     * @param out represents the output stream
     */
    public MyCompressorOutputStream(OutputStream out){
        this.out=out;
    }

    /**
     * This method writes int to the output stream.
     * @param b   the {@code byte}.
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
        this.out.write(b);

    }

    /**
     * This method writes byte[] to the output stream.
     * @param data   the data, byte array.
     * @throws IOException
     */
    @Override
    public void write(byte[] data) throws IOException {
        if (data == null || data.length <= 12) {
            out.write(data);
            return;
        }

        out.write(data, 0, 12); // write header
        byte[] compressed = compress(data, 12);
        out.write(compressed);
    }

    /**
     * Compresses binary byte array into packed bytes starting from offset.
     * @param data  byte array to be compressed
     * @param offset
     * @return compressed byte array
     */
    private byte[] compress(byte[] data, int offset) {
        int length = data.length - offset;
        int numPackedBytes = (int) Math.ceil(length / 8.0);
        byte[] result = new byte[numPackedBytes];

        int i = offset;
        for (int byteIndex = 0; byteIndex < numPackedBytes; byteIndex++) {
            byte packed = 0;
            for (int bit = 0; bit < 8 && i < data.length; bit++, i++) {
                if (data[i] != 0) {
                    packed |= (1 << (7 - bit));
                }
            }
            result[byteIndex] = packed;
        }
        return result;
    }
}
