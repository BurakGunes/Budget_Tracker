
/**
 *
 * @author Burak
 */
public class User {
    
    private String name, surname, suffix, gender, region, currency, transactionFilename;
    private double balance;
    private final long ID;
    
    public User(long ID, String name, String surname, String suffix, String gender, 
                   String region, String currency, double balance, String transactionFilename){
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.suffix = suffix;
        this.gender = gender;
        this.region = region;
        this.currency = currency;
        this.transactionFilename = ID + "_transactions.csv";
        
        this.balance = balance; 
    }
    
    public void setTransactionFilename(String filename){
        this.transactionFilename = filename;
    }
    
    public String getTransactionFilename(){
        return transactionFilename;
    }
    
    public long getID(){
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
}
