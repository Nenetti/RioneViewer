package info;

import java.util.Collection;
import java.util.HashSet;

import entities.Area;
import entities.Category;
import entities.Human;
import module.geometry.Edge;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import module.Utility;

public class TrafficInfo {
	
	
	private WorldInfo worldInfo;
	private HashSet<Traffic> traffics;
	
	
	public TrafficInfo(WorldInfo worldInfo) {
		this.worldInfo=worldInfo;
		worldInfo.setTrafficInfo(this);
		this.traffics=new HashSet<>();
		setup();
	}
	
	public void setup() {
		Collection<Area> areas=worldInfo.getAreaMap().values();
		
		HashSet<Area> noneNodes=new HashSet<>();
		HashSet<Area> openNodes=new HashSet<>();
		HashSet<Integer> closeNodes=new HashSet<>();
		
		openNodes.add(areas.toArray(new Area[1])[0]);
		
		while(!openNodes.isEmpty()) {
			for(Area area: openNodes) {
				for(int i=0;i<area.getNeighbours().length;i++) {
					Area neighbour=area.getNeighbours()[i];
					if(!closeNodes.contains(neighbour.getEntityID())) {
						Traffic traffic=new Traffic(area, neighbour, area.getEdges(neighbour));
						traffics.add(traffic);
						noneNodes.add(neighbour);
					}
				}
				closeNodes.add(area.getEntityID());
			}
			openNodes=noneNodes;
			noneNodes=new HashSet<>();
		}
	}
	
	
	public void update(int time) {
		Collection<Human> humans=worldInfo.getHumanMap().values();
		for(Area area:worldInfo.getAreaMap().values()) {
			area.setCount(0);
		}
		for(Human human:humans) {
			if(human.getAction(time)== Category.Action.ActionMove) {
				for(Area area:human.getMove_path(time)) {
					area.setCount(area.getCount()+1);
				}
			}
		}
		
		for(Traffic traffic:traffics) {
			for(Polyline line:traffic.getLines()) {
				double level=traffic.getAreaA().getCount()+traffic.getAreaB().getCount();
				if(level>5) {
					if(level>10) {
						level=10;
					}
					level/=10;
					line.setStroke(new Color(1, 1-level, 0, 1));
				}else {
					level/=5;
					line.setStroke(new Color(level, 1, 0, 1));
				}
			}
		}
	}
	
	
	public HashSet<Traffic> getTraffics() {
		return traffics;
	}
	
	
	
	
	public class Traffic {
		
		private Area areaA;
		private Area areaB;
		private Group group;
		private Polyline[] lines;
		
		public Traffic(Area areaA, Area areaB, Edge[] relyPoints) {
			this.areaA=areaA;
			this.areaB=areaB;
			this.lines=new Polyline[relyPoints.length];
			group=new Group();
			for(int i=0;i<relyPoints.length;i++) {
				Edge edge=relyPoints[i];
				Polyline line=new Polyline(Utility.toMapX(areaA.getX()), Utility.toMapY(areaA.getY()), Utility.toMapX(edge.getX()), Utility.toMapY(edge.getY()), Utility.toMapX(areaB.getX()), Utility.toMapY(areaB.getY()));
				line.setStroke(new Color(0, 1, 0, 1));
				line.setStrokeWidth(3);
				//line.getStrokeDashArray().add(2.0);
				group.getChildren().add(line);
				lines[i]=line;
			}
			//group.getChildren().add(new Circle(Utility.toMapX(areaB.getX()), Utility.toMapY(areaB.getY()), 3, Color.DARKGREEN));	
		}
		
		public Area getAreaA() {
			return areaA;
		}
		public Area getAreaB() {
			return areaB;
		}
		public Node getLine() {
			return group;
		}
		public Polyline[] getLines() {
			return lines;
		}
		
	}
	
	
	
	
} 