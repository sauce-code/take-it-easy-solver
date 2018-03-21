package tie.core;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the class {@link FasterGame}.
 * 
 * @author Torben Kr&uuml;ger
 * 
 */
public class FasterGameTest {

	/**
	 * Number of iterations for some tests.
	 */
	public static final int ITERATIONS = 100000;

	/**
	 * Tests if the constructor causes crashes.
	 */
	@Test
	public void testConstructor() {
		for (int i = 0; i < ITERATIONS; i++) {
			new FasterGame();
		}
	}

	/**
	 * Tests if the score method causes crashes.
	 */
	@Test
	public void testScore() {
		for (int i = 0; i < ITERATIONS; i++) {
			new FasterGame().getScore();
		}
	}

	/**
	 * Tests if the constructed Games are in a valid score range.
	 */
	@Test
	public void testScoreRange() {
		for (int i = 0; i < ITERATIONS; i++) {
			int score = new FasterGame().getScore();
			assertTrue(score >= Game.SCORE_MIN);
			assertTrue(score <= Game.SCORE_MAX);
		}
	}

	/**
	 * Tests {@link FasterGame#clone(Game))}.
	 */
	@Test
	public void testClone() {

		FasterGame a = new FasterGame();
		FasterGame b = new FasterGame();

		while (a.equals(b)) {
			b = new FasterGame();
		}

		assertNotEquals(a, b);

		a.clone(b);

		assertEquals(a, b);
	}

	/**
	 * Tests {@link FasterGame#FasterGame(FasterGame)}.
	 */
	@Test
	public void testFasterGameClone() {
		FasterGame a = new FasterGame();
		FasterGame b = new FasterGame(a);

		assertEquals(a, b);
	}

	/**
	 * Tests {@link FasterGame#swap()}.
	 */
	@Test
	public void testSwap() {
		FasterGame a = new FasterGame();
		FasterGame b = new FasterGame(a);

		assertEquals(a, b);

		a.swap();

		assertNotEquals(a, b);
	}

	/**
	 * Tests if {@link FasterGame#clone(Game)} throws
	 * {@link IllegalArgumentException} if a {@link Game} is passed as an
	 * argument.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCloneIncorrectClass() {

		FasterGame a = new FasterGame();
		Game b = new Game();

		a.clone(b);
	}

	/**
	 * Test if it's possible to clone a {@link FasterGame} into a {@link Game}.
	 */
	@Test
	public void testCloneCrossClasses() {

		FasterGame a = new FasterGame();
		Game b = new Game();

		while (a.equals(b)) {
			b = new Game();
		}

		b.clone(a);

		assertArrayEquals(a.board, b.board);
		for (int i = 0; i < a.board.length; i++) {
			assertArrayEquals(a.board[i], b.board[i]);
		}
		assertEquals(a.score, b.score);
		assertEquals(a.tiles, b.tiles);
		assertNotEquals(a, b);

	}

}
