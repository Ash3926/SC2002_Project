package SC2002_Project.sc2002project;
import java.util.Scanner;
import java.util.ArrayList;

public class RestaurantGame {

	private GameStats currStats;
	private Table[] tableList;
	private Customer[] custList;
	private MenuItem[] menu;
	private Staff[] staffList;
	private Order[] orderList;
	private int custIndex = 0;

	// Constructor method
	public RestaurantGame() {
		currStats = new GameStats();
		tableList = new Table[2];
		for (int i=0; i<2; i++) {
			tableList[i] = new Table(i);
		}
		custList =  new Customer[10];
		staffList = new Staff[] {new Cashier(), new Waiter(), new Chef()}; //arranged in order of task flow
		orderList = new Order[10];

		// Initialize menu
		menu = new MenuItem[] {new MenuItem("Salad", 9, 1), new MenuItem("Burger", 15, 1), new MenuItem("Pasta", 18, 2)};
	}
	
	public int getCurrTurn() {
		return currStats.getCurrTurn();
	}
	
	public void nextTurn() {
		currStats.incrementTurn();
	}

	public void startTurnDisplay() {
		
		// Display stats
		System.out.println("\n==================================================");
		System.out.println("Current turn: " + currStats.getCurrTurn());
		System.out.println("Revenue: " + currStats.getTRevenue());
		System.out.println("Average satisfaction of paid customers: " + currStats.calcAvgSatisfaction());
		
		// Customer arrival, if any
		customerArrival();

		// Waiting queue
		System.out.println("\nQueue:");
		for (int i=0; i<10; i++) {
			if (custList[i] != null && custList[i].getStatus() == CustomerStatus.WAITING) {
				custList[i].display();
			}
		}

		// Occupied tables
		System.out.println("\nTables:");
		for (int i=0; i<2; i++) {
			System.out.println("------------------------------");
			Table t = tableList[i];
			t.display();
			
			// If occupied, display customer
			if (t.isOccupied()) {
				t.getOccupant().display();
			}
			System.out.println("------------------------------");
		}
		
		// Order status
		System.out.println("\nOrders:");
		for (int i=0; i<10; i++) {
			if (orderList[i] != null) {
				orderList[i].display();
			}
		}

		//staff tasks available 
		//i'm actually not entirely sure what they mean by this lol isn't this always the same?
		System.out.println("\nStaff tasks available:");
		System.out.println("Waiter: \n1. Seat one queued customer at a free table \n2. Take one seated customer's order\n3. Serve one READY order\n4. Wait");
		System.out.println("Chef: \n1. Choose one order to advance by 1 preparation unit\n2. Wait");

	}
	
	// Customer arrival at turn 1, 4, 7, 10, 13, 16
	public void customerArrival() {
		int turn = currStats.getCurrTurn();
		if (turn==1 || turn==4 || turn==7 || turn==10 || turn==13 || turn==16) {
			System.out.println("\nCustomer " + custIndex + " has arrived!");
			Customer c = new Customer(custIndex);
			custList[custIndex] = c;
			custIndex += 1;
		}
	}

	// Waiter choice this turn
	public void waiterChoice(Scanner sc, int choice) {
		Waiter w = (Waiter) staffList[1];
		w.resetForTurn();

		while(w.canAct()) {
			if (choice<1 || choice>4) {
				System.out.println("Invalid input");
				break;
			}

			switch(choice) {
				
				// Seat customer
				case 1:
				
					// No free tables
					if (tableList[0].isOccupied() && tableList[1].isOccupied()) {
						System.out.println("No free tables.");
						
					// 
					} else {
						System.out.print("Customer ID to seat: ");
						Integer custID = Integer.parseInt(sc.nextLine());
						System.out.print("Table ID to seat this customer at: ");
						Integer tableID = Integer.parseInt(sc.nextLine());
						w.seatCustomer(custList[custID], tableList[tableID]);
						System.out.println("Customer seated!");
					}
					break;

				// Take order
				case 2:
					System.out.print("Table ID to take order from: ");
					Integer tableID1 = Integer.parseInt(sc.nextLine());
					Order order = w.takeOrder(tableList[tableID1], menu);
					orderList[order.getOrderID()] = order;
					System.out.println("Order placed!");
					order.display();
					break;


				// Serve order
				case 3:
					System.out.print("Order ID to serve: ");
					Integer orderID = Integer.parseInt(sc.nextLine());
					if (orderList[orderID] != null) {
						w.serveOrder(orderList[orderID]);
						System.out.println("Order served!");
					}
					break;

				// Wait
				case 4:
					w.waiting();
					System.out.println("Waiter waited this turn.");
					break;
			}
		}
	}

	public void chefChoice(Scanner sc, int choice) {
		Chef c = (Chef) staffList[2];
		c.resetForTurn();

		while(c.canAct()) {
			if (choice<1 || choice>2) {
				System.out.println("Invalid input");
				break;
			}

			switch (choice) {
				
				// Cook
				case 1: 
					System.out.print("Order ID to advance by 1 preparation unit: ");
					Integer orderID = Integer.parseInt(sc.nextLine());
					if (orderList[orderID] != null) {
						c.prepareOrder(orderList[orderID]);
						System.out.println("Order prepared!");
					}
					break;

				// Wait
				case 2: 
					c.waiting();
					System.out.println("Chef waited a turn.");
					break;
			}
		}
	}

	// Cashier action this turn
	public void cashierAction() {
		Cashier cash = (Cashier) staffList[0];
		cash.resetForTurn();
		while (cash.canAct()) {
			cash.handlePayment(tableList, custList, currStats);
		}
	}
	
	// Customer spend 1 turn eating, become ready to pay
	public void customerEat() {
		
		// Identify all eating customers
		for (int i=0; i<10; i++) {
			if (custList[i] != null && custList[i].getStatus() == CustomerStatus.EATING) {
				custList[i].changeStatus(CustomerStatus.READY_TO_PAY);
			}
		}
	}
	
	// Apply waiting penalty, make customer leave is satisfaction is subzero
	public void updateSatisfaction() {
		
		// Iterate through every customer
		for (int i=0; i<10; i++) {
			Customer cust = custList[i];
			if (cust != null) {
				
				// Waiting in queue
				if (cust.getStatus() == CustomerStatus.WAITING) {
					if (cust.applyWaitingPenalty() == 0) {
						cust.changeStatus(CustomerStatus.LEFT);
					}
					
				// Seated at table
				} else if (cust.getStatus() == CustomerStatus.SEATED) {
					if (cust.applyWaitingPenalty() == 0) {
						cust.changeStatus(CustomerStatus.LEFT);
						Table t = tableList[cust.getTableID()];
						Order o = t.getOrder();
						t.release();
						o.cancel();
					}
					
				}
			}
		}
	}

}
