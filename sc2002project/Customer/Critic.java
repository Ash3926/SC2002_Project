package SC2002_Project.sc2002project.Customer;

public class Critic extends Customer {
	
	public Critic(int custID){
		super(custID);
	}
	
	@Override
	public int applyWaitingPenalty() {
		satisfaction -= 12;
		return satisfaction;
	}
}