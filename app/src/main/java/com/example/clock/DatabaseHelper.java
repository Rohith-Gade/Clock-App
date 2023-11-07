package com.example.clock;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AlarmDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Alarms";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "alarm_time";
    public static final String COLUMN_DAYS = "alarm_days";
    public static final String COLUMN_SONG = "selected_song";
    public static final String COLUMN_STATUS = "status";
    SQLiteDatabase db;
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TIME + " TEXT, " +
                    COLUMN_DAYS + " TEXT, " +
                    COLUMN_SONG + " TEXT, " +
                    COLUMN_STATUS + " INTEGER DEFAULT 0);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long insertAlarm(String time, String days, String song, int status) {
         db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_DAYS, days);
        values.put(COLUMN_SONG, song);
        values.put(COLUMN_STATUS, status);
        long rowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowId;
    }
    public ArrayList<Alarm> getAllAlarms() {
         db = getReadableDatabase();
        ArrayList<Alarm> alarmList = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TIME, COLUMN_DAYS, COLUMN_SONG, COLUMN_STATUS},
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
            String days = cursor.getString(cursor.getColumnIndex(COLUMN_DAYS));
            String song = cursor.getString(cursor.getColumnIndex(COLUMN_SONG));
            int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

            Alarm alarm = new Alarm(id, time, days, song, status);
            alarmList.add(alarm);
        }

        cursor.close();
        db.close();

        return alarmList;
    }
    public void updateAlarmStatus(Alarm alarm) {
         db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, alarm.getStatus());
        // Define the WHERE clause to update the specific alarm by its ID
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(alarm.getId()) };

        // Update the alarm status in the database
        db.update(TABLE_NAME, values, selection, selectionArgs);

        // Close the database
        db.close();
    }
 /*   public ArrayList<Alarm> getAlarmsByStatus(int status) {
         db = getReadableDatabase();
        ArrayList<Alarm> alarmList = new ArrayList<>();

        String selection = COLUMN_STATUS + " = ?";
        String[] selectionArgs = { String.valueOf(status) };

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TIME, COLUMN_DAYS, COLUMN_SONG, COLUMN_STATUS},
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
            String days = cursor.getString(cursor.getColumnIndex(COLUMN_DAYS));
            String song = cursor.getString(cursor.getColumnIndex(COLUMN_SONG));
            int alarmStatus = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

            Alarm alarm = new Alarm(id, time, days, song, alarmStatus);
            alarmList.add(alarm);
        }

        cursor.close();
        db.close();

        return alarmList;
    }*/

    public ArrayList<Alarm> getSelectedAlarms() {
         db = getReadableDatabase();
        ArrayList<Alarm> selectedAlarms = new ArrayList<>();

        String selection = COLUMN_STATUS + " = ?";
        String[] selectionArgs = { "1" };

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TIME, COLUMN_DAYS, COLUMN_SONG, COLUMN_STATUS},
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
            String days = cursor.getString(cursor.getColumnIndex(COLUMN_DAYS));
            String song = cursor.getString(cursor.getColumnIndex(COLUMN_SONG));
            int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

            Alarm alarm = new Alarm(id, time, days, song, status);
            selectedAlarms.add(alarm);
        }

        cursor.close();
        db.close();

        return selectedAlarms;
    }
    public  boolean deleteAlarm(String name){
        db=this.getWritableDatabase();
        int rows=db.delete(TABLE_NAME,"alarm_time=?",new String[]{name});
        return rows != -1;
    }
    public String retrieveSongNameFromDatabase(String time) {
        String songName = null;

        db = getReadableDatabase();

        // Define the columns to be retrieved from the database
        String[] columnsToRetrieve = {COLUMN_SONG};

        // Define the selection clause to find the alarm with the specified ID
        String selection = COLUMN_TIME + " = ?";
        String[] selectionArgs = {time};

        Cursor cursor = db.query(
                TABLE_NAME,
                columnsToRetrieve,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            // Retrieve the song name from the database
            songName = cursor.getString(cursor.getColumnIndex(COLUMN_SONG));
        }

        cursor.close();
        db.close();

        return songName;
    }

}
