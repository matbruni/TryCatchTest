package it.mbruni.trycatch.pieces;

public class Queen extends Piece {

	public Queen(int row, int column) {
		super(row, column);
	}
	
	@Override
	public boolean attacks(int row, int column) {
		// true if the other piece is on the same row, column or diagonal as this
		if (this.column == column || this.row == row || Math.abs(this.row - row) == Math.abs(this.column - column)) 
			return true;
		
		return false;
	}

	@Override
	public PieceType getType() {
		return PieceType.QUEEN;
	}
}
