import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class GameTest
{

//	@Test
//	public void test_create() throws Exception
//	{
//		String raw = "0 0 0 0 0 0 18 " +
//				"0 0 0 0.5 400 2426 2606 0 0 -1 -1 " +
//				"1 1 0 1.5 400 3677 4088 0 0 -1 -1 " +
//				"2 2 0 1.0 400 -926 2377 0 0 -1 -1 " +
//				"3 0 1 0.5 400 -3469 798 0 0 -1 -1 " +
//				"4 1 1 1.5 400 -5379 1141 0 0 -1 -1 " +
//				"5 2 1 1.0 400 -1595 -1990 0 0 -1 -1 " +
//				"6 0 2 0.5 400 1044 -3404 0 0 -1 -1 " +
//				"7 1 2 1.5 400 1702 -5229 0 0 -1 -1 " +
//				"8 2 2 1.0 400 2521 -387 0 0 -1 -1 " +
//				"9 3 -1 3.0 800 4484 7572 -204 -344 1 8 " +
//				"10 3 -1 3.0 800 -8799 97 400 -4 1 8 " +
//				"11 3 -1 3.0 800 4316 -7669 -196 349 1 8 " +
//				"12 3 -1 3.0 750 8696 969 -398 -44 1 7 " +
//				"13 3 -1 3.0 750 -5188 7046 237 -322 1 7 " +
//				"14 3 -1 3.0 750 -3509 -8016 160 366 1 7 " +
//				"15 3 -1 3.0 800 10485 1559 -396 -59 1 8 " +
//				"16 3 -1 3.0 800 -6593 8300 249 -313 1 8 " +
//				"17 3 -1 3.0 800 -3892 -9860 147 372 1 8";
//		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
//		game.createFromInputLines(new Scanner(raw));
//
//		Assert.assertTrue(game.innerPlayers.size() == 3);
//		Assert.assertTrue(game.innerPlayers.get(0).looters.length == 3);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[0].id == 0);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[0].type == Player.Game.LOOTER_REAPER);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[0].x == 2426);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[0].y == 2606);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[1].id == 1);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[1].type == Player.Game.LOOTER_DESTROYER);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[2].id == 2);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[2].type == Player.Game.LOOTER_DOOF);
//
//		Assert.assertTrue(game.innerPlayers.get(1).looters[0].id == 3);
//		Assert.assertTrue(game.innerPlayers.get(1).looters[1].type == Player.Game.LOOTER_DESTROYER);
//		Assert.assertTrue(game.innerPlayers.get(2).looters[0].id == 6);
//		Assert.assertTrue(game.innerPlayers.get(2).looters[1].type == Player.Game.LOOTER_DESTROYER);
//		Assert.assertTrue(game.wrecks.isEmpty());
//		Assert.assertTrue(game.tankers.size() == 9);
//		Assert.assertTrue(game.tankers.get(8).x == -3892);
//		Assert.assertTrue(game.tankers.get(8).y == -9860);
//		Assert.assertTrue(game.tankers.get(8).water == 1);
//		Assert.assertTrue(game.tankers.get(8).size == 8);
//	}

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
	public void test_create_full_with_skill() throws Exception
	{
		String raw = "7 10 7 201 43 35 21 " +
				"0 0 0 0.5 400 -2266 -196 -116 659 -1 -1 " +
				"1 1 0 1.5 400 -1944 2960 285 197 -1 -1 " +
				"2 2 0 1.0 400 -1196 3699 191 612 -1 -1 " +
				"3 0 1 0.5 400 -1372 4799 61 -175 -1 -1 " +
				"4 1 1 1.5 400 -322 -860 343 90 -1 -1 " +
				"5 2 1 1.0 400 -477 -1685 75 -80 -1 -1 " +
				"6 0 2 0.5 400 2024 2018 455 -693 -1 -1 " +
				"7 1 2 1.5 400 2559 259 43 -84 -1 -1 " +
				"8 2 2 1.0 400 -3004 311 -112 469 -1 -1 " +
				"20 3 -1 4.5 600 3453 -6695 76 -147 4 4 " +
				"24 3 -1 7.0 850 481 -3299 15 -101 9 9 " +
				"32 3 -1 4.5 600 394 2717 14 132 4 4 " +
				"37 3 -1 3.5 700 -2542 -1529 324 175 2 6 " +
				"40 3 -1 3.0 800 -5479 -633 252 29 1 8 " +
				"42 3 -1 3.0 850 6532 4221 -256 -165 1 9 " +
				"29 4 -1 -1.0 750 -2493 1273 0 0 3 -1 " +
				"31 4 -1 -1.0 750 -1343 4183 0 0 5 -1 " +
				"36 4 -1 -1.0 600 -2442 -451 0 0 3 -1 " +
				"39 4 -1 -1.0 850 -3327 1933 0 0 1 -1 " +
				"41 4 -1 -1.0 750 2594 949 0 0 7 -1 " +
				"43 6 -1 -1.0 1000 -2442 -451 0 0 2 -1";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));

		Assert.assertTrue(game.skillEffects.size() == 1);
	}

