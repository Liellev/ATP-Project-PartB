package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in){
        this.in=in;
    }

    public  int read(byte[] b) throws IOException{
        int index = 0;
        int symbol = 0; // מתחילים מ-0

        int count;
        while ((count = in.read()) != -1 && index < b.length) {
            for (int i = 0; i < count && index < b.length; i++) {
                b[index++] = (byte) symbol;
            }
            symbol = 1 - symbol; // מעבר ל־0 או 1
        }

        return index;
    }

}
