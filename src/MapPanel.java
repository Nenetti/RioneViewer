import config.Config;
import entities.*;
import info.WorldInfo;
import material.Material;
import module.GUI;
import entities.Category.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;



public class MapPanel extends JPanel {

    private Graphics2D graphics;

    public void paintComponent (Graphics g) {

        // Edge情報が必要な可能性あり
        // 建物内での隣接区画が分からなくなっているため

        graphics = (Graphics2D) g;
        graphics.setStroke(new BasicStroke(2));
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.clearRect(0, 0, Config.Width, Config.Height);
        graphics.translate(Config.Width / 2, Config.Height / 2);

        graphics.scale(Config.Scale, Config.Scale);
        graphics.translate(Config.MapX, Config.MapY);

        drawRoad();
        drawBlockade();
        drawBuilding();
        drawHumanStep();
        drawHuman();
        drawUI();
//		plusFlame = animationTime;
//		frameRate.calc();
//		animationTime = 0;
//		graphicss2D graphics = (graphicss2D) g;
//		graphics.clearRect(0, 0, mainDisplayWidth, mainDisplayHeight);
//
//		graphics.translate(+mainDisplayWidth / 2, +mainDisplayHeight / 2);
//		graphics.scale(scale / 100, scale / 100);
//		if (traceBox.isSelected()) {
//			if (agentInfoConsole.isDisplayable()) {
//				originX = -agentInfoR_Human.getX(time);
//				originY = -agentInfoR_Human.getY(time);
//			}
//		}
//		graphics.translate(originX, originY);
//
//		switch (mode) {
//		case 0:
//			drawRoad(graphics);
//			drawBuilding(graphics);
//			drawBlockade(graphics);
//			drawUserFactor(graphics);
//			drawAreaEffect(graphics);
//			drawEffectBackGround(graphics);
//			drawAgentPath(graphics);
//			drawAgent(graphics);
//			drawAgentStatus(graphics);
//			drawObjectID(graphics);
//			drawEffect(graphics);
//
//			break;
//
//		case 1:
//			drawRoad(graphics);
//			drawBuilding(graphics);
//			drawBlockade(graphics);
//			drawUserFactor(graphics);
//			drawDevelopAgent(graphics);
//			drawObjectID(graphics);
//			break;
//		}
//		if (serchConsole.isVisible()) {
//			graphics.setColor(new Color(255, 0, 0));
//			graphics.setStroke(new BasicStroke(1));
//			graphics.drawLine(searchConsoleTargetX - 5, searchConsoleTargetY - 5, searchConsoleTargetX + 5,
//					searchConsoleTargetY + 5);
//			graphics.drawLine(searchConsoleTargetX - 5, searchConsoleTargetY + 5, searchConsoleTargetX + 5,
//					searchConsoleTargetY - 5);
//		}
    }

    private void drawUI () {
    }


    private void drawBuilding () {
        if ( GUI.SelectPanel.getNormalBuilding().isSelected() && GUI.SelectPanel.getBurningBuilding().isSelected() ) {
            return;
        }
        Collection<Building> buildings = WorldInfo.getBuildings();
        for (Building building : buildings) {
            Apex apex = building.getApex();
            int[] apexX = apex.getXs();
            int[] apexY = apex.getYs();
            Type type = building.getClassType();
            Fieryness fieryness = building.getFieryness(Config.Time);
            if ( fieryness == Fieryness.UNBURNT && GUI.SelectPanel.getNormalBuilding().isSelected() ) {
                continue;
            }
            if ( fieryness != Fieryness.UNBURNT && GUI.SelectPanel.getBurningBuilding().isSelected() ) {
                continue;
            }
            switch (fieryness) {
                case UNBURNT:
                    graphics.setColor(Config.BUILDING_COLOR);
                    break;
                case HEATING:
                    graphics.setColor(Config.HEATING_COLOR);
                    break;
                case BURNING:
                    graphics.setColor(Config.BURNING_COLOR);
                    break;
                case INFERNO:
                    graphics.setColor(Config.INFERNO_COLOR);
                    break;
                case WATER_DAMAGE:
                    graphics.setColor(Config.WATER_DAMAGE_COLOR);
                    break;
                case MINOR_DAMAGE:
                    graphics.setColor(Config.MINOR_DAMAGE_COLOR);
                    break;
                case MODERATE_DAMAGE:
                    graphics.setColor(Config.MODERATE_DAMAGE_COLOR);
                    break;
                case SEVERE_DAMAGE:
                    graphics.setColor(Config.SEVERE_DAMAGE_COLOR);
                    break;
                case BURNT_OUT:
                    graphics.setColor(Config.BURNT_OUT_COLOR);
                    break;
            }
            graphics.fillPolygon(apexX, apexY, apexX.length);
            graphics.setColor(Config.EDGE_COLOR);
            graphics.drawPolygon(apexX, apexY, apexX.length);
            switch (type) {
                case Refuge:
                    drawImage(Material.Refuge, building.getX(), building.getY());
                    break;
                case GasStation:
                    drawImage(Material.GasStation, building.getX(), building.getY());
                    break;
                case FireStation:
                    drawImage(Material.FireStation, building.getX(), building.getY());
                    break;
                case AmbulanceCenter:
                    drawImage(Material.AmbulanceCenter, building.getX(), building.getY());
                    break;
                case PoliceOffice:
                    drawImage(Material.PoliceOffice, building.getX(), building.getY());
                    break;
            }
        }
    }

