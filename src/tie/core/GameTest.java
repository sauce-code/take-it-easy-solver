package tie.core;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Thets the class {@link Game}.
 * 
 * @author Torben Kr&uuml;ger
 *
 */
public class GameTest {

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
			new Game();
		}
	}

	/**
	 * Tests if the score method causes crashes.
	 */
	@Test
	public void testScore() {
		for (int i = 0; i < ITERATIONS; i++) {
			new Game().getScore();
		}
	}

	/**
	 * Tests if the constructed Games are in a valid score range.
	 */
	@Test
	public void testScoreRange() {
		for (int i = 0; i < ITERATIONS; i++) {
			int score = new Game().getScore();
			assertTrue(score >= Game.SCORE_MIN);
			assertTrue(score <= Game.SCORE_MAX);
		}
	}

	/**
	 * Tests {@link Game#clone(Game)}.
	 */
	@Test
	public void testClone() {

		Game a = new Game();
		Game b = new Game();

		while (a.equals(b)) {
			b = new Game();
		}

		assertNotEquals(a, b);

		a.clone(b);

		assertEquals(a, b);
	}

	/**
	 * Tests {@link Game#Game(Game)}.
	 */
	@Test
	public void testGameClone() {
		Game a = new Game();
		Game b = new Game(a);

		assertEquals(a, b);
	}

	/**
	 * Tests {@link Game#swap()}.
	 */
	@Test
	public void testSwap() {
		Game a = new Game();
		Game b = new Game(a);

		assertEquals(a, b);

		a.swap();

		assertNotEquals(a, b);
	}

}
