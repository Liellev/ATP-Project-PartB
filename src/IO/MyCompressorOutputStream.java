package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;


    public MyCompressorOutputStream(OutputStream out){
        this.out=out;
    }

    @Override
    public void write(int b) throws IOException {
        this.out.write(b);

    }

    @Override
    public void write(byte[] data) throws IOException {
        if (data == null || data.length <= 12) {
            out.write(data); // אין מה לדחוס
            return;
        }

        // כתיבת 12 הבתים הראשונים (metadata)
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
