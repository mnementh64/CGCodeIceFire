import java.util.Arrays;
import java.util.List;
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
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
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
	public void test_create_full() throws Exception
	{
		String raw = "0 0 0 0 0 0 12 " +
				"0 0 0 0.5 400 -1168 3138 0 0 -1 -1 " +
				"1 1 0 1.5 400 1366 2658 0 0 -1 -1 " +
				"2 2 0 1.0 400 2978 1517 0 0 -1 -1 " +
				"3 0 1 0.5 400 -2133 -2581 0 0 -1 -1 " +
				"4 1 1 1.5 400 -2985 -146 0 0 -1 -1 " +
				"5 2 1 1.0 400 -2803 1821 0 0 -1 -1 " +
				"6 0 2 0.5 400 3301 -557 0 0 -1 -1 " +
				"7 1 2 1.5 400 1619 -2512 0 0 -1 -1 " +
				"8 2 2 1.0 400 -176 -3338 0 0 -1 -1 " +
				"9 3 -1 3.0 600 8581 569 -399 -26 1 4 " +
				"10 3 -1 3.0 600 -4783 7147 222 -332 1 4 " +
				"11 3 -1 3.0 600 -3798 -7716 177 359 1 4";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));

		Assert.assertTrue(game.innerPlayers.size() == 3);
		Assert.assertTrue(game.innerPlayers.get(0).looters.length == 3);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].id == 0);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].x == -1168);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].y == 3138);
		Assert.assertTrue(game.innerPlayers.get(0).looters[1].id == 1);
		Assert.assertTrue(game.innerPlayers.get(0).looters[1].x == 1366);
		Assert.assertTrue(game.innerPlayers.get(0).looters[1].y == 2658);
		Assert.assertTrue(game.innerPlayers.get(0).looters[2].id == 2);
		Assert.assertTrue(game.innerPlayers.get(0).looters[2].x == 2978);
		Assert.assertTrue(game.innerPlayers.get(0).looters[2].y == 1517);

		Assert.assertTrue(game.tankerIdToTankerMap.size() == 3);
		Assert.assertTrue(game.tankerIdToTankerMap.get(9).x == 8581);
		Assert.assertTrue(game.tankerIdToTankerMap.get(9).y == 569);
		Assert.assertTrue(game.tankerIdToTankerMap.get(9).water == 1);
		Assert.assertTrue(game.tankerIdToTankerMap.get(9).size == 4);
		Assert.assertTrue(game.tankerIdToTankerMap.get(11).x == -3798);
		Assert.assertTrue(game.tankerIdToTankerMap.get(11).y == -7716);
		Assert.assertTrue(game.tankerIdToTankerMap.get(11).water == 1);
		Assert.assertTrue(game.tankerIdToTankerMap.get(11).size == 4);
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
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
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
	public void test_update_full() throws Exception
	{
		String raw = "0 0 0 0 0 0 12 " +
				"0 0 0 0.5 400 -1168 3138 0 0 -1 -1 " +
				"1 1 0 1.5 400 1366 2658 0 0 -1 -1 " +
				"2 2 0 1.0 400 2978 1517 0 0 -1 -1 " +
				"3 0 1 0.5 400 -2133 -2581 0 0 -1 -1 " +
				"4 1 1 1.5 400 -2985 -146 0 0 -1 -1 " +
				"5 2 1 1.0 400 -2803 1821 0 0 -1 -1 " +
				"6 0 2 0.5 400 3301 -557 0 0 -1 -1 " +
				"7 1 2 1.5 400 1619 -2512 0 0 -1 -1 " +
				"8 2 2 1.0 400 -176 -3338 0 0 -1 -1 " +
				"9 3 -1 3.0 600 8581 569 -399 -26 1 4 " +
				"10 3 -1 3.0 600 -4783 7147 222 -332 1 4 " +
				"11 3 -1 3.0 600 -3798 -7716 177 359 1 4";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));

		String step2 = "0 0 0 2 2 2 12 " +
				"0 0 0 0.5 400 -1168 3138 0 0 -1 -1 " +
				"1 1 0 1.5 400 1366 2658 0 0 -1 -1 " +
				"2 2 0 1.0 400 2687 1589 -218 54 -1 -1 " +
				"3 0 1 0.5 400 -2287 -3057 -123 -380 -1 -1 " +
				"4 1 1 1.5 400 -3006 -345 -15 -139 -1 -1 " +
				"5 2 1 1.0 400 -2551 1658 189 -123 -1 -1 " +
				"6 0 2 0.5 400 3790 -453 391 83 -1 -1 " +
				"7 1 2 1.5 400 1802 -2431 128 57 -1 -1 " +
				"8 2 2 1.0 400 -160 -3038 12 225 -1 -1 " +
				"9 3 -1 3.0 600 8016 532 -339 -22 1 4 " +
				"10 3 -1 3.0 600 -4468 6676 189 -282 1 4 " +
				"11 3 -1 3.0 600 -3547 -7207 150 305 1 4";
		game.updateFromInputLines(new Scanner(step2));

		Assert.assertTrue(game.innerPlayers.size() == 3);
		Assert.assertTrue(game.innerPlayers.get(0).looters.length == 3);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].id == 0);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].x == -1168);
		Assert.assertTrue(game.innerPlayers.get(0).looters[0].y == 3138);
		Assert.assertTrue(game.innerPlayers.get(0).looters[1].id == 1);
		Assert.assertTrue(game.innerPlayers.get(0).looters[1].x == 1366);
		Assert.assertTrue(game.innerPlayers.get(0).looters[1].y == 2658);
		Assert.assertTrue(game.innerPlayers.get(0).looters[2].id == 2);
		Assert.assertTrue(game.innerPlayers.get(0).looters[2].x == 2687);
		Assert.assertTrue(game.innerPlayers.get(0).looters[2].y == 1589);

		Assert.assertTrue(game.innerPlayers.get(1).looters[1].id == 4);
		Assert.assertTrue(game.innerPlayers.get(1).looters[1].x == -3006);
		Assert.assertTrue(game.innerPlayers.get(1).looters[1].y == -345);
		Assert.assertTrue(game.innerPlayers.get(1).looters[1].vx == -15);
		Assert.assertTrue(game.innerPlayers.get(1).looters[1].vy == -139);

		Assert.assertTrue(game.innerPlayers.get(2).looters[2].id == 8);
		Assert.assertTrue(game.innerPlayers.get(2).looters[2].x == -160);
		Assert.assertTrue(game.innerPlayers.get(2).looters[2].y == -3038);
		Assert.assertTrue(game.innerPlayers.get(2).looters[2].vx == 12);
		Assert.assertTrue(game.innerPlayers.get(2).looters[2].vy == 225);

		Assert.assertTrue(game.tankerIdToTankerMap.size() == 3);
		Assert.assertTrue(game.tankerIdToTankerMap.get(9).x == 8016);
		Assert.assertTrue(game.tankerIdToTankerMap.get(9).y == 532);
		Assert.assertTrue(game.tankerIdToTankerMap.get(9).water == 1);
		Assert.assertTrue(game.tankerIdToTankerMap.get(9).size == 4);
		Assert.assertTrue(game.tankerIdToTankerMap.get(11).x == -3547);
		Assert.assertTrue(game.tankerIdToTankerMap.get(11).y == -7207);
		Assert.assertTrue(game.tankerIdToTankerMap.get(11).water == 1);
		Assert.assertTrue(game.tankerIdToTankerMap.get(11).size == 4);
	}

	@Test
	public void test_calculate_two_turns_only_position() throws Exception
	{
		// init game
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(step1));

		// handle players outputs
		game.handleActions(Arrays.asList(
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait()));

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
	public void test_calculate_three_turns_only_position() throws Exception
	{
		// init game
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(step1));

		// handle players outputs --------------------- ROUND 2
