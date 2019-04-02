package material;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class Material {

    public static BufferedImage Refuge = read("Material/Refuge.png");
    public static BufferedImage Hydrant = read("Material/Hydrant.png");
    public static BufferedImage GasStation = read("Material/GasStation.png");
    public static BufferedImage AmbulanceCenter = read("Material/AmbulanceCenter.png");
    public static BufferedImage FireStation = read("Material/FireStation.png");
    public static BufferedImage PoliceOffice = read("Material/PoliceOffice.png");
    public static BufferedImage AmbulanceTeam = read("Material/AmbulanceTeam.png");
    public static BufferedImage FireBrigade = read("Material/FireBrigade.png");
    public static BufferedImage PoliceForce = read("Material/PoliceForce.png");
    public static BufferedImage Civilian = read("Material/Civilian.png");

    private static BufferedImage read (String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
