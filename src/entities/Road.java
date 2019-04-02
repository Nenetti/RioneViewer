package entities;


import java.util.HashMap;

import entities.Category.Type;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;



public class Road extends Area {


    private HashMap<Integer, Blockade[]> blockades;
    private HashMap<Integer, Double> blockadeSurfaces;
    private double surface;

    public Road (Type classType, int entityID, int x, int y, int[] apexes, Blockade[] blockades) {
        super(classType, entityID, x, y, apexes);
        this.blockades = new HashMap<>();
        this.blockades.put(1, blockades);
        this.blockadeSurfaces = new HashMap<>();
//		setBlockadeSurfaces(0, calcBlockadeSurface(0));
        this.surface = calcSurface(createArea(apexes));
    }

    public void update (int time, Blockade[] blockades) {
        this.blockades.put(time, blockades);
        checkBlockade(time, blockades);
//		setBlockadeSurfaces(time, calcBlockadeSurface(time));
    }

    private void checkBlockade (int time, Blockade[] blockades) {
        if ( isTimeExist(time - 1) ) {
            Blockade[] array = getBlockades().get(time - 1);
            for (Blockade blockade : blockades) {
                if ( !isContains(array, blockade) ) {
                    //出現
                    blockade.setGenerateTime(time);
                }
            }
            for (Blockade blockade : array) {
                if ( !isContains(blockades, blockade) ) {
                    //消滅
                    blockade.setDeleteTime(time);
                }
            }
        }
    }

    private boolean isContains (Blockade[] blockades, Blockade blockade) {
        for (Blockade bl : blockades) {
            if ( bl.getEntityID() == blockade.getEntityID() ) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected boolean isTimeExist (int time) {
        return getBlockades().containsKey(time);
    }


    public void setBlockadeSurfaces (int time, double surface) {
        this.blockadeSurfaces.put(time, surface);
    }

    public double getBlockadeSurfaces (int time) {
        return this.blockadeSurfaces.get(time);
    }

    public HashMap<Integer, Blockade[]> getBlockades () {
        return blockades;
    }

    public Blockade[] getBlockades (int time) {
        return blockades.get(time);
    }


    private java.awt.geom.Area createArea (int[] apexes) {
        int[] apexX = new int[apexes.length / 2];
        int[] apexY = new int[apexes.length / 2];
        for (int i = 0; i < apexes.length; i += 2) {
            apexX[i / 2] = apexes[i];
            apexY[i / 2] = apexes[i + 1];
        }
        return new java.awt.geom.Area(new java.awt.Polygon(apexX, apexY, apexX.length));
    }


//	private double calcBlockadeSurface(int time) {
//		Blockade[] blockades=getBlockades(time);
//		java.awt.geom.Area areas=new java.awt.geom.Area();
//		for(Blockade blockade:blockades) {
//			if(blockade.getApexes(time)!=null) {
//				areas.add(createArea(blockade.getApexes(time)));
//			}
//		}
//		return calcSurface(areas);
//	}

    public double getBlockadesPer (int time) {
        double per = getBlockadeSurfaces(time) / this.surface;
        return per > 1 ? 1 : per;
    }

    public double getSurface () {
        return surface;
    }

}