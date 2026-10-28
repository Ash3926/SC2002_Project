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

        if (!canAct() || order == null) {
            return false;
        }

        OrderStatus status = order.getOrderStatus();

        // Only unfinished orders can be prepared
        if (status != OrderStatus.PLACED &&
            status != OrderStatus.PREPARING) {
            return false;
        }

        if (!order.prepareOneUnit()) {
            return false;
        }

        markTaskUsed();

        return true;
    }
}
