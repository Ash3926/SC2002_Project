package SC2002_Project.sc2002project;
import java.util.Scanner;

public class RestaurantUI {

	private GameStats currStats;
	private Table[] tableList;
	private Customer[] custList;
	private MenuItem[] menu;
	private Staff[] staffList;
	private Order[] orderList;
	private int orderIndex = 0;
	
	private static final Scanner sc = new Scanner(System.in);

	public RestaurantUI() {
		//constructor class
		
		currStats = new GameStats();
		tableList = new Table[2]; 
		for (int i=0; i<2; i++) {
			tableList[i] = new Table(i);
		}
		custList =  new Customer[10];
		staffList = new Staff[] {new Cashier(), new Waiter(), new Chef()}; //arranged in order of task flow
		orderList = new Order[10];

		//initialise menu
		menu = new MenuItem[] {new MenuItem("Salad", 9, 1), new MenuItem("Burger", 15, 1), new MenuItem("Pasta", 18, 2)};
		
	}

	public void startTurnDisplay() {
		//display current turn no
		//revenue
		//avg satisfaction of paid customers  
		
		System.out.println("Current turn: " + currStats.getCurrTurn());
		System.out.println("Revenue: " + currStats.getTRevenue());
		System.out.println("Average satisfaction of paid customers: " + currStats.calcAvgSatisfaction());

		//waiting queue
		
		for (int i=0; i<10; i++) {
			if (custList[i] != null) {
				if (custList[i].getStatus() == CustomerStatus.WAITING) {
					custList[i].display();
				}
			} 
		}

		//occupied tables
		for (int i=0; i<2; i++) {
			if (tableList[i].isOccupied()) {
				tableList[i].display();
			}
		}

		//each customer's satisfaction + order status
		for (int i=0; i<10; i++) {
			if (custList[i] != null) {
				System.out.println("Customer ID " + custList[i].getCustomerID() + " has a satisfaction level of " + custList[i].getSatisfaction());
				System.out.println("Order status: " + custList[i].getStatus());
			} 
		}

		//staff tasks available 
		//i'm actually not entirely sure what they mean by this lol isn't this always the same?
		System.out.println("Staff tasks available:");
		System.out.println("Waiter: \n1. Seat one queued customer at a free table \n2. Take one seated customer's order\n3.Serve one READY order\n4. Wait");
		System.out.println("Chef: \n1. Choose one order to advance by 1 preparation unit\n2. Wait");

	}

	public void waiterChoice(Waiter w) {
		boolean action = false;

		while(!action) {
			System.out.println("Pick an action for Waiter to take (enter an integer between 1 and 4): ");
			int choice = sc.nextInt();
			if (choice<1 || choice>4) {
				break;
			}

			switch(choice) {
				case 1: 
					System.out.println("Select a customer ID to seat: ");
					int custID = sc.nextInt();
					System.out.println("Select a table to seat this customer at: ");
					int tableID = sc.nextInt();
					action = w.seatCustomer(custList[custID], tableList[tableID]);
					break;

				case 2:
					System.out.println("Select a table ID to take order from: ");
					int tableID1 = sc.nextInt();
					Order order = w.takeOrder(tableList[tableID1].getOccupant(), tableList[tableID1], menu);


					if (order!=null) {
						action = true;
						orderList[orderIndex] = order;
						orderIndex += 1;
					}
					break;


				case 3:
					System.out.println("Select orderID of order to serve: ");
					int orderID = sc.nextInt();
					for (int i=0; i<10; i++) {
						if (orderList[i] != null) {
							if (orderList[i].getOrderID() == orderID) {
								action = orderList[i].serve();
							}
						}
					}
					break;

				case 4: action = true; break;
			}
		}
	}

	public void chefChoice(Chef c) {
		boolean action = false;

		while(!action) {
			System.out.println("Pick an action for Chef to take (enter an integer between 1 and 2): ");
			int choice = sc.nextInt();
			if (choice<1 || choice>2) {
				break;
			}

			switch(choice) {
				case 1: 
					System.out.println("Select order ID of order to advance by 1 preparation unit: ");
					int orderID = sc.nextInt();

					for (int i=0; i<10; i++) {
						if (orderList[i] != null) {
							if (orderList[i].getOrderID() == orderID) {
								action = orderList[i].prepareOneUnit();
							}
						}
					}
					break;

				case 2: action = true; break;
			}
		}
	}

	//not implementing customerStatusUpdates? not entirely sure what that does ngl
	//public void customerSatisfactioUpdates() {}

	public void updateSatisfaction() {
		//apply waiting penalty and make customer leave is satisfaction is subzero

		for (int i=0; i<10; i++) {
			if (custList[i] != null) {
				int satisfaction = custList[i].applyWaitingPenalty();
				if (satisfaction == 0) {
					//customer leaves
					//TODO: implement something to cancel this customer's order and free any table they are seated at
					custList[i] = null;
				}
			} 
		}
	}

	public void cashierAction() {
		//TODO: implement Cashier class and implement this accordingly
	}

}
