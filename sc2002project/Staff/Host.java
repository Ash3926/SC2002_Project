package SC2002_Project.sc2002project.Staff;

import SC2002_Project.sc2002project.Customer.*;
import SC2002_Project.sc2002project.Objects.*;

public class Host extends Staff {

    public Host() {
        super();
    }

    // Seat one customer at an empty table
    public boolean seatCustomer(Customer cust, Table table) {
        if (!canAct()) {
            System.out.println("Host has already acted this turn.");
            return false;
        }

        if (cust == null || table == null) {
            System.out.println("Invalid customer or table.");
            return false;
        }

        if (table.isOccupied()) {
            System.out.println("This table is occupied.");
            return false;
        }

        if (cust.getStatus() != CustomerStatus.WAITING) {
            System.out.println("This customer is not waiting.");
            return false;
        }

        table.occupy(cust);
        cust.changeStatus(CustomerStatus.SEATED);
        markTaskUsed();
        return true;
    }

    // Do nothing for this turn
    public void waiting() {
        markTaskUsed();
    }
}
