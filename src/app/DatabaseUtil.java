
/**
 * This interface stores common constants used in both DataReader and DataWriter
 * classes.
 * @author Burak
 */
public interface DatabaseUtil {
    
    /**
     * Common
     */
    
    static final String NEW_LINE = "\n";
    static final String COMMA_DELIMITER = ",";
    
    /**
     * User Files
     */
    
    static final String FILE_HEADER = "username,password,name,surname,suffix,gender,region,currency,balance,ID,TRANSACTIONS_FILE"; //deprecated, we no longer add this line
    
    static final String FILE_NAME = "users.csv";
    static final String SALT = "5UALjP56gG"; // A random string to add 
    
    static final int USER_USERNAME = 0;
    static final int USER_PASSWORD = 1;
    static final int USER_NAME = 2;
    static final int USER_SURNAME = 3;
    static final int USER_SUFFIX = 4;
    static final int USER_GENDER = 5; 
    static final int USER_REGION = 6;
    static final int USER_CURRENCY = 7;
    static final int USER_BALANCE = 8;
    static final int USER_ID = 9;
    static final int USER_TRANSACTION_FILENAME = 10;   
    
    /**
     * Transaction Files
     */
    
    static final String TRANSACTIONS_FILE_HEADER = "transaction type, amount, date, comments";
    
    static final int TRANSACTION_TYPE = 0;
    static final int TRANSACTION_AMOUNT = 1;
    static final int TRANSACTION_DATE = 2;
}
