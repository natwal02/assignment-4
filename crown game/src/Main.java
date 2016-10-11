import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		int age = 20;

		while (age >= 18) {

			Scanner reader = new Scanner(System.in); // Reading from System.in
			System.out.println("Enter your name: ");
			String name1 = reader.next(); // Scans the next token of the input
											// as string

			System.out.println("Enter your age: ");
			age = reader.nextInt(); // Scans the next token of the input as an
									// Int
			if (age >= 18) {
				BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

				Dice d1 = new Dice();
				Dice d2 = new Dice();
				Dice d3 = new Dice();

				Player player = new Player(name1, 100);
				Game game = new Game(d1, d2, d3);
				List<DiceValue> cdv = game.getDiceValues();

				int totalWins = 0;
				int totalLosses = 0;

				while (true) {
					int winCount = 0;
					int loseCount = 0;

					for (int i = 0; i < 100; i++) {
						int balance = 100;
						int limit = 0;
						player = new Player(name1, balance);
						player.setLimit(limit);
						int bet = 5;

						System.out.println(String.format("Start Game %d: ", i));
						System.out.println(String.format("%s starts with balance %d, limit %d", player.getName(),
								player.getBalance(), player.getLimit()));

						int turn = 0;
						while (player.balanceExceedsLimitBy(bet) && player.getBalance()> 0) {
							turn++;
							DiceValue pick = DiceValue.getRandom();

							System.out.printf("Turn %d: %s bet %d on %s\n", turn, player.getName(), bet, pick);

							int winnings = game.playRound(player, pick, bet);
							// Bug003 fix
							// If player wins and win/lose become >42, throw the
							// dice again and
							// change it before displaying the throw to user
							if (winnings > 0 && ((float) (winCount + 1) / ((winCount + 1) + loseCount)) > 0.42) {
								while (winnings > 0) {
									d1 = new Dice();
									d2 = new Dice();
									d3 = new Dice();
									game = new Game(d1, d2, d3);
									cdv = game.getDiceValues();
									winnings = game.playRound(player, pick, bet);
								}
							}
							cdv = game.getDiceValues();

							System.out.printf("Rolled %s, %s, %s\n", cdv.get(0), cdv.get(1), cdv.get(2));

							if (winnings > 0) {
								System.out.printf("%s won %d, balance now %d\n\n", player.getName(), winnings,
										player.getBalance());
								winCount++;
							} else {
								System.out.printf("%s lost, balance now %d\n\n", player.getName(), player.getBalance());
								loseCount++;
							}

						} // while

						System.out.print(String.format("%d turns later.\nEnd Game %d: ", turn, i));
						System.out.println(
								String.format("%s now has balance %d\n", player.getName(), player.getBalance()));

					} // for

					System.out.println(String.format("Win count = %d, Lose Count = %d, %.2f", winCount, loseCount,
							(float) winCount / (winCount + loseCount)));
					totalWins += winCount;
					totalLosses += loseCount;

					String ans = console.readLine();
					if (ans.equals("q"))
						break;
				} // while true

				System.out.println(String.format("Overall win rate = %.1f%%",
						(float) (totalWins * 100) / (totalWins + totalLosses)));

			}

		}

		System.out.println("Sorry,age should be grater than 18");

	}

}

