package module;


public class GUI {

    public static SystemPanel SystemPanel;
    public static SelectPanel SelectPanel;
    public static PlayPanel PlayPanel;

    public static void init (SystemPanel systemPanel, SelectPanel selectPanel, PlayPanel playPanel) {
        GUI.SystemPanel = systemPanel;
        GUI.SelectPanel = selectPanel;
        GUI.PlayPanel = playPanel;
    }

    public SystemPanel getSystemPanel () {
        return SystemPanel;
    }

    public PlayPanel getPlayPanel () {
        return PlayPanel;
    }

    public SelectPanel getSelectPanel () {
        return SelectPanel;
    }
}
