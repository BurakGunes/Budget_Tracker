
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Burak
 */
public class DataWriter implements DatabaseUtil{ 
    
    /**
     * Adds a new user to the file that keeps users' data. It initially 
     * checks whether a file was created before, and if it can not find any file,
     * it creates one itself. It also creates the corresponding transactions 
     * file for each appended user.
     * itself.  
     * @param user : User to be added
     * @param username : Username of the user.
     * @param password : Password of the user. Should not be encyrpted. 
     * @param doubleCheckPassword : It is used to check during 
     * registering process that whether two password instances match.
     * @throws UsernameNotAvailableException : It is throwed when the username is already taken.
     * @throws PasswordMismatchException : It is throwed when two password instances do not match.
     * @throws PasswordException : It is throwed when password's length is less than 6.
     */
    public static void appendNewUser(User user, String username, String password, String doubleCheckPassword) throws UsernameNotAvailableException, PasswordMismatchException, PasswordException{
        File usersFile = new File(FILE_NAME);
        if(!usersFile.exists()){
            try {
                usersFile.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        
        File transactionFile = new File(user.getTransactionFilename());
        if(!transactionFile.exists()){
            try {
                transactionFile.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        
        if(!DataReader.isUsernameAvailable((username))){
            throw new UsernameNotAvailableException("This username already has been taken.");
        }
        if(!doubleCheckPassword.equals(password)){
            throw new PasswordMismatchException("Password did not match.");
        }
        if(password.length() < 6){
            throw new PasswordException("Password should contain at least six characters.");
        }
        
        appendInformation(user, getEncryptedText(username), getEncryptedText(password)); 
        
        /**
         * Opening Balance of the user is added as a individual transaction to the list. 
         */
        if(user.getBalance() > 0){
            addTransaction(new Income("Initial Balance",null,user.getBalance()), user);
        }
        else if(user.getBalance() < 0){
            addTransaction(new Expense("Initial Balance", null, Math.abs(user.getBalance())), user);
        }
    }
     
    /**
     * This method is used to encyrpt a text through MD5 algorithm, 
     * with a predetermined SALT value. WARNING : Can not be de-crypted. Use 
     * with caution.
     * @param str : Word to be encrypted.
     * @return the encyrpted text.
     */
    public static String getEncryptedText(String str){
        String result;
        result = str + SALT; 
       
        byte[] md5sum = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md5sum = md.digest(result.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
    
        return String.format("%032X", new BigInteger(1, md5sum)); // Formatting BigInteger's leading zeros. "1" is to denote the positive value of BigInteger
    }   
    
    /**
     * Appends user's information into the file in which users' data are kept.
     * @param user : user to be appended its information
     * @param username : username of the user to be appended
     * @param password  : password of the user to be appended.
     */
    private static void appendInformation(User user, String username, String password){
        try(FileWriter fileWriter = new FileWriter(FILE_NAME, true)){
            fileWriter.append(username);
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(password);
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(user.getName());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(user.getSurname());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(user.getSuffix());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(user.getGender());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(user.getRegion());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(user.getCurrency());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(user.getBalance()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Long.toString(user.getID()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(user.getID()+"_transactions.csv");
            fileWriter.append(NEW_LINE);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * This method adds the specified transaction into user's specifically pre-
     * determined file. 
     * @param t : Transaction to be added.
     * @param std : User posseses this transaction.
     */
    public static void addTransaction(Transaction t, User std){
        String userTransactionsFileName = DataReader.getUsersTransactionFile(std);
        
        File f = new File(userTransactionsFileName);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        
        try(FileWriter fileWriter = new FileWriter(userTransactionsFileName, true)){
            fileWriter.append(t.getTypeOfTheTransaction());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString(t.getEffect()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append("" + new Date().getTime());
            fileWriter.append(NEW_LINE);
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(null, "An error occured while processing the data.\n"+
                    ex.getMessage(), 
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Modifyies the net balance of the user.
     * @param balance : the newest balance value of to replace the existing balance
     * value. 
     * @param ID : ID number of the user.
     */
    public static void modifyBalance(double balance, long ID){
        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            
            while((line = fileReader.readLine()) != null){
                String[] tokens = line.split(COMMA_DELIMITER);
                if(Long.parseLong(tokens[USER_ID]) == ID){
                    tokens[USER_BALANCE] = Double.toString(balance);
                }
                lines.add(String.join(",", tokens));
            }
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(null, "An error occured while processing the data.\n"+
                    ex.getMessage(), 
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        /**
         * Clears the file.
         */
        try(PrintWriter pw = new PrintWriter(FILE_NAME)){
            pw.write("");
        }
        catch(FileNotFoundException ex){
            JOptionPane.showConfirmDialog(null, "An error occured while processing the data.\n"+
                    ex.getMessage(), 
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        /**
         * Appends the lines, modified and unmodified ones, to the file in which
         * users data are kept.
         */
        for(String str : lines){
                writeNewLine(str, FILE_NAME);
        }
    }
    
    /**
     * Appends the provided line into the users data file.
     * @param str : line to be appended.
     */
    private static void writeNewLine(String str, String filename){
        try(FileWriter fileWriter = new FileWriter(filename, true)){
            fileWriter.append(str);
            fileWriter.append(NEW_LINE);
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public static void switchUserCurrency(User user){
        ArrayList<String> lines = new ArrayList<>();
        String newCurrency = null;
        if(user.getCurrency().equals("USD")){
            newCurrency = "TRY";
        }
        else{
            newCurrency = "USD";
        }
        user.setCurrency(newCurrency);
        try(BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            
            while((line = fileReader.readLine()) != null){
                String[] tokens = line.split(COMMA_DELIMITER);
                if(tokens.length > 0 && Long.parseLong(tokens[USER_ID]) == user.getID()){
                    tokens[USER_CURRENCY] = newCurrency;
                }
                lines.add(String.join(",", tokens));
            }
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(null, "An error occured while processing the data.\n"+
                    ex.getMessage(), 
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        /**
         * Clears the file.
         */
        try(PrintWriter pw = new PrintWriter(FILE_NAME)){
            pw.write("");
        }
        catch(FileNotFoundException ex){
            JOptionPane.showConfirmDialog(null, "An error occured while processing the data.\n"+
                    ex.getMessage(), 
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        /**
         * Appends the lines, modified and unmodified ones, to the file in which
         * users data are kept.
         */
        for(String str : lines){
                writeNewLine(str, FILE_NAME);
        }
        modifyTransactions(user);
    }
    
    public static void modifyTransactions(User user){
        ArrayList<String> lines = new ArrayList<>();
        double multiplier = 1;
        String userfile = user.getTransactionFilename();
        if(user.getCurrency().equals("USD")){
            multiplier = 1 / CurrencyData.getTRYagainstUSD();
        }
        else{
            multiplier = CurrencyData.getTRYagainstUSD();
        }
        try(BufferedReader fileReader = new BufferedReader(new FileReader(userfile))){
            String line;
            
            while((line = fileReader.readLine()) != null){
                String[] tokens = line.split(COMMA_DELIMITER);
                if(tokens.length > 0 && tokens[TRANSACTION_AMOUNT] != null){
                    tokens[TRANSACTION_AMOUNT] = "" + Double.parseDouble(tokens[TRANSACTION_AMOUNT]) * multiplier;
                }
                lines.add(String.join(",", tokens));
            }
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(null, "An error occured while processing the data.\n"+
                    ex.getMessage(), 
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        /**
         * Clears the file.
         */
        try(PrintWriter pw = new PrintWriter(userfile)){
            pw.write("");
        }
        catch(FileNotFoundException ex){
            JOptionPane.showConfirmDialog(null, "An error occured while processing the data.\n"+
                    ex.getMessage(), 
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        /**
         * Appends the lines, modified and unmodified ones, to the file in which
         * users data are kept.
         */
        for(String str : lines){
                writeNewLine(str, userfile);
        }
    }
}
