package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rinik on 13/10/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_PROFILE_PIC = "profile";
    private static final String KEY_IS_HAVING = "isHaving";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT,"+ KEY_PROFILE_PIC + " TEXT,"+ KEY_IS_HAVING + " TEXT" + ")";
        Log.d("DB QUER",CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public  void addContact(Contact contact,boolean isHaving) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.name); // Contact Name
        if(contact.phone.length()>10){
        	contact.phone = contact.phone.substring(2);
        	values.put(KEY_PH_NO, contact.phone);
        }else{
        	values.put(KEY_PH_NO, contact.phone);
        }
         
        values.put(KEY_IS_HAVING, isHaving?"yes":"no");
        values.put(KEY_PROFILE_PIC, contact.path);

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        
    }


    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact;
                if(cursor.getString(4).length()==3){
                    contact=new Contact(cursor.getString(1),cursor.getString(2),true,cursor.getString(3));
                }else{
                    contact=new Contact(cursor.getString(1),cursor.getString(2),false," ");
                }
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_PH_NO + " = ?",
                new String[] { String.valueOf(contact.phone) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public Contact isPresent(String number) {
        String countQuery = "SELECT  * FROM "+ TABLE_CONTACTS+" WHERE "+KEY_PH_NO+" = "+number;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        String name="";
        boolean val=cursor.getCount()>0;
        cursor.moveToFirst();
        Contact contact;
        if(val){
            if(cursor.getString(4).length()==3){
                contact=new Contact(cursor.getString(1),cursor.getString(2),true,cursor.getString(3));
            }else{
                contact=new Contact(cursor.getString(1),cursor.getString(2),false," ");
            }
            cursor.close();
            return contact;
        }
        return null;
    }

}
