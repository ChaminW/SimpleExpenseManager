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
    public static final String TABLE_ACC = "account";
    public static final String ACC_ACCOUNT_NO = "account_no";
    public static final String ACC_BANK = "bank";
    public static final String ACC_ACCOUNT_HOLDER = "acc_holder";
    public static final String ACC_BALANCE = "acc_bal";

    public static final String TABLE_TRAN = "transaction";
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
                "create table account " +
                        "(account_no text primary key NOT NULL, bank text NOT NULL,acc_holder text NOT NULL,acc_bal real NOT NULL)");
        db.execSQL(
                "create table transaction " +
                        "(account_no text NOT NULL, type text NOT NULL,amount real NOT NULL,date real NOT NULL, FOREIGN KEY (accountN_no) REFERENCES account(account_no))");

        System.out.println("database created***************************");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS account");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS transaction");
        onCreate(db);
    }
}


