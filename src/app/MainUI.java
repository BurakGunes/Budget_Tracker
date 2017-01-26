
import java.awt.Color;
import java.awt.Font;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;


/**
 * This class models the main interface of the program. It is where the 
 * user interacts with program. It displays graphs, tables, etc.
 */
public class MainUI extends JFrame {

    private int xMouse, yMouse;
    private final User user;
    boolean profileButtonBool = false, gButtonBool = false, acButtonBool = false;
    private final int BASE_FONT = 14; // The lowest possible font dimension.
    private final int FONT_MULTIPLIER = 10;

    /**
     * Creates new form MainGUIInProgress
     *
     * @param user
     */
    public MainUI(User user) {
        this.user = user;
        initComponents();
        initializeUIElements();
        updateNetBalance(true);
        updateTable();
        currencyPlaceHolder.setText(user.getCurrency());
        updateIncomeGraph();
        updateExpenseGraph();
        setIconImage((new ImageIcon(getClass().getResource("/app/pics/icon.png")).getImage()));
        decideCurrency();
        updateLabels(DataReader.getTransactions(user));
        updateLabelVisibility(DataReader.getTransactions(user));
    }

    private void decideCurrency() {
        if (user.getCurrency().equals("USD")) {
            switchCurrency.setIcon(new ImageIcon(getClass().getResource("/app/pics/switchToTryButton.png")));
        } else {
            switchCurrency.setIcon(new ImageIcon(getClass().getResource("/app/pics/switchToUsdButton.png")));
        }
    }

    private void updateLabels(ArrayList<Transaction> t) {
        salaryLabel.setText(incomeChart.updateIncomeInfoSalary(t));
        scholarshipLabel.setText(incomeChart.updateIncomeInfoScholarship(t));
        promotionLabel.setText(incomeChart.updateIncomeInfoPromotion(t));
        otherLabel.setText(incomeChart.updateIncomeInfoOther(t));
        salaryLabel.setFont(new Font("jLabel3", Font.BOLD, BASE_FONT + (int) incomeChart.salaryPercentage(t) / FONT_MULTIPLIER));
        scholarshipLabel.setFont(new Font("jLabel7", Font.BOLD, BASE_FONT + (int) incomeChart.scholarshipPercentage(t) / FONT_MULTIPLIER));
        promotionLabel.setFont(new Font("jLabel12", Font.BOLD, BASE_FONT + (int) incomeChart.promotionPercentage(t) / FONT_MULTIPLIER));
        otherLabel.setFont(new Font("jLabel4", Font.BOLD, BASE_FONT + (int) incomeChart.incomeOtherPercentage(t) / FONT_MULTIPLIER));
        creditcardpaymentLabel.setText(expenseChart.updateExpenseInfoCCP(DataReader.getTransactions(user)));
        educationLabel.setText(expenseChart.updateExpenseInfoEducation(DataReader.getTransactions(user)));
        rentLabel.setText(expenseChart.updateExpenseInfoRent(DataReader.getTransactions(user)));
        taxesLabel.setText(expenseChart.updateExpenseInfoTaxes(DataReader.getTransactions(user)));
        foodLabel.setText(expenseChart.updateExpenseInfoFood(DataReader.getTransactions(user)));
        billsLabel.setText(expenseChart.updateExpenseInfoBills(DataReader.getTransactions(user)));
        otherLabel_expense.setText(expenseChart.updateExpenseInfoOther(DataReader.getTransactions(user)));
        creditcardpaymentLabel.setFont(new Font("jLabel5", Font.BOLD, BASE_FONT + (int) expenseChart.ccpPercentage(t) / FONT_MULTIPLIER));
        educationLabel.setFont(new Font("jLabel11", Font.BOLD, BASE_FONT + (int) expenseChart.educationPercentage(t) / FONT_MULTIPLIER));
        rentLabel.setFont(new Font("jLabel9", Font.BOLD, BASE_FONT + (int) expenseChart.rentPercentage(t) / FONT_MULTIPLIER));
        taxesLabel.setFont(new Font("jLabel6", Font.BOLD, BASE_FONT + (int) expenseChart.taxPercentage(t) / FONT_MULTIPLIER));
        foodLabel.setFont(new Font("jLabel8", Font.BOLD, BASE_FONT + (int) expenseChart.foodPercentage(t) / FONT_MULTIPLIER));
        billsLabel.setFont(new Font("jLabel10", Font.BOLD, BASE_FONT + (int) expenseChart.billsPercentage(t) / FONT_MULTIPLIER));
        otherLabel_expense.setFont(new Font("jLabel11", Font.BOLD, BASE_FONT + (int) expenseChart.expenseOtherPercentage(t) / FONT_MULTIPLIER));
    }

