package tie.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a Take It Easy game.
 * 
 * @author Torben Kr&uuml;ger
 *
 */
public class Game implements Comparable<Game> {

	/**
	 * The number on tiles that fit on a board.
	 */
	public static final int BOARD_CAPACITY = 19;

	/**
	 * The minimum score a game can reach.
	 */
	public static final int SCORE_MIN = 0;

	/**
	 * The maximum score a game can reach.
	 */
	public static final int SCORE_MAX = 307;

	/**
	 * The number of unique tiles for a game.
	 */
	public static final int TILE_COUNT = 27;

	/**
	 * Helps finding random tiles.
	 */
	protected final Map<Integer, Tile> swapMap = new HashMap<Integer, Tile>();

	/**
	 * Saves all tiles which are currently not on the board.
	 */
	protected final List<Tile> tiles = new ArrayList<Tile>();

	/**
	 * The board where the tiles are placed.<br>
	 * ----------[2][0]----------<br>
	 * -----[1][0]----[3][0]-----<br>
	 * [0][0]----[2][1]----[4][0]<br>
	 * -----[1][1]----[3][1]-----<br>
	 * [0][1]----[2][2]----[4][1]<br>
	 * -----[1][2]----[3][2]-----<br>
	 * [0][2]----[2][3]----[4][2]<br>
	 * -----[1][3]----[3][3]-----<br>
	 * ----------[2][4]----------<br>
	 */
	protected final Tile[][] board = {
			// @formatter:off
			new Tile[3],
			new Tile[4],
			new Tile[5],
			new Tile[4],
			new Tile[3]
			// @formatter:on
	};

	/**
	 * The total number of movable tiles on the board.
	 */
	protected int movableBoardTiles = BOARD_CAPACITY;

	/**
	 * The total number of movable tiles, including the ones on the board and in
	 * the reserve.
	 */
	protected int movableTiles = TILE_COUNT;

	/**
	 * The current score.
	 */
	protected int score;

