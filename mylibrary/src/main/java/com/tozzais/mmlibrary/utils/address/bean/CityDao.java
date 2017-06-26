package com.tozzais.mmlibrary.utils.address.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CityDao {
	private SQLiteDatabase db;

	public CityDao(Context context) {
		CityOpenHelper cityOpenHelper = new CityOpenHelper();
		db = cityOpenHelper.openSQLite();
	}

	public static final String TABLE_NAME = "location";

	public List<CityBean> getProvince() {
		if (db == null) {
			return new ArrayList<CityBean>();
		}
		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where pid = 0 order by hot desc,title desc", new String[] {});
		List<CityBean> list = new ArrayList<CityBean>();
		while (cursor.moveToNext()) {
			CityBean cityBean = new CityBean();
			cityBean.name = cursor.getString(cursor.getColumnIndex("title"));
			cityBean.pid = cursor.getString(cursor.getColumnIndex("pid"));
			cityBean.id = cursor.getString(cursor.getColumnIndex("id"));
			list.add(cityBean);
		}
		return list;
	}

	public List<CityBean> getCity(String id) {
		if (db == null) {
			return new ArrayList<CityBean>();
		}
		
		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where pid =?", new String[] { id });
		List<CityBean> list = new ArrayList<CityBean>();
		while (cursor.moveToNext()) {
			CityBean cityBean = new CityBean();
			cityBean.name = cursor.getString(cursor.getColumnIndex("title"));
			cityBean.pid = cursor.getString(cursor.getColumnIndex("pid"));
			cityBean.id = cursor.getString(cursor.getColumnIndex("id"));
			list.add(cityBean);
		}
		return list;
	}

}