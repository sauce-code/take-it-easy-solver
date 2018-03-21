package tie.core;

/**
 * A faster version of {@link Game}. It's the same except the middle tile, which
 * is fixed to {@code [2 1 3]}, which cannot be moved. A perfect solution can be
 * found way fasterthan in the original model.
 * 
 * @author Torben Kr&uuml;ger
 *
 */
public class FasterGame extends Game {
	
	/**
	 * Creates a new {@link FasterGame}.
	 */
	public FasterGame() {
		super();
		
		movableBoardTiles = BOARD_CAPACITY - 1;
		movableTiles = TILE_COUNT - 1;;

		// define tile { 2 1 3 }
		Tile small = new Tile(Tile.A[0], Tile.B[0], Tile.C[0]);

		// search for { 2 1 3 }. if found, replace it by the middle tile
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].equals(small)) {
					board[i][j].clone(board[2][2]);
				}
			}
		}
		
		// search for { 2 1 3 }. if found, replace it by the middle tile
		for (Tile tile : tiles) {
			if (tile.equals(small)) {
				tile.clone(board[2][2]);
			}
		}

		// replace middle tile by { 2 1 3 }
		board[2][2].clone(small);

		// remove swapmap entry for { 2 1 3 } and replace it by the last entry
		swapMap.remove(9);
		swapMap.put(9, swapMap.get(26));
		swapMap.remove(26);
		
	}

	/**
	 * Creates a new {@link FasterGame}, being a clone of {@code o}.
	 * 
	 * @param o
	 *            game being cloned
	 */
	public FasterGame(FasterGame o) {
		this();
		clone(o);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code o} is no instanceof {@link FasterGame}
	 */
	@Override
	public void clone(Game o) {
		if (!(o instanceof FasterGame)) {
			throw new IllegalArgumentException(
					"o has to be instanceof " + getClass().getName());
		}
		super.clone(o);
	}

}
