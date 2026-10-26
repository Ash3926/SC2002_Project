package SC2002_Project.sc2002project;
import java.util.Random;

public class Customer {
	private int customerID;
	private int satisfaction = 100;
	private CustomerStatus status = CustomerStatus.WAITING;
	
	public Customer(int custID) {
		customerID = custID;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	
	public int getSatisfaction() {
		return satisfaction;
	}
	
	public CustomerStatus getStatus() {
		return status;
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
		System.out.println("Customer ID: " + customerID);
		System.out.println("Status: " + status);
		System.out.println("Satisfaction: " + satisfaction);
	}

}
