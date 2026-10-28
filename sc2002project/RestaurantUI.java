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
			game.cashierAction();
			game.customerEat();
			
			// Waiter
			System.out.print("\nWaiter action: " );
			game.waiterChoice(sc, sc.nextLine());
			
			// Chef
			System.out.print("\nChef action: ");
			game.chefChoice(sc, sc.nextLine());
			
			game.updateSatisfaction();
			game.endTurnDisplay();
			game.nextTurn();
		}
		game.endGameDisplay();
		sc.close();
	}
	
}