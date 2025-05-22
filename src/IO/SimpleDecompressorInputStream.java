package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;


    public SimpleDecompressorInputStream(InputStream in) {
        this.in=in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

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
