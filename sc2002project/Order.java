package SC2002_Project.sc2002project;

public class Order {

	private int orderID;
	private int tableID;
    private Table table;
	private MenuItem item;
	private OrderStatus status = OrderStatus.PLACED;
	private int preparedUnits = 0;
	
    public Order(int orderID, Table table, MenuItem item) {
        if (table == null || item == null) {
            throw new IllegalArgumentException(
                "Table and menu item cannot be null."
            );
        }

        this.orderID = orderID;
        this.table = table;
        this.tableID = table.getTableID();
        this.item = item;
    }

	public int getOrderID() {
		return orderID;
	}
	
	public Customer getCustomer() {
		return table.getOccupant();
	}
	
	public MenuItem getItem() {
		return item;
	}
	
	public OrderStatus getOrderStatus() {
		return status;
	}
	
	public int getRemainingUnits() {
		return item.getPreparationUnits() - preparedUnits;
	}

    public boolean prepareOneUnit() {
        // Only unfinished orders can be prepared
        if (status != OrderStatus.PLACED &&
            status != OrderStatus.PREPARING) {
            return false;
        }

        if (preparedUnits >= item.getPreparationUnits()) {
            return false;
        }

        //else, increment preparedUnits by 1 and check if the order is complete
        //update status accordingly
        preparedUnits += 1;
        if (preparedUnits == item.getPreparationUnits()) {
            status = OrderStatus.READY;
        }
        else {
            status = OrderStatus.PREPARING;
        }
        return true;
    }
	
    public boolean serve() {
        //case where order is ready to serve
        if (status == OrderStatus.READY) {
            status = OrderStatus.SERVED;
            return true;
        }

        //else, the food has either already been served or is not ready to serve
        return false;
    }
    
    // Called by Cashier after payment
    public boolean markPaid() {

        if (status != OrderStatus.SERVED) {
            return false;
        }

        status = OrderStatus.PAID;
        return true;
    }

    public boolean cancel() {
        //case where order has already been completed (SERVED or PAID), so it can't be cancelled
        if (status == OrderStatus.SERVED) {
            return false;
        }
        if (status == OrderStatus.PAID) {
            return false;
        }

        //case where order is already CANCELLED 
        if (status == OrderStatus.CANCELLED) {
            return true;
        }

        status = OrderStatus.CANCELLED;
        return true;
    }
}
