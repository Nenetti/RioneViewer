package module.geometry;

public class Polygon {

    private int[] apexesX;
    private int[] apexesY;

    public Polygon (int[] apexes) {
        int length = apexes.length / 2;
        int[] apexesX = new int[length];
        int[] apexesY = new int[length];
        for (int i = 0; i < length; i++) {
            apexesX[i] = apexes[i * 2];
            apexesY[i] = apexes[i * 2 + 1];
        }
        this.apexesX = apexesX;
        this.apexesY = apexesY;
    }

    public int[] getYs () {
        return apexesY;
    }

    public int[] getXs () {
        return apexesX;
    }
}
