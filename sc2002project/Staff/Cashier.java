package SC2002_Project.sc2002project.Staff;

import SC2002_Project.sc2002project.Customer.*;
import SC2002_Project.sc2002project.Objects.*;
import SC2002_Project.sc2002project.GameStats;

public class Cashier extends Staff {

    public void handlePayment(Table[] tableList, Customer[] custList, GameStats stats) {

        // Find the first customer who is ready to pay
        for (int i = 0; i < custList.length; i++) {

            Customer cust = custList[i];

            if (cust != null && cust.getStatus() == CustomerStatus.READY_TO_PAY) {

                Table table = tableList[cust.getTableID()];
                Order order = table.getOrder();
                int bill = order.getItem().getPrice() * (cust.getBilling()/100);

                // Update customer and order
                cust.changeStatus(CustomerStatus.PAID);
                order.markPaid();

                // Update game statistics
                stats.increaseRevenue(bill);
                stats.incrementPaidCustomers();
                stats.updateTSatisfaction(cust.getSatisfaction());

                // Free the table
                table.release();

                System.out.println("Received $" + bill + " from Customer " + cust.getCustomerID());
                System.out.println("Table " + table.getTableID() + " is now free!");

                break;
            }
        }

        markTaskUsed();
    }
}