    private void updateLabelVisibility(ArrayList<Transaction> transactions) {
        for (Transaction t : transactions) {
            if (t.getTypeOfTheTransaction().equals("Credit Card Payment")) {
                System.out.println("Hey???");
                creditcardpaymentLabel.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Education")) {
                educationLabel.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Rent")) {
                rentLabel.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Taxes")) {
                taxesLabel.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Food")) {
                foodLabel.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Bills")) {
                billsLabel.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Expense Other") && t.getAmount() < 0) {
                otherLabel_expense.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Salary")) {
                salaryLabel.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Scholarship")) {
                scholarshipLabel.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Promotion")) {
                promotionLabel.setVisible(true);
            }
            if (t.getTypeOfTheTransaction().equals("Income Other") && t.getAmount() > 0) {
                otherLabel.setVisible(true);
            }
        }
    }

    private void updateIncomeGraph() {
        incomeChart.setEnabled(false);
        incomeChart.setVisible(false);
        incomeChart.refresh(DataReader.getTransactions(user));
        incomeChart.repaint();
        incomeChart.setEnabled(true);
        incomeChart.setVisible(true);
    }

    private void updateExpenseGraph() {
        expenseChart.setEnabled(false);
        expenseChart.setVisible(false);
        expenseChart.refresh(DataReader.getTransactions(user));
        expenseChart.repaint();
        expenseChart.setEnabled(true);
        expenseChart.setVisible(true);
    }

    private void updateNetBalance(boolean initialUpdate) {
        double netBalance = DataReader.getCurrentNetBalance(user);
        if (!initialUpdate) {
            DataWriter.modifyBalance(netBalance, user.getID());
        }
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        currentNetBalance.setText("Hello," + user.getSurname() + ". Your Current Balance: " + df.format(netBalance) + " " + user.getCurrency());
    }

    private void updateTable() {
        ArrayList<Transaction> lastFourTransactions = DataReader.getLastFourTransactions(user);
        for (int i = 0; lastFourTransactions != null && i < lastFourTransactions.size(); i++) {
            Transaction temp = lastFourTransactions.get(i);
            transactionTable.setValueAt(temp.getTypeOfTheTransaction(), i, 0);
            transactionTable.setValueAt(temp.getAmount(), i, 1);
            transactionTable.setValueAt(temp.getDate(), i, 2);
        }
    }

    private void initializeUIElements() {
        this.setLocationRelativeTo(null);
        setLookAndFeel("Windows");
        addPositiveTransaction.setEnabled(false);
        addNegativeTransaction.setEnabled(false);
    }

