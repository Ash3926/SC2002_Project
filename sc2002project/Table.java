package SC2002_Project.sc2002project;

public class Table {
	private int tableID;
	private Customer occupant;
	private Order order;
	private boolean occupied;
	
	public Table(int tableID) {
		this.tableID = tableID;
		occupied = false;
	}
	
	public int getTableID() {
		return tableID;
	}
	
	public Customer getOccupant() {
		return occupant;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public boolean occupy(Customer customer) {
		customer.setTableID(tableID);
		occupant = customer;
		occupied = true;
		return true;
	}
	
	public void assignOrder(Order order) {
		this.order = order;
	}
	
	public void release() {
		occupied = false;
		occupant = null;
		order = null;
	}
	
	public void display() {
		System.out.print("Table ID: " + tableID);
		if (occupied) {
			System.out.println(" - Occupied");
		} else {
			System.out.println(" - Not occupied");
		}
	}

}
