package SC2002_Project.sc2002project;

public class Order {

	private int orderID;
	private int tableID; 
	private MenuItem item;
	private OrderStatus status = OrderStatus.PLACED;
	private int preparedUnits = 0;
	
	public Order(int orderID, int tableID, int choice) {
	    this.orderID = orderID;
		this.tableID = tableID;
		
        /*
        if choice == 1: Salad 
        else if choice == 2: Burger
        else if choice == 3: Pasta
        */

        if (choice == 1) {
            item = menu[0]
        }
        else if (choice == 2) {
            item = menu[1]
        }
        else if (choice == 3) {
            item = menu[2]
        }
	}
	
	public int getOrderID() {
		return orderID;
	}
	
	public Customer getCustomer() {
		return tableList[tableID].getOccupant();
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

        //case where the order is already prepared
        if (status == OrderStatus.READY) {
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
