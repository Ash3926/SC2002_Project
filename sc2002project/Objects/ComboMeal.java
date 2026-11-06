package SC2002_Project.sc2002project.Objects;

public class ComboMeal extends MenuItem {

    private MenuItem firstItem;
    private MenuItem secondItem;

    public ComboMeal(MenuItem firstItem, MenuItem secondItem) {
        super(firstItem.getName() + " + " + secondItem.getName() + " Combo",
              (int) Math.round((firstItem.getPrice() + secondItem.getPrice()) * 0.9),
              firstItem.getPreparationUnits() + secondItem.getPreparationUnits());

        this.firstItem = firstItem;
        this.secondItem = secondItem;
    }

    public MenuItem getFirstItem() {
        return firstItem;
    }

    public MenuItem getSecondItem() {
        return secondItem;
    }
}
