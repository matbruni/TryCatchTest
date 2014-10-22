package it.mbruni.trycatch.pieces;

public class King extends Piece {

	public King(int row, int column) {
		super(row, column);
	}
	
	@Override
	public boolean attacks(int row, int column) {
		// true if the other piece is adjacent to this 
		if (Math.abs(this.column - column) <= 1 && Math.abs(this.row - row) <= 1)
			return true;
		
		return false;
	}

	@Override
	public PieceType getType() {
		return PieceType.KING;
	}
}
