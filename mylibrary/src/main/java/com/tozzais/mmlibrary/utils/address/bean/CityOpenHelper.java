package com.tozzais.mmlibrary.utils.address.bean;

import android.database.sqlite.SQLiteDatabase;

import com.tozzais.mmlibrary.Constant;

import java.io.File;

public class CityOpenHelper {

	//项目存放的地址
	public static final String DATABASE_PATH = Constant.ROOT_PATH + "/static.dll";
	private SQLiteDatabase db;

	public SQLiteDatabase openSQLite() {
		if (db != null) {
			return db;
		} else {
			try {
				File databaseFilename = new File(DATABASE_PATH);
				db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
				
				return db;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}
}
