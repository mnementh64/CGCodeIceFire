import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class GameTest
{

	@Test
	public void test_create() throws Exception
	{
		String raw = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		Player.Game game = new Player.Game(0);
		game.createFromInputLines(new Scanner(raw));

		Assert.assertTrue(game.innerPlayers.size() == 3);
		Assert.assertTrue(game.innerPlayers.get(0).looters.length == 1);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].id == 0);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].x == -2430);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].y == 4794);
		Assert.assertTrue(game.innerPlayers.get(1).looters[0].id == 1);
		Assert.assertTrue(game.innerPlayers.get(2).looters[0].id == 2);
		Assert.assertTrue(game.wreckIdToWreckMap.size() == 3);
		Assert.assertTrue(game.wreckIdToWreckMap.get(4).x == -2983);
		Assert.assertTrue(game.wreckIdToWreckMap.get(4).y == -322);
	}

	@Test
	public void test_update() throws Exception
	{
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		Player.Game game = new Player.Game(0);
		game.createFromInputLines(new Scanner(step1));

		String step2 = "0 5 8 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 39 -3457 -412 -367 -1 -1 " +
				"2 0 2 0.5 400 751 -2747 -283 -95 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"6 4 -1 -1.0 600 723 -2912 0 0 1 -1 ";
		game.updateFromInputLines(new Scanner(step2));

		Assert.assertTrue(game.innerPlayers.size() == 3);
		Assert.assertTrue(game.innerPlayers.get(0).looters.length == 1);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].id == 0);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].x == -2430);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].y == 4794);
		Assert.assertTrue(game.innerPlayers.get(1).looters[0].id == 1);
		Assert.assertTrue(game.innerPlayers.get(1).looters[0].x == 39);
		Assert.assertTrue(game.innerPlayers.get(1).looters[0].y == -3457);
		Assert.assertTrue(game.innerPlayers.get(2).looters[0].id == 2);
		Assert.assertTrue(game.innerPlayers.get(2).looters[0].x == 751);
		Assert.assertTrue(game.innerPlayers.get(2).looters[0].y == -2747);
		Assert.assertTrue(game.wreckIdToWreckMap.size() == 4);
		Assert.assertTrue(game.wreckIdToWreckMap.get(4).x == -2983);
		Assert.assertTrue(game.wreckIdToWreckMap.get(4).y == -322);
		Assert.assertTrue(game.wreckIdToWreckMap.get(4).radius == 850);
		Assert.assertTrue(game.wreckIdToWreckMap.get(4).water == 9);
		Assert.assertTrue(game.wreckIdToWreckMap.get(6).x == 723);
		Assert.assertTrue(game.wreckIdToWreckMap.get(6).y == -2912);
		Assert.assertTrue(game.wreckIdToWreckMap.get(6).radius == 600);
		Assert.assertTrue(game.wreckIdToWreckMap.get(6).water == 1);
	}

	@Test
	public void test_simulate_two_turns_only_position() throws Exception
	{
		// init game
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		Player.Game game = new Player.Game(0);
		game.createFromInputLines(new Scanner(step1));

		// handle players outputs
		handleActions(game, new String[]
		{
				"WAIT", "WAIT", "WAIT",
				"1770 -2422 200", "WAIT", "WAIT",
				"1770 -2422 200", "WAIT", "WAIT"
		});

		// update game
		game.updateGame(2);
		assertLooperPosition(game, 0, -2430, 4794);
		assertLooperPosition(game, 1, -2571, -4339);
		assertLooperPosition(game, 2, 5023, -496);

		String step2 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 -2571 -4339 293 129 -1 -1 " +
				"2 0 2 0.5 400 5023 -496 -275 -163 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		game.updateFromInputLines(new Scanner(step2));
	}

	@Test
	public void test_simulate_three_turns_only_position() throws Exception
	{
		// init game
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		Player.Game game = new Player.Game(0);
		game.createFromInputLines(new Scanner(step1));

		// handle players outputs --------------------- ROUND 2
//		String step2 = "0 0 0 0 0 0 6 " +
//				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
//				"1 0 1 0.5 400 -2571 -4339 293 129 -1 -1 " +
//				"2 0 2 0.5 400 5023 -496 -275 -163 -1 -1 " +
//				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
//				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
//				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		handleActions(game, new String[]
		{
				"WAIT", "WAIT", "WAIT",
				"1770 -2422 200", "WAIT", "WAIT",
				"1770 -2422 200", "WAIT", "WAIT"
		});

		// update game
		game.updateGame(2);
		assertLooperPosition(game, 0, -2430, 4794);
		assertLooperPosition(game, 1, -2571, -4339);
		assertLooperPosition(game, 2, 5023, -496);

		// handle players outputs --------------------- ROUND 3
