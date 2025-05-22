package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in){
        this.in=in;
    }

    @Override
    public int read(byte[] dest) throws IOException {
        byte[] compressed = in.readAllBytes();

        if (compressed.length < 12) {
            System.arraycopy(compressed, 0, dest, 0, compressed.length);
            return compressed.length;
        }

        // מעתיקים את המטה דאטה
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

    @Override
    public int read() throws IOException {
        return this.in.read();
    }
}