//	@Test
//	public void test_update() throws Exception
//	{
//		String step1 = "0 0 0 0 0 0 6 " +
//				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
//				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
//				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
//				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
//				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
//				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
//		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
//		game.createFromInputLines(new Scanner(step1));
//
//		String step2 = "0 5 8 0 0 0 6 " +
//				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
//				"1 0 1 0.5 400 39 -3457 -412 -367 -1 -1 " +
//				"2 0 2 0.5 400 751 -2747 -283 -95 -1 -1 " +
//				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
//				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
//				"6 4 -1 -1.0 600 723 -2912 0 0 1 -1 ";
//		game.updateFromInputLines(new Scanner(step2));
//
//		Assert.assertTrue(game.innerPlayers.size() == 3);
//		Assert.assertTrue(game.innerPlayers.get(0).looters.length == 1);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[0].id == 0);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[0].x == -2430);
//		Assert.assertTrue(game.innerPlayers.get(0).looters[0].y == 4794);
//		Assert.assertTrue(game.innerPlayers.get(1).looters[0].id == 1);
//		Assert.assertTrue(game.innerPlayers.get(1).looters[0].x == 39);
//		Assert.assertTrue(game.innerPlayers.get(1).looters[0].y == -3457);
//		Assert.assertTrue(game.innerPlayers.get(2).looters[0].id == 2);
//		Assert.assertTrue(game.innerPlayers.get(2).looters[0].x == 751);
//		Assert.assertTrue(game.innerPlayers.get(2).looters[0].y == -2747);
//		Assert.assertTrue(game.wreckIdToWreckMap.size() == 4);
//		Assert.assertTrue(game.wreckIdToWreckMap.get(4).x == -2983);
//		Assert.assertTrue(game.wreckIdToWreckMap.get(4).y == -322);
//		Assert.assertTrue(game.wreckIdToWreckMap.get(4).radius == 850);
//		Assert.assertTrue(game.wreckIdToWreckMap.get(4).water == 9);
//		Assert.assertTrue(game.wreckIdToWreckMap.get(6).x == 723);
//		Assert.assertTrue(game.wreckIdToWreckMap.get(6).y == -2912);
//		Assert.assertTrue(game.wreckIdToWreckMap.get(6).radius == 600);
//		Assert.assertTrue(game.wreckIdToWreckMap.get(6).water == 1);
//	}

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
		String step1 = "0 0 0 0 0 0 18 " +
				"0 0 0 0.5 400 2426 2606 0 0 -1 -1 " +
				"1 1 0 1.5 400 3677 4088 0 0 -1 -1 " +
				"2 2 0 1.0 400 -926 2377 0 0 -1 -1 " +
				"3 0 1 0.5 400 -3469 798 0 0 -1 -1 " +
				"4 1 1 1.5 400 -5379 1141 0 0 -1 -1 " +
				"5 2 1 1.0 400 -1595 -1990 0 0 -1 -1 " +
				"6 0 2 0.5 400 1044 -3404 0 0 -1 -1 " +
				"7 1 2 1.5 400 1702 -5229 0 0 -1 -1 " +
				"8 2 2 1.0 400 2521 -387 0 0 -1 -1 " +
				"9 3 -1 3.0 800 4484 7572 -204 -344 1 8 " +
				"10 3 -1 3.0 800 -8799 97 400 -4 1 8 " +
				"11 3 -1 3.0 800 4316 -7669 -196 349 1 8 " +
				"12 3 -1 3.0 750 8696 969 -398 -44 1 7 " +
				"13 3 -1 3.0 750 -5188 7046 237 -322 1 7 " +
				"14 3 -1 3.0 750 -3509 -8016 160 366 1 7 " +
				"15 3 -1 3.0 800 10485 1559 -396 -59 1 8 " +
				"16 3 -1 3.0 800 -6593 8300 249 -313 1 8 " +
				"17 3 -1 3.0 800 -3892 -9860 147 372 1 8";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(step1));

		// handle players outputs
//		1044 -3404 300
//		4484 7572 300
//		1044 -3404 300
		game.handleActions(new Player.Game.Action[]
		{
				Player.Game.actionMove(1044, -3404, 300), Player.Game.actionMove(4484, 7572, 300), Player.Game.actionMove(1044, -3404, 300),
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait() });

		// update game
		game.updateGame(2);
		assertLooperPosition(game, 0, 2292, 2021);
		assertLooperPosition(game, 1, 3649, 4199);
		assertLooperPosition(game, 2, -829, 2093);

//		String step2 = "0 0 0 2 1 1 18 " +
//				"0 0 0 0.5 400 2292 2021 -108 -468 -1 -1 " +
//				"1 1 0 1.5 400 3649 4199 -91 -2 -1 -1 " +
//				"2 2 0 1.0 400 -829 2093 73 -213 -1 -1 " +
//				"3 0 1 0.5 400 -3274 753 156 -36 -1 -1 " +
//				"4 1 1 1.5 400 -5472 1079 39 -65 -1 -1 " +
//				"5 2 1 1.0 400 -1470 -1834 94 117 -1 -1 " +
//				"6 0 2 0.5 400 985 -3213 -47 153 -1 -1 " +
//				"7 1 2 1.5 400 1802 -5278 36 66 -1 -1 " +
//				"8 2 2 1.0 400 2323 -357 -148 23 -1 -1 " +
//				"9 3 -1 3.0 800 4195 7085 -173 -292 1 8 " +
//				"10 3 -1 3.0 800 -8232 91 340 -4 1 8 " +
//				"11 3 -1 3.0 800 4038 -7175 -167 297 1 8 " +
//				"12 3 -1 3.0 750 8132 907 -338 -37 1 7 " +
//				"13 3 -1 3.0 750 -4852 6590 201 -274 1 7 " +
//				"14 3 -1 3.0 750 -3282 -7497 136 311 1 7 " +
//				"15 3 -1 3.0 800 9924 1475 -337 -50 1 8 " +
//				"16 3 -1 3.0 800 -6240 7856 212 -266 1 8 " +
//				"17 3 -1 3.0 800 -3684 -9333 125 316 1 8";

