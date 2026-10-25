package SC2002_Project.sc2002project;

public class MenuItem {

	private String name;
	private int price;
	private int preparationUnits;
	
	public MenuItem(String name, int price, int units) {
		this.name = name;
		this.price = price;
		preparationUnits = units;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getPreparationUnits() {
		return preparationUnits;
	}
}
