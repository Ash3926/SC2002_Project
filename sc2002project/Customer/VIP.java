package SC2002_Project.sc2002project.Customer;

public class VIP extends Customer {
	
	public VIP(int custID) {
		super(custID);
		billing = 90;
	}
	
	@Override
	public void applyWaitingPenalty() {
		if (satisfaction > 5) {
			satisfaction -= 5;
		} else {
			satisfaction = 0;
		}
	}
}