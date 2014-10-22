package it.mbruni.trycatch.pieces;

public class Bishop extends Piece {

	public Bishop(int row, int column) {
		super(row, column);
	}

	@Override
	public boolean attacks(int row, int column) {
		// true if the other piece is on the same diagonal as this
		if (Math.abs(this.row - row) == Math.abs(this.column - column))
			return true;

		return false;
	}

	@Override
	public PieceType getType() {
		return PieceType.BISHOP;
	}
}
