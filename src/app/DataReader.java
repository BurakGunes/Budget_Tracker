
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;


public class DataReader implements DatabaseUtil{
    
    /**
     * Checks whether the username entered is available. If not, returns false.
     * @param username
     * @return : true when desired username is not taken.
     */
    public static boolean isUsernameAvailable(String username){
        try(BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while((line = fileReader.readLine()) != null){
                String[] tokens;
                tokens = line.split(COMMA_DELIMITER);
                if(tokens.length > 0 && tokens[USER_USERNAME].equals(DataWriter.getEncryptedText(username))){
                    return false;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(null, "An error occured while processing the data.\n"+
                    ex.getMessage(), 
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        } 
        return true;
    }
    
    /**
     * Checks whether the log in process is succesfully completed. 
     * @param username : username provided in login process.
     * @param password : password provided in login process.
     * @return : true when username and password exist and match. 
     */
    public static boolean isLogInSuccesful(String username, String password){
        try(BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            
            while((line = fileReader.readLine()) != null){
                String[] tokens;
                tokens = line.split(COMMA_DELIMITER);
                
                if(tokens.length > 0 && tokens[USER_USERNAME].equals(DataWriter.getEncryptedText(username))){
                   return tokens[USER_PASSWORD].equals(DataWriter.getEncryptedText(password));
                }
            }
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(null, "An error occured while processing the data.\n"+
                    ex.getMessage(), 
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        } 
        return false;
    }
    
    /**
     * Returns the file's name in which user's transactions are stored.
     * @param std : user to be returned its transaction file name.
     * @return : a string representation of the transactions file's name
     * including file's extension (which is .csv)
     */
    public static String getUsersTransactionFile(User std){
        try(BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            
            while((line = fileReader.readLine()) != null){
                String[] tokens;
                tokens = line.split(COMMA_DELIMITER);
                if(Long.parseLong(tokens[USER_ID]) == std.getID()){
                    return tokens[USER_TRANSACTION_FILENAME];
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    /**
     * Iterates through the lines of user data file, and finds an available index.
     * @return an available ID.
     */
    public static long getNextAvailableID(){
        long id = 0;
        try(BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))){
            
            while((fileReader.readLine()) != null){
                id++;
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } 
        return id;
    }
    
    /**
     * Reads user's data from the file, and returns them as a User object. An 
     * extra layer of security was added into this method so as to prevent
     * data leak.
     * @param username : username belongs to user.
     * @param password : password belongs to user.
     * @return a User object blongs to the specified username.
     */
    public static User getUserData(String username, String password){
        if(isLogInSuccesful(username, password)){
            try(BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))){
                String line;

                while((line = fileReader.readLine()) != null){
                    String[] tokens;
                    tokens = line.split(COMMA_DELIMITER);
                    if(tokens[USER_USERNAME].equals(DataWriter.getEncryptedText(username))){
                        return new User(Long.parseLong(tokens[USER_ID]),
                                      tokens[USER_NAME], tokens[USER_SURNAME], 
                                      tokens[USER_SUFFIX], tokens[USER_GENDER], tokens[USER_REGION], 
                                      tokens[USER_CURRENCY], Double.parseDouble(tokens[USER_BALANCE]),
                                      tokens[USER_TRANSACTION_FILENAME]);
                    }
                }
        }   catch (IOException ex) {
                    System.out.println(ex);
            }
        }
        return null;
    }
    
    /**
     * Returns the initial balance of the user.
     * @param ID : ID number of the user.
     * @return the initial balance of the user.
     * @deprecated It does not return the initial balance of the user. Rather, 
     * it returns what is written on user's data. Instead of this method, 
     * getCurrentNetBalance(User user) should be used.
     */
    public static double getInitialBalance(long ID){
        try(BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while((line = fileReader.readLine()) != null){
                String[] tokens = line.split(COMMA_DELIMITER);
                if(Long.parseLong(tokens[USER_ID]) == ID){
                    return Double.parseDouble(tokens[USER_BALANCE]);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } 
        return 0;
    }
    
    /**
     * It returns the total of the transactions' amounts. 
     * @param user : User to be returned its net balance.
     * @return : the current balance of the user.
     */
    public static double getCurrentNetBalance(User user){
        double netBalance = 0;
        try(BufferedReader fileReader = new BufferedReader(new FileReader(getUsersTransactionFile(user)))){
            String line;
            while((line = fileReader.readLine()) != null){
                String[] tokens = line.split(COMMA_DELIMITER);
                netBalance += Double.parseDouble(tokens[TRANSACTION_AMOUNT]);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } 
        return netBalance;
    }
    
    /**
     * Returns how many transactions were performed by the user. 
     * @param user : to be returned its total transaction processes.
     * @return the total numbers of transactions performed by the user.
     */
    private static int getTransactionNumbers(User user){
        int result = 0;
        try(BufferedReader fileReader = new BufferedReader(new FileReader(getUsersTransactionFile(user)))){
            while((fileReader.readLine()) != null){
                result++;
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    /**
     * Returns the last four transactions performed by the user. ArrayList does 
     * not have to be full. 
     * @param user : to be process its last four transactions.
     * @return the elements inside the ArrayList
     */
    public static ArrayList<Transaction> getLastFourTransactions(User user){
        int totalTransactions = getTransactionNumbers(user);
        ArrayList<Transaction> transactions = new ArrayList<>();
        try(BufferedReader fileReader = new BufferedReader(new FileReader(getUsersTransactionFile(user)))){
            String line;
            int index = 0;
            while((line = fileReader.readLine()) != null){
                if( index++ + 4 >= totalTransactions){
                    String[] tokens = line.split(COMMA_DELIMITER);
                    if(tokens.length > 0){
                        Transaction temp;
                        double amount = Double.parseDouble(tokens[TRANSACTION_AMOUNT]);
                        if(amount > 0){
                            temp = new Income(tokens[TRANSACTION_TYPE], new Date(Long.parseLong(tokens[TRANSACTION_DATE])), amount);
                        }
                        else{
                            temp = new Expense(tokens[TRANSACTION_TYPE], new Date(Long.parseLong(tokens[TRANSACTION_DATE])), amount);
                        }
                        transactions.add(temp);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return transactions;
    }
    
    /**
     * Returns all the transactions performed by the user.
     * @param user : User to be returned its transactions.
     * @return : An arrayList consist of the transaction elements.
     */
    public static ArrayList<Transaction> getTransactions(User user){
        ArrayList<Transaction> transactions = new ArrayList<>();
        try(BufferedReader fileReader = new BufferedReader(new FileReader(getUsersTransactionFile(user)))){
            String line;
            while((line = fileReader.readLine()) != null){
                    String[] tokens = line.split(COMMA_DELIMITER);
                    Transaction temp;
                    double amount = Double.parseDouble(tokens[TRANSACTION_AMOUNT]);
                    if(amount > 0){
                        temp = new Income(tokens[TRANSACTION_TYPE],new Date(Long.parseLong(tokens[TRANSACTION_DATE])), amount);
                    }
                    else{
                        temp = new Expense(tokens[TRANSACTION_TYPE], new Date(Long.parseLong(tokens[TRANSACTION_DATE])), amount);
                    }
                    transactions.add(temp);
                
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return transactions;
    }
}
