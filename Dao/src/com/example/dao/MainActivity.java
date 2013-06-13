package com.example.dao;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;

import com.example.dao.DaoMaster.DevOpenHelper;

import id.ac.itats.skripsi.dao.util.parsing.GraphBuilder;
import id.ac.itats.skripsi.dao.util.parsing.model.Graph;
import id.ac.itats.skripsi.dao.util.parsing.model.Vertex;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends ListActivity {
	private static final String TAG = "DAO";

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private tb_nodeDao nodeDao;

	private Cursor cursor;
	
	private EditText editTextLat;
	private EditText editTextLon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// GreenDao
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "nodedb", null);

		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		nodeDao = daoSession.getTb_nodeDao();
		
		

		// GraphBuilder
		GraphBuilder builder;
		String path = "file://"
				+ Environment.getExternalStorageDirectory().getPath()
				+ "/testmap.osm";
		Log.d(TAG, path);

		try {
			builder = new GraphBuilder(path);
			Graph graph = builder.getGraph();
			Log.d(TAG, "" + graph.getSize());

			for (Vertex vertex : graph.getVertexs()) {

				tb_node tnode = new tb_node(null, vertex.getNode().id,
						vertex.getNode().lon, vertex.getNode().lat);
				nodeDao.insert(tnode);
				Log.d(TAG, "Inserted new note, ID: " + tnode.getId_node());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// view
		String textColumn = tb_nodeDao.Properties.Id_node.columnName;
		String orderBy = textColumn + " COLLATE LOCALIZED ASC";
		cursor = db.query(nodeDao.getTablename(), nodeDao.getAllColumns(),
				null, null, null, null, orderBy);
		
		String[] from = { textColumn, tb_nodeDao.Properties.Lat_node.columnName , tb_nodeDao.Properties.Lon_node.columnName };
		
		int[] to = { android.R.id.text1, android.R.id.text2 };

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, from, to);
		
		setListAdapter(adapter);

		editTextLat = (EditText) findViewById(R.id.editTextLat);
		editTextLon = (EditText) findViewById(R.id.editTextLon);
		addUiListeners();

	}

	 protected void addUiListeners() {
	        editTextLat.setOnEditorActionListener(new OnEditorActionListener() {

	            @Override
	            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	                if (actionId == EditorInfo.IME_ACTION_DONE) {
	                    addNote();
	                    return true;
	                }
	                return false;
	            }
	        });

	        final View button = findViewById(R.id.buttonAdd);
	        button.setEnabled(false);
	        editTextLat.addTextChangedListener(new TextWatcher() {

	            @Override
	            public void onTextChanged(CharSequence s, int start, int before, int count) {
	                boolean enable = s.length() != 0;
	                button.setEnabled(enable);
	            }

	            @Override
	            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	            }

	            @Override
	            public void afterTextChanged(Editable s) {
	            }
	        });
	    }

	    public void onMyButtonClick(View view) {
	        addNote();
	    }

	    private void addNote() {
	    	
	    	String id = ""+Math.random()*100;
	    	
	        String lat = editTextLat.getText().toString();
	        editTextLat.setText("");
	        
	        String lon = editTextLon.getText().toString();
	        editTextLon.setText("");

	     	        
	        tb_node tnode = new tb_node(null, id,lon, lat);
			nodeDao.insert(tnode);
	        Log.d(TAG, "Inserted new note, ID: " + tnode.getId());

	        cursor.requery();
	    }

	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	    	nodeDao.deleteByKey(id);
	        Log.d("DaoExample", "Deleted note, ID: " + id);
	        cursor.requery();
	    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
