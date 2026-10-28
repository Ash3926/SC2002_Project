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

        if (!canAct() || cust == null || table == null) {
            return false;
        }

        if (table.isOccupied() ||
            cust.getStatus() != CustomerStatus.WAITING) {
            return false;
        }

        if (!table.occupy(cust)) {
            return false;
        }

        cust.changeStatus(CustomerStatus.SEATED);
        markTaskUsed();

        return true;
    }

    // Take an order from a seated customer
    public Order takeOrder(Table table, MenuItem[] menu) {
		Customer cust = table.getOccupant();

        // if (!canAct() || cust == null ||
            // table == null || menu == null) {
            // return null;
        // }

        // if (cust.getStatus() != CustomerStatus.SEATED ||
            // table.getOccupant() != cust ||
            // table.getOrder() != null) {
            // return null;
        // }

        // Customer chooses their food
        int choice = cust.placeOrder();

        // if (choice < 0 || choice >= 3 ||
            // menu[choice] == null) {
            // return null;
        // }

        Order order = new Order(nextOrderID, table, menu[choice]);

        table.assignOrder(order);
        nextOrderID++;
        markTaskUsed();

        return order;
    }

    // Serve a completed order
    public boolean serveOrder(Order order) {

        if (!canAct() || order == null) {
            return false;
        }

        if (order.getOrderStatus() != OrderStatus.READY) {
            return false;
        }

        Customer cust = order.getCustomer();

        if (cust == null ||
            cust.getStatus() != CustomerStatus.SEATED) {
            return false;
        }

        if (!order.serve()) {
            return false;
        }

        cust.changeStatus(CustomerStatus.EATING);
        markTaskUsed();

        return true;
    }
}
