package com.example.codefortest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class PopProvider extends ContentProvider {

    public final static String DBNAME = "NameDatabase";

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    };


    public final static String TABLE_FUNKO_POP_TABLE = "FunkoPOP";
    public final static String COLUMN_ID = "Identify";
    public final static String COLUMN_POP_NAME= "PopName";
    public final static String COLUMN_POP_NUMBER = "PopNumber";
    public final static String COLUMN_POP_TYPE = "PopType";
    public final static String COLUMN_FANDOM = "Fandom";
    public final static String COLUMN_ON = "isOn";
    public final static String COLUMN_ULTIMATE = "Ultimate";
    public final static String COLUMN_PRICE = "Price";



    public static final String AUTHORITY = "com.example.provider";
    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY +"/" + TABLE_FUNKO_POP_TABLE);

    private static UriMatcher sUriMatcher;

    private MainDatabaseHelper mOpenHelper;

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            TABLE_FUNKO_POP_TABLE +  // Table's name
            "(" +               // The columns in the table
            " _ID INTEGER PRIMARY KEY, " +
            COLUMN_ID +
            " TEXT," +
            COLUMN_POP_NAME +
            " TEXT," +

            COLUMN_POP_NUMBER +
            " TEXT," +
            COLUMN_POP_TYPE+
            " TEXT," +
            COLUMN_FANDOM +
            " TEXT," +
            COLUMN_ON +
            " TEXT," +
            COLUMN_ULTIMATE +
            " TEXT," +

            COLUMN_PRICE +
            " TEXT)";



    public PopProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().delete(TABLE_FUNKO_POP_TABLE, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String popID = values.getAsString(COLUMN_ID).trim();
        String popName = values.getAsString(COLUMN_POP_NAME).trim();
        String popNumb = values.getAsString(COLUMN_POP_NUMBER).trim();
        String popType = values.getAsString(COLUMN_POP_TYPE).trim();
        String popFandom = values.getAsString(COLUMN_FANDOM).trim();
        String popOn = values.getAsString(COLUMN_ON).trim();
        String popUlt = values.getAsString(COLUMN_ULTIMATE).trim();
        String popPrice = values.getAsString(COLUMN_PRICE).trim();

        if (popID.equals(""))
            return null;

        if (popName.equals(""))
            return null;

        long id = mOpenHelper.getWritableDatabase().insert(TABLE_FUNKO_POP_TABLE, null, values);

        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(TABLE_FUNKO_POP_TABLE, projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().update(TABLE_FUNKO_POP_TABLE, values, selection, selectionArgs);
    }
}
