package Presention;
import Presention.command_.AddCommand;
import Presention.command_.Command;
import domain.ElectricBillService;
import domain.model.ElectricBill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ElectricBillController implements ActionListener, ItemListener, ListSelectionListener {
    private static ElectricBillController instance;
    private ElectricBillView electricBillView;
    private ElectricBillService electricBillService;

    public ElectricBillController(ElectricBillView electricBillView, ElectricBillService electricBillService) {
        this.electricBillView = electricBillView;
        this.electricBillService = electricBillService;
        
        this.electricBillView.addAddButtonListener(this);
        this.electricBillView.addRemoveButtonListener(this);
        this.electricBillView.addEditButtonListener(this);
        this.electricBillView.addSearchBillButtonListener(this);
        this.electricBillView.addAvgAmountOfMoneyFromForeignerCustomersButtonListener(this);
        this.electricBillView.addIssueInvoiceButtonListener(this);
        this.electricBillView.addExitButtonListener(this);
        this.electricBillView.addVietnamTypeButtonListener(this);
        this.electricBillView.addForeignerTypeButtonListener(this);
        
    }

    public static ElectricBillController getInstance(ElectricBillView electricBillView, ElectricBillService electricBillService) {
        if (instance == null) {
            instance = new ElectricBillController(electricBillView, electricBillService);
        }
        return instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == electricBillView.getAddButton()) {
            ElectricBill electricBill = electricBillView.getDataFromTextField();
            if(electricBill != null){
            Command addCommand = new AddCommand(electricBillService, electricBill);
            execute(addCommand);
        }
        } else if (source == electricBillView.getRemoveButton()) {
            // Implement remove logic
        } else if (source == electricBillView.getEditButton()) {
            // Implement edit logic
        } else if (source == electricBillView.getSearchBillButton()) {
            // Implement search logic
        } else if (source == electricBillView.getAvgAmountOfMoneyFromForeignerCustomersButton()) {
            // Implement average amount logic
        } else if (source == electricBillView.getIssueInvoiceButton()) {
            // Implement issue invoice logic
        } else if (source == electricBillView.getExitButton()) {
            // Implement exit logic
         } else if (source == electricBillView.getVietnamTypeButton()) {
            electricBillView.showVietnamFields();
        } else if (source == electricBillView.getForeignerTypeButton()) {
            electricBillView.showForeignerFields();
        }
            
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Implement the logic for item state changes
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Implement the logic for list selection changes
    }

    private void execute(Command command) {
        command.execute();
    }
}



