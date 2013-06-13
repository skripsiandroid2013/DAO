package com.example.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;



// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TB_NODE.
*/
public class tb_nodeDao extends AbstractDao<tb_node, Long> {

    public static final String TABLENAME = "TB_NODE";

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Id_node = new Property(1, String.class, "id_node", false, "ID_NODE");
        public final static Property Lon_node = new Property(2, String.class, "lon_node", false, "LON_NODE");
        public final static Property Lat_node = new Property(3, String.class, "lat_node", false, "LAT_NODE");
    };


    public tb_nodeDao(DaoConfig config) {
        super(config);
    }
    
    public tb_nodeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String sql = "CREATE TABLE " + (ifNotExists? "IF NOT EXISTS ": "") + "'TB_NODE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_NODE' TEXT," + // 1: id_node
                "'LON_NODE' TEXT," + // 2: lon_node
                "'LAT_NODE' TEXT);"; // 3: lat_node
        db.execSQL(sql);
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TB_NODE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, tb_node entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String id_node = entity.getId_node();
        if (id_node != null) {
            stmt.bindString(2, id_node);
        }
 
        String lon_node = entity.getLon_node();
        if (lon_node != null) {
            stmt.bindString(3, lon_node);
        }
 
        String lat_node = entity.getLat_node();
        if (lat_node != null) {
            stmt.bindString(4, lat_node);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public tb_node readEntity(Cursor cursor, int offset) {
        tb_node entity = new tb_node( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id_node
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // lon_node
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // lat_node
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, tb_node entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId_node(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLon_node(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLat_node(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected Long updateKeyAfterInsert(tb_node entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(tb_node entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}