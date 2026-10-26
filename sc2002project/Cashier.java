package SC2002_Project.sc2002project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Cashier extends Staff {

    // Records when each order first became READY_TO_PAY
    private final Map<Order, Integer> readySinceTurn = new HashMap<>();

    public Cashier() {
        super();
    }

    public boolean processPayment(Table[] tables, GameStats stats) {

        if (!canAct() || tables == null || stats == null) {
            return false;
        }

        Table selectedTable = null;
        Order selectedOrder = null;
        Customer selectedCustomer = null;
        int earliestReadyTurn = Integer.MAX_VALUE;

        Set<Order> currentlyReady = new HashSet<>();

        // Find the customer who has waited longest to pay
        for (Table table : tables) {

            if (table == null || !table.isOccupied()) {
                continue;
            }

            Customer customer = table.getOccupant();
            Order order = table.getOrder();

            if (customer == null || order == null ||
                customer.getStatus() != CustomerStatus.READY_TO_PAY ||
                order.getOrderStatus() != OrderStatus.SERVED) {
                continue;
            }

            currentlyReady.add(order);
            readySinceTurn.putIfAbsent(order, stats.getCurrTurn());

            int readyTurn = readySinceTurn.get(order);

            // Cannot pay during the turn they became ready
            if (readyTurn >= stats.getCurrTurn()) {
                continue;
            }

            if (selectedOrder == null ||
                readyTurn < earliestReadyTurn ||
                (readyTurn == earliestReadyTurn &&
                 order.getOrderID() < selectedOrder.getOrderID())) {

                selectedTable = table;
                selectedOrder = order;
                selectedCustomer = customer;
                earliestReadyTurn = readyTurn;
            }
        }

        // Remove orders that are no longer waiting for payment
        readySinceTurn.keySet().retainAll(currentlyReady);

        if (selectedOrder == null) {
            return false;
        }

        // Process payment
        if (!selectedOrder.markPaid()) {
            return false;
        }

        selectedCustomer.changeStatus(CustomerStatus.PAID);

        stats.increaseRevenue(selectedOrder.getItem().getPrice());
        stats.incrementPaidCustomers();
        stats.updateTSatisfaction(selectedCustomer.getSatisfaction());

        // Free the occupied table
        selectedTable.release();

        readySinceTurn.remove(selectedOrder);
        markTaskUsed();

        return true;
    }
}
