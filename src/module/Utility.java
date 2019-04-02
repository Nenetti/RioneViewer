package module;

import java.io.File;
import java.text.DecimalFormat;

public class Utility {


	public static File toFile(String path){
		return new File(path);
	}
	
	public static String createPath(String path, String folder, String file, String extension) {
		return path+folder+"/"+file+"."+extension;
	}
	public static File createFile(String path, String folder, String file, String extension) throws Exception{
		File f=new File(path+folder+"/"+file+"."+extension);
		f.createNewFile();
		return f;
	}
	public static File createDirectory(String path, String folder, String file) throws Exception{
		File f=new File(path+folder+"/"+file);
		f.mkdirs();
		return f;
	}
	
	public static String getMemoryInfo() {
	    DecimalFormat f2 = new DecimalFormat("##.#");
	    long free = Runtime.getRuntime().freeMemory() / 1024;
	    long total = Runtime.getRuntime().totalMemory() / 1024;
	    long max = Runtime.getRuntime().maxMemory() / 1024;
	    long used = total - free;
	    double ratio = (used * 100 / (double)total);
	    String info = 
	    "Java メモリ情報 : 合計=" + getMemory(total) + "、" +
	    "使用量=" + getMemory(used) + " (" + f2.format(ratio) + "%)、" +
	    "使用可能最大="+getMemory(max);
	    return info;
	}
	
	private static String getMemory(long v) {
		long kb=v%1000;
		long mb=(v/1000)%1000;
		long gb=(v/1000000)&1000;
		return gb+"GB "+mb+"MB "+kb+"KB";
	}
	
	public static double toMapX(double v){
		return v/200;
	}
	public static double toMapY(double v){
		return -v/200;
	}
}