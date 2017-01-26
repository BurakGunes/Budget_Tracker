
import java.util.Date;

/**
 * This class models an expense.
 * @author Burak
 */
public class Expense extends Transaction {

    public Expense(String typeOfTheTransaction, Date date, double amount) {
        super(typeOfTheTransaction, date, amount);
    }

    @Override
    public double getEffect() {
        return getAmount() * -1;
    }
    
}