    private void drawRoad () {
        if ( !GUI.SelectPanel.getRoad().isSelected() ) {
            Collection<Road> roads = WorldInfo.getRoads();
            for (Road road : roads) {
                Apex apex = road.getApex();
                int[] apexX = apex.getXs();
                int[] apexY = apex.getYs();
                Type type = road.getClassType();
                graphics.setColor(Config.ROAD_COLOR);
                graphics.fillPolygon(apexX, apexY, apexX.length);
                graphics.setColor(Config.EDGE_COLOR);
                graphics.drawPolygon(apexX, apexY, apexX.length);
                switch (type) {
                    case Hydrant:
                        drawImage(Material.Hydrant, road.getX(), road.getY());
                        break;
                }
            }
        }
    }

    private void drawBlockade () {
        if ( GUI.SelectPanel.getBlockade().isSelected() ) {
            return;
        }
        Collection<Blockade> blockades = WorldInfo.getBlockades();
        for (Blockade blockade : blockades) {
            if ( blockade.isGenerated(Config.Time) ) {
                Apex apex = blockade.getApex(blockade.getGenerateTime());
                int[] apexX = apex.getXs();
                int[] apexY = apex.getYs();
                graphics.setColor(Config.REMOVE_BLOCKADE_COLOR);
                graphics.fillPolygon(apexX, apexY, apexX.length);
                graphics.setColor(Config.REMOVE_BLOCKADE_EDGE_COLOR);
                graphics.drawPolygon(apexX, apexY, apexX.length);
            }
        }
        for (Blockade blockade : blockades) {
            if ( blockade.isTimeExist(Config.Time) ) {
                Apex apex = blockade.getApex(Config.Time);
                int[] apexX = apex.getXs();
                int[] apexY = apex.getYs();
                graphics.setColor(Config.BLOCKADE_COLOR);
                graphics.fillPolygon(apexX, apexY, apexX.length);
                graphics.setColor(Config.BLOCKADE_EDGE_COLOR);
                graphics.drawPolygon(apexX, apexY, apexX.length);
            }
        }
    }

    private void drawHuman () {
        Collection<Human> humans = WorldInfo.getHumans();
        for (Human human : humans) {
            int x;
            int y;
            Step step = human.getStep(Config.Time);
            if ( step != null ) {
                x = step.getX(Config.MilliTime / 1000.0);
                y = step.getY(Config.MilliTime / 1000.0);
            } else {
                x = human.getX(Config.Time);
                y = human.getY(Config.Time);
            }
            Type type = human.getClassType();
            graphics.setColor(Color.RED);
            switch (type) {
                case AmbulanceTeam:
                    drawImage(Material.AmbulanceTeam, x, y - 25);
                    fillCircle(x, y, 5, 5, Config.AT_COLOR);
                    drawCircle(x, y, 5, 5, Color.BLACK);
                    break;
                case FireBrigade:
                    drawImage(Material.FireBrigade, x, y - 25);
                    fillCircle(x, y, 5, 5, Config.FB_COLOR);
                    drawCircle(x, y, 5, 5, Color.BLACK);
                    break;
                case PoliceForce:
                    drawImage(Material.PoliceForce, x, y - 25);
                    fillCircle(x, y, 5, 5, Config.PF_COLOR);
                    drawCircle(x, y, 5, 5, Color.BLACK);
                    break;
                case Civilian:
                    drawImage(Material.Civilian, x, y - 25);
                    fillCircle(x, y, 5, 5, Config.CIV_Color);
                    drawCircle(x, y, 5, 5, Color.BLACK);
                    break;
            }
        }
    }

    private void drawHumanStep () {
        Collection<Human> humans = WorldInfo.getHumans();
        graphics.setColor(Config.STEP_COLOR);
        for (Human human : humans) {
            Step step = human.getStep(Config.Time);
            if ( step != null ) {
                int[] xs = step.getShortXs();
                int[] ys = step.getShortYs();
                fillCircle(xs[0], ys[0], 5, 5);
                graphics.drawPolyline(xs, ys, xs.length);
            }
        }
    }

    private void drawImage (BufferedImage image, int x, int y) {
        x -= image.getWidth() / 2;
        y -= image.getHeight() / 2;
        graphics.drawImage(image, null, x, y);
    }

    private void drawCircle (int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        drawCircle(x, y, width, height);
    }

    private void drawCircle (int x, int y, int width, int height) {
        x -= width / 2;
        y -= height / 2;
        graphics.drawArc(x, y, width, height, 0, 360);
    }

    private void fillCircle (int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        fillCircle(x, y, width, height);
    }

    private void fillCircle (int x, int y, int width, int height) {
        x -= width / 2;
        y -= height / 2;
        graphics.fillArc(x, y, width, height, 0, 360);
    }

}