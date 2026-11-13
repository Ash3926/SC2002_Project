package SC2002_Project.sc2002project.Customer;
import SC2002_Project.sc2002project.Objects.*;

public class Customer {
	private int customerID;
	private int tableID;
	protected int satisfaction = 100;
	protected int billing;
	private int servedTurn = -1;
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

	public void setServedTurn(int turn) {
		servedTurn = turn;
	}

	public int getServedTurn() {
		return servedTurn;
	}

	public int getBilling() {
		return billing;
	}
	
	public void setBilling(double multiplier) {
		billing = (int) (billing * multiplier);
	}
	
	public int placeOrder(MenuItem[] menu) {
		return customerID % menu.length;
	}
	
	public void applyWaitingPenalty() {
		if (satisfaction > 8) {
			satisfaction -= 8;
		} else {
			satisfaction = 0;
		}
	}
	
	public boolean wantsToLeave() {
		return satisfaction == 0;
	}
	
	public void recoverSatisfaction(int recovery) {
		satisfaction += recovery;
		if (satisfaction > 100) {
			satisfaction = 100;
		}
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
