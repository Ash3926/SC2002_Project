package SC2002_Project.sc2002project.Staff;
import SC2002_Project.sc2002project.Customer.*;
import SC2002_Project.sc2002project.Objects.*;

public class Waiter extends Staff {

    private int nextOrderID = 0;

    public Waiter() {
        super();
    }
	
	// Wait, do nothing
	public void waiting() {
		markTaskUsed();
	}

    // Seat a customer at an available table
    public boolean seatCustomer(Customer cust, Table table) {
		// case 1: invalid customer
        if (cust == null) {
			System.out.println("There is no such customer.");
            return false;
        }
		// case 2: occupied table or customer not in waiting queue
        if (table.isOccupied()) {
			System.out.println("This table is occupied.");
            return false;
        }
		// case 3: customer not in waiting queue
		if (cust.getStatus() != CustomerStatus.WAITING) {
			System.out.println("This customer is not in the waiting queue.");
			return false;
		}
		// successful seating
		table.occupy(cust);
        cust.changeStatus(CustomerStatus.SEATED);
        markTaskUsed();
        return true;
    }

    // Take an order from a seated customer
    public Order takeOrder(Table table, MenuItem[] menu) {
		Customer cust = table.getOccupant();
		// case 1: invalid customer
        if (cust == null) {
			System.out.println("There is no customer at this table.");
            return null;
        }
		// case 2: table already ordered
		if (table.getOrder() != null) {
			System.out.println("This table has already ordered.");
			return null;
		}
        // successful ordering
        int choice = cust.placeOrder(menu);
        Order order = new Order(nextOrderID, table, menu[choice]);
        table.assignOrder(order);
        nextOrderID++;
        markTaskUsed();
        return order;
    }

    // Serve a completed order
    public boolean serveOrder(Order order) {
		// case 1: invalid order
        if (order == null) {
			System.out.println("There is no such order.");
            return false;
        }
		// case 2: order is not ready to be served
        if (order.getOrderStatus() != OrderStatus.READY) {
			System.out.println("This order cannot be served.");
            return false;
        }
		
		Customer cust = order.getCustomer();
        if (cust == null) {
			System.out.println("There is no customer for this order.");
			return false;
		}
		// successful serving
		order.serve();
        cust.changeStatus(CustomerStatus.EATING);
        markTaskUsed();
        return true;
    }
}
