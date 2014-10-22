package it.mbruni.trycatch.pieces;
public abstract class Piece {
	protected int row;
	protected int column;

	public abstract boolean attacks(int row, int column);
	public abstract PieceType getType();
	
	protected Piece(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getName());
		builder.append(" [");
		builder.append(row);
		builder.append(", ");
		builder.append(column);
		builder.append("]");
		return builder.toString();
	}
}