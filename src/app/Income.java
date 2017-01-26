
import java.util.Date;

/**
 * This class models the incomes of the user. 
 */
public class Income extends Transaction {

    /**
     *
     * @param typeOfTheTransaction
     * @param date
     * @param amount
     */
    public Income(String typeOfTheTransaction, Date date, double amount) {
        super(typeOfTheTransaction, date, amount);
    }

    /**
     *
     * @return
     */
    @Override
    public double getEffect() {
        return getAmount();
    }

}
