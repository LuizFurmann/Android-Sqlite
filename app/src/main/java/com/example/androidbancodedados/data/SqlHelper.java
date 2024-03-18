package com.example.androidbancodedados.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contact_db";
    private static final String TABLE_CONTACT = "contact";
    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String CREATED_DATE = "created_date";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_CONTACT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT,"
                + PHONE_NUMBER + " TEXT,"
                + CREATED_DATE + " DATETIME)";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

    public long addItem(String name, String phoneNumber){
        SQLiteDatabase db = getWritableDatabase();

        long contactId = 0;

        try {
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("phone_number", phoneNumber);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
            String currentDate = sdf.format(new Date());

            values.put("created_date", currentDate);
            contactId = db.insertOrThrow("contact", null, values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("SQLITE", e.getMessage(), e);
        } finally {
            if(db.isOpen())
                db.endTransaction();
        }
        return contactId;
    }
}
