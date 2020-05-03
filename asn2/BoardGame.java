package assignment2;

import java.util.Arrays;

/**
 * This class implements all the support methods needed by the algorithm that
 * plays the board game.
 * 
 * @author MingCong Zhou
 *
 */
public class BoardGame {
	private final int DICTIONARY_SIZE = 7507;

	private int board_size;
	private int empty_positions;
	private int max_levels;

	/*
	 * The human player uses blue tiles and always starts the game. The computer
	 * uses orange tiles. Empty positions on the board are green.
	 */
	char[][] gameBoard;

	public BoardGame(int board_size, int empty_positions, int max_levels) {
		this.board_size = board_size;
		this.empty_positions = empty_positions;
		this.max_levels = max_levels;
		gameBoard = new char[board_size][board_size];
		for (char[] row : gameBoard) {
			Arrays.fill(row, 'g');
		}
	}

	/**
	 * 
	 * @return returns an empty HashDictionary of the size that you have selected
	 */
	public HashDictionary makeDictionary() {
		return new HashDictionary(DICTIONARY_SIZE);
	}

	/**
	 * checks whether the string representing the gameBoard is in dict
	 * 
	 * @param dict the dictionary for all occurrences.
	 * @return its associated score if this occurrence is repeated, -1 if not
	 */
	public int isRepeatedConfig(HashDictionary dict) {
		return dict.getScore(getStringRep());
	}

	/**
	 * put the current game board into the dictionary
	 * 
	 * @param dict  the dictionary used to store entries
	 * @param score the corresponding score
	 */
	public void putConfig(HashDictionary dict, int score) {

		try {
			dict.put(new Configuration(getStringRep(), score));
		} catch (DictionaryException e) {
			System.out.println("This configuration has already been put in.");
		}
	}

	/**
	 * stores symbol in gameBoard[row][col] to keep a player's move
	 * 
	 * @param row
	 * @param col
	 * @param symbol the char that represents human by b, or computer by o
	 */
	public void savePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}

	/**
	 * checks whether a location is empty
	 * 
	 * @param row
	 * @param col
	 * @return true if the location is empty
	 */
	public boolean positionIsEmpty(int row, int col) {
		return (gameBoard[row][col] == 'g');
	}

	/**
	 * checks whether there is a human's tiles at the specified location
	 * 
	 * @param row
	 * @param col
	 * @return true if there is one, and false otherwise
	 */
	public boolean tileOfHuman(int row, int col) {
		return (gameBoard[row][col] == 'b');
	}

	/**
	 * checks whether there is a computer's tiles at the specified location
	 * 
	 * @param row
	 * @param col
	 * @return true if there is one, and false otherwise
	 */
	public boolean tileOfComputer(int row, int col) {
		return (gameBoard[row][col] == 'o');
	}

	/**
	 * Returns true if there are n adjacent tiles of type symbol in the same row,
	 * column, or diagonal of gameBoard, where n is the size of the game- board.
	 * 
	 * @param symbol the char that represents human by b, or computer by o
	 * @return if the specified player has won
	 */
	public boolean wins(char symbol) {
		// check each row by checking if all the values in a row match the symbol
		outerloop: for (int row = 0; row < board_size; row++) {
			for (int col = 0; col < board_size; col++) {
				// if the do not match, stop checking this row and go to the next row
				if (gameBoard[row][col] != symbol) {
					continue outerloop;
				}
			}
			// if all the values match the symbol, return true;
			return true;
		}

		// check each column by checking if all the values in a column match the symbol
		outerloop: for (int col = 0; col < board_size; col++) {
			for (int row = 0; row < board_size; row++) {
				// if the do not match, stop checking this row and go to the next row
				if (gameBoard[row][col] != symbol) {
					continue outerloop;
				}
			}
			// if all the values match the symbol, return true;
			return true;
		}

		// check the top-left to bottom-right diagonal
		boolean hasWon = true;
		for (int row = 0, col = 0; row < board_size; row++, col++) {
			if (gameBoard[row][col] != symbol) {
				hasWon = false;
				break;
			}
		}

		if (hasWon) {
			return true;
		}

		// check the top-right to bottom-left diagonal
		hasWon = true;
		for (int row = 0, col = board_size; row < board_size; row++, col--) {
			if (gameBoard[row][col] != symbol) {
				hasWon = false;
				break;
			}
		}

		if (hasWon) {
			return true;
		}

		// if after all tests we still cannot find a win situation for the player, we
		// return a false to indicate that the player has not won
		return false;
	}

	/**
	 * 
	 * @param symbol         the player who plays the next
	 * @param empty_position the number of positions of the game board that must
	 *                       remain empty.
	 * @return true if the game configuration corresponding to gameBoard is a draw
	 *         assuming that the player that will perform the next move uses tiles
	 *         of the type specified by symbol.
	 */
	public boolean isDraw(char symbol, int empty_positions) {
		if (wins('o') | wins('g')) {
			return false;
		} else {
			if (empty_positions == 0) {
				return true;
			} else if (!canSlide(symbol)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Checks whether the specified player can still slides his tile(s).
	 * 
	 * @param symbol the player who is being checked
	 * @return
	 */
	private boolean canSlide(char symbol) {
		for (int row = 0; row < board_size; row++) {
			for (int col = 0; col < board_size; col++) {
				if (positionIsEmpty(row, col)) {

					/*
					 * First we need to check if the location is valid to avoid any exception, and
					 * if it is not valid, the second method won't be called because of the
					 * "short-circuit" property of && operation
					 */

					// check left-above
					if (isValidLocation(row - 1, col - 1) && gameBoard[row - 1][col - 1] == symbol) {
						return true;
					}
					// check above
					if (isValidLocation(row - 1, col) && gameBoard[row - 1][col] == symbol) {
						return true;
					}
					// check right-above
					if (isValidLocation(row - 1, col + 1) && gameBoard[row - 1][col + 1] == symbol) {
						return true;
					}
					// check left
					if (isValidLocation(row, col - 1) && gameBoard[row][col - 1] == symbol) {
						return true;
					}
					// check right
					if (isValidLocation(row, col + 1) && gameBoard[row][col + 1] == symbol) {
						return true;
					}
					// check left-below
					if (isValidLocation(row + 1, col - 1) && gameBoard[row + 1][col - 1] == symbol) {
						return true;
					}
					// check below
					if (isValidLocation(row + 1, col) && gameBoard[row + 1][col] == symbol) {
						return true;
					}
					// check right-below
					if (isValidLocation(row + 1, col + 1) && gameBoard[row + 1][col + 1] == symbol) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * checks if the location specified by the row and column is valid (within the
	 * game board two dimensional array)
	 * 
	 * @param row
	 * @param col
	 * @return true if the location is valid, and false otherwise
	 */
	private boolean isValidLocation(int row, int col) {
		return ((row > 0) && (row < board_size) && (col > 0) && (col < board_size));
	}

	/**
	 * evaluates the score for the game board
	 * 
	 * @param symbol the player who plays the next
	 * @param empty_positions the number of positions of the game board that must
	 *                        remain empty.
	 * @return
	 */
	public int evalBoard(char symbol, int empty_positions) {
		if(wins('o')){
			return 3;
		}else if(wins('b')) {
			return 0;
		}else if(isDraw(symbol, empty_positions)) {
			return 2;
		}else {
			return 1;
		}
	}

	private String getStringRep() {
		StringBuilder str = new StringBuilder();
		for (int col = 0; col < board_size; col++) {
			for (int row = 0; row < board_size; row++) {
				str.append(gameBoard[row][col]);
			}
		}
		return str.toString();
	}
}
