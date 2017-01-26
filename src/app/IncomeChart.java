
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

/**
 * This class models a pie chart. This class is used to display income values 
 * of the user by their type of transactions. 
 * 
 */
public class IncomeChart extends javax.swing.JPanel {

    //This two are unncessary Do not override
    public static ArrayList gradingValues;
    public static ArrayList gradingColors;

    double percent = 0; //percent is used for simple indicator and graded indicator

    //These two arraylists draw pie chart---------------
    public ArrayList<Double> values;
    public ArrayList<Color> colors;

    //---------------------------------------------------
    /**
     * Constructor right now directly initiates two important array list
     */
    public IncomeChart() {
        initComponents();
        

        values = new ArrayList<>();
        colors = new ArrayList<>();

        colors.add(Color.BLACK);
        colors.add(Color.GRAY);
        colors.add(Color.WHITE);
        colors.add(Color.DARK_GRAY);

        colors.add(new Color(255, 255, 10));
        colors.add(new Color(255, 22, 183));
        colors.add(new Color(45, 6, 6));
        colors.add(new Color(244, 24, 43));
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

    public double salaryPercentage(ArrayList<Transaction> transactions) {

        double salaryAmount = 0;
        double scholarshipAmount = 0;
        double promotionAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Salary")) {
                salaryAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Scholarship")) {
                scholarshipAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Promotion")) {
                promotionAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Income Other")) {
                otherAmount += t.getAmount();
            }
        }

        return 100 * salaryAmount / (salaryAmount + scholarshipAmount + promotionAmount + otherAmount);
    }

    public double scholarshipPercentage(ArrayList<Transaction> transactions) {

        double salaryAmount = 0;
        double scholarshipAmount = 0;
        double promotionAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Salary")) {
                salaryAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Scholarship")) {
                scholarshipAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Promotion")) {
                promotionAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Income Other")) {
                otherAmount += t.getAmount();
            }
        }

        return 100 * scholarshipAmount / (salaryAmount + scholarshipAmount + promotionAmount + otherAmount);
    }

    public double promotionPercentage(ArrayList<Transaction> transactions) {

        double salaryAmount = 0;
        double scholarshipAmount = 0;
        double promotionAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Salary")) {
                salaryAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Scholarship")) {
                scholarshipAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Promotion")) {
                promotionAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Income Other")) {
                otherAmount += t.getAmount();
            }
        }

        return 100 * promotionAmount / (salaryAmount + scholarshipAmount + promotionAmount + otherAmount);
    }

    public double incomeOtherPercentage(ArrayList<Transaction> transactions) {

        double salaryAmount = 0;
        double scholarshipAmount = 0;
        double promotionAmount = 0;
        double otherAmount = 0;

        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Salary")) {
                salaryAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Scholarship")) {
                scholarshipAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Promotion")) {
                promotionAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Income Other")) {
                otherAmount += t.getAmount();
            }
        }

        return 100 * otherAmount / (salaryAmount + scholarshipAmount + promotionAmount + otherAmount);
    }

    public String updateIncomeInfoSalary(ArrayList<Transaction> transactions) {
        return "Salary " + round(salaryPercentage(transactions), 1);
    }

    public String updateIncomeInfoScholarship(ArrayList<Transaction> transactions) {

        return "Scholarship " + round(scholarshipPercentage(transactions), 1);
    }

    public String updateIncomeInfoPromotion(ArrayList<Transaction> transactions) {

        return "Promotion " + round(promotionPercentage(transactions), 1);
    }

    public String updateIncomeInfoOther(ArrayList<Transaction> transactions) {
        return "Other " + round(incomeOtherPercentage(transactions), 1);
    }

    public void refresh(ArrayList<Transaction> transactions) {
        double salaryAmount = 0;
        double scholarshipAmount = 0;
        double promotionAmount = 0;
        double otherAmount = 0;

        values.clear();
        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Salary")) {
                salaryAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Scholarship")) {
                scholarshipAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Promotion")) {
                promotionAmount += t.getAmount();
            } else if (t.getTypeOfTheTransaction().equals("Income Other")) {
                otherAmount += t.getAmount();
            }
        }

        double coefficient = 100 / (salaryAmount + scholarshipAmount + promotionAmount + otherAmount);

        values.add(salaryAmount * coefficient);
        values.add(scholarshipAmount * coefficient);
        values.add(promotionAmount * coefficient);
        values.add(otherAmount * coefficient);

        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
