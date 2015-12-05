package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by Dell on 12/3/2015.
 */
public class PersistentTransactionDAO extends DBHelper implements TransactionDAO {


    public PersistentTransactionDAO(Context context)
    {
        super(context);

    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db= this.getWritableDatabase();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormater.format(date);

        try{
            String query = "insert into transactions VALUES (?,?,?,?)";
            db.execSQL(query, new String[]{accountNo,expenseType == ExpenseType.EXPENSE ? "EXPENSE": "INCOME", String.valueOf(amount),strDate});

        }
        catch(SQLiteException e){
            //do nothing
        }
        finally {
            db.close();
        }



    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> array_list = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        try {
            String query = "select * from transactions order by date(`date`) DESC";
            Cursor res = db.rawQuery(query, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {

                SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = dateF.parse(res.getString(res.getColumnIndex(TRANS_DATE)));

                } catch (ParseException e) {
                    //do nothing
                }

                array_list.add(new Transaction(date, res.getString(res.getColumnIndex(TRAN_ACCOUNT_NO)), (res.getString(res.getColumnIndex(TRAN_TYPE)) == "EXPENSE") ? ExpenseType.EXPENSE : ExpenseType.INCOME, res.getDouble(res.getColumnIndex(TRAN_AMOUNT))));
                res.moveToNext();
            }

        }
        catch(SQLiteException e){
            // do nothing
        }
        finally {
            db.close();

        }

        return array_list ;



    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> array_list = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();

        try {
            String query = "select * from transactions order by date(`date`) DESC LIMIT " + limit;
            Cursor res = db.rawQuery(query, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {

                SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = dateF.parse(res.getString(res.getColumnIndex(TRANS_DATE)));

                } catch (ParseException e) {
                    //do nothing
                }

                array_list.add(new Transaction(date, res.getString(res.getColumnIndex(TRAN_ACCOUNT_NO)), (res.getString(res.getColumnIndex(TRAN_TYPE)) == "EXPENSE") ? ExpenseType.EXPENSE : ExpenseType.INCOME, res.getDouble(res.getColumnIndex(TRAN_AMOUNT))));
                res.moveToNext();
            }
        }
        catch( SQLiteException e){
            //do nothing
        }
        finally {
            db.close();
        }

        return array_list;
    }
}
