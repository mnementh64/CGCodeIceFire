import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Player {

    static final boolean DEBUG = true;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        Game g = new Game();
        g.init(in);

        // game loop
        while (true) {

            g.update(in);
            //g.debug();
            g.buildOutput();
            g.output();
        }
    }
}

class Game {

    private List<Unit> units;
    private List<Building> buildings;
    private int gold, income;
    private List<Command> output;
    private GameBoard gameBoard;

    private int nbMyUnits;
    private int nbRounds = 1;
    private Position otherHQ;
    private Position myHQ;

    Game() {
        gameBoard = new GameBoard();
        units = new ArrayList<>();
        buildings = new ArrayList<>();
        output = new ArrayList<>();
    }

    // not useful in Wood 3
    void init(Scanner in) {
        int numberMineSpots = in.nextInt();
        for (int i = 0; i < numberMineSpots; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
        }
    }

    void update(Scanner in) {

        units.clear();
        buildings.clear();
        output.clear();
        gameBoard.clear();

        // READ TURN INPUT
        gold = in.nextInt();
        int income = in.nextInt();
        int opponentGold = in.nextInt();
        int opponentIncome = in.nextInt();
        for (int i = 0; i < 12; i++) {
            String line = in.next();
            System.err.println(line);
            gameBoard.parseLine(line, i);
        }
        int buildingCount = in.nextInt();
        for (int i = 0; i < buildingCount; i++) {
            int owner = in.nextInt();
            int buildingType = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            buildings.add(new Building(x, y, buildingType, owner));

            if (buildingType == BuildingType.HQ.getiType()) {
                if (owner == 1) {
                    otherHQ = new Position(x, y);
                } else {
                    myHQ = new Position(x, y);
                }
            }
        }
        int unitCount = in.nextInt();
        nbMyUnits = 0;
        for (int i = 0; i < unitCount; i++) {
            int owner = in.nextInt();
            int unitId = in.nextInt();
            int level = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            units.add(new Unit(x, y, unitId, level, owner));
            nbMyUnits += owner == 0 ? 1 : 0;

            if (owner == 1) {
                gameBoard.opponentUnitAt(level, x, y);
            }
        }
        if (Player.DEBUG) {
            System.err.println("Round " + nbRounds + " / " + nbMyUnits + " units / " + gold + " gold");
        }
    }

    void addUnit(Unit unit) {
        units.add(unit);
    }

    void buildOutput() {
        trainUnits();
        moveUnits();
    }

    private void trainUnits() {
        Position trainingPosition = findTrainingPosition();
        if (gold > (nbMyUnits * 1) || nbRounds == 1) {
            output.add(new Command(CommandType.TRAIN, 1, trainingPosition));
        }
    }

    // move to the center
    private void moveUnits() {
        units.stream()
                .filter(Unit::isOwned)
                .forEach(
                        myUnit -> {
                            Position target = myUnit.findPosition(gameBoard, nbRounds, myHQ, otherHQ);
                            output.add(new Command(CommandType.MOVE, myUnit.id, target));
                        }
                );
    }

    // train near the HQ for now
    private Position findTrainingPosition() {
        // TODO : improve that !
        Building HQ = getHQ();
        if (HQ.p.x == 0) {
            return new Position(0, 1);
        }
        return new Position(11, 10);
    }

    void output() {
        StringBuilder s = new StringBuilder("WAIT;");
        output.forEach(c -> s.append(c.toOutput()));
        System.out.println(s);

        nbRounds++;
    }

    public List<Command> getOutput() {
        return output;
    }

    public void debug() {
        units.forEach(Unit::debug);
        buildings.forEach(Building::debug);
    }

    private Building getHQ() {
        return buildings.stream().filter(b -> b.isHQ() && b.isOwned()).findFirst().orElseThrow(RuntimeException::new);
    }

    private Building getOpponentHQ() {
        return buildings.stream().filter(b -> b.isHQ() && !b.isOwned()).findFirst().orElseThrow(RuntimeException::new);
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setNbMyUnits(int nbMyUnits) {
        this.nbMyUnits = nbMyUnits;
    }
}

class Command {

