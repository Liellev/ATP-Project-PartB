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
    public void write(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) return;

        int current = bytes[0]; // מתחילים מ-0 או 1
        int count = 0;

        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == current) {
                count++;
                if (count == 255) {
                    out.write(255);
                    count = 0;
                    // מחליפים ערך לספירה אפסית, כלומר: נרשום 0 הופעות מהערך ההפוך
                    current = 1 - current;
                    out.write(0); // מייצג 0 הופעות של הערך ההפוך
                }
            } else {
                out.write(count);
                count = 1;
                current = bytes[i];
            }
        }

        // כותבים את הספירה האחרונה
        out.write(count);
    }

}
