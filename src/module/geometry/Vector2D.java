package module.geometry;


public class Vector2D {

    private int x;
    private int y;
    private double length;

    public Vector2D (Point2D s, Point2D e) {
        this.x = e.getX() - s.getX();
        this.y = e.getY() - s.getY();
        this.length = Math.sqrt(x * x + y * y);
    }

    public Vector2D (int x, int y, double length) {
        this.x = x;
        this.y = y;
        this.length = length;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public double getLength () {
        return length;
    }
}
