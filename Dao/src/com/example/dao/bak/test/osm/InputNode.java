package com.test.osm;

import java.util.Set;

public class InputNode {
	public static void main(String[] args) throws Exception {
		OSM osm = OSMParser.parse("data/surabaya.osm");
		Set<OSMNode> allTempat = osm.getNodes();
		for (OSMNode tempat : allTempat) {
			System.out.println("Node : " + tempat.id);
			System.out.println("Point : "+tempat.lat +","+tempat.lon);
		}
	}
}
