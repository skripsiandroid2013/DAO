package com.example.dao.initdata;

import id.ac.itats.skripsi.dao.util.parsing.engine.OSMParser;
import id.ac.itats.skripsi.dao.util.parsing.model.Graph;
import id.ac.itats.skripsi.dao.util.parsing.osm.OSM;
import id.ac.itats.skripsi.dao.util.parsing.osm.OSMNode;
import id.ac.itats.skripsi.dao.util.parsing.osm.Way;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dao.DaoMaster;
import com.example.dao.DaoSession;
import com.example.dao.tb_nodeDao;
import com.example.dao.DaoMaster.DevOpenHelper;

public class DataBuilder {
	private OSM osmData;
	private Set<OSMNode> nodeAwalAkhir = new HashSet<OSMNode>();
	private Graph graph = new Graph();

	// GreenDao
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DevOpenHelper helper;
	private DaoSession daoSession;
	private tb_nodeDao nodeDao;
	private Cursor cursor;

	public DataBuilder(String file) throws Exception {
		osmData = OSMParser.parse(file);
		nodeAwalAkhir = getNodeAwalAkhir();

		//TODO fix to activity ziez!
		helper = new DaoMaster.DevOpenHelper(this, "nodedb", null);
	
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		nodeDao = daoSession.getTb_nodeDao();

	
		
	}

	public void initData(SQLiteDatabase db) {
		for (Way way : this.osmData.getWays()) {
			if (((way.getVisible() == null) || (way.getVisible().equals("true")))
					&& (way.isAccessibleByCar())) {

				OSMNode previousNode = null;
				int previousPosition = -1;
				int position = 0;

				for (OSMNode currentNode : way.getNodes()) {

					if (nodeAwalAkhir.contains(currentNode)) {
						if (previousNode != null) {
							// jarak antar node
							double jarak = way.getWayPartLength(
									previousPosition, position + 1);

							graph.addEdge(previousNode, currentNode, jarak,
									way.getId());
							
//							 tb_node tnode = new tb_node(null, node.id, node.lon, node.lat);
						     //    nodeDao.insert(tnode);

						}
						previousNode = currentNode;
						previousPosition = position;
					}
					position++;
				}
			}
		}


	}

	public Graph getGraph() {
		return graph;
	}

	public Set<OSMNode> getNodesInGraph() {
		return nodeAwalAkhir;
	}

	private Set<OSMNode> getNodeAwalAkhir() {
		Set<OSMNode> result = new HashSet<OSMNode>();
		for (Way way : osmData.getWays()) {
			if (((way.getVisible() == null) || (way.getVisible().equals("true")))
					&& (way.isAccessibleByCar())) {
				List<OSMNode> nodes = way.getNodes();
				if (nodes.size() > 0) {
					result.add(nodes.get(0));
					result.add(nodes.get(nodes.size() - 1));
				}
			}
		}
		return result;
	}

	/*
	 * GRAPH = (V,E)
	 */
	private void initiateGraph() {
		for (Way way : this.osmData.getWays()) {
			if (((way.getVisible() == null) || (way.getVisible().equals("true")))
					&& (way.isAccessibleByCar())) {

				OSMNode previousNode = null;
				int previousPosition = -1;
				int position = 0;

				for (OSMNode currentNode : way.getNodes()) {

					if (nodeAwalAkhir.contains(currentNode)) {
						if (previousNode != null) {
							// jarak antar node
							double jarak = way.getWayPartLength(
									previousPosition, position + 1);

							graph.addEdge(previousNode, currentNode, jarak,
									way.getId());

						}
						previousNode = currentNode;
						previousPosition = position;
					}
					position++;
				}
			}
		}

	}

}
