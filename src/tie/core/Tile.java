package tie.core;

/**
 * Represents a single tile of a Take It Easy game.
 * 
 * @author Torben Kr&uuml;ger
 *
 */
public class Tile {

	/**
	 * The values for direction a.
	 */
	public static final int[] A = {2, 6, 7};

	/**
	 * The values for direction b.
	 */
	public static final int[] B = {1, 5, 9};

	/**
	 * The values for direction c.
	 */
	public static final int[] C = {3, 4, 8};

	/**
	 * The direction a value.
	 */
	protected int a;

	/**
	 * The direction b value.
	 */
	protected int b;

	/**
	 * The direction c value.
	 */
	protected int c;

	/**
	 * Creates a new tile with certain values.
	 * 
	 * @param a
	 *            direction a value
	 * @param b
	 *            direction b value
	 * @param c
	 *            direction c value
	 */
	public Tile(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Swaps the values with another tile.
	 * 
	 * @param o
	 *            the other tiles which shall swap values with this
	 */
	public void swap(Tile o) {
		int temp;

		temp = a;
		a = o.a;
		o.a = temp;

		temp = b;
		b = o.b;
		o.b = temp;

		temp = c;
		c = o.c;
		o.c = temp;
	}

	/**
	 * Lets this tile take the values of another tile.
	 * 
	 * @param o
	 *            the other tile which values shall be taken
	 */
	public void clone(Tile o) {
		a = o.a;
		b = o.b;
		c = o.c;
	}

	@Override
	public String toString() {
		return "{ " + a + " " + b + " " + c + " }";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (a != other.a)
			return false;
		if (b != other.b)
			return false;
		if (c != other.c)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a;
		result = prime * result + b;
		result = prime * result + c;
		return result;
	}

}
