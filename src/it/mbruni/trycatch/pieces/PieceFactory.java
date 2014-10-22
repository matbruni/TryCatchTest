package it.mbruni.trycatch.pieces;

public class PieceFactory {

	/**
	 * Factory method used to instantiate the piece
	 * 
	 * @param type
	 *            The type of the new piece
	 * @param row
	 *            The piece row
	 * @param column
	 *            The piece column
	 * @return the new {@link Piece}
	 */
	public static Piece getPiece(PieceType type, int row, int column) {
		Piece retVal = null;

		switch (type) {
		case KING:
			retVal = new King(row, column);
			break;
		case QUEEN:
			retVal = new Queen(row, column);
			break;
		case BISHOP:
			retVal = new Bishop(row, column);
			break;
		case KNIGHT:
			retVal = new Knight(row, column);
			break;
		case ROOK:
			retVal = new Rook(row, column);
			break;
		}

		return retVal;
	}
}
