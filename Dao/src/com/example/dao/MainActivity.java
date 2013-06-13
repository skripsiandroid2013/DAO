package com.example.dao;

import java.util.Collection;

import com.example.dao.DaoMaster.DevOpenHelper;

import id.ac.itats.skripsi.dao.util.parsing.GraphBuilder;
import id.ac.itats.skripsi.dao.util.parsing.model.Graph;
import id.ac.itats.skripsi.dao.util.parsing.model.Vertex;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private static final String TAG = "DAO";

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DevOpenHelper helper;
    private DaoSession daoSession;
    private tb_nodeDao nodeDao;
    private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//GreenDao
		helper = new DaoMaster.DevOpenHelper(this, "nodedb", null);
      
		db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        nodeDao = daoSession.getTb_nodeDao();
        
        
        //GraphBuilder
        GraphBuilder builder;
        String path = "file://" +Environment.getExternalStorageDirectory().getPath()+ "/testmap.osm";
        Log.d(TAG, path);
        
		try {
			builder = new GraphBuilder(path);
			Graph graph = builder.getGraph();
			Log.d(TAG, ""+graph.getSize());
			
		
			for (Vertex vertex : graph.getVertexs()){
				
				tb_node tnode = new tb_node(null, vertex.getNode().id,vertex.getNode().lon, vertex.getNode().lat);
				nodeDao.insert(tnode);
				Log.d(TAG, "Inserted new note, ID: " + tnode.getId_node());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