//		985 -3213 300
//		4195 7085 300
//		985 -3213 300
		game.handleActions(new Player.Game.Action[]
		{
				Player.Game.actionMove(985, -3213, 300), Player.Game.actionMove(4195, 7085, 300), Player.Game.actionMove(985, -3213, 300),
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait() });

		// update game
		game.updateGame(2);
		assertLooperPosition(game, 0, 2039, 971);
		assertLooperPosition(game, 1, 3521, 4307);
		assertLooperPosition(game, 2, -659, 1596);

//		String step3= "0 0 0 5 3 3 18 " +
//				"0 0 0 0.5 400 2039 971 -203 -840 -1 -1 " +
//				"1 1 0 1.5 400 3521 4307 -115 46 -1 -1 " +
//				"2 2 0 1.0 400 -659 1596 128 -373 -1 -1 " +
//				"3 0 1 0.5 400 -2923 672 281 -65 -1 -1 " +
//				"4 1 1 1.5 400 -5486 938 14 -103 -1 -1 " +
//				"5 2 1 1.0 400 -1251 -1561 164 205 -1 -1 " +
//				"6 0 2 0.5 400 879 -2869 -84 275 -1 -1 " +
//				"7 1 2 1.5 400 1930 -5220 82 63 -1 -1 " +
//				"8 2 2 1.0 400 1977 -303 -259 40 -1 -1 " +
//				"9 3 -1 3.0 800 3937 6650 -155 -261 1 8 " +
//				"10 3 -1 3.0 800 -7725 85 304 -4 1 8 " +
//				"11 3 -1 3.0 800 3789 -6733 -149 265 1 8 " +
//				"12 3 -1 3.0 750 7628 852 -302 -33 1 7 " +
//				"13 3 -1 3.0 750 -4552 6182 180 -245 1 7 " +
//				"14 3 -1 3.0 750 -3079 -7033 122 278 1 7 " +
//				"15 3 -1 3.0 800 9422 1400 -301 -45 1 8 " +
//				"16 3 -1 3.0 800 -5924 7459 189 -238 1 8 " +
//				"17 3 -1 3.0 800 -3498 -8862 112 283 1 8";
	}

//	@Test
//	public void test_calculate_three_turns_only_position() throws Exception
//	{
//		// init game
//		String step1 = "0 0 0 0 0 0 6 " +
//				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
//				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
//				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
//				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
//				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
//				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
//		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
//		game.createFromInputLines(new Scanner(step1));
//
//		// handle players outputs --------------------- ROUND 2
////		String step2 = "0 0 0 0 0 0 6 " +
////				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
////				"1 0 1 0.5 400 -2571 -4339 293 129 -1 -1 " +
////				"2 0 2 0.5 400 5023 -496 -275 -163 -1 -1 " +
////				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
////				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
////				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
//		game.handleActions(Arrays.asList(
//				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
//				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait(),
//				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait()));
//
//		// update game
//		game.updateGame(2);
//		assertLooperPosition(game, 0, -2430, 4794);
//		assertLooperPosition(game, 1, -2571, -4339);
//		assertLooperPosition(game, 2, 5023, -496);
//
//		// handle players outputs --------------------- ROUND 3
////		String step3 = "0 0 0 0 0 0 6 " +
////				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
////				"1 0 1 0.5 400 -1912 -4048 527 232 -1 -1 " +
////				"2 0 2 0.5 400 4404 -863 -495 -293 -1 -1 " +
////				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
////				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
////				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
//		game.handleActions(Arrays.asList(
//				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
//				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait(),
//				Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait()));
//
//		// update game
//		game.updateGame(2);
//		assertLooperPosition(game, 0, -2430, 4794);
//		assertLooperPosition(game, 1, -1912, -4048);
//		assertLooperPosition(game, 2, 4404, -863);
//
//	}

