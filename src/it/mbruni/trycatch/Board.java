package it.mbruni.trycatch;

import it.mbruni.trycatch.pieces.Piece;
import it.mbruni.trycatch.pieces.PieceType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Board {
	private int width;
	private int height;

	// available (not placed on the board) pieces
	private TreeMap<PieceType, Integer> availablePieces = new TreeMap<PieceType, Integer>();

	// placed pieces
	private HashSet<Piece> placedPieces = new HashSet<Piece>();

	/**
	 * Initializes this board.
	 * 
	 * @param M
	 *            Board width
	 * @param N
	 *            Board height
	 * @param availablePieces
	 *            Map of available pieces
	 */
	public Board(int M, int N, Map<PieceType, Integer> availablePieces) {
		this.width = M;
		this.height = N;
		this.availablePieces = new TreeMap<PieceType, Integer>(availablePieces);
	}

	/**
	 * Checks if no placed piece takes or is taken by a new piece
	 * 
	 * @param piece
	 *            The piece to be tested
	 * @return false if the place cannot be placed
	 */
	public boolean canPutPiece(Piece piece) {
		for (Piece p : placedPieces) {
			if (p.attacks(piece.getRow(), piece.getColumn())) {
				return false;
			}

			if (piece.attacks(p.getRow(), p.getColumn())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Adds a new piece to the board
	 * 
	 * @param p
	 *            The piece to add
	 */
	public void addPiece(Piece p) {
		// Only adds the piece if the specific piece type is available
		if (availablePieces.containsKey(p.getType())) {
			placedPieces.add(p);
			int remainder = availablePieces.get(p.getType()) - 1;
			if (remainder == 0) {
				availablePieces.remove(p.getType());
			} else {
				availablePieces.put(p.getType(), remainder);
			}
		}
	}

	/**
	 * Removes a piece from the board
	 * 
	 * @param p
	 *            The piece to remove
	 */
	public void removePiece(Piece p) {
		placedPieces.remove(p);
		// After removing the piece set the right piece type counter
		int currCount = 0;
		if (availablePieces.containsKey(p.getType())) {
			currCount = availablePieces.get(p.getType());
		}
		availablePieces.put(p.getType(), currCount + 1);
	}

	/**
	 * @return board width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return board height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return true if there are available (not placed on the board) pieces
	 */
	public boolean hasAvailablePieces() {
		return !availablePieces.isEmpty();
	}

	/**
	 * @return a {@link Set} of available {@link PieceType}s
	 */
	public Set<PieceType> getAvailablePieces() {
		return availablePieces.keySet();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int column = 0; column < getWidth(); column++) {
			for (int row = 0; row < getHeight(); row++) {
				builder.append(' ');
			}
			builder.append("|\n|");
		}
		builder.setLength(builder.length() - 3);

		for (Piece piece : placedPieces) {
			builder.replace((piece.getRow() * getWidth()) + piece.getColumn() + (3 * piece.getRow()),
					(piece.getRow() * getWidth()) + piece.getColumn() + (3 * piece.getRow()) + 1, piece.getType()
							.toString());
		}

		builder.insert(0, "+");
		for (int col = 0; col < getWidth(); col++) {
			builder.insert(1, "-");
		}
		builder.insert(getWidth() + 1, "+\n|");

		builder.append("|\n+");
		for (int col = 0; col < getWidth(); col++) {
			builder.append("-");
		}
		builder.append("+\n");

		return builder.toString();
	}

	/**
	 * @return a unique name based on board configuration
	 */
	public String getBoardName() {
		StringBuilder builder = new StringBuilder();

		builder.append("Board-");
		builder.append(getWidth());
		builder.append("x");
		builder.append(getHeight());
		builder.append("-");

		for (PieceType type : availablePieces.keySet()) {
			int total = availablePieces.get(type);
			for (Piece p : placedPieces) {
				if (p.getType().equals(type)) {
					total++;
				}
			}
			builder.append(total);
			builder.append(type.toString());
		}

		return builder.toString();
	}
}