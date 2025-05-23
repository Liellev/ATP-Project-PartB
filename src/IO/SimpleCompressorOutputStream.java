package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is for compressing output streams.
 * it will use us to reduce memory use with mazes.
 * In this class we use a specific method for counting appearances of 0 and 1.
 * This class extends class OutputStream.
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     * Constructor with parameter.
     * @param out represents the output stream.
     */
    public SimpleCompressorOutputStream(OutputStream out){
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
     * Using counting appearances of 0 and 1 and writing it to the output stream.
     * @param b   the data, byte array.
     * @throws IOException
     */
    @Override
    public void write(byte[] b) throws IOException {
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        int index = 12;
        byte current = b[index];
        int count = 0;

        while (index < b.length) {
            if (b[index] == current) {
                count++;

                if (count == 255) {
                    out.write(count);
                    count = 0;
                    current = (byte) (1 - current);
                    out.write(0);
                }
            } else {
                out.write(count);
                count = 1;
                current = b[index];
            }
            index++;
        }

        if (count > 0) {
            out.write(count);
        }
    }
}
