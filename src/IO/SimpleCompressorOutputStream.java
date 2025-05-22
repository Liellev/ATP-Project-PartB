package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     *
     * @param out
     */
    public SimpleCompressorOutputStream(OutputStream out){
        this.out=out;
    }

    /**
     *
     * @param b   the {@code byte}.
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
        this.out.write(b);

    }

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
