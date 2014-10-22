package it.mbruni.trycatch.pieces;

public class Knight extends Piece {

	public Knight(int row, int column) {
		super(row, column);
	}

	@Override
	public boolean attacks(int row, int column) {
		// true if this piece and the other are distant two rows and one column
		// or two columns and one row
		if (Math.abs(this.column - column) * Math.abs(this.row - row) == 2)
			return true;

		return false;
	}

	@Override
	public PieceType getType() {
		return PieceType.KNIGHT;
	}
}
