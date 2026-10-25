package sc2002project;

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
		occupant = customer;
		occupied = true;
		return true;
	}
	
	public void assignOrder(Order order) {
		this.order = order;
	}
	
	public void release() {
		occupied = false;
	}

}
