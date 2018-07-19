package com.sp.mgm.trustmebank.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sp.mgm.trustmebank.model.Account;


public class AccountDAO extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TrustMeBank";
    private static final String TABLE_ACCOUNT = "ACCOUNT";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_TOKEN = "token";

    public AccountDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        try {
            String script = "CREATE TABLE " + TABLE_ACCOUNT + "("
                    + COLUMN_USERNAME + " NVARCHAR(50) PRIMARY KEY,"
                    + COLUMN_TOKEN + " NVARCHAR(100))";
            db.execSQL(script);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
            // Và tạo lại.
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //thêm bản ghi
    public void addAccount(Account account) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String deleteSql = "DELETE FROM " + TABLE_ACCOUNT + " WHERE username='" + account.getUsername() + "'";
            db.execSQL(deleteSql);

            String sql = "INSERT INTO " + TABLE_ACCOUNT + " VALUES ('" + account.getUsername() + "','"
                    + account.getToken() + "')";

            db.execSQL(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account getAccount(String username) {
        Account account = new Account();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNT + " WHERE username = '"
                    + username + "'";

            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor != null)
                cursor.moveToFirst();
            account.setUsername(cursor.getString(0));
            account.setToken(cursor.getString(1));
            cursor.close();
            db.close();
            return account;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public void deleteAccount(String username) {

        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String deleteSql = "DELETE FROM " + TABLE_ACCOUNT + " WHERE username='" + username + "'";
            db.execSQL(deleteSql);
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}