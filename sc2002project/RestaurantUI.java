package SC2002_Project.sc2002project;
import java.util.Scanner;
import SC2002_Project.sc2002project.Customer.*;
import SC2002_Project.sc2002project.Objects.*;
import SC2002_Project.sc2002project.Staff.*;

public class RestaurantUI {
	
	public static void main(String[] args) {
		System.out.println("Starting Restaurant Rush game...\nWelcome, Manager!");
		Scanner sc = new Scanner(System.in);
		RestaurantGame game = new RestaurantGame();

		// Game runs for 18 turns
		while (game.getCurrTurn() <= 18) {

			game.startTurnDisplay();

			// Waiter chooses an action
			System.out.print("\nWaiter action: ");
			game.waiterChoice(sc, sc.nextLine());

			// Chef chooses an action
			System.out.print("\nChef action: ");
			game.chefChoice(sc, sc.nextLine());

			// Cashier handles payment
			game.cashierAction();

			// Customers finish eating
			game.customerEat();

			// Update satisfaction
			game.updateSatisfaction();

			// Display results and move to next turn
			game.endTurnDisplay();
			game.nextTurn();
		}

		game.endTurnDisplay();
		sc.close();
	}
}