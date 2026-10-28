package SC2002_Project.sc2002project.Staff;
import SC2002_Project.sc2002project.Customer.*;
import SC2002_Project.sc2002project.Objects.*;
import SC2002_Project.sc2002project.*;

public class Cashier extends Staff {

    public Cashier() {
        super();
    }
	
	public boolean handlePayment(Table[] tableList, Customer[] custList, GameStats stats) {
		
		// Find oldest ready to pay customer by index in custList
		for (int i=0; i<10; i++) {
			Customer cust = custList[i];
			if (cust != null && cust.getStatus() == CustomerStatus.READY_TO_PAY) {
				Table selectedTable = tableList[cust.getTableID()];
				Order order = selectedTable.getOrder();
				int bill = order.getItem().getPrice();
				System.out.println("\nReceived $" + bill + " from Customer " + cust.getCustomerID() + "!");
				System.out.println("Table " + selectedTable.getTableID() + " is now freed up!");
				
				//Update stats
				cust.changeStatus(CustomerStatus.PAID);
				stats.increaseRevenue(bill);
				stats.incrementPaidCustomers();
				stats.updateTSatisfaction(cust.getSatisfaction());
				order.markPaid();
				selectedTable.release();
				markTaskUsed();
				return true; // can only accept one payment per turn
			}
		}
		// no payment made
		markTaskUsed();
		return false;
	}
}