    private void setLookAndFeel(String lookAndFeel) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (lookAndFeel.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println(ex); // There is nothing we can do. 
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPane = new javax.swing.JPanel();
        profileLabel = new javax.swing.JLabel();
        activityTimelineLabel = new javax.swing.JLabel();
        currencyPlaceHolder = new javax.swing.JLabel();
        educationLabel = new javax.swing.JLabel();
        creditcardpaymentLabel = new javax.swing.JLabel();
        taxesLabel = new javax.swing.JLabel();
        scholarshipLabel = new javax.swing.JLabel();
        foodLabel = new javax.swing.JLabel();
        rentLabel = new javax.swing.JLabel();
        billsLabel = new javax.swing.JLabel();
        otherLabel_expense = new javax.swing.JLabel();
        promotionLabel = new javax.swing.JLabel();
        otherLabel = new javax.swing.JLabel();
        salaryLabel = new javax.swing.JLabel();
        currencyLabel = new javax.swing.JLabel();
        logoutLabel = new javax.swing.JLabel();
        switchCurrency = new javax.swing.JLabel();
        addPositiveTransaction = new javax.swing.JButton();
        currentNetBalance = new javax.swing.JLabel();
        expenseParent = new javax.swing.JPanel();
        expenseChart = new ExpenseChart();
        incomeParent = new javax.swing.JPanel();
        incomeChart = new IncomeChart();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionTable = new javax.swing.JTable();
        incomeTypes = new javax.swing.JComboBox<>();
        expenseTypes = new javax.swing.JComboBox<>();
        addNegativeTransaction = new javax.swing.JButton();
        amountField = new javax.swing.JTextField();
        background = new javax.swing.JLabel();
        minimizeIconLabel = new javax.swing.JLabel();
        terminateIconLabel = new javax.swing.JLabel();
        draggingAreaLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        contentPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profileLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/profileButton.png"))); // NOI18N
        profileLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profileLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profileLabelMouseExited(evt);
            }
        });
        contentPane.add(profileLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 90, 40));

        activityTimelineLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/acButton.png"))); // NOI18N
        activityTimelineLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                activityTimelineLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                activityTimelineLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                activityTimelineLabelMouseExited(evt);
            }
        });
        contentPane.add(activityTimelineLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, -1, 30));

        currencyPlaceHolder.setBackground(new java.awt.Color(255, 255, 255));
        currencyPlaceHolder.setFont(new java.awt.Font("Arial Black", 0, 72)); // NOI18N
        currencyPlaceHolder.setText("$");
        currencyPlaceHolder.setFocusable(false);
        currencyPlaceHolder.setOpaque(true);
        contentPane.add(currencyPlaceHolder, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 720, 340, 140));

        educationLabel.setForeground(new java.awt.Color(153, 153, 153));
        educationLabel.setText("jLabel1");
        contentPane.add(educationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 210, -1, -1));
        educationLabel.setVisible(false);

        creditcardpaymentLabel.setText("jLabel5");
        contentPane.add(creditcardpaymentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 150, -1, -1));
        creditcardpaymentLabel.setVisible(false);

        taxesLabel.setForeground(new java.awt.Color(102, 102, 102));
        taxesLabel.setText("jLabel6");
        contentPane.add(taxesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 310, -1, -1));
        taxesLabel.setVisible(false);

        scholarshipLabel.setForeground(new java.awt.Color(153, 153, 153));
        scholarshipLabel.setText("jLabel7");
        contentPane.add(scholarshipLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, -1, 30));
        scholarshipLabel.setVisible(false);

        foodLabel.setForeground(new java.awt.Color(106, 0, 0));
        foodLabel.setText("jLabel8");
        contentPane.add(foodLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 360, -1, -1));
        foodLabel.setVisible(false);

        rentLabel.setForeground(new java.awt.Color(255, 255, 255));
        rentLabel.setText("jLabel9");
        contentPane.add(rentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 260, -1, -1));
        rentLabel.setVisible(false);

        billsLabel.setForeground(new java.awt.Color(0, 110, 0));
        billsLabel.setText("jLabel10");
        contentPane.add(billsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 400, -1, -1));
        billsLabel.setVisible(false);

        otherLabel_expense.setForeground(new java.awt.Color(0, 0, 110));
        otherLabel_expense.setText("jLabel11");
        contentPane.add(otherLabel_expense, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 450, -1, -1));
        otherLabel_expense.setVisible(false);

        promotionLabel.setForeground(new java.awt.Color(255, 255, 255));
        promotionLabel.setText("jLabel12");
        contentPane.add(promotionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 344, -1, 40));
        promotionLabel.setVisible(false);

        otherLabel.setForeground(new java.awt.Color(102, 102, 102));
        otherLabel.setText("jLabel4");
        contentPane.add(otherLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 444, -1, 30));
        otherLabel.setVisible(false);

        salaryLabel.setText("jLabel3");
        contentPane.add(salaryLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, -1, 40));
        salaryLabel.setVisible(false);

        currencyLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/currencyButton.png"))); // NOI18N
        currencyLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                currencyLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                currencyLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                currencyLabelMouseExited(evt);
            }
        });
        contentPane.add(currencyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 210, 30));

        logoutLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/logoutButton.png"))); // NOI18N
        logoutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutLabelMouseExited(evt);
            }
        });
        contentPane.add(logoutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1095, 40, -1, 50));

        switchCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/switchToTryButton.png"))); // NOI18N
        switchCurrency.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                switchCurrencyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                switchCurrencyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                switchCurrencyMouseExited(evt);
            }
        });
        contentPane.add(switchCurrency, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 220, 30));

        addPositiveTransaction.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        addPositiveTransaction.setText("+");
        addPositiveTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPositiveTransactionActionPerformed(evt);
            }
        });
        contentPane.add(addPositiveTransaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 720, 110, 100));

        currentNetBalance.setBackground(new java.awt.Color(255, 255, 255));
        currentNetBalance.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        currentNetBalance.setForeground(new java.awt.Color(255, 255, 255));
        currentNetBalance.setText("Current Net Balance : ");
        contentPane.add(currentNetBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, -1, 30));

        expenseParent.setOpaque(false);

        expenseChart.setPreferredSize(new java.awt.Dimension(475, 402));

        javax.swing.GroupLayout expenseChartLayout = new javax.swing.GroupLayout(expenseChart);
        expenseChart.setLayout(expenseChartLayout);
        expenseChartLayout.setHorizontalGroup(
            expenseChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        expenseChartLayout.setVerticalGroup(
            expenseChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout expenseParentLayout = new javax.swing.GroupLayout(expenseParent);
        expenseParent.setLayout(expenseParentLayout);
        expenseParentLayout.setHorizontalGroup(
            expenseParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
            .addGroup(expenseParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(expenseParentLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(expenseChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        expenseParentLayout.setVerticalGroup(
            expenseParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
            .addGroup(expenseParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(expenseParentLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(expenseChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        contentPane.add(expenseParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 190, 410, 380));

        incomeParent.setOpaque(false);

        javax.swing.GroupLayout incomeChartLayout = new javax.swing.GroupLayout(incomeChart);
        incomeChart.setLayout(incomeChartLayout);
        incomeChartLayout.setHorizontalGroup(
            incomeChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        incomeChartLayout.setVerticalGroup(
            incomeChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout incomeParentLayout = new javax.swing.GroupLayout(incomeParent);
        incomeParent.setLayout(incomeParentLayout);
        incomeParentLayout.setHorizontalGroup(
            incomeParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(incomeChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        incomeParentLayout.setVerticalGroup(
            incomeParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(incomeParentLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(incomeChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        contentPane.add(incomeParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 410, 380));

        transactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Transaction", "Amount", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        transactionTable.setAutoscrolls(false);
        transactionTable.setDoubleBuffered(true);
        jScrollPane1.setViewportView(transactionTable);

        contentPane.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 1180, 90));

        incomeTypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Scholarship", "Salary", "Promotion", "Income Other" }));
        incomeTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeTypesActionPerformed(evt);
            }
        });
        contentPane.add(incomeTypes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 830, 110, 30));

        expenseTypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Credit Card Payment", "Food", "Taxes", "Rent", "Bills", "Education", "Expense Other" }));
        expenseTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseTypesActionPerformed(evt);
            }
        });
        contentPane.add(expenseTypes, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 830, 110, 30));

        addNegativeTransaction.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        addNegativeTransaction.setText("-");
        addNegativeTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNegativeTransactionActionPerformed(evt);
            }
        });
        contentPane.add(addNegativeTransaction, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 720, 110, 100));

        amountField.setFont(new java.awt.Font("Tahoma", 0, 72)); // NOI18N
        amountField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        amountField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                amountFieldKeyTyped(evt);
            }
        });
        contentPane.add(amountField, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 720, 620, 140));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/MainGUI.png"))); // NOI18N
        contentPane.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        minimizeIconLabel.setText("jLabel2");
        minimizeIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeIconLabelMouseClicked(evt);
            }
        });
        contentPane.add(minimizeIconLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1124, 0, 40, 40));

        terminateIconLabel.setText("jLabel3");
        terminateIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                terminateIconLabelMouseClicked(evt);
            }
        });
        contentPane.add(terminateIconLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 0, 40, 40));

        draggingAreaLabel.setText("jLabel1");
        draggingAreaLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                draggingAreaLabelMouseDragged(evt);
            }
        });
        draggingAreaLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                draggingAreaLabelMousePressed(evt);
            }
        });
        contentPane.add(draggingAreaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void terminateIconLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_terminateIconLabelMouseClicked
        this.dispose();
    }//GEN-LAST:event_terminateIconLabelMouseClicked

    private void minimizeIconLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeIconLabelMouseClicked
        this.setState(ICONIFIED);
    }//GEN-LAST:event_minimizeIconLabelMouseClicked

    private void draggingAreaLabelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_draggingAreaLabelMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_draggingAreaLabelMouseDragged

    private void profileLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileLabelMouseEntered
        profileLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/profileButtonHover.png")));
    }//GEN-LAST:event_profileLabelMouseEntered

    private void profileLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileLabelMouseExited
        profileLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/profileButton.png")));
    }//GEN-LAST:event_profileLabelMouseExited

    private void draggingAreaLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_draggingAreaLabelMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_draggingAreaLabelMousePressed

    private void activityTimelineLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activityTimelineLabelMouseEntered
        activityTimelineLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/acButtonHover.png")));
    }//GEN-LAST:event_activityTimelineLabelMouseEntered

    private void activityTimelineLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activityTimelineLabelMouseExited
        activityTimelineLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/acButton.png")));
    }//GEN-LAST:event_activityTimelineLabelMouseExited

    private void addPositiveTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPositiveTransactionActionPerformed
        try {
            System.out.println("selected: " + (String) incomeTypes.getSelectedItem());
            System.out.println(new Date());
            System.out.println(Double.parseDouble(amountField.getText()));
            Income temp = new Income((String) incomeTypes.getSelectedItem(), null, Double.parseDouble(amountField.getText()));
            DataWriter.addTransaction(temp, user);
            updateNetBalance(false);
            updateTable();
            updateIncomeGraph();
            ArrayList<Transaction> temp_tr = DataReader.getTransactions(user);
            updateLabels(temp_tr);
            updateLabelVisibility(temp_tr);
        } catch (NumberFormatException e) {
            System.out.println(e);
            amountField.setBackground(Color.RED);
            currencyPlaceHolder.setBackground(Color.RED);
        }
    }//GEN-LAST:event_addPositiveTransactionActionPerformed

    private void incomeTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeTypesActionPerformed
        // TODO add your handling code here:
        if (!((String) incomeTypes.getSelectedItem()).equals(" ")) {
            addPositiveTransaction.setEnabled(true);
            addNegativeTransaction.setEnabled(false);
            expenseTypes.setSelectedIndex(0);
        }
        if (((String) incomeTypes.getSelectedItem()).equals(" ")) {
            addPositiveTransaction.setEnabled(false);
        }
    }//GEN-LAST:event_incomeTypesActionPerformed

    private void expenseTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseTypesActionPerformed
        // TODO add your handling code here:
        if (!((String) expenseTypes.getSelectedItem()).equals(" ")) {
            addNegativeTransaction.setEnabled(true);
            addPositiveTransaction.setEnabled(false);
            incomeTypes.setSelectedIndex(0);
        }
        if (((String) expenseTypes.getSelectedItem()).equals(" ")) {
            addNegativeTransaction.setEnabled(false);
        }
    }//GEN-LAST:event_expenseTypesActionPerformed

    private void addNegativeTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNegativeTransactionActionPerformed
        try {
            Expense temp = new Expense((String) expenseTypes.getSelectedItem(), null, Double.parseDouble(amountField.getText()));
            DataWriter.addTransaction(temp, user);
            updateNetBalance(false);
            updateTable();
            updateExpenseGraph();
            updateLabels(DataReader.getTransactions(user));
            updateLabelVisibility(DataReader.getTransactions(user));
        } catch (NumberFormatException e) {
            amountField.setBackground(Color.RED);
            currencyPlaceHolder.setBackground(Color.RED);
        }
    }//GEN-LAST:event_addNegativeTransactionActionPerformed

    private void profileLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileLabelMouseClicked
        new ProfileUI(user).setVisible(true);
    }//GEN-LAST:event_profileLabelMouseClicked

    private void activityTimelineLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activityTimelineLabelMouseClicked
        new TransactionTableFrame(DataReader.getTransactions(user)).setVisible(true);
        System.out.println("j");
    }//GEN-LAST:event_activityTimelineLabelMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void amountFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountFieldKeyTyped
        if (((JTextField) (evt.getSource())).getBackground() == Color.RED) {
            amountField.setBackground(Color.WHITE);
            currencyPlaceHolder.setBackground(Color.WHITE);
        }
    }//GEN-LAST:event_amountFieldKeyTyped

    private void currencyLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_currencyLabelMouseClicked
        new Thread(){
            @Override
            public void run(){
                Application.launch(CurrencyGraph.class);
            }
        }.start();
    }//GEN-LAST:event_currencyLabelMouseClicked

    private void currencyLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_currencyLabelMouseEntered
        currencyLabel.setIcon(new ImageIcon(getClass().getResource("/app/pics/currencyButtonHover.png")));
    }//GEN-LAST:event_currencyLabelMouseEntered

    private void currencyLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_currencyLabelMouseExited
        currencyLabel.setIcon(new ImageIcon(getClass().getResource("/app/pics/currencyButton.png")));
    }//GEN-LAST:event_currencyLabelMouseExited

    private void logoutLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutLabelMouseEntered
        logoutLabel.setIcon(new ImageIcon(getClass().getResource("/app/pics/logoutButtonHover.png")));
    }//GEN-LAST:event_logoutLabelMouseEntered

    private void logoutLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutLabelMouseExited
        logoutLabel.setIcon(new ImageIcon(getClass().getResource("/app/pics/logoutButton.png")));
    }//GEN-LAST:event_logoutLabelMouseExited

    private void logoutLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutLabelMouseClicked
        this.dispose();
        new LoginGUI().setVisible(true);
    }//GEN-LAST:event_logoutLabelMouseClicked

    private void switchCurrencyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchCurrencyMouseClicked
        DataWriter.switchUserCurrency(user);
        updateTable();
        updateNetBalance(false);
        String[] tokens = switchCurrency.getIcon().toString().split("/");
        if(tokens.length > 0 && (tokens[tokens.length - 1].equals("switchToTryButton.png") || tokens[tokens.length - 1].equals("switchToTryButtonHover.png"))){
            switchCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/switchToUsdButton.png")));
        }
        else if(tokens.length > 0 && (tokens[tokens.length - 1].equals("switchToUsdButton.png") || tokens[tokens.length - 1].equals("switchToUsdButtonHover.png"))){
            switchCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/switchToTryButton.png")));
        }
        currencyPlaceHolder.setText(user.getCurrency());
    }//GEN-LAST:event_switchCurrencyMouseClicked

    private void switchCurrencyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchCurrencyMouseEntered
        String[] tokens = switchCurrency.getIcon().toString().split("/");
        if(tokens.length > 0 && tokens[tokens.length - 1].equals("switchToTryButton.png")){
            switchCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/switchToTryButtonHover.png")));
        }
        else if(tokens.length > 0 && tokens[tokens.length - 1].equals("switchToUsdButton.png")){
            switchCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/switchToUsdButtonHover.png")));
        }
    }//GEN-LAST:event_switchCurrencyMouseEntered

    private void switchCurrencyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchCurrencyMouseExited
        String[] tokens = switchCurrency.getIcon().toString().split("/");
        if(tokens.length > 0 && tokens[tokens.length - 1].equals("switchToTryButtonHover.png")){
            switchCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/switchToTryButton.png")));
        }
        else if(tokens.length > 0 && tokens[tokens.length - 1].equals("switchToUsdButtonHover.png")){
            switchCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/pics/switchToUsdButton.png")));
        }
    }//GEN-LAST:event_switchCurrencyMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activityTimelineLabel;
    private javax.swing.JButton addNegativeTransaction;
    private javax.swing.JButton addPositiveTransaction;
    private javax.swing.JTextField amountField;
    private javax.swing.JLabel background;
    private javax.swing.JLabel billsLabel;
    private javax.swing.JPanel contentPane;
    private javax.swing.JLabel creditcardpaymentLabel;
    private javax.swing.JLabel currencyLabel;
    private javax.swing.JLabel currencyPlaceHolder;
    private javax.swing.JLabel currentNetBalance;
    private javax.swing.JLabel draggingAreaLabel;
    private javax.swing.JLabel educationLabel;
    private ExpenseChart expenseChart;
    private javax.swing.JPanel expenseParent;
    private javax.swing.JComboBox<String> expenseTypes;
    private javax.swing.JLabel foodLabel;
    private IncomeChart incomeChart;
    private javax.swing.JPanel incomeParent;
    private javax.swing.JComboBox<String> incomeTypes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logoutLabel;
    private javax.swing.JLabel minimizeIconLabel;
    private javax.swing.JLabel otherLabel;
    private javax.swing.JLabel otherLabel_expense;
    private javax.swing.JLabel profileLabel;
    private javax.swing.JLabel promotionLabel;
    private javax.swing.JLabel rentLabel;
    private javax.swing.JLabel salaryLabel;
    private javax.swing.JLabel scholarshipLabel;
    private javax.swing.JLabel switchCurrency;
    private javax.swing.JLabel taxesLabel;
    private javax.swing.JLabel terminateIconLabel;
    private javax.swing.JTable transactionTable;
    // End of variables declaration//GEN-END:variables
}