    private CommandType t;
    private Position p;
    private int idOrLevel;

    Command(CommandType t, int idOrLevel, Position p) {
        this.t = t;
        this.p = p;
        this.idOrLevel = idOrLevel;
    }

    String toOutput() {
        return t.toString() + " " + idOrLevel + " " + p.x + " " + p.y + ";";
    }
}

class Position {

    int x, y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

abstract class GameItem {

    Position p;
    int owner;

    GameItem(int x, int y, int owner) {
        this.p = new Position(x, y);
        this.owner = owner;
    }

    boolean isOwned() {
        return owner == 0;
    }
}

class GameBoard {

    List<GameCell> cells = new ArrayList<>();

    void parseFile(String content) {
        String[] rows = content.split("\n");
        for (int y = 0; y < 12; y++) {
            parseLine(rows[y], y);
        }
    }

    void parseLine(String line, int y) {
        int x = 0;
        for (char c : line.toCharArray()) {
            if (c == '#') {
                cells.add(new GameCell(x, y, GameCellType.VOID, 2));
            } else if (c == '.') {
                cells.add(new GameCell(x, y, GameCellType.NEUTRAL, 2));
            } else if (c == 'O') {
                cells.add(new GameCell(x, y, GameCellType.OWNED_AND_ACTIVE, 0));
            } else if (c == 'X') {
                cells.add(new GameCell(x, y, GameCellType.OWNED_AND_INACTIVE, 0));
            } else if (c == 'o') {
                cells.add(new GameCell(x, y, GameCellType.NOT_OWNED_AND_ACTIVE, 1));
            } else if (c == 'x') {
                cells.add(new GameCell(x, y, GameCellType.NOT_OWNED_AND_INACTIVE, 1));
            }
            x++;
        }
    }

    public void clear() {
        cells.clear();
    }

    public void opponentUnitAt(int level, int x, int y) {
        GameCell cell = cells.get(y * 12 + x);
        cell.setLevelOfOpponentUnit(level);
    }
}

class GameCell extends GameItem {

    GameCellType cellType;
    int levelOfOpponentUnit = 0;

    GameCell(int x, int y, GameCellType cellType, int owner) {
        super(x, y, owner);
        this.cellType = cellType;
    }

    public void setLevelOfOpponentUnit(int levelOfOpponentUnit) {
        this.levelOfOpponentUnit = levelOfOpponentUnit;
    }

    public int getLevelOfOpponentUnit() {
        return levelOfOpponentUnit;
    }
}

enum GameCellType {

    VOID,
    NEUTRAL,
    OWNED_AND_ACTIVE,
    OWNED_AND_INACTIVE,
    NOT_OWNED_AND_ACTIVE,
    NOT_OWNED_AND_INACTIVE;

}

class Unit extends GameItem {

    private static final Position[] FORCED_POSITIONS = new Position[]{
//    private static final Position[] FORCED_POSITIONS = new Position[]{
//            new Position(0, 2),
//            new Position(1, 2),
//            new Position(1, 3),
//            new Position(1, 4),
//            new Position(1, 5),
//            new Position(2, 5)
    };
    private int level;
    int id;

    Unit(int x, int y, int id, int level, int owner) {
        super(x, y, owner);
        this.id = id;
        this.level = level;
    }

    void debug() {
        System.err.println("unit of level " + level + " at " + p.x + " " + p.y + " owned by " + owner);
    }

    Position findPosition(GameBoard gameBoard, int nbRounds, Position myHQ, Position otherHQ) {
        if (nbRounds != 1 && nbRounds <= FORCED_POSITIONS.length) {
            if (Player.DEBUG) {
                System.err.println("Force positon : " + FORCED_POSITIONS[nbRounds - 1].x + "," + FORCED_POSITIONS[nbRounds - 1].y);
            }
            return FORCED_POSITIONS[nbRounds - 1];
        } else {
            // check around the current position and random choose amongst candidates
            List<Position> candidates = locateCandidatePositions(gameBoard, myHQ, otherHQ);

            return pickupMostInteresting(candidates);
        }
    }

