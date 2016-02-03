package com.technext.fabtest2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alessandro on 28/03/2015.
 */
public class DatabaseAdapter{

    DatabaseOpenHelper helper;

    public DatabaseAdapter(Context context) {
        helper = new DatabaseOpenHelper(context);
    }

    public long insertSubject(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.NAME, name);
        long id = db.insert(DatabaseOpenHelper.SUBJECT_TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertMark(String mark, int subject_id, String date, int type){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.MARK, mark);
        contentValues.put(DatabaseOpenHelper.SUBJECT_UID, subject_id);
        contentValues.put(DatabaseOpenHelper.MARK_DATE, date);
        contentValues.put(DatabaseOpenHelper.MARK_TYPE, type);
        long id = db.insert(DatabaseOpenHelper.MARK_TABLE_NAME, null, contentValues);
        return id;
    }

    public List<CustomDataTable> getAllSubjects(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DatabaseOpenHelper.UID, DatabaseOpenHelper.NAME};
        Cursor cursor = db.query(DatabaseOpenHelper.SUBJECT_TABLE_NAME, columns, null, null, null, null, helper.NAME+" ASC");
        List<CustomDataTable> data = new ArrayList<CustomDataTable>();
        while(cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(DatabaseOpenHelper.UID);
            int index2 = cursor.getColumnIndex(DatabaseOpenHelper.NAME);
            int cid = cursor.getInt(index1);
            String name = cursor.getString(index2);
            CustomDataTable temp = new CustomDataTable();
            temp.UID = cid;
            temp.SUBJECT = name;
            data.add(temp);
        }
        return data;
    }

    public List<CustomMarkDataTable> getAllMarks(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DatabaseOpenHelper.UID, DatabaseOpenHelper.SUBJECT_UID, DatabaseOpenHelper.MARK_DATE, DatabaseOpenHelper.MARK, DatabaseOpenHelper.MARK_TYPE};
        Cursor cursor = db.query(DatabaseOpenHelper.MARK_TABLE_NAME, columns, null, null, null, null, null);
        List<CustomMarkDataTable> data = new ArrayList<CustomMarkDataTable>();
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                int index1 = cursor.getColumnIndex(DatabaseOpenHelper.MARK);
                int index2 = cursor.getColumnIndex(DatabaseOpenHelper.SUBJECT_UID);
                int index3 = cursor.getColumnIndex(DatabaseOpenHelper.MARK_DATE);
                int index4 = cursor.getColumnIndex(DatabaseOpenHelper.MARK_TYPE);
                String mark = cursor.getString(index1);
                int subj_id = cursor.getInt(index2);
                String date = cursor.getString(index3);
                String type = cursor.getString(index4);
                CustomMarkDataTable temp = new CustomMarkDataTable();
                temp.mark = mark;
                temp.subject_id = subj_id;
                temp.date = date;
                temp.type = type;
                data.add(temp);
            }
        }
        return data;
    }

    static class DatabaseOpenHelper extends SQLiteOpenHelper{
        Context context;

        private static final String DATABASE_NAME = "SCHOOLBUDDY";
        private static final String SUBJECT_TABLE_NAME = "SUBJECTS";
        private static final String MARK_TABLE_NAME = "MARKS";
        private static final int DATABASE_VERSION = 5;
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String SUBJECT_UID = "_subject_id";
        private static final String MARK_DATE = "Mark_date";
        private static final String MARK_TYPE = "Mark_type";
        private static final String MARK = "Mark";
        private static final String CREATE_SUBJECT_TABLE = "CREATE TABLE "+SUBJECT_TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255));";
        private static final String CREATE_MARK_TABLE = "CREATE TABLE "+MARK_TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SUBJECT_UID+" INTEGER, "+MARK_DATE+" TEXT, "+MARK+" TEXT, "+MARK_TYPE+" INTEGER);";
        private static final String DROP_SUBJECT_TABLE = "DROP TABLE IF EXISTS "+SUBJECT_TABLE_NAME + ";";
        private static final String DROP_MARK_TABLE = "DROP TABLE IF EXISTS "+MARK_TABLE_NAME+";";

        public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_SUBJECT_TABLE);
                db.execSQL(CREATE_MARK_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_SUBJECT_TABLE);
                db.execSQL(DROP_MARK_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
