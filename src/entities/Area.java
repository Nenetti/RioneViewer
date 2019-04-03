package entities;


import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import entities.Category.Type;
import module.geometry.Edge;
import module.geometry.Polygon;



public abstract class Area extends Entity {


    private int entityID;
    private int x;
    private int y;
    private Polygon polygon;
    private Area[] neighbours;
    private Edge[] edge;

    private int count;

    public Area (Type classType, int entityID, int x, int y, int[] apexes) {
        super(classType);
        this.entityID = entityID;
        this.x = x;
        this.y = y;
        this.polygon = new Polygon(apexes);
    }

    public void update (Area[] neighbours, Edge[] edge) {
        this.neighbours = neighbours;
        this.edge = edge;
    }

    public int getEntityID () {
        return entityID;
    }

    public int getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }

    public Polygon getPolygon () {
        return polygon;
    }

    public Area[] getNeighbours () {
        return neighbours;
    }

    public Edge[] getEdges (Area area) {
        List<Edge> list = new ArrayList<>();
        for (int i = 0; i < edge.length; i++) {
            if ( area.getEntityID() == edge[i].getNeighbour().getEntityID() ) {
                list.add(edge[i]);
            }
        }
        return list.toArray(new Edge[list.size()]);
    }

    public double calcSurface (java.awt.geom.Area area) {
        PathIterator pathIterator = area.getPathIterator(null);
        double all = 0;
        while (!pathIterator.isDone()) {
            List<double[]> points = new ArrayList<double[]>();
            while (!pathIterator.isDone()) {
                double point[] = new double[2];
                int type = pathIterator.currentSegment(point);
                pathIterator.next();
                if ( type == PathIterator.SEG_CLOSE ) {
                    if ( points.size() > 0 )
                        points.add(points.get(0));
                    break;
                }
                points.add(point);
            }
            double sum = 0;
            for (int i = 0; i < points.size() - 1; i++) {
                sum += (points.get(i)[0] * points.get(i + 1)[1]) - (points.get(i)[1] * points.get(i + 1)[0]);
            }
            all += Math.abs(sum) / 2;
        }
        return all / 1000000;
    }


    public void setCount (int count) {
        this.count = count;
    }

    public int getCount () {
        return count;
    }


}