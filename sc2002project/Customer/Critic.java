package SC2002_Project.sc2002project.Customer;

public class Critic extends Customer {
	
	public Critic(int custID){
		super(custID);
	}
	
	@Override
	public void applyWaitingPenalty() {
		satisfaction -= 12; //can go below 0
	}
	
	@Override
	public boolean wantsToLeave() {
		return false;
	}
}