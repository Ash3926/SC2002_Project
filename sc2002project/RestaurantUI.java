package SC2002_Project.sc2002project;
import java.util.Scanner;

public class RestaurantUI {
	
	public void main(String[] args) {
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
			String w = sc.nextLine();
			game.waiterChoice(sc, w);
			
			// Chef
			System.out.print("\nChef action: ");
			String c = sc.nextLine();
			game.chefChoice(sc, c);
			
			game.updateSatisfaction();
			game.nextTurn();
		}
		sc.close();
	}
	
}