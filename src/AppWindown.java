import Presention.ElectricBillView;
import domain.ElectricBillServiceImpl;
import java.sql.SQLException;
import pesistance.ElectricBillGateway;
import pesistance.ElectricBillJdbcGateway;

public class AppWindown {
    
        public static void main(String[] args) throws ClassNotFoundException, SQLException {

                ElectricBillGateway electricBillGateway = new ElectricBillJdbcGateway();
                ElectricBillServiceImpl electricBillService = new ElectricBillServiceImpl(electricBillGateway);
                ElectricBillView electricBillView = new ElectricBillView(electricBillService);
        
                electricBillService.subscribe(electricBillView); 
                electricBillView.setVisible(true);
        }
}
    
