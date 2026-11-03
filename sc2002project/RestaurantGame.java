package SC2002_Project.sc2002project;
import SC2002_Project.sc2002project.Customer.*;
import SC2002_Project.sc2002project.Staff.*;
import SC2002_Project.sc2002project.Objects.*;
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
	private int happyHourTurns = 3;

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
		if (isHappyHour()) {
			happyHourTurns -= 1;
		}
	}

	public void startTurnDisplay() {
		
		// Display stats
		System.out.println("\n==================================================");
		System.out.println("Current turn: " + currStats.getCurrTurn());
		System.out.println("Revenue: " + currStats.getTRevenue());
		int avgSatisfaction = currStats.calcAvgSatisfaction();
		if (currStats.getPaidCustomers() == 0) {
			System.out.println("Average satisfaction of paid customers: N/A");
		}
		else {
			System.out.println("Average satisfaction of paid customers: " + avgSatisfaction);
		}
		
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
		System.out.println("\nStaff tasks available:");
		System.out.println("Waiter: \n1. Seat one queued customer at a free table \n2. Take one seated customer's order\n3. Serve one READY order\n4. Wait");
		System.out.println("Chef: \n1. Choose one order to advance by 1 preparation unit\n2. Wait");

	}
	
	public boolean isHappyHour() {
		return (happyHourTurns == 1 || happyHourTurns == 2);
	}
	
	public void happyHour(Scanner sc) {
		if (happyHourTurns == 3) {
			System.out.print("\nWould you like to activate Happy Hour for 2 turns (once per game)? [Y/N]: ");
			String choice = sc.nextLine();
			while (!choice.equals("Y") && !choice.equals("N")) {
				System.out.print("Please enter either 'Y' or 'N': ");
				choice = sc.nextLine();
			}
			if (choice.equals("Y")) {
				happyHourTurns -= 1;
				System.out.println("Happy Hour activated!");
			}
		} else if (happyHourTurns == 0) {
			System.out.println("\nYou can no longer activate Happy Hour.");
		}
		if (isHappyHour()) {
			System.out.println("\nHappy Hour turns left: " + happyHourTurns);
			
			for (int i=0; i<10; i++) {
				Customer cust = custList[i];
				if (cust != null) {
					if (cust.getStatus() == CustomerStatus.WAITING || cust.getStatus() == CustomerStatus.SEATED) {
						cust.recoverSatisfaction(15);
					}
				}
			}
			System.out.println("Waiting and seated customers recovered 15 satisfaction!");
		}
	}
	
	// Customer arrival at turn 1, 4, 7, 10, 13, 16
	// Turn 4 introduces the Critic
	// Turn 10 introduces the VIP
	public void customerArrival() {
		int turn = currStats.getCurrTurn();
		if (turn==1 || turn==4 || turn==7 || turn==10 || turn==13 || turn==16) {
			Customer c;
			if (turn==4){
				System.out.println("\nCritic Customer " + custIndex + " has arrived!");
				System.out.println("Critic Customers do not leave until they have been served, and lose satisfaction faster!");
				System.out.println("Managerial Board Hint: Get Karen outta here!");
				c = new Critic(custIndex);
			}
			else if (turn==10){
				System.out.println("\nVIP Customer " + custIndex + " has arrived!");
				System.out.println("VIP Customers lose less satisfaction per turn, but pay 10% less!");
				c = new VIP(custIndex);
			}
			else {
				System.out.println("\nCustomer " + custIndex + " has arrived!");
				c = new Customer(custIndex);
			}
			custList[custIndex] = c;
			custIndex += 1;
		}
	}
	
	// Method to return a valid int input and in range
	public int validIntegerRange(Scanner sc, String input, int start, int end) {
		while (true) {
			try {
				int i = Integer.parseInt(input);
				if (i<start || i>end) {
					System.out.print("Please enter an integer within the range " + start + "-" + end + ": ");
					input = sc.nextLine();
				} else {
					return Integer.parseInt(input);
				}
			}
			catch (NumberFormatException e) {
				System.out.print("Please enter a valid integer: ");
				input = sc.nextLine();
			}
		}
		
	}

	// Waiter choice this turn
	public void waiterChoice(Scanner sc, String input) {
		Waiter w = (Waiter) staffList[1];
		w.resetForTurn();
		int choice = validIntegerRange(sc, input, 1, 4);
		
		while(w.canAct()) {

			switch(choice) {
				
				// Seat customer
				case 1:
					System.out.print("Customer ID to seat: ");
					int custID = validIntegerRange(sc, sc.nextLine(), 0, 9);
					System.out.print("Table ID to seat this customer at: ");
					int tabID_1 = validIntegerRange(sc, sc.nextLine(), 0, 1);
					// attempt to seat, if error let player choose action again
					if (w.seatCustomer(custList[custID], tableList[tabID_1])) {
						System.out.println("Customer seated!");
					} else {
						System.out.println("Please try again.");
						System.out.print("\nWaiter action: " );
						choice = validIntegerRange(sc, sc.nextLine(), 1, 4);
					}
					break;
					
				// Take order
				case 2:
					System.out.print("Table ID to take order from: ");
					int tabID_2 = validIntegerRange(sc, sc.nextLine(), 0, 1);
					// attempt to take order
					Table table = tableList[tabID_2];
					Order order = w.takeOrder(table, menu);
					if (order != null) {
						orderList[order.getOrderID()] = order;
						System.out.println("Order placed!");
						order.display();
						if (isHappyHour()) {
							Customer cust = table.getOccupant();
							cust.setBilling(0.8);
						}
					} else {
						System.out.println("Please try again.");
						System.out.print("\nWaiter action: " );
						choice = validIntegerRange(sc, sc.nextLine(), 1, 4);
					}
					break;


				// Serve order
				case 3:
					System.out.print("Order ID to serve: ");
					int ordID = validIntegerRange(sc, sc.nextLine(), 0, 9);
					// attempt to serve
					if (w.serveOrder(orderList[ordID])) {
						System.out.println("Order served!");
						currStats.incrementServedCustomers();
						orderList[ordID].getCustomer().setServedTurn(currStats.getCurrTurn());
					} else {
						System.out.println("Please try again.");
						System.out.print("\nWaiter action: " );
						choice = validIntegerRange(sc, sc.nextLine(), 1, 4);
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

	// Chef choice this turn
	public void chefChoice(Scanner sc, String input) {
		Chef c = (Chef) staffList[2];
		c.resetForTurn();
		int choice = validIntegerRange(sc, input, 1, 2);

		while(c.canAct()) {

			switch (choice) {
				
				// Cook
				case 1: 
					System.out.print("Order ID to advance by 1 preparation unit: ");
					int ordID = validIntegerRange(sc, sc.nextLine(), 0, 9);
					// attempt to cook
					if (c.prepareOrder(orderList[ordID])) {
						System.out.println("Order prepared!");
					} else {
						System.out.println("Please try again.");
						System.out.print("\nChef action: " );
						choice = validIntegerRange(sc, sc.nextLine(), 1, 2);
					}
					break;

				// Wait
				case 2: 
					c.waiting();
					System.out.println("Chef waited this turn.");
					break;
			}
		}
	}

	// Cashier action this turn
	public void cashierAction() {
		Cashier cash = (Cashier) staffList[0];
		cash.resetForTurn();
		cash.handlePayment(tableList, custList, currStats);
	}
	
	// Customer spend 1 turn eating, become ready to pay
	public void customerEat() {
		for (int i = 0; i < custList.length; i++) {
			Customer cust = custList[i];
			if (cust != null && cust.getStatus() == CustomerStatus.EATING) {
				if (cust.getServedTurn() < currStats.getCurrTurn()) {
					cust.changeStatus(CustomerStatus.READY_TO_PAY);
				}
			}
		}
	}
	
	// Apply waiting penalty, make customer leave if satisfaction is subzero
	public void updateSatisfaction() {
		
		// Iterate through every customer
		for (int i=0; i<10; i++) {
			Customer cust = custList[i];
			if (cust != null) {
				
				// Waiting in queue
				if (cust.getStatus() == CustomerStatus.WAITING) {
					if (cust.applyWaitingPenalty() == 0) {
						cust.changeStatus(CustomerStatus.LEFT);
						currStats.incrementUnhappyDepartures();
						System.out.println("\nCustomer " + cust.getCustomerID() + " has left!");
					}
					
				// Seated at table
				} else if (cust.getStatus() == CustomerStatus.SEATED) {
					if (cust.applyWaitingPenalty() == 0) {
						cust.changeStatus(CustomerStatus.LEFT);
						Table t = tableList[cust.getTableID()];
						Order o = t.getOrder();
						if (o != null) {
							o.cancel();
						}
						t.release();
						currStats.incrementUnhappyDepartures();
						System.out.println("\nCustomer " + cust.getCustomerID() + " has left!");
					}
				}
			}
		}
	}

	public void endTurnDisplay() {
		System.out.println("\n================== Turn " + currStats.getCurrTurn() + " over ====================");
		//served count
		System.out.println("Number of customers served: " + currStats.getTServedCustomers());
		//unhappy departures
		System.out.println("Number of unhappy departures: " + currStats.getTUnhappyDepartures());
		//revenue
		System.out.println("Total Revenue: " + currStats.getTRevenue());
		//average satisfaction of paid customers
		int avgSatisfaction = currStats.calcAvgSatisfaction();
		if (currStats.getPaidCustomers() == 0) {
			System.out.println("Average satisfaction of paid customers: N/A");
		}
		else {
			System.out.println("Average satisfaction of paid customers: " + avgSatisfaction);
		}
	}

	
	public void endGameDisplay() {

		System.out.println("\n=================== GAME OVER ====================");

		// Check victory conditions
		if (currStats.getPaidCustomers() >= 4 &&
			currStats.getTRevenue() >= 45 &&
			currStats.calcAvgSatisfaction() >= 60) {

			System.out.println("VICTORY!");

		} else {
			System.out.println("DEFEAT.");
		}

		// Display final statistics
		System.out.println("\nSTATISTICS!");
		System.out.println("Total revenue: " + currStats.getTRevenue());
		System.out.println("Total customers served: " + currStats.getTServedCustomers());
		System.out.println("Total unhappy departures: " + currStats.getTUnhappyDepartures());

		if (currStats.getPaidCustomers() == 0) {
			System.out.println("Average satisfaction of paid customers: N/A");
		} else {
			System.out.println("Average satisfaction of paid customers: " + currStats.calcAvgSatisfaction());
		}

		System.out.println("\nThank you for playing Papa's Western Fooderia!");
	}

		
}
