package com.chrusty.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import com.chrusty.StopWatch.Message;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBControllerAdaptor {
	
	private Context context;
	private DBHelper db;
	
	public DBControllerAdaptor(Context context){
		this.context = context;
		db = new DBHelper(context);
	}
	
	public long insert(String time, String splitlaps, String date) {
		
		SQLiteDatabase database = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_TIME, time);
		values.put(DBHelper.TABLE_LAPS, splitlaps);
		values.put(DBHelper.TABLE_DATE, date);
		long id=database.insert(DBHelper.DATABASE_TABLE, null, values);
		return id;
	}

	public void delete(String id) {
		SQLiteDatabase database = db.getWritableDatabase();	 
		String deleteQuery = "DELETE FROM  "+DBHelper.DATABASE_TABLE+" where "+DBHelper.TABLE_ID+"='"+ id +"'";
		database.execSQL(deleteQuery);
		Message.message(context, "deleted");
	}
	
	public String getAllData(){
		SQLiteDatabase database = db.getWritableDatabase();
		String[] columns = {DBHelper.TABLE_ID, DBHelper.TABLE_TIME, DBHelper.TABLE_LAPS};
		Cursor cursor = database.query(DBHelper.DATABASE_TABLE, columns, null, null, null, null, null);
		StringBuffer buffer = new StringBuffer();
		while(cursor.moveToNext()){
			int cid = cursor.getInt(0);
			String time = cursor.getString(1);
			String lap = cursor.getString(2);
			String date = cursor.getString(3);
			buffer.append(cid + " " + time + " " + lap + " " + date + "\n");
		}
		return buffer.toString();
	}
	
	public ArrayList<HashMap<String, String>> getAll() {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM "+ DBHelper.DATABASE_TABLE+"";
	    SQLiteDatabase database = db.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("id", cursor.getString(0));
	        	map.put("time", cursor.getString(1));
	        	map.put("laps", cursor.getString(2));
	        	map.put("date", cursor.getString(3));
                wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	    //Message.message(context, "List updated");
	    return wordList;
	}

	static class DBHelper extends SQLiteOpenHelper {
		
		private static final String DATABASE_NAME = "stopwatch_database";
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_TABLE = "lap_records";
		private static final String TABLE_ID = "_id";
		private static final String TABLE_TIME = "_time";
		private static final String TABLE_LAPS = "_laps";
		private static final String TABLE_DATE = "_date";
		private static final String CREATE_TABLE = "CREATE TABLE "+DATABASE_TABLE+" ( "+TABLE_ID+" INTEGER PRIMARY KEY, "+TABLE_TIME+" VARCHAR(50), "+TABLE_LAPS+" VARCHAR(50), "+TABLE_DATE+" VARCHAR(50))";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+DATABASE_TABLE;

		private Context context;
		
		public DBHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        this.context = context;
	        //Message.message(context, "Constructor called");
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE);
				Message.message(context, "Created was called");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Message.message(context, ""+e);
			}
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int version_old, int current_version) {
					
			try {
				db.execSQL(DROP_TABLE);
				onCreate(db);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Message.message(context, ""+e);
			}
					
		}

	}
}
