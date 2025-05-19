package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;
    private int lastByteCounter; //to count number of time last byte length.
    private int lastByte; //1 or 0 to represent the last byte we wrote. starts with 0

    public MyCompressorOutputStream(OutputStream out){
        this.out=out;
        this.lastByteCounter=0;
    }

    @Override
    public void write(int b) throws IOException {

    }

    @Override
    public void write(byte[] bytes) throws IOException {
        if(bytes==null || bytes.length==0){
            return;
        }
        super.write(bytes);
        //array in even places is 0, in odd places is 1
        byte current= bytes[0];

        for (int i=0;i<bytes.length;i++){
            if(i%2==0 && bytes[i]!=0){ //if even (0) and number of times shown is not 0 then
                this.lastByteCounter=bytes[i];
                out.write(this.lastByteCounter);//write num of shows
                out.write(0);//write the byte itself
            }
            if(i%2==0 && bytes[i]==0){//if even (0) and number of times shown is  0 then
                continue;
            }
        }
    }
}
