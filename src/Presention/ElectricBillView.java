package Presention;

import domain.ElectricBillService;
import domain.Subscriber;
import domain.model.ElectricBill;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ElectricBillView extends JFrame implements Subscriber {
    private ElectricBillService electricBillService;
    private JTextField idField, nameField, qtyField, unitPriceField, searchField, nationalField;
    private JLabel countCustomersVietNam, countCustomersForeigner;
    private JComboBox<String> comboBoxMonth, comboBoxCustomersType, comboBoxElectricityRates;
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel inputContainerPanel, outputContainerPanel, buttonPanel;
    private JButton addButton, removeButton, editButton, searchBillButton, avgAmountOfMoneyFromForeignerCustomersButton;
    private JButton issueInvoiceButton, exitButton;
    private JRadioButton vietnamTypeButton, foreignerTypeButton;
    private JMenuBar menuBar;
    private ElectricBillController electricBillController;

    public ElectricBillView(ElectricBillService electricBillService) {
        this.electricBillService = electricBillService;
        this.electricBillService.subscribe(this); // Subscribe to updates

        initialize();
        loadElectricBills();
    }

    private void initialize() {
        setTitle("Electric Bill Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildMenu();
        buildPanel();

        loadElectricBills();
       // updateCustomerCounts();
    }

    private void buildPanel() {
        // Input Panel
        inputContainerPanel = new JPanel(new GridLayout(7, 2));

        inputContainerPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputContainerPanel.add(idField);

        inputContainerPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputContainerPanel.add(nameField);

        inputContainerPanel.add(new JLabel("Qty:"));
        qtyField = new JTextField();
        inputContainerPanel.add(qtyField);

        inputContainerPanel.add(new JLabel("Unit Price:"));
        unitPriceField = new JTextField();
        inputContainerPanel.add(unitPriceField);

        inputContainerPanel.add(new JLabel("Month:"));
        comboBoxMonth = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        inputContainerPanel.add(comboBoxMonth);

        inputContainerPanel.add(new JLabel("Customer Type:"));
        comboBoxCustomersType = new JComboBox<>(new String[]{"Vietnam", "Foreigner"});
        inputContainerPanel.add(comboBoxCustomersType);

        inputContainerPanel.add(new JLabel("Electricity Rates:"));
        comboBoxElectricityRates = new JComboBox<>(new String[]{"Rate1", "Rate2", "Rate3"});
        inputContainerPanel.add(comboBoxElectricityRates);

        // Output Panel
        outputContainerPanel = new JPanel(new GridLayout(1, 2));

        countCustomersVietNam = new JLabel("Vietnam Customers: 0");
        outputContainerPanel.add(countCustomersVietNam);

        countCustomersForeigner = new JLabel("Foreigner Customers: 0");
        outputContainerPanel.add(countCustomersForeigner);

        // Button Panel
        buttonPanel = new JPanel(new GridLayout(2, 4));

        addButton = new JButton("Add");
        buttonPanel.add(addButton);

        removeButton = new JButton("Remove");
        buttonPanel.add(removeButton);
        

        editButton = new JButton("Edit");
        buttonPanel.add(editButton);
        

        searchBillButton = new JButton("Search");
        buttonPanel.add(searchBillButton);

        avgAmountOfMoneyFromForeignerCustomersButton = new JButton("Average Amount (Foreigner)");
        buttonPanel.add(avgAmountOfMoneyFromForeignerCustomersButton);

        issueInvoiceButton = new JButton("Issue Invoice");
        buttonPanel.add(issueInvoiceButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        });
        buttonPanel.add(exitButton);

        vietnamTypeButton = new JRadioButton("Vietnam Type");
        buttonPanel.add(vietnamTypeButton);

        foreignerTypeButton = new JRadioButton("Foreigner Type");
        buttonPanel.add(foreignerTypeButton);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Person", "Month", "Date", "Qty", "Unit Price", "Quota", "Total"}, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add panels to the frame
        getContentPane().add(inputContainerPanel, BorderLayout.NORTH);
        getContentPane().add(outputContainerPanel, BorderLayout.EAST);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void buildMenu() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
    }

     public void update(List<ElectricBill> electricBills) {
        updateTable(electricBills);
        //updateCustomerCounts();
    }

    private void loadElectricBills() {
        List<ElectricBill> electricBills = electricBillService.getAllElectricBills();
        updateTable(electricBills);
    }

   /*  private void updateCustomerCounts() {
        int vietnamCount = electricBillService.countVietnamCustomers();
        int foreignerCount = electricBillService.countForeignerCustomers();

        countCustomersVietNam.setText("Vietnam Customers: " + vietnamCount);
        countCustomersForeigner.setText("Foreigner Customers: " + foreignerCount);
    }
*/
    private void reset() {
        // Logic to reset view
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        qtyField.setText("");
        unitPriceField.setText("");
        nationalField.setText("");
    }

    private double extractQuota(String rateString) {
        // Logic to extract quota from rateString
        return 0.0;
    }

    public ElectricBill getDataFromTextField() {
    String id = idField.getText();
    String name = nameField.getText();
    Integer qty = null;
    Double unitPrice = null;
    Double quota = extractQuota(comboBoxElectricityRates.getSelectedItem().toString());

    try {
        qty = Integer.parseInt(qtyField.getText());
        unitPrice = Double.parseDouble(unitPriceField.getText());
        quota = extractQuota(comboBoxElectricityRates.getSelectedItem().toString());
    } catch (NumberFormatException e) {
        // Handle exception: show error message to user, return null or a default ElectricBill, etc.
        JOptionPane.showMessageDialog(this, "Please enter valid numbers for quantity and unit price.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    return new ElectricBill(id, name, "", "", null, qty, unitPrice, quota);
        }

    void showVietnamFields() {
        // Logic to show Vietnam-specific fields
    }

    void showForeignerFields() {
        // Logic to show Foreigner-specific fields
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void addEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addSearchBillButtonListener(ActionListener listener) {
        searchBillButton.addActionListener(listener);
    }

    public void addAvgAmountOfMoneyFromForeignerCustomersButtonListener(ActionListener listener) {
        avgAmountOfMoneyFromForeignerCustomersButton.addActionListener(listener);
    }

    public void addIssueInvoiceButtonListener(ActionListener listener) {
        issueInvoiceButton.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void addVietnamTypeButtonListener(ActionListener listener) {
        vietnamTypeButton.addActionListener(listener);
    }

    public void addForeignerTypeButtonListener(ActionListener listener) {
        foreignerTypeButton.addActionListener(listener);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getSearchBillButton() {
        return searchBillButton;
    }

    public JButton getAvgAmountOfMoneyFromForeignerCustomersButton() {
        return avgAmountOfMoneyFromForeignerCustomersButton;
    }

    public JButton getIssueInvoiceButton() {
        return issueInvoiceButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JRadioButton getVietnamTypeButton() {
        return vietnamTypeButton;
    }

    public JRadioButton getForeignerTypeButton() {
        return foreignerTypeButton;
    }

    private void exitApplication() {
        System.exit(0);
    }

    private void updateTable(List<ElectricBill> electricBills) {
        tableModel.setRowCount(0);
        for (ElectricBill electricBill : electricBills) {
            tableModel.addRow(new Object[]{electricBill.getIdClient(), electricBill.getFullName(), electricBill.getPerson(), electricBill.getMonthlyElectricly(), electricBill.getTime(), electricBill.getQty(), electricBill.getUnitPrice(), electricBill.getQuota(), electricBill.getTotal()});
        }
    }
}