//	@Test
//	public void test_calculate_multiple_turns() throws Exception
//	{
//		// init game
//		String step1 = "0 0 0 0 0 0 6 " +
//				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
//				"1 0 1 0.5 400 -2937 -4501 0 0 -1 -1 " +
//				"2 0 2 0.5 400 5367 -292 0 0 -1 -1 " +
//				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
//				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
//				"5 4 -1 -1.0 850 1770 -2422 0 0 9 -1";
//		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
//		game.createFromInputLines(new Scanner(step1));
//
//		// handle players outputs --------------------- ROUND 2
//		for (int i = 1; i <= 11; i++)
//		{
//			game.handleActions(Arrays.asList(
//					Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
//					Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait(),
//					Player.Game.actionMove(1770, -2422, 200), Player.Game.actionWait(), Player.Game.actionWait()));
//
//			// update game
//			game.updateGame(1 + i);
//		}
//
//		for (int i = 1; i <= 3; i++)
//		{
//			game.handleActions(Arrays.asList(
//					Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionWait(),
//					Player.Game.actionMove(723, -2912, 200), Player.Game.actionWait(), Player.Game.actionWait(),
//					Player.Game.actionMove(723, -2912, 200), Player.Game.actionWait(), Player.Game.actionWait()));
//
//			// update game
//			game.updateGame(12 + i);
//		}
//
//		String step = "0 5 8 0 0 0 6 " +
//				"0 0 0 0.5 400 -2430 4794 0 0 -1 -1 " +
//				"1 0 1 0.5 400 39 -3457 -412 -367 -1 -1 " +
//				"2 0 2 0.5 400 751 -2747 -283 -95 -1 -1 " +
//				"3 4 -1 -1.0 850 1213 2744 0 0 9 -1 " +
//				"4 4 -1 -1.0 850 -2983 -322 0 0 9 -1 " +
//				"6 4 -1 -1.0 600 723 -2912 0 0 1 -1";
//		assertLooperPosition(game, 0, -2430, 4794);
//		assertLooperPosition(game, 1, 39, -3457);
//		assertLooperPosition(game, 2, 751, -2747);
//	}

	@Test
	public void test_simulate_three_turns_only_position() throws Exception
	{
		// init game
		String step1 = "1 0 2 90 78 111 26 " +
				"0 0 0 0.5 400 -506 1230 787 -666 -1 -1 " +
				"1 1 0 1.5 400 3978 1355 73 325 -1 -1 " +
				"2 2 0 1.0 400 -2306 3388 157 62 -1 -1 " +
				"3 0 1 0.5 400 -767 2713 298 -42 -1 -1 " +
				"4 1 1 1.5 400 -1453 3180 202 11 -1 -1 " +
				"5 2 1 1.0 400 -3811 2782 -154 -63 -1 -1 " +
				"6 0 2 0.5 400 -1627 3982 382 92 -1 -1 " +
				"7 1 2 1.5 400 -1557 -2757 -26 31 -1 -1 " +
				"8 2 2 1.0 400 1814 4489 155 574 -1 -1 " +
				"24 3 -1 3.0 800 -3441 4167 28 98 1 8 " +
				"26 3 -1 3.0 700 2984 2901 -180 -175 1 6 " +
				"28 3 -1 3.0 800 -1485 -4878 74 241 1 8 " +
				"31 3 -1 3.0 800 -1168 5830 50 -252 1 8 " +
				"32 3 -1 3.0 700 -5626 1591 247 -70 1 6 " +
				"34 3 -1 3.0 700 1541 -6076 -64 254 1 6 " +
				"37 3 -1 3.0 800 -5801 -5106 228 200 1 8 " +
				"38 3 -1 3.0 600 -3682 6564 149 -265 1 4 " +
				"40 3 -1 3.0 800 8338 -2814 -379 128 1 8 " +
				"21 4 -1 -1.0 800 4794 866 0 0 1 -1 " +
				"25 4 -1 -1.0 700 5134 -38 0 0 1 -1 " +
				"27 4 -1 -1.0 800 -1069 -3881 0 0 1 -1 " +
				"29 4 -1 -1.0 750 2394 266 0 0 7 -1 " +
				"33 4 -1 -1.0 700 -2032 -2964 0 0 1 -1 " +
				"35 4 -1 -1.0 750 -1584 2248 0 0 6 -1 " +
				"36 4 -1 -1.0 800 4423 1021 0 0 1 -1 " +
				"39 4 -1 -1.0 750 -1351 -1638 0 0 7 -1";
		String step2 = "1 1 2 92 80 114 26 " +
				"0 0 0 0.5 400 880 539 1109 -553 -1 -1 " +
				"1 1 0 1.5 400 3943 1848 -25 345 -1 -1 " +
				"2 2 0 1.0 400 -1962 3654 202 194 -1 -1 " +
				"3 0 1 0.5 400 -872 2393 -57 -283 -1 -1 " +
				"4 1 1 1.5 400 -1353 3069 81 -86 -1 -1 " +
				"5 2 1 1.0 400 -4150 2643 -254 -104 -1 -1 " +
				"6 0 2 0.5 400 -1330 4179 293 226 -1 -1 " +
				"7 1 2 1.5 400 -1627 -2565 -49 134 -1 -1 " +
				"8 2 2 1.0 400 2004 5212 -164 -254 -1 -1 " +
				"24 3 -1 3.0 800 -3307 4136 80 -18 1 8 " +
				"26 3 -1 3.0 700 2684 2610 -180 -175 1 6 " +
				"28 3 -1 3.0 800 -1362 -4478 74 240 1 8 " +
				"31 3 -1 3.0 800 -1085 5415 50 -249 1 8 " +
				"32 3 -1 3.0 700 -5219 1476 244 -69 1 6 " +
				"34 3 -1 3.0 700 1436 -5660 -63 249 1 6 " +
				"37 3 -1 3.0 800 -5448 -4796 212 186 1 8 " +
				"38 3 -1 3.0 600 -3451 6154 138 -246 1 4 " +
				"40 3 -1 3.0 800 7801 -2633 -322 109 1 8 " +
				"21 4 -1 -1.0 800 4794 866 0 0 1 -1 " +
				"25 4 -1 -1.0 700 5134 -38 0 0 1 -1 " +
				"27 4 -1 -1.0 800 -1069 -3881 0 0 1 -1 " +
				"29 4 -1 -1.0 750 2394 266 0 0 7 -1 " +
				"33 4 -1 -1.0 700 -2032 -2964 0 0 1 -1 " +
				"35 4 -1 -1.0 750 -1584 2248 0 0 5 -1 " +
				"36 4 -1 -1.0 800 4423 1021 0 0 1 -1 " +
				"39 4 -1 -1.0 750 -1351 -1638 0 0 7 -1";
		String step3 = "2 2 2 94 83 117 26 " +
				"0 0 0 0.5 400 2584 67 1363 -378 -1 -1 " +
				"1 1 0 1.5 400 3747 2297 -137 314 -1 -1 " +
				"2 2 0 1.0 400 -1743 3868 158 156 -1 -1 " +
				"3 0 1 0.5 400 -929 2110 -46 -226 -1 -1 " +
				"4 1 1 1.5 400 -1418 3063 -46 -4 -1 -1 " +
				"5 2 1 1.0 400 -4589 2463 -329 -135 -1 -1 " +
				"6 0 2 0.5 400 -776 3605 445 -525 -1 -1 " +
				"7 1 2 1.5 400 -1717 -2269 -63 207 -1 -1 " +
				"8 2 2 1.0 400 1732 4790 -204 -317 -1 -1 " +
				"24 3 -1 3.0 800 -3123 3988 110 -89 1 8 " +
				"26 3 -1 3.0 700 2385 2319 -180 -175 1 6 " +
				"28 3 -1 3.0 800 -1240 -4079 73 240 1 8 " +
				"31 3 -1 3.0 800 -985 5110 61 -173 1 8 " +
				"32 3 -1 3.0 700 -4815 1362 243 -69 1 6 " +
				"34 3 -1 3.0 700 1332 -5249 -62 246 1 6 " +
				"37 3 -1 3.0 800 -5111 -4500 202 178 1 8 " +
				"38 3 -1 3.0 600 -3231 5763 132 -235 1 4 " +
				"40 3 -1 3.0 800 7321 -2471 -288 97 1 8 " +
				"21 4 -1 -1.0 800 4794 866 0 0 1 -1 " +
				"25 4 -1 -1.0 700 5134 -38 0 0 1 -1 " +
				"27 4 -1 -1.0 800 -1069 -3881 0 0 1 -1 " +
				"29 4 -1 -1.0 750 2394 266 0 0 6 -1 " +
				"33 4 -1 -1.0 700 -2032 -2964 0 0 1 -1 " +
				"35 4 -1 -1.0 750 -1584 2248 0 0 4 -1 " +
				"36 4 -1 -1.0 800 4423 1021 0 0 1 -1 " +
				"39 4 -1 -1.0 750 -1351 -1638 0 0 7 -1";

		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(step1));
		game.updateFromInputLines(new Scanner(step2));
		game.updateFromInputLines(new Scanner(step3));
		assertLooperPosition(game, 2, -1743, 3868);

		// simulate a move
