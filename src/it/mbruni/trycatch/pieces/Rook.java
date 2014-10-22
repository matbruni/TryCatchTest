package it.mbruni.trycatch.pieces;

public class Rook extends Piece {

	public Rook(int row, int column) {
		super(row, column);
	}
	
	@Override
	public boolean attacks(int row, int column) {
		// true if the other piece is on the same row or column as this
		if (this.column == column || this.row == row)
			return true;
		
		return false;
	}

	@Override
	public PieceType getType() {
		return PieceType.ROOK;
	}
}
