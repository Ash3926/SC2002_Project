package SC2002_Project.sc2002project.Objects;
import SC2002_Project.sc2002project.Customer.*;

public class Order {

	private int orderID;
	private int tableID;
    private Table table;
	private MenuItem item;
	private OrderStatus status = OrderStatus.PLACED;
	private int preparedUnits = 0;
	private int finalPrice;
	
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
		this.finalPrice = item.getPrice();
    }

	public int getOrderID() {
		return orderID;
	}

    public int getTableID() {
        return tableID;
    }
	
	public Customer getCustomer() {
		return table.getOccupant();
	}
	
	public MenuItem getItem() {
		return item;
	}

	public int getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(int price) {
		finalPrice = price;
	}
	
	public OrderStatus getOrderStatus() {
		return status;
	}
	
	public int getRemainingUnits() {
		return item.getPreparationUnits() - preparedUnits;
	}
	
	public void prepareOneUnit() {
		preparedUnits += 1;
		if (preparedUnits == item.getPreparationUnits()) {
			status = OrderStatus.READY;
		} else {
			status = OrderStatus.PREPARING;
		}
	}
	
    public void serve() {
        status = OrderStatus.SERVED;
    }
    
    // Called by Cashier after payment
    public boolean markPaid() {

        if (status != OrderStatus.SERVED) {
            return false;
        }

        status = OrderStatus.PAID;
        return true;
    }

    public void cancel() {
        status = OrderStatus.CANCELLED;
    }
	
	public void display() {
		System.out.print("Order " + orderID + " (");
		System.out.print("Status: " + status);
		System.out.print(", Item: " + item.getName());
		System.out.printf(", Units left: %d, Bill: $%.2f)%n",
                  getRemainingUnits(), finalPrice / 100.0);
	}
}