//		4423 1021 300
//		2385 2319 300
//		-776 3605 300
//
//		WAIT
//		WAIT
//		-4918 2328 200
//
//		-1584 2248 250
//		-3123 3988 250
//		1528 4473 200

		game.handleActions(new Player.Game.Action[]
		{
				Player.Game.actionMove(4423, 1021, 300), Player.Game.actionMove(2385, 2319, 300), Player.Game.actionMove(-776, 3605, 300),
				Player.Game.actionWait(), Player.Game.actionWait(), Player.Game.actionMove(-4918, 2328, 200),
				Player.Game.actionMove(-1584, 2248, 250), Player.Game.actionMove(-3123, 3988, 250), Player.Game.actionMove(1528, 4473, 200) });

		// update game
		game.updateGame(2);

//		3 3 2 95 85 121 25
//		0 0 0 0.5 400 4480 -35 1516 -81 -1 -1
//		1 1 0 1.5 400 3410 2614 -236 222 -1 -1
//		2 2 0 1.0 400 -1450 3785 115 -83 -1 -1
//		3 0 1 0.5 400 -975 1884 -37 -181 -1 -1
//		4 1 1 1.5 400 -1556 2921 -105 -207 -1 -1
//		5 2 1 1.0 400 -5019 2435 87 -203 -1 -1
//		6 0 2 0.5 400 -310 2834 398 -600 -1 -1
//		7 1 2 1.5 400 -1817 -1899 -70 259 -1 -1
//		8 2 2 1.0 400 1420 4305 -234 -364 -1 -1
//		24 3 -1 3.0 800 -2910 3768 128 -132 1 8
//		26 3 -1 3.5 700 2086 2028 -180 -175 2 6
//		28 3 -1 3.0 800 -1119 -3680 73 240 1 8
//		31 3 -1 3.0 800 -841 4865 115 -97 1 8
//		32 3 -1 3.0 700 -4420 1177 236 -115 1 6
//		34 3 -1 3.0 700 1229 -4841 -62 245 1 6
//		37 3 -1 3.0 800 -4784 -4212 196 173 1 8
//		38 3 -1 3.0 600 -3017 5383 128 -228 1 4
//		40 3 -1 3.0 800 6875 -2321 -268 90 1 8
//		21 4 -1 -1.0 800 4794 866 0 0 1 -1
//		27 4 -1 -1.0 800 -1069 -3881 0 0 1 -1
//		29 4 -1 -1.0 750 2394 266 0 0 6 -1
//		33 4 -1 -1.0 700 -2032 -2964 0 0 1 -1
//		35 4 -1 -1.0 750 -1584 2248 0 0 3 -1
//		36 4 -1 -1.0 800 4423 1021 0 0 1 -1
//		39 4 -1 -1.0 750 -1351 -1638 0 0 7 -1
		assertLooperPosition(game, 0, 4480, -35);
		assertLooperPosition(game, 1, 3410, 2614);
		assertLooperPosition(game, 2, -1450, 3785);
		assertLooperPosition(game, 3, -975, 1884);
		assertLooperPosition(game, 4, -1556, 2921);
		assertLooperPosition(game, 5, -5019, 2435);
		assertLooperPosition(game, 6, -310, 2834);
		assertLooperPosition(game, 7, -1817, -1899);
		assertLooperPosition(game, 8, 1420, 4305);
		Assert.assertTrue(game.looterIdToLooterMap.get(0).vx == 1516);
		Assert.assertTrue(game.looterIdToLooterMap.get(0).vy == -81);
	}

	@Test
	public void test_duplicate_game() throws Exception
	{
		// init game
		String step1 = "0 0 0 78 58 81 24 " +
				"0 0 0 0.5 400 -2849 2459 -291 334 -1 -1 " +
				"1 1 0 1.5 400 3182 -253 -314 -8 -1 -1 " +
				"2 2 0 1.0 400 -3625 1918 -68 383 -1 -1 " +
				"3 0 1 0.5 400 -838 907 -107 21 -1 -1 " +
				"4 1 1 1.5 400 -2623 3264 147 232 -1 -1 " +
				"5 2 1 1.0 400 -4615 110 240 392 -1 -1 " +
				"6 0 2 0.5 400 -2720 4674 150 -82 -1 -1 " +
				"7 1 2 1.5 400 -914 -3539 -218 266 -1 -1 " +
				"8 2 2 1.0 400 589 -32 148 544 -1 -1 " +
				"13 3 -1 6.0 750 -1565 1978 34 -26 7 7 " +
				"14 3 -1 4.5 750 -1372 -1765 97 161 4 7 " +
				"20 3 -1 3.0 700 -2113 -3077 65 86 1 6 " +
				"22 3 -1 3.0 800 5793 1337 -250 -58 1 8 " +
				"24 3 -1 3.0 800 -4347 4659 178 -191 1 8 " +
				"26 3 -1 3.0 700 4809 4677 -193 -188 1 6 " +
				"28 3 -1 3.0 800 -2256 -7390 89 291 1 8 " +
				"31 3 -1 3.0 800 -1732 8628 79 -392 1 8 " +
				"32 3 -1 3.0 700 -8372 2367 385 -109 1 6 " +
				"21 4 -1 -1.0 800 4794 866 0 0 1 -1 " +
				"23 4 -1 -1.0 800 -2941 3443 0 0 1 -1 " +
				"25 4 -1 -1.0 700 5134 -38 0 0 1 -1 " +
				"27 4 -1 -1.0 800 -1069 -3881 0 0 1 -1 " +
				"29 4 -1 -1.0 750 2394 266 0 0 7 -1 " +
				"30 4 -1 -1.0 700 -1666 3475 0 0 1 -1";

		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(step1));

		Player.Game duplicateGame = new Player.Game(game);

		Assert.assertTrue(duplicateGame.innerPlayers.get(0).looters.length == game.innerPlayers.get(0).looters.length);
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				Assert.assertTrue(duplicateGame.innerPlayers.get(i).looters[j].x == game.innerPlayers.get(i).looters[j].x);
				Assert.assertTrue(duplicateGame.innerPlayers.get(i).looters[j].y == game.innerPlayers.get(i).looters[j].y);
				Assert.assertTrue(duplicateGame.innerPlayers.get(i).looters[j].vx == game.innerPlayers.get(i).looters[j].vx);
				Assert.assertTrue(duplicateGame.innerPlayers.get(i).looters[j].vy == game.innerPlayers.get(i).looters[j].vy);
			}
		}
		for (int i = 0; i < 9; i++)
		{
			Assert.assertTrue(duplicateGame.tankers.get(i).x == game.tankers.get(i).x);
			Assert.assertTrue(duplicateGame.tankers.get(i).y == game.tankers.get(i).y);
			Assert.assertTrue(duplicateGame.tankers.get(i).vx == game.tankers.get(i).vx);
			Assert.assertTrue(duplicateGame.tankers.get(i).vy == game.tankers.get(i).vy);
			Assert.assertTrue(duplicateGame.tankers.get(i).water == game.tankers.get(i).water);
			Assert.assertTrue(duplicateGame.tankers.get(i).size == game.tankers.get(i).size);
		}
		for (int i = 0; i < 6; i++)
		{
			Assert.assertTrue(duplicateGame.wrecks.get(i).x == game.wrecks.get(i).x);
			Assert.assertTrue(duplicateGame.wrecks.get(i).y == game.wrecks.get(i).y);
			Assert.assertTrue(duplicateGame.wrecks.get(i).water == game.wrecks.get(i).water);
			Assert.assertTrue(duplicateGame.wrecks.get(i).radius == game.wrecks.get(i).radius);
		}
	}

	@Test
	public void test_duplicate_game_with_skill() throws Exception
	{
		String raw = "7 10 7 201 43 35 21 " +
				"0 0 0 0.5 400 -2266 -196 -116 659 -1 -1 " +
				"1 1 0 1.5 400 -1944 2960 285 197 -1 -1 " +
				"2 2 0 1.0 400 -1196 3699 191 612 -1 -1 " +
				"3 0 1 0.5 400 -1372 4799 61 -175 -1 -1 " +
				"4 1 1 1.5 400 -322 -860 343 90 -1 -1 " +
				"5 2 1 1.0 400 -477 -1685 75 -80 -1 -1 " +
				"6 0 2 0.5 400 2024 2018 455 -693 -1 -1 " +
				"7 1 2 1.5 400 2559 259 43 -84 -1 -1 " +
				"8 2 2 1.0 400 -3004 311 -112 469 -1 -1 " +
				"20 3 -1 4.5 600 3453 -6695 76 -147 4 4 " +
				"24 3 -1 7.0 850 481 -3299 15 -101 9 9 " +
				"32 3 -1 4.5 600 394 2717 14 132 4 4 " +
				"37 3 -1 3.5 700 -2542 -1529 324 175 2 6 " +
				"40 3 -1 3.0 800 -5479 -633 252 29 1 8 " +
				"42 3 -1 3.0 850 6532 4221 -256 -165 1 9 " +
				"29 4 -1 -1.0 750 -2493 1273 0 0 3 -1 " +
				"31 4 -1 -1.0 750 -1343 4183 0 0 5 -1 " +
				"36 4 -1 -1.0 600 -2442 -451 0 0 3 -1 " +
				"39 4 -1 -1.0 850 -3327 1933 0 0 1 -1 " +
				"41 4 -1 -1.0 750 2594 949 0 0 7 -1 " +
				"43 6 -1 -1.0 1000 -2442 -451 0 0 2 -1";

		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));

		Player.Game duplicateGame = new Player.Game(game);

		Assert.assertTrue(duplicateGame.skillEffects.size() == 1);
		for (int i = 0; i < game.skillEffects.size(); i++)
		{
			Assert.assertTrue(duplicateGame.skillEffects.iterator().next().x == game.skillEffects.iterator().next().x);
			Assert.assertTrue(duplicateGame.skillEffects.iterator().next().y == game.skillEffects.iterator().next().y);
		}
	}

