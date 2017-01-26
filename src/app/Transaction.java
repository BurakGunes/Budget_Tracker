
import java.util.Date;

/*
 * Developed and Designed by BURAK
 * 
 */

/**
 *
 * @author Burak
 */
public abstract class Transaction {
	
    private String typeOfTheTransaction;
    private double amount;
    private final Date date;

    public Transaction(String typeOfTheTransaction, Date date, double amount) {
    	this.typeOfTheTransaction = typeOfTheTransaction;
        if(date != null){
            this.date = date;
        }
        else{
            this.date = new Date();
        }
    	this.amount = amount;
    }
    
    public abstract double getEffect();

    /**
     * @return the typeOfTheTransaction
     */
    public String getTypeOfTheTransaction() {
        return typeOfTheTransaction;
    }

    /**
     * @param typeOfTheTransaction the typeOfTheTransaction to set
     */
    public void setTypeOfTheTransaction(String typeOfTheTransaction) {
        this.typeOfTheTransaction = typeOfTheTransaction;
    }
 
    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public Date getDate(){
        return date;
    }

    

}
