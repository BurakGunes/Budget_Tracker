
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;


/**
 * This class models a pie chart. This class is used to display expense values 
 * of the user by their type of transactions. 
 */
public class ExpenseChart extends javax.swing.JPanel {

    public static ArrayList gradingValues;
    public static ArrayList gradingColors;

    double percent = 0; //percent is used for simple indicator and graded indicator

    public ArrayList<Double> values;
    public ArrayList<Color> colors;

    public ExpenseChart() {
        initComponents();        

        values = new ArrayList<>();
        colors = new ArrayList<>();

        colors.add(Color.BLACK);
        colors.add(new Color(106, 0, 0)); // BORDO
        colors.add(Color.GRAY);
        colors.add(new Color(0, 0, 77)); // DARK BLUE
        colors.add(Color.WHITE);
        colors.add(new Color(0, 50, 0));
        colors.add(Color.DARK_GRAY);
    }

    /**
     * Refreshes.
     *
     * @param transactions : transactios
     */
    public double rentPercentage(ArrayList<Transaction> transactions) {
        double rentAmount = 0;
        double taxAmount = 0;
        double foodAmount = 0;
        double ccpAmount = 0;
        double billsAmount = 0;
        double educationAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Rent")) {
                rentAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Taxes")) {
                taxAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Education")) {
                educationAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Expense Other")) {
                otherAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Bills")) {
                billsAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Food")) {
                foodAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Credit Card Payment")) {
                ccpAmount += t.getAmount();
            }
        }

        double sum = rentAmount + taxAmount + foodAmount + ccpAmount + billsAmount + educationAmount + otherAmount;
        return (100 * rentAmount) / sum;

    }

    public double educationPercentage(ArrayList<Transaction> transactions) {
        double rentAmount = 0;
        double taxAmount = 0;
        double foodAmount = 0;
        double ccpAmount = 0;
        double billsAmount = 0;
        double educationAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Rent")) {
                rentAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Taxes")) {
                taxAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Education")) {
                educationAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Expense Other")) {
                otherAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Bills")) {
                billsAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Food")) {
                foodAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Credit Card Payment")) {
                ccpAmount += t.getAmount();
            }
        }

        double sum = rentAmount + taxAmount + foodAmount + ccpAmount + billsAmount + educationAmount + otherAmount;
        return (100 * educationAmount) / sum;

    }

    public double billsPercentage(ArrayList<Transaction> transactions) {
        double rentAmount = 0;
        double taxAmount = 0;
        double foodAmount = 0;
        double ccpAmount = 0;
        double billsAmount = 0;
        double educationAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Rent")) {
                rentAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Taxes")) {
                taxAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Education")) {
                educationAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Expense Other")) {
                otherAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Bills")) {
                billsAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Food")) {
                foodAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Credit Card Payment")) {
                ccpAmount += t.getAmount();
            }
        }

        double sum = rentAmount + taxAmount + foodAmount + ccpAmount + billsAmount + educationAmount + otherAmount;
        return (100 * billsAmount) / sum;

    }

    public double expenseOtherPercentage(ArrayList<Transaction> transactions) {
        double rentAmount = 0;
        double taxAmount = 0;
        double foodAmount = 0;
        double ccpAmount = 0;
        double billsAmount = 0;
        double educationAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Rent")) {
                rentAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Taxes")) {
                taxAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Education")) {
                educationAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Expense Other")) {
                otherAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Bills")) {
                billsAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Food")) {
                foodAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Credit Card Payment")) {
                ccpAmount += t.getAmount();
            }

        }

        double sum = rentAmount + taxAmount + foodAmount + ccpAmount + billsAmount + educationAmount + otherAmount;
        return (100 * otherAmount) / sum;

    }

    public double taxPercentage(ArrayList<Transaction> transactions) {
        double rentAmount = 0;
        double taxAmount = 0;
        double foodAmount = 0;
        double ccpAmount = 0;
        double billsAmount = 0;
        double educationAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Rent")) {
                rentAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Taxes")) {
                taxAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Education")) {
                educationAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Expense Other")) {
                otherAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Bills")) {
                billsAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Food")) {
                foodAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Credit Card Payment")) {
                ccpAmount += t.getAmount();
            }
        }

        double sum = rentAmount + taxAmount + foodAmount + ccpAmount + billsAmount + educationAmount + otherAmount;
        return (100 * taxAmount) / sum;
    }

    public double foodPercentage(ArrayList<Transaction> transactions) {
        double rentAmount = 0;
        double taxAmount = 0;
        double foodAmount = 0;
        double ccpAmount = 0;
        double billsAmount = 0;
        double educationAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Rent")) {
                rentAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Taxes")) {
                taxAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Education")) {
                educationAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Expense Other")) {
                otherAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Bills")) {
                billsAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Food")) {
                foodAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Credit Card Payment")) {
                ccpAmount += t.getAmount();
            }
        }

        double sum = rentAmount + taxAmount + foodAmount + ccpAmount + billsAmount + educationAmount + otherAmount;
        return (100 * foodAmount) / sum;

    }

    public double ccpPercentage(ArrayList<Transaction> transactions) {
        double rentAmount = 0;
        double taxAmount = 0;
        double foodAmount = 0;
        double ccpAmount = 0;
        double billsAmount = 0;
        double educationAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Rent")) {
                rentAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Taxes")) {
                taxAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Education")) {
                educationAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Expense Other")) {
                otherAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Bills")) {
                billsAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Food")) {
                foodAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Credit Card Payment")) {
                ccpAmount += t.getAmount();
            }
        }

        double sum = rentAmount + taxAmount + foodAmount + ccpAmount + billsAmount + educationAmount + otherAmount;
        return (100 * ccpAmount) / sum;
    }

    public String updateExpenseInfoRent(ArrayList<Transaction> transactions) {
        return "Rent: " + round(rentPercentage(transactions),1);
    }

    public String updateExpenseInfoTaxes(ArrayList<Transaction> transactions) {
        return "Taxes " + round(taxPercentage(transactions),1);
    }

    public String updateExpenseInfoFood(ArrayList<Transaction> transactions) {
        return "Food :" + round(foodPercentage(transactions),1);
    }

    public String updateExpenseInfoCCP(ArrayList<Transaction> transactions) {
        return "Cards :" + round(ccpPercentage(transactions),1);
    }

    public String updateExpenseInfoBills(ArrayList<Transaction> transactions) {
        return "Bills :" + round(billsPercentage(transactions),1);
    }

    public String updateExpenseInfoEducation(ArrayList<Transaction> transactions) {
        return "Education: " + round(educationPercentage(transactions), 1);
    }

    public String updateExpenseInfoOther(ArrayList<Transaction> transactions) {
        return "Other :" + round(expenseOtherPercentage(transactions), 1);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void refresh(ArrayList<Transaction> transactions) {
        double ccpAmount = 0;
        double foodAmount = 0;
        double taxesAmount = 0;
        double otherAmount = 0;
        double rentAmount = 0;
        double billsAmount = 0;
        double educationAmount = 0;

        values.clear();
        for (Transaction t : transactions) {

            if (t.getTypeOfTheTransaction().equals("Credit Card Payment")) {
                ccpAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Food")) {
                foodAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Taxes")) {
                taxesAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Expense Other")) {
                otherAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Rent")) {
                rentAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Bills")) {
                billsAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Education")) {
                educationAmount += t.getAmount();
            }

        }

        double coefficient = 100 / (ccpAmount + foodAmount + taxesAmount + otherAmount + rentAmount + billsAmount + educationAmount);

        values.add(ccpAmount * coefficient);
        values.add(foodAmount * coefficient);
        values.add(taxesAmount * coefficient);
        values.add(otherAmount * coefficient);
        values.add(rentAmount * coefficient);
        values.add(billsAmount * coefficient);
        values.add(educationAmount * coefficient);

        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {

        //Wıdth minus 100 is dıameter of chaert
        int width = getSize().width - 100;

        //Creating Graphic object
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //90 degree
        int lastPoint = -270;

        //Filling arc loop
        for (int i = 0; i < values.size(); i++) {
            //settıng color of arc
            g2d.setColor((Color) colors.get(i));

            //Angle of arc
            Double val = values.get(i);
            Double angle = (val / 100) * 360;
            
            //Filling arc
            g2d.fillArc(0, 0, width, width, lastPoint, -angle.intValue());
            //prıntıng values for development puposes
            System.out.println("fill arc " + lastPoint + " "
                    + -angle.intValue());
            
            //Updating last poınt of every element
            lastPoint = lastPoint + -angle.intValue();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>

}
