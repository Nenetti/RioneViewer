package reader;

import java.io.*;
import java.util.HashMap;

import info.WorldInfo;
import module.Utility;

public class Config_Reader {
	
	
	
	
	private HashMap<String, String> configMap;
	private String map;

	
	
	public Config_Reader() throws Exception{
		this.configMap=new HashMap<>();
		setup();
	}
	
	
	private void setup() {
		try {
			File file=new File("Data/World/config.cfg");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line=reader.readLine())!=null) {
				String[] split=line.split(":");
				if(split.length==2) {
					this.configMap.put(split[0], split[1]);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setMapName();
	}
	
	private void setMapName() {
		String[] split=getValue("gis.map.dir").split("/");
		if(split[split.length-1].equals("map")) {
			this.map=split[split.length-2];
		}else {
			this.map=split[split.length-1];
		}
	}
	
	
	public String getValue(String key) {
		return this.configMap.get(key);
	}
	
	public String getMap() {
		return map;
	}
	
	
	
	
	
}