    private Position pickupMostInteresting(List<Position> positions) {
        // sort potential positions beginning by the closest
        return positions.stream().sorted((p1, p2) -> {
            int d1 = Math.abs(p.x - p1.x) + Math.abs(p.y - p1.y);
            int d2 = Math.abs(p.x - p2.x) + Math.abs(p.y - p2.y);
            if (d1 == d2) {
                int score1 = computePositionScore(p1);
                int score2 = computePositionScore(p2);

                return Integer.compare(score1, score2);
            } else {
                return Integer.compare(d1, d2);
            }
        }).findFirst().orElseThrow(RuntimeException::new);
    }

    private int computePositionScore(Position position) {
        // priority to :
        // - below / same y / above
        // - left / same x / right
        int score = 0;

        if (position.y > p.y) {
            score += 2;
        } else if (position.y == p.y) {
            score += 1;
        }

        if (position.x > p.x) {
            score += 2;
        } else if (position.x == p.x) {
            score += 1;
        }

        return score;
    }

    public List<Position> locateCandidatePositions(GameBoard gameBoard, Position myHQ, Position otherHQ) {
        Map<Integer, List<Position>> candidates = new HashMap<>();

        /*
            For highest to lowest priority :
            - 0 : opponent HQ
            - 1 : not owned active with opponent
            - 2 : not owned active
            - 3 : not owned inactive
            - 4 : neutral
            - 5 : owned (active / inactive)
            - none : is not a candidate
         */
        for (GameCell cell : gameBoard.cells) {

            if ((cell.p.x == myHQ.x && cell.p.y == myHQ.y) ||   // skip my HQ
                    (cell.p.x == p.x && cell.p.y == p.y) ||   // skip my position
                    cell.cellType.equals(GameCellType.VOID)) {  // skip void cells
                continue;
            }

            int priority = -1;
            if (cell.cellType.equals(GameCellType.NEUTRAL)) {
                priority = 4;
            } else {
                // consider other possibilities only if we are close enough
                int distance = Math.abs(p.x - cell.p.x) + Math.abs(p.y - cell.p.y);
                if (distance < 3) {
                    if (cell.p.x == otherHQ.x && cell.p.y == otherHQ.y) {
                        priority = 0;
                    } else if (cell.cellType.equals(GameCellType.NOT_OWNED_AND_ACTIVE)) {
                        int levelOfOpponenetOnCell = cell.getLevelOfOpponentUnit();
                        if (levelOfOpponenetOnCell == 1) { // the only level we coudl defeat for the moment !
                            priority = 1;
                        } else {
                            priority = 2;
                        }
                    } else if (cell.cellType.equals(GameCellType.NOT_OWNED_AND_INACTIVE)) {
                        priority = 3;
                    }
                }
            }

            // ignore default cells
            if (priority == -1) {
                continue;
            }

            List<Position> positions = candidates.computeIfAbsent(priority, k -> new ArrayList<>());
            positions.add(cell.p);
        }

        if (Player.DEBUG) {
            for (int priority = 0; priority < 6; priority++) {
                if (candidates.containsKey(priority)) {
                    System.err.println("Prio " + priority + " : " + candidates.get(priority).size() + " candidates");
                }
            }
        }

        // ignore priority -1 -> it means no interest
        for (int priority = 0; priority < 6; priority++) {
            if (candidates.containsKey(priority)) {
                return candidates.get(priority);
            }
        }

        return new ArrayList<>();
    }

}

class Building extends GameItem {

    private BuildingType t;

    Building(int x, int y, int t, int owner) {
        super(x, y, owner);
        this.t = BuildingType.get(t);
    }

    void debug() {
        System.err.println(t + " at " + p.x + " " + p.y + " owned by " + owner);
    }

    boolean isHQ() {
        return t.equals(BuildingType.HQ);
    }
}

enum BuildingType {

    HQ(0);

    private final int iType;

    BuildingType(int iType) {
        this.iType = iType;
    }

    public int getiType() {
        return iType;
    }

    static public BuildingType get(int iType) {
        for (BuildingType type : BuildingType.values()) {
            if (type.iType == iType) {
                return type;
            }
        }
        return null;
    }
}

enum CommandType {

    MOVE,
    TRAIN;
}