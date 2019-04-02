package rione;


import rescuecore2.config.Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;



/**
 * サーバーの情報をビューワー対応形式に外部出力する.
 */
public class ServerExporter {

    private static String CONFIG_PATH = "World/config.cfg";
    private static String TIME_PATH = "Time.bin";

    public ServerExporter () {
        Utility.init();
    }

    /**
     * 設定されたConfigのパラメーターを外部出力
     *
     * @param config
     */
    public void exportConfig (Config config) {
        try {
            File file = Utility.createNewFile(CONFIG_PATH);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            Set<String> keys = config.getAllKeys();
            for (String key : keys) {
                String value = config.getValue(key);
                writer.write(String.format("%s:%s", key, value));
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 時刻名のファイルを外部出力することで経過時刻ログに残す.
     *
     * @param time
     */
    public void updateTime (int time) {
        System.out.println(String.format("Server Time: %d", time));
        File file = Utility.createNewFile(TIME_PATH);
        try {
            BinaryWriter writer = new BinaryWriter(file);
            writer.write(time);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 古いログデータをすべて消去する.
     */
    public void deleteLog () {
        Utility.deleteLog();
    }
}