package rione;


import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;



public class Utility {

    private final static String TARGET = "RioneViewer4";
    private static String PATH;

    public static void init () {
        if ( PATH != null ) {
            return;
        }
        try {
            PATH = getInstalledPath(TARGET) + "/Data";
            System.out.println(String.format("ログは %s に保存されます.", PATH));
        } catch (FileNotFoundException e) {
            System.err.println(String.format("ディレクトリ <%s> が見つかりません.", TARGET));
            System.err.println("RioneViewerのバージョンと合わせて確認してください.");
            System.exit(1);
        }
    }

    private static String getInstalledPath (String target) throws FileNotFoundException {
        File home = new File(System.getProperty("user.home"));
        ArrayDeque<File> queue = new ArrayDeque<>(Arrays.asList(home.listFiles()));
        while (!queue.isEmpty()) {
            File f = queue.poll();
            if ( f.isDirectory() && !f.isHidden() ) {
                if ( f.getName().equals(target) ) {
                    return f.getPath();
                }
                queue.addAll(Arrays.asList(f.listFiles()));
            }
        }
        throw new FileNotFoundException();
    }

    public static String toPath (String s) {
        return String.join("/", PATH, s);
    }

    public static File createNewFile (String path) {
        try {
            File file = new File(toPath(path));
            file.getParentFile().mkdirs();
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 古いログファイルをディレクトリ含めてすべて消去する.
     */
    public static void deleteLog () {
        delete(new File(Utility.PATH));
    }

    /**
     * ファイル,ディレクトリ消去の再帰処理
     *
     * @param file
     */
    private static void delete (File file) {
        if ( file.isDirectory() ) {
            for (File f : file.listFiles()) {
                if ( f.isFile() ) {
                    f.delete();
                } else {
                    delete(f);
                }
            }
        }
        file.delete();
    }

}