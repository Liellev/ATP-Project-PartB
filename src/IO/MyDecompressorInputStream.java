package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in){
        this.in=in;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int index = 0;

        // read header
        for (int i = 0; i < 12; i++) {
            b[index++] = (byte) in.read();
        }

        int value;
        while ((value = in.read()) != -1) {
            int count = in.read();
            for (int i = 0; i < count; i++) {
                if (index >= b.length) return index;
                b[index++] = (byte) value;
            }
        }

        return index;
    }


    @Override
    public int read() throws IOException {
        return 0;
    }
}
