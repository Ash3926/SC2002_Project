package SC2002_Project.sc2002project;

public class Chef extends Staff {

    public Chef() {
        super();
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