//		String step3 = "0 0 0 0 0 0 6 " +
//				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
//				"1 0 1 0.5 400 -1912 -4048 527 232 -1 -1 " +
//				"2 0 2 0.5 400 4404 -863 -495 -293 -1 -1 " +
//				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
//				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
//				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		handleActions(game, new String[]
		{
				"WAIT", "WAIT", "WAIT",
				"1770 -2422 200", "WAIT", "WAIT",
				"1770 -2422 200", "WAIT", "WAIT"
		});

		// update game
		game.updateGame(2);
		assertLooperPosition(game, 0, -2430, 4794);
		assertLooperPosition(game, 1, -1912, -4048);
		assertLooperPosition(game, 2, 4404, -863);

	}

	@Test
	public void test_simulate_multiple_turns() throws Exception
	{
		// init game
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		Player.Game game = new Player.Game(0);
		game.createFromInputLines(new Scanner(step1));

		// handle players outputs --------------------- ROUND 2
		for (int i = 1; i <= 11; i++)
		{
			handleActions(game, new String[]
			{
					"WAIT", "WAIT", "WAIT",
					"1770 -2422 200", "WAIT", "WAIT",
					"1770 -2422 200", "WAIT", "WAIT"
			});

			// update game
			game.updateGame(1 + i);
		}

		for (int i = 1; i <= 3; i++)
		{
			handleActions(game, new String[]
			{
					"WAIT", "WAIT", "WAIT",
					"723 -2912 200", "WAIT", "WAIT",
					"723 -2912 200", "WAIT", "WAIT"
			});

			// update game
			game.updateGame(12 + i);
		}

		String step = "0 5 8 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 39 -3457 -412 -367 -1 -1 " +
				"2 0 2 0.5 400 751 -2747 -283 -95 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"6 4 -1 -1.0 600 723 -2912 0 0 1 -1";
		assertLooperPosition(game, 0, -2430, 4794);
		assertLooperPosition(game, 1, 39, -3457);
		assertLooperPosition(game, 2, 751, -2747);
	}

	private void handleActions(Player.Game game, String[] outputs) throws Exception
	{
		String[] outputs0 = new String[]
		{ outputs[0], outputs[1], outputs[2] };
		game.handlePlayerOutput(1, 1, 0, outputs0);
		String[] outputs1 = new String[]
		{ outputs[3], outputs[4], outputs[5] };
		game.handlePlayerOutput(1, 1, 1, outputs1);
		String[] outputs2 = new String[]
		{ outputs[6], outputs[7], outputs[8] };
		game.handlePlayerOutput(1, 1, 2, outputs2);
	}

	private void assertLooperPosition(Player.Game game, int playerIndex, int x, int y)
	{
		System.out.println("Player " + playerIndex + " : " + game.looterIdToLooterMap.get(playerIndex).x + "," + game.looterIdToLooterMap.get(playerIndex).y);
		Assert.assertTrue(game.looterIdToLooterMap.get(playerIndex).x == x);
		Assert.assertTrue(game.looterIdToLooterMap.get(playerIndex).y == y);
	}
}