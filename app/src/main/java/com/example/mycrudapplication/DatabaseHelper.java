package com.example.mycrudapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PersonalDB";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PERSONAL_INFO = "personal_info";

    // Common column names
    private static final String KEY_ID = "id";

    // USERS Table - column names
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    // PERSONAL_INFO Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";

    // Table Create Statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USERNAME + " TEXT,"
            + KEY_PASSWORD + " TEXT" + ")";

    private static final String CREATE_TABLE_PERSONAL_INFO = "CREATE TABLE " + TABLE_PERSONAL_INFO + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT,"
            + KEY_EMAIL + " TEXT,"
            + KEY_PHONE + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PERSONAL_INFO);

        // Add a default user
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, "admin");
        values.put(KEY_PASSWORD, "admin123");
        db.insert(TABLE_USERS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAL_INFO);
        onCreate(db);
    }

    // User Login Check
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID};
        String selection = KEY_USERNAME + " = ?" + " AND " + KEY_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // User Registration
    public long registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        return db.insert(TABLE_USERS, null, values);
    }

    // Check if Username exists
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID};
        String selection = KEY_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }


    // CRUD for Personal Info
    public long addPersonalInfo(com.example.mycrudapplication.PersonalInfo info) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, info.getName());
        values.put(KEY_EMAIL, info.getEmail());
        values.put(KEY_PHONE, info.getPhone());
        return db.insert(TABLE_PERSONAL_INFO, null, values);
    }

    public List<com.example.mycrudapplication.PersonalInfo> getAllPersonalInfo() {
        List<com.example.mycrudapplication.PersonalInfo> infoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PERSONAL_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                com.example.mycrudapplication.PersonalInfo info = new com.example.mycrudapplication.PersonalInfo();
                info.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                info.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
                info.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)));
                info.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE)));
                infoList.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return infoList;
    }

    public int updatePersonalInfo(com.example.mycrudapplication.PersonalInfo info) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, info.getName());
        values.put(KEY_EMAIL, info.getEmail());
        values.put(KEY_PHONE, info.getPhone());
        return db.update(TABLE_PERSONAL_INFO, values, KEY_ID + " = ?", new String[]{String.valueOf(info.getId())});
    }

    public void deletePersonalInfo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSONAL_INFO, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
}