//	@Test
//	public void test_run() throws Exception
//	{
//		// init game
//		String step1 = "0 0 0 0 0 0 6 " +
//				"0 0 0 0.5 400 -483 1533 0 0 -1 -1 " +
//				"1 0 1 0.5 400 -1086 -1184 0 0 -1 -1 " +
//				"2 0 2 0.5 400 1569 -349 0 0 -1 -1 " +
//				"3 4 -1 -1.0 850 2230 2007 0 0 9 -1 " +
//				"4 4 -1 -1.0 850 -2853 927 0 0 9 -1 " +
//				"5 4 -1 -1.0 850 624 -2934 0 0 9 -1";
//
//		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
//		game.createFromInputLines(new Scanner(step1));
//		game.findBestAction(System.currentTimeMillis(), 100);
//
//		Player.applyBestSolution();
//	}

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
	}

	@Test
	public void test_run_check_simulation() throws Exception
	{
		String raw = "43 43 43 300 300 271 22 " +
				"0 0 0 0.5 400 3146 -546 430 -598 -1 -1 " +
				"1 1 0 1.5 400 5499 1008 69 -23 -1 -1 " +
				"2 2 0 1.0 400 -656 2864 264 -124 -1 -1 " +
				"3 0 1 0.5 400 -838 1728 -123 -238 -1 -1 " +
				"4 1 1 1.5 400 -1765 1321 -320 -184 -1 -1 " +
				"5 2 1 1.0 400 -3193 70 -200 -123 -1 -1 " +
				"6 0 2 0.5 400 2496 -1187 -450 -619 -1 -1 " +
				"7 1 2 1.5 400 -154 -3200 -53 -73 -1 -1 " +
				"8 2 2 1.0 400 -4358 2713 -436 319 -1 -1 " +
				"101 3 -1 4.5 600 -3591 -4220 -108 -124 4 4 " +
				"115 3 -1 5.0 650 -4118 724 -195 47 5 5 " +
				"119 3 -1 6.0 750 -2793 -1209 -100 -67 7 7 " +
				"125 3 -1 3.0 700 -1303 -3061 94 225 1 6 " +
				"130 3 -1 3.0 600 -845 4824 83 -242 1 4 " +
				"131 3 -1 3.0 750 -2185 4593 70 -236 1 7 " +
				"134 3 -1 3.0 600 5856 -1943 -249 83 1 4 " +
				"135 3 -1 3.0 750 6657 1156 -266 -46 1 7 " +
				"137 3 -1 3.0 750 7632 -816 -302 32 1 7 " +
				"102 4 -1 -1.0 650 930 -2134 0 0 3 -1 " +
				"118 4 -1 -1.0 650 -1897 1604 0 0 2 -1 " +
				"133 4 -1 -1.0 800 -303 -2423 0 0 2 -1 " +
				"136 4 -1 -1.0 700 -884 -3021 0 0 1 -1";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));
		game.findBestAction(System.currentTimeMillis(), 50);
	}

	@Test
	public void test_run_check_simulation_2() throws Exception
	{
		String raw = "43 43 43 300 300 271 22 " +
				"0 0 0 0.5 400 3146 -546 430 -598 -1 -1 " +
				"1 1 0 1.5 400 5499 1008 69 -23 -1 -1 " +
				"2 2 0 1.0 400 -656 2864 264 -124 -1 -1 " +
				"3 0 1 0.5 400 -838 1728 -123 -238 -1 -1 " +
				"4 1 1 1.5 400 -1765 1321 -320 -184 -1 -1 " +
				"5 2 1 1.0 400 -3193 70 -200 -123 -1 -1 " +
				"6 0 2 0.5 400 2496 -1187 -450 -619 -1 -1 " +
				"7 1 2 1.5 400 -154 -3200 -53 -73 -1 -1 " +
				"8 2 2 1.0 400 -4358 2713 -436 319 -1 -1 " +
				"101 3 -1 4.5 600 -3591 -4220 -108 -124 4 4 " +
				"115 3 -1 5.0 650 -4118 724 -195 47 5 5 " +
				"119 3 -1 6.0 750 -2793 -1209 -100 -67 7 7 " +
				"125 3 -1 3.0 700 -1303 -3061 94 225 1 6 " +
				"130 3 -1 3.0 600 -845 4824 83 -242 1 4 " +
				"131 3 -1 3.0 750 -2185 4593 70 -236 1 7 " +
				"134 3 -1 3.0 600 5856 -1943 -249 83 1 4 " +
				"135 3 -1 3.0 750 6657 1156 -266 -46 1 7 " +
				"137 3 -1 3.0 750 7632 -816 -302 32 1 7 " +
				"102 4 -1 -1.0 650 930 -2134 0 0 3 -1 " +
				"118 4 -1 -1.0 650 -1897 1604 0 0 2 -1 " +
				"133 4 -1 -1.0 800 -303 -2423 0 0 2 -1 " +
				"136 4 -1 -1.0 700 -884 -3021 0 0 1 -1";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));

		game.looters.get(0).setWantedThrust(new Player.Game.Point(2500, -1200), 300);
		game.updateGame(1);
		System.out.println("Reaper at " + game.looters.get(0).x + ", " + game.looters.get(0).y);
	}

	@Test
	public void test_sortWrecksByInterest() throws Exception
	{
		String raw = "44 48 44 157 161 74 23 " +
				"0 0 0 0.5 400 1208 -943 -466 361 -1 -1 " +
				"1 1 0 1.5 400 -1486 839 -69 230 -1 -1 " +
				"2 2 0 1.0 400 6 -645 -15 98 -1 -1 " +
				"3 0 1 0.5 400 240 -1730 -240 -322 -1 -1 " +
				"4 1 1 1.5 400 2057 548 22 -239 -1 -1 " +
				"5 2 1 1.0 400 2371 -243 -205 -339 -1 -1 " +
				"6 0 2 0.5 400 -125 2759 85 -40 -1 -1 " +
				"7 1 2 1.5 400 -363 1266 163 -140 -1 -1 " +
				"8 2 2 1.0 400 1266 -1875 -21 -147 -1 -1 " +
				"127 3 -1 5.0 650 2549 -1600 113 -57 5 5 " +
				"130 3 -1 4.0 800 781 1863 -193 -176 3 8 " +
				"133 3 -1 3.5 750 -2318 1843 196 -156 2 7 " +
				"136 3 -1 3.0 750 4958 2311 -230 -108 1 7 " +
				"137 3 -1 3.0 750 -810 -5409 38 251 1 7 " +
				"139 3 -1 3.0 850 -5990 114 257 -5 1 9 " +
				"36 4 -1 -1.0 700 947 -4377 0 0 1 -1 " +
				"89 4 -1 -1.0 800 -3273 574 0 0 2 -1 " +
				"115 4 -1 -1.0 850 1016 2967 0 0 5 -1 " +
				"120 4 -1 -1.0 750 -1563 -2164 0 0 1 -1 " +
				"125 4 -1 -1.0 700 -2101 -3167 0 0 1 -1 " +
				"132 4 -1 -1.0 650 -4419 -2753 0 0 1 -1 " +
				"135 4 -1 -1.0 800 273 2570 0 0 5 -1 " +
				"138 4 -1 -1.0 750 -2471 495 0 0 7 -1";
		Player.Game game = new Player.Game(Player.CURRENT_VERSION);
		game.createFromInputLines(new Scanner(raw));

		List<Player.Game.WreckInterest> wis = Player.Game.sortWrecksByInterest(game);
		wis.forEach(w -> System.out.println("Target " + w.target.x + "," + w.target.y + " -> score : " + w.score));
	}

	private void assertLooperPosition(Player.Game game, int looterIndex, int x, int y)
	{
		System.out.println("Player " + looterIndex + " : " + game.looterIdToLooterMap.get(looterIndex).x + "," + game.looterIdToLooterMap.get(looterIndex).y);
		Assert.assertTrue(game.looterIdToLooterMap.get(looterIndex).x == x);
		Assert.assertTrue(game.looterIdToLooterMap.get(looterIndex).y == y);
	}
}