//		String step2 = "0 0 0 0 0 0 6 " +
//				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
//				"1 0 1 0.5 400 -2571 -4339 293 129 -1 -1 " +
//				"2 0 2 0.5 400 5023 -496 -275 -163 -1 -1 " +
//				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
//				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
//				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		game.handleActions(Arrays.asList(
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait()));

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
		game.handleActions(Arrays.asList(
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait()));

		// update game
		game.updateGame(2);
		assertLooperPosition(game, 0, -2430, 4794);
		assertLooperPosition(game, 1, -1912, -4048);
		assertLooperPosition(game, 2, 4404, -863);

	}

	@Test
	public void test_calculate_multiple_turns() throws Exception
	{
		// init game
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(step1));

		// handle players outputs --------------------- ROUND 2
		for (int i = 1; i <= 11; i++)
		{
			game.handleActions(Arrays.asList(
					Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
					Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait(),
					Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait()));

			// update game
			game.updateGame(1 + i);
		}

		for (int i = 1; i <= 3; i++)
		{
			game.handleActions(Arrays.asList(
					Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
					Player.Game.actionMove(723, -2912, 200), Player.Game.actionWait(), Player.Game.actionWait(),
					Player.Game.actionMove(723, -2912, 200), Player.Game.actionWait(), Player.Game.actionWait()));

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

	@Test
	public void test_simulate_three_turns_only_position() throws Exception
	{
		// init game
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -483 1533 0 0 -1 -1 " +
				"1 0 1 0.5 400 -1086 -1184 0 0 -1 -1 " +
				"2 0 2 0.5 400 1569 -349 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 2230 2007 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2853 927 0 0 9 -1 " +
				"5 4 -1 -1.0 850 624 -2934 0 0 9 -1";
		String step2 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -285 1185 158 -278 -1 -1 " +
				"1 0 1 0.5 400 -806 -1470 224 -229 -1 -1 " +
				"2 0 2 0.5 400 1432 -725 -110 -301 -1 -1 " +
				"3 4 -1 -1.0 850 2230 2007 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2853 927 0 0 9 -1 " +
				"5 4 -1 -1.0 850 624 -2934 0 0 9 -1";
		String step3 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 71 559 285 -500 -1 -1 " +
				"1 0 1 0.5 400 -302 -1985 403 -412 -1 -1 " +
				"2 0 2 0.5 400 1185 -1402 -198 -541 -1 -1 " +
				"3 4 -1 -1.0 850 2230 2007 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2853 927 0 0 9 -1 " +
				"5 4 -1 -1.0 850 624 -2934 0 0 9 -1";

		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(step1));
		game.updateFromInputLines(new Scanner(step2));
		game.updateFromInputLines(new Scanner(step3));

		// simulate a move
		game.handleActions(Arrays.asList(
				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait()));

		// update game
		game.updateGame(2);

//		0 1 1 0 0 0 6
//		0 0 0 0.5 400 554 -289 386 -678 -1 -1
//		1 0 1 0.5 400 245 -2764 39 -863 -1 -1
//		2 0 2 0.5 400 985 -2238 239 -429 -1 -1
//		3 4 -1 -1.0 850 2230 2007 0 0 9 -1
//		4 4 -1 -1.0 850 -2853 927 0 0 9 -1
//		5 4 -1 -1.0 850 624 -2934 0 0 7 -1
		assertLooperPosition(game, 0, 554, -289);
		Assert.assertTrue(game.looterIdToLooterMap.get(0).vx == 386);
		Assert.assertTrue(game.looterIdToLooterMap.get(0).vy == -678);
	}

	@Test
	public void test_duplicate_game() throws Exception
	{
		// init game
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -483 1533 0 0 -1 -1 " +
				"1 0 1 0.5 400 -1086 -1184 0 0 -1 -1 " +
				"2 0 2 0.5 400 1569 -349 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 2230 2007 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2853 927 0 0 9 -1 " +
				"5 4 -1 -1.0 850 624 -2934 0 0 9 -1";

		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(step1));

		Player.Game duplicateGame = new Player.Game(game);

		Assert.assertTrue(duplicateGame.innerPlayers.get(0).looters.length == game.innerPlayers.get(0).looters.length);
		for (int i = 0; i < 3; i++)
		{
			Assert.assertTrue(duplicateGame.looters.get(i).x == game.looters.get(i).x);
			Assert.assertTrue(duplicateGame.looters.get(i).y == game.looters.get(i).y);
			Assert.assertTrue(duplicateGame.looters.get(i).vx == game.looters.get(i).vx);
			Assert.assertTrue(duplicateGame.looters.get(i).vy == game.looters.get(i).vy);
		}
		Assert.assertTrue(duplicateGame.wrecks.get(0).x == game.wrecks.get(0).x);
		Assert.assertTrue(duplicateGame.wrecks.get(0).y == game.wrecks.get(0).y);
	}

	@Test
	public void test_run() throws Exception
	{
		// init game
		String step1 = "0 0 0 0 0 0 6 " +
				"0 0 0 0.5 400 -483 1533 0 0 -1 -1 " +
				"1 0 1 0.5 400 -1086 -1184 0 0 -1 -1 " +
				"2 0 2 0.5 400 1569 -349 0 0 -1 -1 " +
				"3 4 -1 -1.0 850 2230 2007 0 0 9 -1 " +
				"4 4 -1 -1.0 850 -2853 927 0 0 9 -1 " +
				"5 4 -1 -1.0 850 624 -2934 0 0 9 -1";

		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(step1));
		game.findBestAction(System.currentTimeMillis(), 100);

		Player.applyBestSolution();
	}

	@Test
	public void test_run_full() throws Exception
	{
		String raw = "0 0 0 0 0 0 12 " +
				"0 0 0 0.5 400 -1168 3138 0 0 -1 -1 " +
				"1 1 0 1.5 400 1366 2658 0 0 -1 -1 " +
				"2 2 0 1.0 400 2978 1517 0 0 -1 -1 " +
				"3 0 1 0.5 400 -2133 -2581 0 0 -1 -1 " +
				"4 1 1 1.5 400 -2985 -146 0 0 -1 -1 " +
				"5 2 1 1.0 400 -2803 1821 0 0 -1 -1 " +
				"6 0 2 0.5 400 3301 -557 0 0 -1 -1 " +
				"7 1 2 1.5 400 1619 -2512 0 0 -1 -1 " +
				"8 2 2 1.0 400 -176 -3338 0 0 -1 -1 " +
				"9 3 -1 3.0 600 8581 569 -399 -26 1 4 " +
				"10 3 -1 3.0 600 -4783 7147 222 -332 1 4 " +
				"11 3 -1 3.0 600 -3798 -7716 177 359 1 4";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));
		game.findBestAction(System.currentTimeMillis(), 100);

		Player.applyBestSolution();
	}

	@Test
	public void test_run_check_simulation() throws Exception
	{
		String raw = "42 43 41 300 300 261 24 " +
				"0 0 0 0.5 400 2872 2201 192 -708 -1 -1 " +
				"1 1 0 1.5 400 5382 1087 115 -8 -1 -1 " +
				"2 2 0 1.0 400 -1326 2878 34 246 -1 -1 " +
				"3 0 1 0.5 400 -459 2587 255 -236 -1 -1 " +
				"4 1 1 1.5 400 -1111 1726 -66 -90 -1 -1 " +
				"5 2 1 1.0 400 -2122 364 -437 -283 -1 -1 " +
				"6 0 2 0.5 400 2457 -1166 74 57 -1 -1 " +
				"7 1 2 1.5 400 31 -2948 -109 -148 -1 -1 " +
				"8 2 2 1.0 400 -3218 1879 -398 291 -1 -1 " +
				"101 3 -1 4.5 600 -3231 -3810 -108 -118 4 4 " +
				"115 3 -1 5.0 650 -3582 643 -154 9 5 5 " +
				"119 3 -1 6.0 750 -2478 -1016 -70 -26 7 7 " +
				"125 3 -1 3.0 700 -1610 -3803 86 215 1 6 " +
				"130 3 -1 3.0 600 -1105 5635 85 -245 1 4 " +
				"131 3 -1 3.0 750 -2439 5380 72 -241 1 7 " +
				"134 3 -1 3.0 600 6696 -2222 -268 89 1 4 " +
				"135 3 -1 3.0 750 7564 1313 -300 -52 1 7 " +
				"137 3 -1 3.0 750 8700 -931 -398 43 1 7 " +
				"102 4 -1 -1.0 650 930 -2134 0 0 3 -1 " +
				"118 4 -1 -1.0 650 -1897 1604 0 0 2 -1 " +
				"128 4 -1 -1.0 800 2320 -558 0 0 2 -1 " +
				"129 4 -1 -1.0 700 3034 839 0 0 1 -1 " +
				"133 4 -1 -1.0 800 -303 -2423 0 0 2 -1 " +
				"136 4 -1 -1.0 700 -884 -3021 0 0 1 -1";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));
		game.findBestAction(System.currentTimeMillis(), 100);

		Player.applyBestSolution();
	}

	@Test
	public void test_run_check_simulation_2() throws Exception
	{
		String raw = "42 43 41 300 300 261 24 " +
				"0 0 0 0.5 400 2872 2201 192 -708 -1 -1 " +
				"1 1 0 1.5 400 5382 1087 115 -8 -1 -1 " +
				"2 2 0 1.0 400 -1326 2878 34 246 -1 -1 " +
				"3 0 1 0.5 400 -459 2587 255 -236 -1 -1 " +
				"4 1 1 1.5 400 -1111 1726 -66 -90 -1 -1 " +
				"5 2 1 1.0 400 -2122 364 -437 -283 -1 -1 " +
				"6 0 2 0.5 400 2457 -1166 74 57 -1 -1 " +
				"7 1 2 1.5 400 31 -2948 -109 -148 -1 -1 " +
				"8 2 2 1.0 400 -3218 1879 -398 291 -1 -1 " +
				"101 3 -1 4.5 600 -3231 -3810 -108 -118 4 4 " +
				"115 3 -1 5.0 650 -3582 643 -154 9 5 5 " +
				"119 3 -1 6.0 750 -2478 -1016 -70 -26 7 7 " +
				"125 3 -1 3.0 700 -1610 -3803 86 215 1 6 " +
				"130 3 -1 3.0 600 -1105 5635 85 -245 1 4 " +
				"131 3 -1 3.0 750 -2439 5380 72 -241 1 7 " +
				"134 3 -1 3.0 600 6696 -2222 -268 89 1 4 " +
				"135 3 -1 3.0 750 7564 1313 -300 -52 1 7 " +
				"137 3 -1 3.0 750 8700 -931 -398 43 1 7 " +
				"102 4 -1 -1.0 650 930 -2134 0 0 3 -1 " +
				"118 4 -1 -1.0 650 -1897 1604 0 0 2 -1 " +
				"128 4 -1 -1.0 800 2320 -558 0 0 2 -1 " +
				"129 4 -1 -1.0 700 3034 839 0 0 1 -1 " +
				"133 4 -1 -1.0 800 -303 -2423 0 0 2 -1 " +
				"136 4 -1 -1.0 700 -884 -3021 0 0 1 -1";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));



		game.looters.get(0).setWantedThrust(new Player.Game.Point(2500,-1200), 300);
		game.updateGame(1);
		System.out.println("Reaper at " + game.looters.get(0).x + ", " + game.looters.get(0).y);
	}

	@Test
	public void test_listPossibleActionsForReaper() throws Exception
	{
		String raw = "0 0 0 0 0 0 12 " +
				"0 0 0 0.5 400 -1168 3138 0 0 -1 -1 " +
				"1 1 0 1.5 400 1366 2658 0 0 -1 -1 " +
				"2 2 0 1.0 400 2978 1517 0 0 -1 -1 " +
				"3 0 1 0.5 400 -2133 -2581 0 0 -1 -1 " +
				"4 1 1 1.5 400 -2985 -146 0 0 -1 -1 " +
				"5 2 1 1.0 400 -2803 1821 0 0 -1 -1 " +
				"6 0 2 0.5 400 3301 -557 0 0 -1 -1 " +
				"7 1 2 1.5 400 1619 -2512 0 0 -1 -1 " +
				"8 2 2 1.0 400 -176 -3338 0 0 -1 -1 " +
				"9 3 -1 3.0 600 8581 569 -399 -26 1 4 " +
				"10 3 -1 3.0 600 -4783 7147 222 -332 1 4 " +
				"11 3 -1 3.0 600 -3798 -7716 177 359 1 4";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));

		List<Player.Game.Action> actions = game.listPossibleActionsForReaper();

	}

	private void assertLooperPosition(Player.Game game, int playerIndex, int x, int y)
	{
		System.out.println("Player " + playerIndex + " : " + game.looterIdToLooterMap.get(playerIndex).x + "," + game.looterIdToLooterMap.get(playerIndex).y);
		Assert.assertTrue(game.looterIdToLooterMap.get(playerIndex).x == x);
		Assert.assertTrue(game.looterIdToLooterMap.get(playerIndex).y == y);
	}
}