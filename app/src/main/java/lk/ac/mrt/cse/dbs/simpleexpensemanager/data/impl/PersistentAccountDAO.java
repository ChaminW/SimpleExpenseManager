package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by Dell on 12/3/2015.
 */
public class PersistentAccountDAO extends DBHelper implements AccountDAO {



    public PersistentAccountDAO(Context context)
        {
            super(context);

        }



    @Override
    public List<String> getAccountNumbersList() {
        //give account number list
        List<String> array_list = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();

        String query = "select account_no from account";
        Cursor res =  db.rawQuery( query, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ACC_ACCOUNT_NO)));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }

    @Override
    public List<Account> getAccountsList() {
        // give list of all accounts
        List<Account> array_list = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();

        String query = "select * from account";
        Cursor res =  db.rawQuery( query, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(new Account(res.getString(res.getColumnIndex(ACC_ACCOUNT_NO)),res.getString(res.getColumnIndex(ACC_BANK)),res.getString(res.getColumnIndex(ACC_ACCOUNT_HOLDER)),res.getDouble(res.getColumnIndex(ACC_BALANCE))));
            res.moveToNext();
        }
        db.close();


        return array_list ;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        //give account details as requested account number
        SQLiteDatabase db =this.getReadableDatabase();

        String query = "select * from account where account_no=?";
        Cursor res =  db.rawQuery( query, new String[]{accountNo} );
        res.moveToFirst();
        while(res.getCount()==1){
            return new Account(res.getString(res.getColumnIndex(ACC_ACCOUNT_NO)),res.getString(res.getColumnIndex(ACC_BANK)),res.getString(res.getColumnIndex(ACC_ACCOUNT_HOLDER)),res.getDouble(res.getColumnIndex(ACC_BALANCE)));
        }
        db.close();




        String msg = "account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override
    public void addAccount(Account account) {
        // add new account
        SQLiteDatabase db= this.getWritableDatabase();
        String query = "insert into account VALUES (?,?,?,?)";
        db.execSQL(query, new String[]{account.getAccountNo(), account.getBankName(), account.getAccountHolderName(), String.valueOf(account.getBalance())});
        db.close();

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        // remove existing account. if it is not in db give error.
        SQLiteDatabase db= this.getWritableDatabase();
        try {
            db.delete(TABLE_ACC, ACC_ACCOUNT_NO + "=?", new String[]{accountNo});
        }
        catch (SQLiteException e) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        finally {
            db.close();
        }
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        SQLiteDatabase db= this.getWritableDatabase();

        try{
            String query = "select * from account where account_no=?";
            Cursor res =  db.rawQuery( query, new String[]{accountNo} );
            if(res.getCount()==0){
                String msg = "Account " + accountNo + " is invalid.";
                throw new InvalidAccountException(msg);
            }
            else{
                res.moveToFirst();
                double bal =res.getDouble(res.getColumnIndex(ACC_BALANCE));
                db.execSQL("UPDATE accounts SET balance = ? WHERE accountNo = ?",expenseType == ExpenseType.EXPENSE ? new String[]{String.valueOf(bal - amount),accountNo} : new String[]{String.valueOf(bal + amount),accountNo});

            }

        }
        catch (SQLiteException e){

            //do nothing
        }
        finally {
            db.close();
        }

    }
}
