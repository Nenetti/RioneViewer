package rione.viewer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class BinaryWriter {



	private BufferedOutputStream stream;



	public BinaryWriter (File file) throws Exception {
		this.stream=new BufferedOutputStream(new FileOutputStream(file));
	}

	public BinaryWriter (File file, boolean isAppend) throws Exception {
		this.stream=new BufferedOutputStream(new FileOutputStream(file, isAppend));
	}



	public void write(int i) throws Exception {
		stream.write(toByte(i));
	}

	public void write(int[] a) throws Exception{
		if(a!=null) {
			stream.write(toByte(a.length));
			for(int i=0;i<a.length;i++) {
				stream.write(toByte(a[i]));
			}
		}else {
			stream.write(toByte(0));
		}
	}

	public void write(double i) throws Exception {
		stream.write(toByte(i));
	}

	public void write(double[] a) throws Exception{
		if(a!=null) {
			stream.write(toByte(a.length));
			for(int i=0;i<a.length;i++) {
				stream.write(toByte(a[i]));
			}
		}else {
			stream.write(toByte(0));
		}
	}
	
	public void write(long a) throws Exception{
		stream.write(toByte(a));
	}

	public void write(String i) throws Exception {
		if(i!=null) {
			byte[] bs=toByte(i);
			stream.write(toByte(bs.length));
			stream.write(bs);
		}else {
			stream.write(toByte(0));
		}
	}

	public void write(boolean i) throws Exception{
		stream.write(toByte(i));
	}

	private byte[] toByte(int i) {
		return ByteBuffer.allocate(4).putInt(i).array();
	}

	private byte[] toByte(double i) {
		return ByteBuffer.allocate(8).putDouble(i).array();
	}

	private byte[] toByte(long i) {
		return ByteBuffer.allocate(8).putLong(i).array();
	}
	
	private byte[] toByte(String i) {
		return i.getBytes();
	}

	private byte toByte(boolean i) {
		if(i) {
			return 1;
		}else {
			return 0;
		}
	}

	public void flush() throws Exception{
		stream.flush();
	}
	
	public void close() throws Exception {
		stream.close();
	}

}