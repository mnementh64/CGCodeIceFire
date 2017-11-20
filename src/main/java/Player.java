import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player
{

	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);

		// game loop
		while (true)
		{
			int myScore = in.nextInt();
			int enemyScore1 = in.nextInt();
			int enemyScore2 = in.nextInt();
			int myRage = in.nextInt();
			int enemyRage1 = in.nextInt();
			int enemyRage2 = in.nextInt();
			int unitCount = in.nextInt();
			System.err.println(myScore + " " + enemyScore1 + " " + enemyScore2 + " " + myRage + " " + enemyRage1 + " " + enemyRage2 + " " + unitCount);
			for (int i = 0; i < unitCount; i++)
			{
				int unitId = in.nextInt();
				int unitType = in.nextInt();
				int player = in.nextInt();
				float mass = in.nextFloat();
				int radius = in.nextInt();
				int x = in.nextInt();
				int y = in.nextInt();
				int vx = in.nextInt();
				int vy = in.nextInt();
				int extra = in.nextInt();
				int extra2 = in.nextInt();
				String row = unitId + " " + unitType + " " + player + " " + mass + " " + radius + " " + x + " " + y + " " + vx + " " + vy + " " + extra + " "
						+ extra2;
				System.err.println(row);
			}

			// Write an action using System.out.println()
			// To debug: System.err.println("Debug messages...");

			System.out.println("WAIT");
			System.out.println("WAIT");
			System.out.println("WAIT");
		}
	}
}

