import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

public class GameTest {

//    @Test
//    public void test_case1() throws IOException {
//        // Given
//        String board = loadResourceAsString(getClass(), "case1.txt");
//        GameBoard gameBoard = new GameBoard();
//        gameBoard.parseFile(board);
//
//        Game game = new Game();
//        game.addUnit(new Unit(0, 2, 100, 1, 0));
//        game.setGameBoard(gameBoard);
//        game.setNbMyUnits(10);
//
//        // When
//        game.buildOutput();
//
//        // Then
//        Assert.assertEquals(1, game.getOutput().size());
//        Assert.assertEquals("MOVE 100 1 2;", game.getOutput().get(0).toOutput());
//
//    }
//
//    @Test
//    public void test_case2() throws IOException {
//        // Given
//        String board = loadResourceAsString(getClass(), "case2.txt");
//        GameBoard gameBoard = new GameBoard();
//        gameBoard.parseFile(board);
//
//        Game game = new Game();
//        game.addUnit(new Unit(4, 0, 100, 1, 0));
//        game.setGameBoard(gameBoard);
//        game.setNbMyUnits(10);
//
//        // When
//        game.buildOutput();
//
//        // Then
//        Assert.assertEquals(1, game.getOutput().size());
//        Assert.assertEquals("MOVE 100 4 1;", game.getOutput().get(0).toOutput());
//
//    }
//
//    @Test
//    public void test_case3() throws IOException {
//        // Given
//        String board = loadResourceAsString(getClass(), "case3.txt");
//        GameBoard gameBoard = new GameBoard();
//        gameBoard.parseFile(board);
//
//        Game game = new Game();
//        game.addUnit(new Unit(2, 5, 100, 1, 0));
//        game.setGameBoard(gameBoard);
//        game.setNbMyUnits(10);
//
//        // When
//        game.buildOutput();
//
//        // Then
//        Assert.assertEquals(1, game.getOutput().size());
//        Assert.assertNotEquals("MOVE 100 1 5;", game.getOutput().get(0).toOutput());
//
//    }

    @Test
    public void test_case4() throws IOException {
        // Given
        String board = loadResourceAsString(getClass(), "case4.txt");
        GameBoard gameBoard = new GameBoard();
        gameBoard.parseFile(board);
        Unit unit1 = new Unit(2, 0, 100, 1, 0);
        Position myHQ = new Position(0, 0);
        Position otherHQ = new Position(11, 11);

        // When
        List<Position> candidates = unit1.locateCandidatePositions(gameBoard, myHQ, otherHQ);

        // Then
        Assert.assertEquals(126, candidates.size());
        Position p1 = candidates.stream().filter(p -> p.x == 3 && p.y == 0).findFirst().orElseThrow(RuntimeException::new);
        Position p2 = candidates.stream().filter(p -> p.x == 2 && p.y == 1).findFirst().orElseThrow(RuntimeException::new);
        Assert.assertTrue(candidates.stream().noneMatch(p -> p.x == 1 && p.y == 1));
    }

    private static byte[] loadResourceAsBytes(Class theClass, String filePath) throws IOException {
        try (InputStream is = theClass.getResourceAsStream(filePath)) {
            return IOUtils.toByteArray(is);
        }
    }

    private static String loadResourceAsString(Class theClass, String filePath) throws IOException {
        return new String(loadResourceAsBytes(theClass, filePath), Charset.forName("UTF-8"));
    }

}