	/**
	 * Creates a new {@link Game}, randomly fileld with tiles.
	 */
	public Game() {

		// create all possible tiles
		for (int a = 0; a < 3; a++) {
			for (int b = 0; b < 3; b++) {
				for (int c = 0; c < 3; c++) {
					tiles.add(new Tile(Tile.A[a], Tile.B[b], Tile.C[c]));
				}
			}
		}

		// fill the board
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = tiles.remove(randomInt(tiles.size()));
			}
		}

		evaluateScore();

		// @formatter:off
		swapMap.put( 0, board[0][0]);
		swapMap.put( 1, board[0][1]);
		swapMap.put( 2, board[0][2]);
		swapMap.put( 3, board[1][0]);
		swapMap.put( 4, board[1][1]);
		swapMap.put( 5, board[1][2]);
		swapMap.put( 6, board[1][3]);
		swapMap.put( 7, board[2][0]);
		swapMap.put( 8, board[2][1]);
		swapMap.put( 9, board[2][2]);
		swapMap.put(10, board[2][3]);
		swapMap.put(11, board[2][4]);
		swapMap.put(12, board[3][0]);
		swapMap.put(13, board[3][1]);
		swapMap.put(14, board[3][2]);
		swapMap.put(15, board[3][3]);
		swapMap.put(16, board[4][0]);
		swapMap.put(17, board[4][1]);
		swapMap.put(18, board[4][2]);
		swapMap.put(19, tiles.get(0));
		swapMap.put(20, tiles.get(1));
		swapMap.put(21, tiles.get(2));
		swapMap.put(22, tiles.get(3));
		swapMap.put(23, tiles.get(4));
		swapMap.put(24, tiles.get(5));
		swapMap.put(25, tiles.get(6));
		swapMap.put(26, tiles.get(7));
		// @formatter:on

	}

	/**
	 * Creates a new {@link Game}, being a clone of {@code o}.
	 * 
	 * @param o
	 *            game being cloned
	 */
	public Game(Game o) {
		this();
		clone(o);
	}

	/**
	 * Returns the current score.
	 * 
	 * @return current score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Evaluates the current score of the board and saves it to {@link #score}.
	 */
	protected void evaluateScore() {
		score = 0;
		// @formatter:off
		
		// -------------- A --------------
		if (board[0][0].a == board[1][0].a && 
			board[0][0].a == board[2][0].a) {
				score += board[0][0].a * 3;
		}
		if (board[0][1].a == board[1][1].a && 
			board[0][1].a == board[2][1].a &&
			board[0][1].a == board[3][0].a) {
				score += board[0][1].a * 4;
		}
		if (board[0][2].a == board[1][2].a && 
			board[0][2].a == board[2][2].a &&
			board[0][2].a == board[3][1].a &&
			board[0][2].a == board[4][0].a) {
				score += board[0][2].a * 5;
		}
		if (board[1][3].a == board[2][3].a && 
			board[1][3].a == board[3][2].a &&
			board[1][3].a == board[4][1].a) {
				score += board[1][3].a * 4;
		}
		if (board[2][4].a == board[3][3].a && 
			board[2][4].a == board[4][2].a) {
				score += board[2][4].a * 3;
		}
		
		// -------------- B --------------
		if (board[0][0].b == board[0][1].b && 
			board[0][0].b == board[0][2].b) {
				score += board[0][0].b * 3;
		}
		if (board[1][0].b == board[1][1].b && 
			board[1][0].b == board[1][2].b &&
			board[1][0].b == board[1][3].b) {
				score += board[1][0].b * 4;
		}
		if (board[2][0].b == board[2][1].b && 
			board[2][0].b == board[2][2].b &&
			board[2][0].b == board[2][3].b &&
			board[2][0].b == board[2][4].b) {
				score += board[2][0].b * 5;
		}
		if (board[3][0].b == board[3][1].b && 
			board[3][0].b == board[3][2].b &&
			board[3][0].b == board[3][3].b) {
				score += board[3][0].b * 4;
		}
		if (board[4][0].b == board[4][1].b && 
			board[4][0].b == board[4][2].b) {
				score += board[4][0].b * 3;
		}
		
		// -------------- C --------------
		if (board[2][0].c == board[3][0].c && 
			board[2][0].c == board[4][0].c) {
				score += board[2][0].c * 3;
		}
		if (board[1][0].c == board[2][1].c && 
			board[1][0].c == board[3][1].c &&
			board[1][0].c == board[4][1].c) {
				score += board[1][0].c * 4;
		}
		if (board[0][0].c == board[1][1].c && 
			board[0][0].c == board[2][2].c &&
			board[0][0].c == board[3][2].c &&
			board[0][0].c == board[4][2].c) {
				score += board[0][0].c * 5;
		}
		if (board[0][1].c == board[1][2].c && 
			board[0][1].c == board[2][3].c &&
			board[0][1].c == board[3][3].c) {
				score += board[0][1].c * 4;
		}
		if (board[0][2].c == board[1][3].c && 
			board[0][2].c == board[2][4].c) {
				score += board[0][2].c * 3;
		}
		
		// @formatter:on
	}

	/**
	 * Generates a random int in range [0, maxExclusive).
	 * 
	 * @param maxExclusive
	 *            max value, exclusive
	 * @return random int
	 */
	protected int randomInt(int maxExclusive) {
		return ThreadLocalRandom.current().nextInt(0, maxExclusive);
	}

	/**
	 * Swaps 2 random tiles and calculates the new score.
	 */
	public void swap() {
		int swap1 = randomInt(movableBoardTiles);
		int swap2;
		do {
			swap2 = randomInt(movableTiles);
		} while (swap1 == swap2);
		swapMap.get(swap1).swap(swapMap.get(swap2));
		evaluateScore();
	}

	/**
	 * Swaps 2 random tiles {@code n} times and calculates the new score
	 * afterwards. It is <b>not</b> guaranteed that the resulting game differs
	 * from the original one, even tho the chance is very low.
	 * 
	 * @param n
	 *            how often tiles shall be swapped
	 */
	public void swap(int n) {
		for (int i = 0; i < n; i++) {
			int swap1 = randomInt(movableBoardTiles);
			int swap2 = randomInt(movableTiles);
			swapMap.get(swap1).swap(swapMap.get(swap2));
		}
		evaluateScore();
	}

	/**
	 * Lets this game become another one.
	 * 
	 * @param o
	 *            the game this one shall become
	 */
	public void clone(Game o) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j].clone(o.board[i][j]);
			}
		}
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).clone(o.tiles.get(i));
		}
		score = o.score;
	}

	@Override
	public int compareTo(Game o) {
		return score - o.score;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		String blank = "         ";

		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(board[2][0]);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(board[1][0]);
		sb.append(blank);
		sb.append(board[3][0]);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append(board[0][0]);
		sb.append(blank);
		sb.append(board[2][1]);
		sb.append(blank);
		sb.append(board[4][0]);
		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(board[1][1]);
		sb.append(blank);
		sb.append(board[3][1]);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append(board[0][1]);
		sb.append(blank);
		sb.append(board[2][2]);
		sb.append(blank);
		sb.append(board[4][1]);
		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(board[1][2]);
		sb.append(blank);
		sb.append(board[3][2]);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append(board[0][2]);
		sb.append(blank);
		sb.append(board[2][3]);
		sb.append(blank);
		sb.append(board[4][2]);
		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(board[1][3]);
		sb.append(blank);
		sb.append(board[3][3]);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append(blank);
		sb.append(blank);
		sb.append(board[2][4]);
		sb.append(blank);
		sb.append(blank);
		sb.append('\n');

		sb.append("score: ");
		sb.append(getScore());

		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (score != other.score)
			return false;
		if (swapMap == null) {
			if (other.swapMap != null)
				return false;
		} else if (!swapMap.equals(other.swapMap))
			return false;
		if (tiles == null) {
			if (other.tiles != null)
				return false;
		} else if (!tiles.equals(other.tiles))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		result = prime * result + score;
		result = prime * result + ((swapMap == null) ? 0 : swapMap.hashCode());
		result = prime * result + ((tiles == null) ? 0 : tiles.hashCode());
		return result;
	}

}
