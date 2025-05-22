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
    public void write(byte[] b) throws IOException {
        // נניח שה-12 הראשונים הם מטא-דאטה
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);  // כתיבה רגילה
        }

        byte current = b[12];
        int count = 1;
        for (int i = 13; i < b.length; i++) {
            if (b[i] == current) {
                count++;
            } else {
                out.write(current);
                out.write(count);
                current = b[i];
                count = 1;
            }
        }
        out.write(current);
        out.write(count);
    }
}
