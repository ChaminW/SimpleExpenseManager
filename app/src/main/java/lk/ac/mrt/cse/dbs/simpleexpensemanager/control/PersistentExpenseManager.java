package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;


import lk.ac.mrt.cse.dbs.simpleexpensemanager.ContextClass;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by Dell on 12/5/2015.
 */
public class PersistentExpenseManager extends ExpenseManager {

    public PersistentExpenseManager() {
        setup();
    }

    @Override
    public void setup() {
        /*** Begin generating dummy data for In-Memory implementation ***/

        TransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(ContextClass.getCustomAppContext());
        setTransactionsDAO(persistentTransactionDAO);

        AccountDAO persistentAccountDAO = new PersistentAccountDAO(ContextClass.getCustomAppContext());
        setAccountsDAO(persistentAccountDAO);

        // dummy data
        //Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        //Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        //getAccountsDAO().addAccount(dummyAcct1);
        //getAccountsDAO().addAccount(dummyAcct2);

        /*** End ***/
    }
}