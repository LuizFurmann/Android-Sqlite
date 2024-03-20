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

import com.example.androidbancodedados.model.Client;
import com.example.androidbancodedados.model.Contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String CLIENT_DATABASE_NAME = "client_db.db";
    private static final String TABLE_CLIENT = "client";
    private static final String TABLE_CONTACT = "contact";

    //client
    private static final String CLIENT_ID = "id";
    private static final String CLIENT_NAME = "name";
    private static final String CLIENT_CITY = "city";
    private static final String CLIENT_STATUS = "status";

    //contact
    private static final String CONTACT_ID = "id";
    private static final String CONTACT_NAME = "name";
    private static final String CONTACT_PHONE_NUMBER = "phone_number";
    private static final String CONTACT_CREATED_DATE = "created_date";
    private static final String CLIENT_ID_REFERENCE = "client_id";


    public SqlHelper(Context context) {
        super(context, CLIENT_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_CONTACT);
    }

    private static final String CREATE_TABLE_CLIENT = "CREATE TABLE " + TABLE_CLIENT + "("
            + CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CLIENT_NAME + " TEXT,"
            + CLIENT_CITY + " TEXT,"
            + CLIENT_STATUS + " TEXT" + ")";

    private static final String CREATE_TABLE_CONTACT = "CREATE TABLE " + TABLE_CONTACT + "("
            + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CONTACT_NAME + " TEXT,"
            + CONTACT_PHONE_NUMBER + " TEXT,"
            + CONTACT_CREATED_DATE + " TEXT,"
            + CLIENT_ID_REFERENCE + " INTEGER" + ")";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT);
        onCreate(db);
    }

    public void addClient(Client client){
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put("name", client.getName());
            values.put("city", client.getCity());
            values.put("status", client.getStatus());

            db.insertOrThrow(TABLE_CLIENT, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("SQLITE", e.getMessage(), e);
        } finally {
            if(db.isOpen())
                db.endTransaction();
        }
    }

    public List<Client> getClients() {
        List<Client> clientList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CLIENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Client client = new Client();
                client.setId(cursor.getInt(cursor.getColumnIndex(CLIENT_ID)));
                client.setName(cursor.getString(cursor.getColumnIndex(CLIENT_NAME)));
                client.setCity(cursor.getString(cursor.getColumnIndex(CLIENT_CITY)));

                clientList.add(client);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return clientList;
    }

    public void editClient(Client client) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", client.getName());
        values.put("city", client.getCity());

        String[] args = {client.getId().toString()};
        db.update(TABLE_CLIENT, values, "id=?", args);
        db.close();
    }

    public void deleteClient(Client client) {
        SQLiteDatabase db = getWritableDatabase();

        String[] args = {client.getId().toString()};
        db.delete(TABLE_CLIENT, "id=?", args);
        db.close();
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put("name", contact.getName());
            values.put("phone_number", contact.getPhoneNumber());
            values.put("client_id", contact.getClientId());

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
//            String currentDate = sdf.format(new Date());
//
//            values.put("created_date", currentDate);
            db.insertOrThrow(TABLE_CONTACT, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("SQLITE", e.getMessage(), e);
        } finally {
            if(db.isOpen())
                db.endTransaction();
        }
    }


//    public void editItem(Contact contact){
//        SQLiteDatabase db = getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put("name", contact.getName());
//        values.put("phone_number", contact.getPhoneNumber());
//
//        String [] args = {contact.getId().toString()};
//        db.update("contact", values, "id=?", args);
//        db.close();
//    }

//    public void deleteContact(Contact contact){
//        SQLiteDatabase db = getWritableDatabase();
//
//        String [] args = {contact.getId().toString()};
//        db.delete("contact", "id=?", args);
//        db.close();
//    }

    public List<Contact> getContacts(Client client) {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACT + " WHERE client_id = " + client.getId();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(CONTACT_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndex(CONTACT_NAME)));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(CONTACT_PHONE_NUMBER)));

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return contactList;
    }
}
