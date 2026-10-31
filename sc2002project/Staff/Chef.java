package SC2002_Project.sc2002project.Staff;
import SC2002_Project.sc2002project.Objects.*;

public class Chef extends Staff {

    public Chef() {
        super();
    }
	
	// Wait, do nothing
	public void waiting() {
		markTaskUsed();
	}

    // Prepare one unit of an order
    public boolean prepareOrder(Order order) {
		// case 1: invalid order
        if (order == null) {
			System.out.println("There is no such order.");
            return false;
        }
        OrderStatus status = order.getOrderStatus();
        // case 2: order not placed or preparing
        if (status != OrderStatus.PLACED && status != OrderStatus.PREPARING) {
			System.out.println("There order cannot be prepared.");
            return false;
        }
		// successful cooking
		order.prepareOneUnit();
        markTaskUsed();
        return true;
    }
}
