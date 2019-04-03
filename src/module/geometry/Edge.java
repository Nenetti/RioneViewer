package module.geometry;


import entities.Area;



public class Edge {

    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private Area neighbour;

    public Edge (int startX, int startY, int endX, int endY, Area neighbour) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.neighbour = neighbour;
    }

    public int getX () {
        return (startX + endX) / 2;
    }

    public int getY () {
        return (startY + endY) / 2;
    }

    public Area getNeighbour () {
        return neighbour;
    }
}
