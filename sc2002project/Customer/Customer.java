package SC2002_Project.sc2002project.Customer;
import java.util.Random;

public class Customer {
	private int customerID;
	private int tableID;
	private int satisfaction = 100;
	protected int billing;
	private CustomerStatus status = CustomerStatus.WAITING;
	
	public Customer(int custID) {
		customerID = custID;
		billing = 100;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	
	public int getTableID() {
		return tableID;
	}
	
	public int getSatisfaction() {
		return satisfaction;
	}
	
	public CustomerStatus getStatus() {
		return status;
	}
	
	public void setTableID(int tabID) {
		tableID = tabID;
	}
	
	public int placeOrder() {
		Random r = new Random();
		int choice = r.nextInt(3);
		return choice;
	}
	
	public int applyWaitingPenalty() {
		if (satisfaction > 8) {
			satisfaction -= 8;
		} else {
			satisfaction = 0;
		}
		return satisfaction;
	}
	
	public void changeStatus(CustomerStatus custStatus) {
		status = custStatus;
	}
	
	public void display() {
		System.out.print("Customer " + customerID + " (");
		System.out.print("Status: " + status);
		System.out.println(", Satisfaction: " + satisfaction + ")");
	}

}
