package tie.core;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests {@link Tile}.
 * 
 * @author Torben Kr&uuml;ger
 *
 */
public class TileTest {

	/**
	 * Tests {@link Tile#swap(Tile)}.
	 */
	@Test
	public void testSwap1() {

		Tile a = new Tile(Tile.A[0], Tile.B[1], Tile.C[2]);
		Tile b = new Tile(Tile.A[2], Tile.B[1], Tile.C[0]);

		assertEquals(Tile.A[0], a.a);
		assertEquals(Tile.B[1], a.b);
		assertEquals(Tile.C[2], a.c);

		assertEquals(Tile.A[2], b.a);
		assertEquals(Tile.B[1], b.b);
		assertEquals(Tile.C[0], b.c);

		a.swap(b);

		assertEquals(Tile.A[2], a.a);
		assertEquals(Tile.B[1], a.b);
		assertEquals(Tile.C[0], a.c);

		assertEquals(Tile.A[0], b.a);
		assertEquals(Tile.B[1], b.b);
		assertEquals(Tile.C[2], b.c);

	}

	/**
	 * Tests {@link Tile#swap(Tile)}.
	 */
	@Test
	public void testSwap2() {

		Tile a = new Tile(Tile.A[0], Tile.B[1], Tile.C[2]);
		Tile b = new Tile(Tile.A[0], Tile.B[1], Tile.C[2]);
		Tile c = new Tile(Tile.A[2], Tile.B[1], Tile.C[0]);
		Tile d = new Tile(Tile.A[2], Tile.B[1], Tile.C[0]);

		assertEquals(a, b);
		assertNotEquals(a, c);
		assertNotEquals(a, d);
		assertNotEquals(b, c);
		assertNotEquals(b, d);
		assertEquals(c, d);

		a.swap(c);

		assertNotEquals(a, b);
		assertNotEquals(a, c);
		assertEquals(a, d);
		assertEquals(b, c);
		assertNotEquals(b, d);
		assertNotEquals(c, d);

	}

	/**
	 * Tests {@link Tile#clone(Tile)}.
	 */
	@Test
	public void testClone1() {

		Tile a = new Tile(Tile.A[0], Tile.B[1], Tile.C[2]);
		Tile b = new Tile(Tile.A[2], Tile.B[1], Tile.C[0]);

		assertEquals(Tile.A[0], a.a);
		assertEquals(Tile.B[1], a.b);
		assertEquals(Tile.C[2], a.c);

		assertEquals(Tile.A[2], b.a);
		assertEquals(Tile.B[1], b.b);
		assertEquals(Tile.C[0], b.c);

		a.clone(b);

		assertEquals(Tile.A[2], a.a);
		assertEquals(Tile.B[1], a.b);
		assertEquals(Tile.C[0], a.c);

		assertEquals(Tile.A[2], b.a);
		assertEquals(Tile.B[1], b.b);
		assertEquals(Tile.C[0], b.c);

	}

	/**
	 * Tests {@link Tile#clone(Tile)}.
	 */
	@Test
	public void testClone2() {

		Tile a = new Tile(Tile.A[0], Tile.B[1], Tile.C[2]);
		Tile b = new Tile(Tile.A[2], Tile.B[1], Tile.C[0]);

		assertNotEquals(a, b);

		a.clone(b);

		assertEquals(a, b);

	}

}
