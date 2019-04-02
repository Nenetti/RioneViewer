package module;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BinaryReader {

    private BufferedInputStream stream;
    private ByteBuffer buffer;
    private boolean isReadable;

    public BinaryReader (File file) {
        try {
            this.stream = new BufferedInputStream(new FileInputStream(file));
            byte[] bs = new byte[stream.available()];
            stream.read(bs);
            stream.close();
            this.buffer = ByteBuffer.wrap(bs);
            isReadable = true;
        } catch (Exception e) {
            isReadable = false;
        }
    }

    public int getInt () {
        return buffer.getInt();
    }

    public double getDouble () {
        return buffer.getDouble();
    }

    public long getLong () {
        return buffer.getLong();
    }

    public boolean getBoolean () {
        byte b = buffer.get();
        if ( b == 0 ) {
            return false;
        } else {
            return true;
        }
    }

    public String getString () {
        int length = buffer.getInt();
        byte[] bs = new byte[length];
        buffer.get(bs);
        return new String(bs);
    }

    public int[] getIntArray () {
        int length = buffer.getInt();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = buffer.getInt();
        }
        return array;
    }

    public double[] getDoubleArray () {
        int length = buffer.getInt();
        double[] array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = buffer.getDouble();
        }
        return array;
    }


    private void isAble () {
        if ( buffer.remaining() > 0 ) {
            this.isReadable = true;
        } else {
            this.isReadable = false;
        }
    }

    public boolean isReadable () {
        isAble();
        return this.isReadable;
    }

}