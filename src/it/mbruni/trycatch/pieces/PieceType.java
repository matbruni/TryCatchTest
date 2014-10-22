package it.mbruni.trycatch.pieces;

public enum PieceType {
	KING, QUEEN, BISHOP, KNIGHT, ROOK;

	@Override
	public String toString() {
		switch (this) {
		case KING:
			return "K";
		case QUEEN:
			return "Q";
		case BISHOP:
			return "B";
		case KNIGHT:
			return "N";
		case ROOK:
			return "R";
		}
		return super.toString();
	}
}
