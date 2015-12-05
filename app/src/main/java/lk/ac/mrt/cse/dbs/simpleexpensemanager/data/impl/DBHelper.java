package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Dell on 12/3/2015.
 */


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "130648V.db";
    public static final String TABLE_ACC = "accounts";
    public static final String ACC_ACCOUNT_NO = "account_no";
    public static final String ACC_BANK = "bank";
    public static final String ACC_ACCOUNT_HOLDER = "acc_holder";
    public static final String ACC_BALANCE = "acc_bal";

    public static final String TABLE_TRAN = "transactions";
    public static final String TRAN_ACCOUNT_NO = "account_no";
    public static final String TRAN_TYPE = "type";
    public static final String TRAN_AMOUNT = "amount";
    public static final String TRANS_DATE ="date";

    private HashMap hp;


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE accounts " +
                        "(account_no TEXT PRIMARY KEY NOT NULL, bank TEXT NOT NULL,acc_holder TEXT NOT NULL,acc_bal REAL NOT NULL)"
        );
        db.execSQL(
                "CREATE TABLE transactions " +
                        "(account_no TEXT NOT NULL, type TEXT NOT NULL,amount REAL NOT NULL,date REAL NOT NULL, FOREIGN KEY (account_no) REFERENCES accounts(account_no))"
        );

        System.out.println("database created***************************");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS transaction");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS account");
        onCreate(db);


    }
}


