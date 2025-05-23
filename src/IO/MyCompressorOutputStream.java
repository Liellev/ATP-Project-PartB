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

        out.write(data, 0, 12);

        int i = 12;
        while (i < data.length) {
            byte packedByte = 0;
            for (int bit = 0; bit < 8 && i < data.length; bit++, i++) {
                if (data[i] != 0) {
                    packedByte |= (1 << (7 - bit));
                }
            }
            out.write(packedByte);
        }
    }

}
