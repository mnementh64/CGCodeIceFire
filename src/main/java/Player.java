import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class Player
{

	static boolean DEBUG = false;
	static boolean DEBUG_SOLUTION = true;
	static boolean DEBUG_INPUT = true;
	private static final boolean SORT_WRECK_BY_INTEREST = false;
	private static final long GAME_TIME_LIMIT_FIRST = 960;
	private static final long GAME_TIME_LIMIT = 40;

	public static final int CURRENT_VERSION = 3;
	static Game game = new Game(CURRENT_VERSION);
	static int round = 1;
	static int nbSolutionsBySimulation = 0;
	static int nbRoundsWithAllSolutionsTested = 0;

	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);

		// game loop
		while (true)
		{
			long t0 = System.currentTimeMillis();
			if (round == 1)
			{
				game.createFromInputLines(in);
			}
			else
			{
				game.updateFromInputLines(in);
			}

			if (DEBUG)
				System.err.println("Init done in " + (System.currentTimeMillis() - t0) + "ms");
			List<Game.Action> bestActions = game.findBestAction(t0, round == 1 ? GAME_TIME_LIMIT_FIRST : GAME_TIME_LIMIT);

			// apply the best solution
			bestActions.stream().limit(3).map(Game.Action::toString).forEach(System.out::println);

			if (DEBUG_SOLUTION)
			{
				System.err.println(
						"Round " + round + " done in " + (System.currentTimeMillis() - t0) + "ms / Solutions found by simulation : " + nbSolutionsBySimulation);
				System.err.println(
						"Nb of rounds with all solutions tested : " + nbRoundsWithAllSolutionsTested);
			}

			round++;
		}

	}

	static class Game
	{

		private static int GAME_VERSION = 3;

		static boolean SPAWN_WRECK = false;
		static int LOOTER_COUNT = 3;
		static boolean REAPER_SKILL_ACTIVE = true;
		static boolean DESTROYER_SKILL_ACTIVE = true;
		static boolean DOOF_SKILL_ACTIVE = true;

		static class PowerIterator
		{

			int start;
			int step;

			public PowerIterator(int start, int step)
			{
				this.start = start;
				this.step = step;
			}
		}

		final static PowerIterator POWER_ITERATOR_ROUND1 = new PowerIterator(100, 100);
		final static PowerIterator POWER_ITERATOR_ROUND2 = new PowerIterator(200, -1);
		final static int ANGLE_STEP_ROUND1 = 15;
		final static int ANGLE_STEP_ROUND2 = 30;
		final static double[] ALL_COSINUS_ROUND1 = new double[360 / ANGLE_STEP_ROUND1];
		final static double[] ALL_SINUS_ROUND1 = new double[360 / ANGLE_STEP_ROUND1];;
		final static double[] ALL_COSINUS_ROUND2 = new double[360 / ANGLE_STEP_ROUND2];
		final static double[] ALL_SINUS_ROUND2 = new double[360 / ANGLE_STEP_ROUND2];

		public Game(int gameVersion)
		{
			for (int i = 0; i < (360 / ANGLE_STEP_ROUND1); i++)
			{
				ALL_COSINUS_ROUND1[i] = Math.cos(2.0 * Math.PI * ((double) i * ANGLE_STEP_ROUND1) / 360.0);
				ALL_SINUS_ROUND1[i] = Math.sin(2.0 * Math.PI * ((double) i * ANGLE_STEP_ROUND1) / 360.0);
			}

			for (int i = 0; i < (360 / ANGLE_STEP_ROUND2); i++)
			{
				ALL_COSINUS_ROUND2[i] = Math.cos(2.0 * Math.PI * ((double) i * ANGLE_STEP_ROUND2) / 360.0);
				ALL_SINUS_ROUND2[i] = Math.sin(2.0 * Math.PI * ((double) i * ANGLE_STEP_ROUND2) / 360.0);
			}

			GAME_VERSION = gameVersion;

			switch (GAME_VERSION)
			{
				case 0:
					SPAWN_WRECK = true;
					LOOTER_COUNT = 1;
					REAPER_SKILL_ACTIVE = false;
					DESTROYER_SKILL_ACTIVE = false;
					DOOF_SKILL_ACTIVE = false;
					break;
				case 1:
					LOOTER_COUNT = 2;
					REAPER_SKILL_ACTIVE = false;
					DESTROYER_SKILL_ACTIVE = false;
					DOOF_SKILL_ACTIVE = false;
					break;
				case 2:
					LOOTER_COUNT = 3;
					REAPER_SKILL_ACTIVE = false;
					DOOF_SKILL_ACTIVE = false;
					break;
				default:
			}
		}

		static double MAP_RADIUS = 6000.0;
		static int TANKERS_BY_PLAYER;
		static int TANKERS_BY_PLAYER_MIN = 1;
		static int TANKERS_BY_PLAYER_MAX = 3;

		static double WATERTOWN_RADIUS = 3000.0;

		static int TANKER_THRUST = 500;
		static double TANKER_EMPTY_MASS = 2.5;
		static double TANKER_MASS_BY_WATER = 0.5;
		static double TANKER_FRICTION = 0.40;
		static double TANKER_RADIUS_BASE = 400.0;
		static double TANKER_RADIUS_BY_SIZE = 50.0;
		static int TANKER_EMPTY_WATER = 1;
		static int TANKER_MIN_SIZE = 4;
		static int TANKER_MAX_SIZE = 10;
		static double TANKER_MIN_RADIUS = TANKER_RADIUS_BASE + TANKER_RADIUS_BY_SIZE * TANKER_MIN_SIZE;
		static double TANKER_MAX_RADIUS = TANKER_RADIUS_BASE + TANKER_RADIUS_BY_SIZE * TANKER_MAX_SIZE;
		static double TANKER_SPAWN_RADIUS = 8000.0;
		static int TANKER_START_THRUST = 2000;

		static int MAX_THRUST = 300;
		static int MAX_RAGE = 300;
		static int WIN_SCORE = 50;

		static double REAPER_MASS = 0.5;
		static double REAPER_FRICTION = 0.20;
		static int REAPER_SKILL_DURATION = 3;
		static int REAPER_SKILL_COST = 30;
		static int REAPER_SKILL_ORDER = 0;
		static double REAPER_SKILL_RANGE = 2000.0;
		static double REAPER_SKILL_RADIUS = 1000.0;
		static double REAPER_SKILL_MASS_BONUS = 10.0;

		static double DESTROYER_MASS = 1.5;
		static double DESTROYER_FRICTION = 0.30;
		static int DESTROYER_SKILL_DURATION = 1;
		static int DESTROYER_SKILL_COST = 60;
		static int DESTROYER_SKILL_ORDER = 2;
		static double DESTROYER_SKILL_RANGE = 2000.0;
		static double DESTROYER_SKILL_RADIUS = 1000.0;
		static int DESTROYER_NITRO_GRENADE_POWER = 1000;

		static double DOOF_MASS = 1.0;
		static double DOOF_FRICTION = 0.25;
		static double DOOF_RAGE_COEF = 1.0 / 100.0;
		static int DOOF_SKILL_DURATION = 3;
		static int DOOF_SKILL_COST = 30;
		static int DOOF_SKILL_ORDER = 1;
		static double DOOF_SKILL_RANGE = 2000.0;
		static double DOOF_SKILL_RADIUS = 1000.0;

		static double LOOTER_RADIUS = 400.0;
		public static int LOOTER_REAPER = 0;
		public static int LOOTER_DESTROYER = 1;
		public static int LOOTER_DOOF = 2;

		public static int TYPE_TANKER = 3;
		public static int TYPE_WRECK = 4;
		static int TYPE_REAPER_SKILL_EFFECT = 5;
		static int TYPE_DOOF_SKILL_EFFECT = 6;
		static int TYPE_DESTROYER_SKILL_EFFECT = 7;

		static double EPSILON = 0.00001;
		static double MIN_IMPULSE = 30.0;
		static double IMPULSE_COEFF = 0.5;

		// Global first free id for all elements on the map
		static int GLOBAL_ID = 0;

		// Center of the map
		final static Point WATERTOWN = new Point(0, 0);

		// The null collision
		final static Collision NULL_COLLISION = new Collision(1.0 + EPSILON);

		long seed;
		int playerCount = 3;
		List<Unit> units;
		List<Looter> looters;
		List<Tanker> tankers;
		List<Wreck> wrecks;
		List<List<? extends Unit>> unitsByType;
		List<InnerPlayer> innerPlayers;
		Set<SkillEffect> skillEffects;

		Map<Integer, Looter> looterIdToLooterMap = new HashMap<>();
		Map<Integer, Tanker> tankerIdToTankerMap = new HashMap<>();
		Map<Integer, Wreck> wreckIdToWreckMap = new HashMap<>();

		public List<Action> findBestAction(long t0, long timeLimit)
		{
			int nbSimu = 0;
			boolean bestFromSimu = false;
			List<Action> bestActions;
			Game game;

			int playerScore = innerPlayers.get(0).score;

			Action w11 = actionWait();
			Action w12 = actionWait();
			Action w13 = actionWait();
			Action w21 = actionWait();
			Action w22 = actionWait();
			Action w23 = actionWait();

			// push an heuristic as default action if not better has been found
			Action destroyerAction1 = destroyerAction(this);
			Action doofAction1 = doofAction(this);
			Action defaultAction = reaperAction(this);

			// evaluate the my score with these default heuristic actions
			game = new Game(this);
			try
			{
				game.evolve(new Action[]
				{ defaultAction, destroyerAction1, doofAction1, w11, w12, w13, w21, w22, w23 });
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			int bestScore = game.evaluate();
			bestActions = Arrays.asList(defaultAction, destroyerAction1, doofAction1);

			if (DEBUG_SOLUTION)
				System.err.println("Before simulation after " + (System.currentTimeMillis() - t0) + "ms - score by heuristic : " + bestScore);

			int nbMaxSimu = 0;
			try
			{
				// list possible actions for reaper
				Action[][] allActions = listPossibleActionsForReaper();

				nbMaxSimu = allActions.length;
				if (DEBUG_SOLUTION)
					System.err.println("Max nb of simu : " + nbMaxSimu);

				for (int i = 0; i < nbMaxSimu; i++)
				{
					Action[] reaperActions = allActions[i];
					if ((System.currentTimeMillis() - t0) > timeLimit)
					{
						break;
					}

					// work on game duplicate
					game = new Game(this);

					// STEP 1
					game.evolve(new Action[]
					{ reaperActions[0], destroyerAction1, doofAction1, w11, w12, w13, w21, w22, w23 });
					Point pt1 = new Point(game.looters.get(0).x, game.looters.get(0).y);
					int value = game.evaluate();

					// STEP 2
					// compute new destroyer / doof actions in game at next step
					Action destroyerAction2 = destroyerAction(game);
					Action doofAction2 = doofAction(game);

					game.evolve(new Action[]
					{ reaperActions[1], destroyerAction2, doofAction2, w11, w12, w13, w21, w22, w23 });
					Point pt2 = new Point(game.looters.get(0).x, game.looters.get(0).y);
					value += game.evaluate() - playerScore;

					// register solution only if the projected score is better than the score
					if (value > bestScore)
					{
						if (DEBUG_SOLUTION)
						{
							System.err.println("solution : " + value + " - looter from " + looters.get(0).x + "," + looters.get(0).y +
									" to A: " + pt1.x + "," + pt1.y +
									" to B: " + pt2.x + "," + pt2.y +
									" (target " + reaperActions[0].x + "," + reaperActions[0].y + " with power " + reaperActions[0].extra + ")");
						}
						bestActions = Arrays.asList(reaperActions[0], destroyerAction1, doofAction1);

						bestFromSimu = true;
						bestScore = value;
					}

					nbSimu++;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			nbSolutionsBySimulation += bestFromSimu ? 1 : 0;
			nbRoundsWithAllSolutionsTested += (nbSimu == nbMaxSimu) ? 1 : 0;

			if (DEBUG_SOLUTION)
			{
				System.err.println("Nb simulations : " + nbSimu);
			}

			return bestActions;
		}

		private Action[][] listPossibleActionsForReaper()
		{
			List<List<Action>> possibleActions = listPossibleActionsForReaperForRounds();
			Action[][] allActions = new Action[possibleActions.size()][];
			int idx = 0;
			for (List<Action> nestedList : possibleActions)
			{
				allActions[idx++] = nestedList.toArray(new Action[nestedList.size()]);
			}

			return allActions;
		}

		public List<List<Action>> listPossibleActionsForReaperForRounds()
		{
			int r = 2000;
			List<List<Action>> actions = new ArrayList<>();

			// always try with wait
			actions.add(Arrays.asList(actionWait(), actionWait()));

			// also wait and one step all around
			Looter reaper = looters.get(0);
			double x = reaper.x;
			double y = reaper.y;
			int powerLimit1 = POWER_ITERATOR_ROUND1.step != -1 ? 300 : POWER_ITERATOR_ROUND1.start;
			int powerStep1 = POWER_ITERATOR_ROUND1.step != -1 ? POWER_ITERATOR_ROUND1.step : 300;
			int powerLimit2 = POWER_ITERATOR_ROUND2.step != -1 ? 300 : POWER_ITERATOR_ROUND2.start;
			int powerStep2 = POWER_ITERATOR_ROUND2.step != -1 ? POWER_ITERATOR_ROUND2.step : 300;
			for (int i = 0; i < (360 / ANGLE_STEP_ROUND1); i++)
			{
				for (int power = POWER_ITERATOR_ROUND1.start; power <= powerLimit1; power += powerStep1)
				{
					Action action = actionMove((int) (x + r * ALL_COSINUS_ROUND1[i]), (int) (y + r * ALL_SINUS_ROUND1[i]), power);
					actions.add(Arrays.asList(actionWait(), action));
				}
			}

			// then append one step all around and a next one all around (with less angles)
			for (int i = 0; i < (360 / ANGLE_STEP_ROUND1); i++)
			{
				for (int power1 = 100; power1 <= 300; power1 += 100)
				{
					Action action1 = actionMove((int) (x + r * ALL_COSINUS_ROUND1[i]), (int) (y + r * ALL_SINUS_ROUND1[i]), power1);

					for (int j = 0; j < (360 / ANGLE_STEP_ROUND2); j++)
					{
						for (int power2 = POWER_ITERATOR_ROUND2.start; power2 <= powerLimit2; power2 += powerStep2)
						{
							Action action2 = actionMove((int) (x + r * ALL_COSINUS_ROUND2[j]), (int) (y + r * ALL_SINUS_ROUND2[j]), power2);
							actions.add(Arrays.asList(action1, action2));
						}
					}
				}
			}

			return actions;
		}

		static class WreckInterest
		{

			Point target;
			int score;

			public WreckInterest(Point point, int score)
			{
				this.target = point;
				this.score = score;
			}
		}

		private static Action reaperAction(Game game)
		{
			Point target = null;
			double distanceMin = Double.MAX_VALUE;
			int power = 0;

			// sort wreck by their interest (the number of other wrecks they intersect)
			if (Player.SORT_WRECK_BY_INTEREST)
			{
				List<WreckInterest> wrecksByInterest = sortWrecksByInterest(game);
				if (!wrecksByInterest.isEmpty())
				{
					target = wrecksByInterest.get(0).target;
				}
			}
			else
			{
				Looter looter = game.looters.get(0);
				for (Wreck wreck : game.wrecks)
				{
					if (wreck.water <= 0)
					{
						continue;
					}
					// if oil on wreck, skip it
					if (isOilOnTarget(game, wreck))
					{
						continue;
					}

					// if tanker on target, skip it
					if (isTankerOnTarget(game, wreck))
					{
						continue;
					}

					double distance = wreck.distance(looter);
					if (distance < distanceMin)
					{
						distanceMin = distance;
						target = wreck;
					}
				}
			}
			if (target != null)
			{
				power = distanceMin <= 300 ? (int) (distanceMin / 10) : 300;
			}
//					if (target != null && DEBUG)
//					{
//						System.err.println("Looper " + looter.id + " targets tanker " + ((Tanker) target).id + " at " + target.x + "," + target.y);
//					}
			if (target != null)
			{
				power = 300;
			}

			if (target != null)
			{
				return Game.actionMove(new Double(target.x).intValue(), new Double(target.y).intValue(), power);
			}

			return targetWinnerReaper(game);
		}

		public static List<WreckInterest> sortWrecksByInterest(Game game)
		{
			Looter myReaper = game.looters.get(0);
			List<WreckInterest> wreckByInterest = game.wrecks.stream()
					.filter(wreck -> wreck.water > 0 && !isOilOnTarget(game, wreck) && !isTankerOnTarget(game, wreck))
					.map(wreck ->
					{
						int score = wreck.water;

						//						System.err.println("Wreck " + wreck.id + " - initial score : " + wreck.water);

						double totalX = wreck.radius * wreck.x;
						double totalY = wreck.radius * wreck.y;
						double totalRadius = wreck.radius;

						for (Wreck w : game.wrecks)
						{
							if (w.id == wreck.id)
							{
								continue;
							}

							// other wreck is at distance
							double distanceToWreck = w.distance(wreck);
							//							System.err.println("\tdistance to wreck " + w.id + " : " + distanceToWreck + " compare to radius " + );
							if (distanceToWreck < (wreck.radius + w.radius) / 1.25)
							{
								score += 2 * w.water; // wreck superposition values a lot !
								totalX += w.radius * w.x;
								totalY += w.radius * w.y;
								totalRadius += w.radius;
								//								System.err.println("\tWreck " + wreck.id + " intersect " + w.id + " -> add water " + w.water + " to score " + score);
							}
						}

						// compute the target
						Point target = new Point(totalX / totalRadius, totalY / totalRadius);

						// minus the score with the distance -> 1 pt every 1000
						double otherWreckDistance = myReaper.distance(target);
						score -= otherWreckDistance / 500;
						//						System.err.println("\tAdjust by distance " + otherWreckDistance + " to score " + score);

						score = -score;// negative value to use natural order

						return new WreckInterest(target, score);
					}).collect(Collectors.toList());
			wreckByInterest.sort(Comparator.comparingInt(value -> value.score));

			return wreckByInterest;
		}

		private static boolean isTankerOnTarget(Game game, Point target)
		{
			return game.tankers.stream().anyMatch(tanker -> tanker.isInRange(target, tanker.radius));
		}

		private static boolean isOilOnTarget(Game game, Point target)
		{
			return game.skillEffects.stream().anyMatch(effect -> effect.isInRange(target, DOOF_SKILL_RADIUS));
		}

		private static Action destroyerAction(Game game)
		{
			Point target = null;
			double distanceMin = Double.MAX_VALUE;
			int power = 0;
//			InnerPlayer me = game.innerPlayers.get(0);
//			Looter reaper0 = game.looters.get(0);
//			Looter reaper1 = game.looters.get(3);
//			Looter reaper2 = game.looters.get(6);

			Looter myDestroyer = game.looters.get(1);
			for (Tanker tanker : game.tankers)
			{
				if (tanker.water <= 0)
				{
					continue;
				}
				double distance = tanker.distance(myDestroyer);
				if (distance < distanceMin)
				{
//					// validate this tanker as a target only if my reaper is the closest reaper to it
//					double d0 = reaper0.distance(tanker);
//					double d1 = reaper1.distance(tanker);
//					double d2 = reaper2.distance(tanker);
//
//					if (d0 < d1 && d0 < d2)
//					{
					distanceMin = distance;
					target = tanker;
//					}
				}
			}

			// if too far, cancel the target
			if (distanceMin > 3000)
			{
				target = null;
			}
//					if (target != null && DEBUG)
//					{
//						System.err.println("Looper " + looter.id + " targets tanker " + ((Tanker) target).id + " at " + target.x + "," + target.y);
//					}
			if (target != null)
			{
				power = 300;
			}

			if (target != null)
			{
				return Game.actionMove(new Double(target.x).intValue(), new Double(target.y).intValue(), power);
			}
			// I am not convinced by this heuristic !
//			else if (me.rage > 100) // enough rage ?
//			{
//				// alternative action : launch a grenade
//				// look if an opponent reaper in a wreck
//				boolean isReaper1InWreck = isReaperInWreck(game, reaper1) && reaper1.isInRange(myDestroyer, 2000); // also within skill range ?
//				boolean isReaper2InWreck = isReaperInWreck(game, reaper2) && reaper2.isInRange(myDestroyer, 2000); // also within skill range ?
//
//				if (isReaper1InWreck && !isReaper2InWreck)
//				{
//					target = reaper1;
//				}
//				else if (!isReaper1InWreck && isReaper2InWreck)
//				{
//					target = reaper2;
//				}
//				else if (isReaper1InWreck)
//				{
//					if (game.innerPlayers.get(1).score > game.innerPlayers.get(2).score)
//						target = reaper1;
//					else
//						target = reaper2;
//				}
//
//				if (target != null)
//				{
//					// if my reaper is too close, cancel
//					Looter myReaper = game.looters.get(0);
//					if (!myReaper.isInRange(target, DESTROYER_SKILL_RADIUS))
//					{
//						if (DEBUG_SOLUTION)
//						{
//							System.err.println("Launch grenade to " + target.x + "," + target.y);
//						}
//						// TODO : find a point between my reaper and the target, close to the target
//						// so expulse it in an opposite way to my reaper
//						return actionSkill(new Double(target.x - 10).intValue(), new Double(target.y - 10).intValue());
//					}
//					else
//					{
//						if (DEBUG_SOLUTION)
//						{
//							System.err.println("Cancel grenade --> reaper too close !");
//						}
//					}
//				}
//			}

			return targetWinnerReaper(game);
		}

		private static boolean isReaperInWreck(Game game, Looter reaper)
		{
			return game.wrecks.stream().anyMatch(w -> reaper.isInRange(w, w.radius));
		}

		private static Action doofAction(Game game)
		{
			Looter target = null;
			InnerPlayer me = game.innerPlayers.get(0);
			Looter reaper0 = game.looters.get(0);
			Looter reaper1 = game.looters.get(3);
			Looter reaper2 = game.looters.get(6);
			if (me.rage > REAPER_SKILL_COST) // enough rage ?
			{
				// alternative action : launch a oil
				// look if an opponent reaper in a wreck
				boolean isReaper1InWreck = isReaperInWreck(game, reaper1) && !reaper1.isInRange(reaper0, 2000); // also within skill range ?
				boolean isReaper2InWreck = isReaperInWreck(game, reaper2) && !reaper2.isInRange(reaper0, 2000); // also within skill range ?

				if (isReaper1InWreck && !isReaper2InWreck)
				{
					target = reaper1;
				}
				else if (!isReaper1InWreck && isReaper2InWreck)
				{
					target = reaper2;
				}
				else if (isReaper1InWreck)
				{
					if (game.innerPlayers.get(1).score > game.innerPlayers.get(2).score)
						target = reaper1;
					else
						target = reaper2;
				}

				if (target != null)
				{
					// if my reaper is too close, cancel
					Looter myReaper = game.looters.get(0);
					if (!myReaper.isInRange(target, DOOF_SKILL_RADIUS))
					{
						if (DEBUG_SOLUTION)
						{
							System.err.println("Launch oil to " + target.x + "," + target.y);
						}
						return actionSkill(new Double(target.x).intValue(), new Double(target.y).intValue());
					}
					else
					{
						if (DEBUG_SOLUTION)
						{
							System.err.println("Cancel oil --> reaper too close !");
						}
					}
				}
			}

			return targetWinnerReaper(game);
		}

		private static Action targetWinnerReaper(Game game)
		{
			// find the opponent with the best score
			if (game.innerPlayers.get(1).score > game.innerPlayers.get(2).score)
			{
				return Game.actionMove(new Double(game.looters.get(3).x).intValue(), new Double(game.looters.get(3).y).intValue(), 300);
			}
			else
			{
				return Game.actionMove(new Double(game.looters.get(6).x).intValue(), new Double(game.looters.get(6).y).intValue(), 300);
			}
		}

		private void evolve(Action[] actions) throws Exception
		{
			handleActions(actions);
			updateGame(0);
		}

		static class RoundAction
		{

			List<Action> actions; // one action per looper

		}

		static class Action
		{

			int x;
			int y;
			int extra;
			ActionType what;

			@Override
			public String toString()
			{
				return what.equals(ActionType.MOVE) ? x + " " + y + " " + extra : what.equals(ActionType.WAIT) ? "WAIT" : "SKILL " + x + " " + y;
			}
		}

		public static Action actionWait()
		{
			Action action = new Action();
			action.what = ActionType.WAIT;
			return action;
		}

		public static Action actionMove(int x, int y, int power)
		{
			Action action = new Action();
			action.what = ActionType.MOVE;
			action.x = x;
			action.y = y;
			action.extra = power;
			return action;
		}

		public static Action actionSkill(int x, int y)
		{
			Action action = new Action();
			action.what = ActionType.SKILL;
			action.x = x;
			action.y = y;
			return action;
		}

		public int evaluate()
		{
			// simpliest eval function
			int score = innerPlayers.get(0).score;

			// add points for the size of the wreck if in wreck radius
			score += findWreckWater(looters.get(0));

			return score;
		}

		private int findWreckWater(Point point)
		{
			return wrecks.stream()
					.filter(w -> w.water > 0 && w.isInRange(point, w.radius))
					//					.peek(w -> System.err.println("Wreck : " + w.id + " -> " + w.water))
					.mapToInt(w -> w.water)
					.sum();
		}

		public Game(Game game)
		{
			initEmpty();

			// copy players
			for (int playerIdx = 0; playerIdx < 3; playerIdx++)
			{
				InnerPlayer innerPlayerModel = game.innerPlayers.get(playerIdx);
				InnerPlayer innerPlayer = new InnerPlayer(playerIdx, innerPlayerModel.score, innerPlayerModel.rage);
				this.innerPlayers.add(innerPlayer);

				// copy loopers
				for (int looterIdx = 0; looterIdx < innerPlayerModel.looters.length; looterIdx++)
				{
					Looter looterModel = innerPlayerModel.looters[looterIdx];
					Looter looter = copyLooter(looterModel, innerPlayer);

					// add it to all involved collections
					innerPlayer.looters[looter.type] = looter;
					units.add(looter);
					looters.add(looter);
					looterIdToLooterMap.put(looter.id, looter);
				}
			}

			// copy tankers
			game.tankers.forEach(tankerModel ->
			{
				Tanker tanker = new Tanker(tankerModel.size, null);
				copyUnit(tankerModel, tanker);
				tanker.x = tankerModel.x;
				tanker.y = tankerModel.y;
				tanker.water = tankerModel.water;

				tankers.add(tanker);
				tankerIdToTankerMap.put(tanker.id, tanker);
			});

			// copy wrecks
			game.wrecks.forEach(wreckModel ->
			{
				Wreck wreck = new Wreck(wreckModel.x, wreckModel.y, wreckModel.water, wreckModel.radius);
				wreck.id = wreckModel.id;
				wreck.known = wreckModel.known;

				wrecks.add(wreck);
				wreckIdToWreckMap.put(wreck.id, wreck);
			});

			game.skillEffects.forEach(skillEffect ->
			{
				SkillEffect effect = null;
				if (skillEffect.type == TYPE_REAPER_SKILL_EFFECT)
				{
					effect = new ReaperSkillEffect(TYPE_REAPER_SKILL_EFFECT, skillEffect.x, skillEffect.y, REAPER_SKILL_RADIUS, REAPER_SKILL_DURATION,
							REAPER_SKILL_ORDER, null);
				}
				else if (skillEffect.type == TYPE_DESTROYER_SKILL_EFFECT)
				{
					effect = new DestroyerSkillEffect(TYPE_DESTROYER_SKILL_EFFECT, skillEffect.x, skillEffect.y, DESTROYER_SKILL_RADIUS,
							DESTROYER_SKILL_DURATION, DESTROYER_SKILL_ORDER, null);
				}
				else if (skillEffect.type == TYPE_DOOF_SKILL_EFFECT)
				{
					effect = new DoofSkillEffect(TYPE_DOOF_SKILL_EFFECT, skillEffect.x, skillEffect.y, DOOF_SKILL_RADIUS, DOOF_SKILL_DURATION, DOOF_SKILL_ORDER,
							null);
				}
				skillEffects.add(effect);
			});
		}

		private void copyUnit(Unit from, Unit to)
		{
			to.id = from.id;
			to.vx = from.vx;
			to.vy = from.vy;
			to.radius = from.radius;
			to.mass = from.mass;
			to.friction = from.friction;
			to.known = from.known;
		}

		private Looter copyLooter(Looter looterModel, InnerPlayer innerPlayer)
		{
			Looter looter = createLooter(looterModel.type, innerPlayer, looterModel.x, looterModel.y);
			// Unit class
			copyUnit(looterModel, looter);

			// Looter class
			looter.skillCost = looterModel.skillCost;
			looter.skillRange = looterModel.skillRange;
			looter.skillActive = looterModel.skillActive;

			if (looterModel.wantedThrustTarget != null)
			{
				looter.wantedThrustTarget = new Point(looterModel.wantedThrustTarget.x, looterModel.wantedThrustTarget.y);
				looter.wantedThrustPower = looterModel.wantedThrustPower;
			}

			looter.message = looterModel.message;
			looter.attempt = looterModel.attempt;

			if (looterModel.skillResult != null)
			{
				looter.skillResult = new SkillResult(looterModel.skillResult.getX(), looterModel.skillResult.getY());
				looter.skillResult.code = looterModel.skillResult.code;
			}

			return looter;
		}

		public void handleActions(Action[] actions) throws Exception
		{
			handlePlayerOutput(0, actions, 0);
			handlePlayerOutput(1, actions, 3);
			handlePlayerOutput(2, actions, 6);
		}

		protected void initEmpty()
		{
			units = new ArrayList<>();
			looters = new ArrayList<>();
			tankers = new ArrayList<>();
			wrecks = new ArrayList<>();
			innerPlayers = new ArrayList<>();

			unitsByType = new ArrayList<>();
			unitsByType.add(looters);
			unitsByType.add(tankers);

			skillEffects = new TreeSet<>((a, b) ->
			{
				int order = a.order - b.order;

				if (order != 0)
				{
					return order;
				}

				return a.id - b.id;
			});
		}

		protected void createFromInputLines(Scanner in)
		{
			initEmpty();

			int myScore = in.nextInt();
			int enemyScore1 = in.nextInt();
			int enemyScore2 = in.nextInt();
			int myRage = in.nextInt();
			int enemyRage1 = in.nextInt();
			int enemyRage2 = in.nextInt();

			// Create players
			InnerPlayer innerPlayer = new InnerPlayer(0, myScore, myRage);
			innerPlayers.add(innerPlayer);
			innerPlayer = new InnerPlayer(1, enemyScore1, enemyRage1);
			innerPlayers.add(innerPlayer);
			innerPlayer = new InnerPlayer(2, enemyScore2, enemyRage2);
			innerPlayers.add(innerPlayer);

			int unitCount = in.nextInt();
			if (Player.DEBUG_INPUT)
			{
				System.err.println(myScore + " " + enemyScore1 + " " + enemyScore2 + " " + myRage + " " + enemyRage1 + " " + enemyRage2 + " " + unitCount);
			}
			for (int i = 0; i < unitCount; i++)
			{
				int unitId = in.nextInt();
				int unitType = in.nextInt();
				int player = in.nextInt();

				createOrUpdateUnit(unitId, unitType, player, in);
			}

			adjust();
		}

		protected void updateFromInputLines(Scanner in)
		{
			int myScore = in.nextInt();
			int enemyScore1 = in.nextInt();
			int enemyScore2 = in.nextInt();
			int myRage = in.nextInt();
			int enemyRage1 = in.nextInt();
			int enemyRage2 = in.nextInt();

			// Create players
			InnerPlayer innerPlayer = innerPlayers.get(0);
			innerPlayer.score = myScore;
			innerPlayer.rage = myRage;

			innerPlayer = innerPlayers.get(1);
			innerPlayer.score = enemyScore1;
			innerPlayer.rage = enemyRage1;

			innerPlayer = innerPlayers.get(2);
			innerPlayer.score = enemyScore2;
			innerPlayer.rage = enemyRage2;

			int unitCount = in.nextInt();
			if (Player.DEBUG_INPUT)
			{
				System.err.println(myScore + " " + enemyScore1 + " " + enemyScore2 + " " + myRage + " " + enemyRage1 + " " + enemyRage2 + " " + unitCount);
			}

			Set<Integer> expectedWreckIds = new HashSet<>();
			Set<Integer> expectedTankersIds = new HashSet<>();
			for (int i = 0; i < unitCount; i++)
			{
				int unitId = in.nextInt();
				int unitType = in.nextInt();
				int player = in.nextInt();

				createOrUpdateUnit(unitId, unitType, player, in);

				if (unitType == TYPE_TANKER)
				{
					expectedTankersIds.add(unitId);
				}
				else if (unitType == TYPE_WRECK)
				{
					expectedWreckIds.add(unitId);
				}
			}

			// remove unexpected tankers and wrecks
			wrecks.removeIf(wreck -> !expectedWreckIds.contains(wreck.id));
			tankers.removeIf(tanker -> !expectedTankersIds.contains(tanker.id));
		}

		private void createOrUpdateUnit(int unitId, int unitType, int playerIndex, Scanner in)
		{
			float mass = in.nextFloat();
			int radius = in.nextInt();
			int x = in.nextInt();
			int y = in.nextInt();
			int vx = in.nextInt();
			int vy = in.nextInt();
			int extra = in.nextInt();
			int extra2 = in.nextInt();
			if (Player.DEBUG_INPUT)
			{
				String row =
						unitId + " " + unitType + " " + playerIndex + " " + mass + " " + radius + " " + x + " " + y + " " + vx + " " + vy + " " + extra + " "
								+ extra2;
				System.err.println(row);
			}

			if (unitType < LOOTER_COUNT)
			{
				InnerPlayer player = playerIndex >= 0 ? innerPlayers.get(playerIndex) : null;

				// get / create looter
				Looter looter = looterIdToLooterMap.get(unitId);
				if (looter == null)
				{
					looter = createLooter(unitType, player, x, y);
					looter.id = unitId;
					looter.mass = mass;
					looter.radius = radius;

					// add it to all involved collections
					player.looters[unitType] = looter;
					units.add(looter);
					looters.add(looter);
					looterIdToLooterMap.put(unitId, looter);
				}
				else
				{
					looter.x = x;
					looter.y = y;
				}
				looter.vx = vx;
				looter.vy = vy;
			}
			else if (unitType == TYPE_TANKER)
			{
				// get / create tanker
				Tanker tanker = tankerIdToTankerMap.get(unitId);
				if (tanker == null)
				{
					tanker = new Tanker(extra2, null);
					tanker.id = unitId;

					tankers.add(tanker);
					tankerIdToTankerMap.put(unitId, tanker);
				}

				tanker.x = x;
				tanker.y = y;
				tanker.vx = vx;
				tanker.vy = vy;
				tanker.mass = mass;
				tanker.radius = radius;
				tanker.water = extra;
			}
			else if (unitType == TYPE_WRECK)
			{
				// get / create wreck
				Wreck wreck = wreckIdToWreckMap.get(unitId);
				if (wreck == null)
				{
					wreck = new Wreck(x, y, extra, radius);
					wreck.id = unitId;

					wrecks.add(wreck);
					wreckIdToWreckMap.put(unitId, wreck);
				}
				else
				{
					wreck.water = extra;
				}
			}
			else if (unitType == TYPE_REAPER_SKILL_EFFECT)
			{
				SkillEffect effect =
						new ReaperSkillEffect(TYPE_REAPER_SKILL_EFFECT, x, y, REAPER_SKILL_RADIUS, REAPER_SKILL_DURATION, REAPER_SKILL_ORDER, null);
				skillEffects.add(effect);
			}
			else if (unitType == TYPE_DESTROYER_SKILL_EFFECT)
			{
				SkillEffect effect =
						new DestroyerSkillEffect(TYPE_DESTROYER_SKILL_EFFECT, x, y, DESTROYER_SKILL_RADIUS, DESTROYER_SKILL_DURATION, DESTROYER_SKILL_ORDER,
								null);
				skillEffects.add(effect);
			}
			else if (unitType == TYPE_DOOF_SKILL_EFFECT)
			{
				SkillEffect effect =
						new DoofSkillEffect(TYPE_DOOF_SKILL_EFFECT, x, y, DOOF_SKILL_RADIUS, DOOF_SKILL_DURATION, DOOF_SKILL_ORDER, null);
				skillEffects.add(effect);
			}
		}

		protected void updateGame(int round) throws GameOverException
		{
			// Apply skill effects
			for (SkillEffect effect : skillEffects)
			{
				effect.apply(units);
			}

			// Apply thrust for tankers
			for (Tanker t : tankers)
			{
				t.play();
			}

			// Apply wanted thrust for looters
			for (InnerPlayer player : innerPlayers)
			{
				for (Looter looter : player.looters)
				{
					if (looter.wantedThrustTarget != null)
					{
						looter.thrust(looter.wantedThrustTarget, looter.wantedThrustPower);
					}
				}
			}

			double t = 0.0;

			// Play the round. Stop at each collisions and play it. Reapeat until t > 1.0

			Collision collision = getNextCollision();

			while (collision.t + t <= 1.0)
			{
				double delta = collision.t;
				units.forEach(u -> u.move(delta));
				t += collision.t;

				playCollision(collision);

				collision = getNextCollision();
			}

			// No more collision. Move units until the end of the round
			double delta = 1.0 - t;
			units.forEach(u -> u.move(delta));

			List<Tanker> tankersToRemove = new ArrayList<>();

			tankers.forEach(tanker ->
			{
				double distance = tanker.distance(WATERTOWN);
				boolean full = tanker.isFull();

				if (distance <= WATERTOWN_RADIUS && !full)
				{
					// A non full tanker in watertown collect some water
					tanker.water += 1;
					tanker.mass += TANKER_MASS_BY_WATER;
				}
				else if (distance >= TANKER_SPAWN_RADIUS + tanker.radius && full)
				{
					// Remove too far away and not full tankers from the game
					tankersToRemove.add(tanker);
				}
			});

			units.removeAll(tankersToRemove);
			tankers.removeAll(tankersToRemove);

			Set<Wreck> deadWrecks = new HashSet<>();

			// Water collection for reapers
			wrecks = wrecks.stream().filter(w ->
			{
				boolean alive = w.harvest(innerPlayers, skillEffects);

				if (!alive)
				{
					deadWrecks.add(w);
				}

				return alive;
			}).collect(Collectors.toList());

			// Round values and apply friction
			adjust();

			// Generate rage
			if (LOOTER_COUNT >= 3)
			{
				innerPlayers.forEach(p -> p.rage = Math.min(MAX_RAGE, p.rage + p.getDoof().sing()));
			}

			// Restore masses
			units.forEach(u ->
			{
				while (u.mass >= REAPER_SKILL_MASS_BONUS)
				{
					u.mass -= REAPER_SKILL_MASS_BONUS;
				}
			});

			// Remove dead skill effects
			Set<SkillEffect> effectsToRemove = new HashSet<>();
			for (SkillEffect effect : skillEffects)
			{
				if (effect.duration <= 0)
				{
					effectsToRemove.add(effect);
				}
			}
			skillEffects.removeAll(effectsToRemove);
		}

//		protected void updateGame(int round) throws GameOverException
//		{
//			// Apply skill effects
//			for (SkillEffect effect : skillEffects)
//			{
//				effect.apply(units);
//			}
//
//			// Apply thrust for tankers
//			for (Tanker t : tankers)
//			{
//				t.play();
//			}
//
//			// Apply wanted thrust for looters
//			for (InnerPlayer innerPlayer : innerPlayers)
//			{
//				for (Looter looter : innerPlayer.looters)
//				{
//					if (looter.wantedThrustTarget != null)
//					{
//						looter.thrust(looter.wantedThrustTarget, looter.wantedThrustPower);
//					}
//				}
//			}
//
//			double t = 0.0;
//
//			// Play the round. Stop at each collisions and play it. Repeat until t > 1.0
//
//			Collision collision = getNextCollision();
//
//			while (collision.t + t <= 1.0)
//			{
//				double delta = collision.t;
//				units.forEach(u -> u.move(delta));
//				t += collision.t;
//
//				playCollision(collision);
//
//				collision = getNextCollision();
//			}
//
//			// No more collision. Move units until the end of the round
//			double delta = 1.0 - t;
//			units.forEach(u -> u.move(delta));
//
//			List<Tanker> tankersToRemove = new ArrayList<>();
//
//			tankers.forEach(tanker ->
//			{
//				double distance = tanker.distance(WATERTOWN);
//				boolean full = tanker.isFull();
//
//				if (distance <= WATERTOWN_RADIUS && !full)
//				{
//					// A non full tanker in watertown collect some water
//					tanker.water += 1;
//					tanker.mass += TANKER_MASS_BY_WATER;
//				}
//				else if (distance >= TANKER_SPAWN_RADIUS + tanker.radius && full)
//				{
//					// Remove too far away and not full tankers from the game
//					tankersToRemove.add(tanker);
//				}
//			});
//
//			units.removeAll(tankersToRemove);
//			tankers.removeAll(tankersToRemove);
//
//			// Water collection for reapers
//			wrecks = wrecks.stream().filter(w -> w.harvest(innerPlayers, skillEffects)).collect(Collectors.toList());
//
//			// Round values and apply friction
//			adjust();
//
//			// Generate rage
//			if (LOOTER_COUNT >= 3)
//			{
//				innerPlayers.forEach(p -> p.rage = Math.min(MAX_RAGE, p.rage + p.getDoof().sing()));
//			}
//
//			// Restore masses
//			units.forEach(u ->
//			{
//				while (u.mass >= REAPER_SKILL_MASS_BONUS)
//				{
//					u.mass -= REAPER_SKILL_MASS_BONUS;
//				}
//			});
//
//			// Remove dead skill effects
//			Set<SkillEffect> effectsToRemove = new HashSet<>();
//			for (SkillEffect effect : skillEffects)
//			{
//				if (effect.duration <= 0)
//				{
//					effectsToRemove.add(effect);
//				}
//			}
//			skillEffects.removeAll(effectsToRemove);
//		}

		Looter createLooter(int type, InnerPlayer innerPlayer, double x, double y)
		{
			if (type == LOOTER_REAPER)
			{
				return new Reaper(innerPlayer, x, y);
			}
			else if (type == LOOTER_DESTROYER)
			{
				return new Destroyer(innerPlayer, x, y);
			}
			else if (type == LOOTER_DOOF)
			{
				return new Doof(innerPlayer, x, y);
			}

			// Not supposed to happen
			return null;
		}

		protected void adjust()
		{
			units.forEach(u -> u.adjust(skillEffects));
		}

		// Get the next collision for the current round
		// All units are tested
		Collision getNextCollision()
		{
			Collision result = NULL_COLLISION;

			for (int i = 0; i < units.size(); ++i)
			{
				Unit unit = units.get(i);

				// Test collision with map border first
				Collision collision = unit.getCollision();

				if (collision.t < result.t)
				{
					result = collision;
				}

				for (int j = i + 1; j < units.size(); ++j)
				{
					collision = unit.getCollision(units.get(j));

					if (collision.t < result.t)
					{
						result = collision;
					}
				}
			}

			return result;
		}

		// Play a collision
		void playCollision(Collision collision)
		{
			if (collision.b == null)
			{
				// Bounce with border
				collision.a.bounce();
			}
			else
			{
				Tanker dead = collision.dead();

				if (dead != null)
				{
					// A destroyer kill a tanker
					tankers.remove(dead);
					units.remove(dead);

					Wreck wreck = dead.die();

					// If a tanker is too far away, there's no wreck
					if (wreck != null)
					{
						wrecks.add(wreck);
					}
				}
				else
				{
					// Bounce between two units
					collision.a.bounce(collision.b);
				}
			}
		}

		protected void handlePlayerOutput(int playerIdx, Action[] actions, int idx1) throws Exception
		{
			InnerPlayer innerPlayer = innerPlayers.get(playerIdx);

			for (int i = 0; i < LOOTER_COUNT; ++i)
			{
				Looter looter = innerPlayer.looters[i];
				Action action = actions[idx1 + i];

				if (action.what.equals(ActionType.MOVE))
				{
					looter.attempt = ActionType.MOVE;
					looter.setWantedThrust(new Point(action.x, action.y), action.extra);
					continue;
				}

				if (action.what.equals(ActionType.WAIT))
				{
					looter.attempt = ActionType.WAIT;
					continue;
				}

				if (action.what.equals(ActionType.SKILL))
				{
					if (!looter.skillActive)
					{
						// Don't kill the player for that. Just do a WAIT instead
						looter.attempt = ActionType.WAIT;
						continue;
					}

					looter.attempt = ActionType.SKILL;

					SkillResult result = new SkillResult(action.x, action.y);
					looter.skillResult = result;

					try
					{
						SkillEffect effect = looter.skill(new Point(action.x, action.y));
						skillEffects.add(effect);
					}
					catch (NoRageException e)
					{
						result.code = SkillResult.NO_RAGE;
					}
					catch (TooFarException e)
					{
						result.code = SkillResult.TOO_FAR;
					}
				}
			}
		}

		static public int round(double x)
		{
			int s = x < 0 ? -1 : 1;
			return s * (int) Math.round(s * x);
		}

		private static class GameOverException extends Exception
		{
		}

		private static class NoRageException extends Exception
		{
		}

		private static class TooFarException extends Exception
		{
		}

		enum ActionType
		{
			SKILL, MOVE, WAIT;
		}

		class SkillResult
		{

			static final int OK = 0;
			static final int NO_RAGE = 1;
			static final int TOO_FAR = 2;
			Point target;
			int code;

			SkillResult(int x, int y)
			{
				target = new Point(x, y);
				code = OK;
			}

			int getX()
			{
				return (int) target.x;
			}

			int getY()
			{
				return (int) target.y;
			}
		}

		static class Point
		{

			double x;
			double y;

			Point(double x, double y)
			{
				this.x = x;
				this.y = y;
			}

			double distance(Point p)
			{
				return Math.sqrt((this.x - p.x) * (this.x - p.x) + (this.y - p.y) * (this.y - p.y));
			}

			// Move the point to x and y
			void move(double x, double y)
			{
				this.x = x;
				this.y = y;
			}

			// Move the point to an other point for a given distance
			void moveTo(Point p, double distance)
			{
				double d = distance(p);

				if (d < EPSILON)
				{
					return;
				}

				double dx = p.x - x;
				double dy = p.y - y;
				double coef = distance / d;

				this.x += dx * coef;
				this.y += dy * coef;
			}

			boolean isInRange(Point p, double range)
			{
				return p != this && distance(p) <= range;
			}

			@Override
			public int hashCode()
			{
				// copied from java.awt.Point2D
				long bits = java.lang.Double.doubleToLongBits(x);
				bits ^= java.lang.Double.doubleToLongBits(y) * 31;
				return (((int) bits) ^ ((int) (bits >> 32)));
//				final int prime = 31;
//				int result = 1;
//				long temp;
//				temp = Double.doubleToLongBits(x);
//				result = prime * result + (int) (temp ^ (temp >>> 32));
//				temp = Double.doubleToLongBits(y);
//				result = prime * result + (int) (temp ^ (temp >>> 32));
//				return result;
			}

			@Override
			public boolean equals(Object obj)
			{
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				final Point other = (Point) obj;
				return (x == other.x) && (y == other.y);

//				if (this == obj)
//					return true;
//				if (obj == null)
//					return false;
//				if (getClass() != obj.getClass())
//					return false;
//				Point other = (Point) obj;
//				if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
//					return false;
//				if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
//					return false;
//				return true;
			}
		}

		static class Wreck extends Point
		{

			int id;
			double radius;
			int water;
			boolean known;
			InnerPlayer innerPlayer;

			Wreck(double x, double y, int water, double radius)
			{
				super(x, y);

				id = GLOBAL_ID++;

				this.radius = radius;
				this.water = water;
			}

			// Reaper harvesting
			public boolean harvest(List<InnerPlayer> innerPlayers, Set<SkillEffect> skillEffects)
			{
				innerPlayers.forEach(p ->
				{
					if (isInRange(p.getReaper(), radius) && !p.getReaper().isInDoofSkill(skillEffects))
					{
						p.score += 1;
						water -= 1;
					}
				});

				return water > 0;
			}
		}

		static abstract class Unit extends Point
		{

			int type;
			int id;
			double vx;
			double vy;
			double radius;
			double mass;
			double friction;
			boolean known;

			Unit(int type, double x, double y)
			{
				super(x, y);

				id = GLOBAL_ID++;
				this.type = type;

				vx = 0.0;
				vy = 0.0;

				known = false;
			}

			void move(double t)
			{
				x += vx * t;
				y += vy * t;
			}

			double speed()
			{
				return Math.sqrt(vx * vx + vy * vy);
			}

			@Override
			public int hashCode()
			{
				final int prime = 31;
				int result = 1;
				result = prime * result + id;
				return result;
			}

			@Override
			public boolean equals(Object obj)
			{
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Unit other = (Unit) obj;
				if (id != other.id)
					return false;
				return true;
			}

			void thrust(Point p, int power)
			{
				double distance = distance(p);

				// Avoid a division by zero
				if (Math.abs(distance) <= EPSILON)
				{
					return;
				}

				double coef = (((double) power) / mass) / distance;
				vx += (p.x - this.x) * coef;
				vy += (p.y - this.y) * coef;
			}

			public boolean isInDoofSkill(Set<SkillEffect> skillEffects)
			{
				return skillEffects.stream().anyMatch(s -> s instanceof DoofSkillEffect && isInRange(s, s.radius + radius));
			}

			void adjust(Set<SkillEffect> skillEffects)
			{
				x = round(x);
				y = round(y);

				if (isInDoofSkill(skillEffects))
				{
					// No friction if we are in a doof skill effect
					vx = round(vx);
					vy = round(vy);
				}
				else
				{
					vx = round(vx * (1.0 - friction));
					vy = round(vy * (1.0 - friction));
				}
			}

			// Search the next collision with the map border
			Collision getCollision()
			{
				// Check instant collision
				if (distance(WATERTOWN) + radius >= MAP_RADIUS)
				{
					return new Collision(0.0, this);
				}

				// We are not moving, we can't reach the map border
				if (vx == 0.0 && vy == 0.0)
				{
					return NULL_COLLISION;
				}

				// Search collision with map border
				// Resolving: sqrt((x + t*vx)^2 + (y + t*vy)^2) = MAP_RADIUS - radius <=> t^2*(vx^2 + vy^2) + t*2*(x*vx + y*vy) + x^2 + y^2 - (MAP_RADIUS - radius)^2 = 0
				// at^2 + bt + c = 0;
				// a = vx^2 + vy^2
				// b = 2*(x*vx + y*vy)
				// c = x^2 + y^2 - (MAP_RADIUS - radius)^2

				double a = vx * vx + vy * vy;

				if (a <= 0.0)
				{
					return NULL_COLLISION;
				}

				double b = 2.0 * (x * vx + y * vy);
				double c = x * x + y * y - (MAP_RADIUS - radius) * (MAP_RADIUS - radius);
				double delta = b * b - 4.0 * a * c;

				if (delta <= 0.0)
				{
					return NULL_COLLISION;
				}

				double t = (-b + Math.sqrt(delta)) / (2.0 * a);

				if (t <= 0.0)
				{
					return NULL_COLLISION;
				}

				return new Collision(t, this);
			}

			// Search the next collision with an other unit
			Collision getCollision(Unit u)
			{
				// Check instant collision
				if (distance(u) <= radius + u.radius)
				{
					return new Collision(0.0, this, u);
				}

				// Both units are motionless
				if (vx == 0.0 && vy == 0.0 && u.vx == 0.0 && u.vy == 0.0)
				{
					return NULL_COLLISION;
				}

				// Change referencial
				// Unit u is not at point (0, 0) with a speed vector of (0, 0)
				double x2 = x - u.x;
				double y2 = y - u.y;
				double r2 = radius + u.radius;
				double vx2 = vx - u.vx;
				double vy2 = vy - u.vy;

				// Resolving: sqrt((x + t*vx)^2 + (y + t*vy)^2) = radius <=> t^2*(vx^2 + vy^2) + t*2*(x*vx + y*vy) + x^2 + y^2 - radius^2 = 0
				// at^2 + bt + c = 0;
				// a = vx^2 + vy^2
				// b = 2*(x*vx + y*vy)
				// c = x^2 + y^2 - radius^2

				double a = vx2 * vx2 + vy2 * vy2;

				if (a <= 0.0)
				{
					return NULL_COLLISION;
				}

				double b = 2.0 * (x2 * vx2 + y2 * vy2);
				double c = x2 * x2 + y2 * y2 - r2 * r2;
				double delta = b * b - 4.0 * a * c;

				if (delta < 0.0)
				{
					return NULL_COLLISION;
				}

				double t = (-b - Math.sqrt(delta)) / (2.0 * a);

				if (t <= 0.0)
				{
					return NULL_COLLISION;
				}

				return new Collision(t, this, u);
			}

			// Bounce between 2 units
			void bounce(Unit u)
			{
				double mcoeff = (mass + u.mass) / (mass * u.mass);
				double nx = x - u.x;
				double ny = y - u.y;
				double nxnysquare = nx * nx + ny * ny;
				double dvx = vx - u.vx;
				double dvy = vy - u.vy;
				double product = (nx * dvx + ny * dvy) / (nxnysquare * mcoeff);
				double fx = nx * product;
				double fy = ny * product;
				double m1c = 1.0 / mass;
				double m2c = 1.0 / u.mass;

				vx -= fx * m1c;
				vy -= fy * m1c;
				u.vx += fx * m2c;
				u.vy += fy * m2c;

				fx = fx * IMPULSE_COEFF;
				fy = fy * IMPULSE_COEFF;

				// Normalize vector at min or max impulse
				double impulse = Math.sqrt(fx * fx + fy * fy);
				double coeff = 1.0;
				if (impulse > EPSILON && impulse < MIN_IMPULSE)
				{
					coeff = MIN_IMPULSE / impulse;
				}

				fx = fx * coeff;
				fy = fy * coeff;

				vx -= fx * m1c;
				vy -= fy * m1c;
				u.vx += fx * m2c;
				u.vy += fy * m2c;

				double diff = (distance(u) - radius - u.radius) / 2.0;
				if (diff <= 0.0)
				{
					// Unit overlapping. Fix positions.
					moveTo(u, diff - EPSILON);
					u.moveTo(this, diff - EPSILON);
				}
			}

			// Bounce with the map border
			void bounce()
			{
				double mcoeff = 1.0 / mass;
				double nxnysquare = x * x + y * y;
				double product = (x * vx + y * vy) / (nxnysquare * mcoeff);
				double fx = x * product;
				double fy = y * product;

				vx -= fx * mcoeff;
				vy -= fy * mcoeff;

				fx = fx * IMPULSE_COEFF;
				fy = fy * IMPULSE_COEFF;

				// Normalize vector at min or max impulse
				double impulse = Math.sqrt(fx * fx + fy * fy);
				double coeff = 1.0;
				if (impulse > EPSILON && impulse < MIN_IMPULSE)
				{
					coeff = MIN_IMPULSE / impulse;
				}

				fx = fx * coeff;
				fy = fy * coeff;
				vx -= fx * mcoeff;
				vy -= fy * mcoeff;

				double diff = distance(WATERTOWN) + radius - MAP_RADIUS;
				if (diff >= 0.0)
				{
					// Unit still outside of the map, reposition it
					moveTo(WATERTOWN, diff + EPSILON);
				}
			}

			public int getExtraInput()
			{
				return -1;
			}

			public int getExtraInput2()
			{
				return -1;
			}

			public int getPlayerIndex()
			{
				return -1;
			}
		}

		static class Tanker extends Unit
		{

			int water;
			int size;
			InnerPlayer innerPlayer;
			boolean killed;

			Tanker(int size, InnerPlayer innerPlayer)
			{
				super(TYPE_TANKER, 0.0, 0.0);

				this.innerPlayer = innerPlayer;
				this.size = size;

				water = TANKER_EMPTY_WATER;
				mass = TANKER_EMPTY_MASS + TANKER_MASS_BY_WATER * water;
				friction = TANKER_FRICTION;
				radius = TANKER_RADIUS_BASE + TANKER_RADIUS_BY_SIZE * size;
			}

			String getFrameId()
			{
				return id + "@" + water;
			}

			Wreck die()
			{
				// Don't spawn a wreck if our center is outside of the map
				if (distance(WATERTOWN) >= MAP_RADIUS)
				{
					return null;
				}

				return new Wreck(round(x), round(y), water, radius);
			}

			boolean isFull()
			{
				return water >= size;
			}

			void play()
			{
				if (isFull())
				{
					// Try to leave the map
					thrust(WATERTOWN, -TANKER_THRUST);
				}
				else if (distance(WATERTOWN) > WATERTOWN_RADIUS)
				{
					// Try to reach watertown
					thrust(WATERTOWN, TANKER_THRUST);
				}
			}

			Collision getCollision()
			{
				// Tankers can go outside of the map
				return NULL_COLLISION;
			}

			public int getExtraInput()
			{
				return water;
			}

			public int getExtraInput2()
			{
				return size;
			}
		}

		static abstract class Looter extends Unit
		{

			int skillCost;
			double skillRange;
			boolean skillActive;

			InnerPlayer innerPlayer;

			Point wantedThrustTarget;
			int wantedThrustPower;

			String message;
			ActionType attempt;
			SkillResult skillResult;

			Looter(int type, InnerPlayer innerPlayer, double x, double y)
			{
				super(type, x, y);

				this.innerPlayer = innerPlayer;

				radius = LOOTER_RADIUS;
			}

			SkillEffect skill(Point p) throws TooFarException, NoRageException
			{
				if (innerPlayer.rage < skillCost)
					throw new NoRageException();
				if (distance(p) > skillRange)
					throw new TooFarException();

				innerPlayer.rage -= skillCost;
				return skillImpl(p);
			}

			public int getPlayerIndex()
			{
				return innerPlayer.index;
			}

			abstract SkillEffect skillImpl(Point p);

			public void setWantedThrust(Point target, Integer power)
			{
				if (power < 0)
				{
					power = 0;
				}

				wantedThrustTarget = target;
				wantedThrustPower = Math.min(power, MAX_THRUST);
			}

			public void reset()
			{
				message = null;
				attempt = null;
				skillResult = null;
				wantedThrustTarget = null;
			}
		}

		static class Reaper extends Looter
		{

			Reaper(InnerPlayer innerPlayer, double x, double y)
			{
				super(LOOTER_REAPER, innerPlayer, x, y);

				mass = REAPER_MASS;
				friction = REAPER_FRICTION;
				skillCost = REAPER_SKILL_COST;
				skillRange = REAPER_SKILL_RANGE;
				skillActive = REAPER_SKILL_ACTIVE;
			}

			SkillEffect skillImpl(Point p)
			{
				return new ReaperSkillEffect(TYPE_REAPER_SKILL_EFFECT, p.x, p.y, REAPER_SKILL_RADIUS, REAPER_SKILL_DURATION, REAPER_SKILL_ORDER, this);
			}
		}

		static class Destroyer extends Looter
		{

			Destroyer(InnerPlayer innerPlayer, double x, double y)
			{
				super(LOOTER_DESTROYER, innerPlayer, x, y);

				mass = DESTROYER_MASS;
				friction = DESTROYER_FRICTION;
				skillCost = DESTROYER_SKILL_COST;
				skillRange = DESTROYER_SKILL_RANGE;
				skillActive = DESTROYER_SKILL_ACTIVE;
			}

			SkillEffect skillImpl(Point p)
			{
				return new DestroyerSkillEffect(TYPE_DESTROYER_SKILL_EFFECT, p.x, p.y, DESTROYER_SKILL_RADIUS, DESTROYER_SKILL_DURATION,
						DESTROYER_SKILL_ORDER, this);
			}
		}

		static class Doof extends Looter
		{

			Doof(InnerPlayer innerPlayer, double x, double y)
			{
				super(LOOTER_DOOF, innerPlayer, x, y);

				mass = DOOF_MASS;
				friction = DOOF_FRICTION;
				skillCost = DOOF_SKILL_COST;
				skillRange = DOOF_SKILL_RANGE;
				skillActive = DOOF_SKILL_ACTIVE;
			}

			SkillEffect skillImpl(Point p)
			{
				return new DoofSkillEffect(TYPE_DOOF_SKILL_EFFECT, p.x, p.y, DOOF_SKILL_RADIUS, DOOF_SKILL_DURATION, DOOF_SKILL_ORDER, this);
			}

			// With flame effects! Yeah!
			int sing()
			{
				return (int) Math.floor(speed() * DOOF_RAGE_COEF);
			}
		}

		static class InnerPlayer
		{

			int score;
			int index;
			int rage;
			Looter[] looters;
			boolean dead;
			Queue<TankerSpawn> tankers;

			InnerPlayer(int index, int score, int rage)
			{
				this.index = index;
				this.score = score;
				this.rage = rage;

				looters = new Looter[LOOTER_COUNT];
			}

			InnerPlayer(int index)
			{
				this.index = index;

				looters = new Looter[LOOTER_COUNT];
			}

			void kill()
			{
				dead = true;
			}

			Reaper getReaper()
			{
				return (Reaper) looters[LOOTER_REAPER];
			}

			Destroyer getDestroyer()
			{
				return (Destroyer) looters[LOOTER_DESTROYER];
			}

			Doof getDoof()
			{
				return (Doof) looters[LOOTER_DOOF];
			}
		}

		static class TankerSpawn
		{

			int size;
			double angle;

			TankerSpawn(int size, double angle)
			{
				this.size = size;
				this.angle = angle;
			}
		}

		static class Collision
		{

			double t;
			Unit a;
			Unit b;

			Collision(double t)
			{
				this(t, null, null);
			}

			Collision(double t, Unit a)
			{
				this(t, a, null);
			}

			Collision(double t, Unit a, Unit b)
			{
				this.t = t;
				this.a = a;
				this.b = b;
			}

			Tanker dead()
			{
				if (a instanceof Destroyer && b instanceof Tanker && b.mass < REAPER_SKILL_MASS_BONUS)
				{
					return (Tanker) b;
				}

				if (b instanceof Destroyer && a instanceof Tanker && a.mass < REAPER_SKILL_MASS_BONUS)
				{
					return (Tanker) a;
				}

				return null;
			}
		}

		static abstract class SkillEffect extends Point
		{

			int id;
			int type;
			double radius;
			int duration;
			int order;
			boolean known;
			Looter looter;

			SkillEffect(int type, double x, double y, double radius, int duration, int order, Looter looter)
			{
				super(x, y);

				id = GLOBAL_ID++;

				this.type = type;
				this.radius = radius;
				this.duration = duration;
				this.looter = looter;
				this.order = order;
			}

			void apply(List<Unit> units)
			{
				duration -= 1;
				applyImpl(units.stream().filter(u -> isInRange(u, radius + u.radius)).collect(Collectors.toList()));
			}

			abstract void applyImpl(List<Unit> units);

			@Override
			public int hashCode()
			{
				final int prime = 31;
				int result = 1;
				result = prime * result + id;
				return result;
			}

			@Override
			public boolean equals(Object obj)
			{
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				SkillEffect other = (SkillEffect) obj;
				if (id != other.id)
					return false;
				return true;
			}
		}

		static class ReaperSkillEffect extends SkillEffect
		{

			ReaperSkillEffect(int type, double x, double y, double radius, int duration, int order, Reaper reaper)
			{
				super(type, x, y, radius, duration, order, reaper);
			}

			void applyImpl(List<Unit> units)
			{
				// Increase mass
				units.forEach(u -> u.mass += REAPER_SKILL_MASS_BONUS);
			}
		}

		static class DestroyerSkillEffect extends SkillEffect
		{

			DestroyerSkillEffect(int type, double x, double y, double radius, int duration, int order, Destroyer destroyer)
			{
				super(type, x, y, radius, duration, order, destroyer);
			}

			void applyImpl(List<Unit> units)
			{
				// Push units
				units.forEach(u -> u.thrust(this, -DESTROYER_NITRO_GRENADE_POWER));
			}
		}

		static class DoofSkillEffect extends SkillEffect
		{

			DoofSkillEffect(int type, double x, double y, double radius, int duration, int order, Doof doof)
			{
				super(type, x, y, radius, duration, order, doof);
			}

			void applyImpl(List<Unit> units)
			{
				// Nothing to do now
			}
		}

	}
}
