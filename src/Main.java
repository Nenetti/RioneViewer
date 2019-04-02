import info.WorldInfo;
import reader.LogReader;



public class Main {


    public static void main (String[] args) {

        RioneViewer4 RioneViewer = new RioneViewer4();
        WorldInfo.init();
        LogReader reader = new LogReader();
        reader.read();

        RioneViewer.setup();

    }
}