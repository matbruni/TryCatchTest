package it.mbruni.trycatch;

import it.mbruni.trycatch.pieces.Piece;
import it.mbruni.trycatch.pieces.PieceFactory;
import it.mbruni.trycatch.pieces.PieceType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BoardChecker {

	// used to calculate processing time
	private long initialTime = System.currentTimeMillis();
	private long lastAccess = System.currentTimeMillis();

	// used to count total placements tested
	private int totalPlacements = 0;

	// used to dump to file all correct placements
	private BufferedWriter outFileWriter = null;

	// the board to be tested
	private Board board = null;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		BoardChecker checker = new BoardChecker();

		do {
			// read input parameters
			System.out.print("Please insert board width (M): ");
			int width = in.nextInt();
			System.out.print("Please insert board height (N): ");
			int height = in.nextInt();
			System.out.print("Please insert number of Kings: ");
			int kings = in.nextInt();
			System.out.print("Please insert number of Queens: ");
			int queens = in.nextInt();
			System.out.print("Please insert number of Bishops: ");
			int bishops = in.nextInt();
			System.out.print("Please insert number of Knights: ");
			int knights = in.nextInt();
			System.out.print("Please insert number of Rooks: ");
			int rooks = in.nextInt();

			// create available pieces data structure
			HashMap<PieceType, Integer> pieces = new HashMap<PieceType, Integer>();
			if (kings > 0) {
				pieces.put(PieceType.KING, kings);
			}
			if (queens > 0) {
				pieces.put(PieceType.QUEEN, queens);
			}
			if (bishops > 0) {
				pieces.put(PieceType.BISHOP, bishops);
			}
			if (knights > 0) {
				pieces.put(PieceType.KNIGHT, knights);
			}
			if (rooks > 0) {
				pieces.put(PieceType.ROOK, rooks);
			}

			// instantiate the board and find all correct placements
			Board board = new Board(width, height, pieces);
			checker.checkBoard(board);
			System.out.print("Continue (y/n)? ");
		} while (in.next().equalsIgnoreCase("Y"));

		in.close();
	}

	/**
	 * Finds all correct placements on the board.
	 * 
	 * Creates a text file (current directory, filename based on board
	 * configuration) where all correct placements are logged for reference.
	 * 
	 * Prints out the number of correct placements were found, the elaboration
	 * time in seconds, and the number of tested placements.
	 * 
	 * @param board
	 *            The {@link Board} to be tested
	 */
	public void checkBoard(Board board) {
		this.board = board;

		if (board != null) {
			init(board.getBoardName() + ".txt");
			StringBuilder builder = new StringBuilder();
			builder.append("Found ");
			builder.append(searchCorrectPlacements(null));
			builder.append(" correct placements in ");
			builder.append(getTotalTime() / 1000D);
			builder.append("s (");
			builder.append(getPlacements());
			builder.append(" placements tested)\n");

			if (outFileWriter != null) {
				try {
					outFileWriter.append(builder.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(builder.toString());

			releaseResources();
		}
	}

	/**
	 * Search for correct placements on the board.
	 * 
	 * Iterates over available (not yet placed on the board) pieces placing them
	 * in the first position (running along the board from left to right, top to
	 * bottom) where no piece could be taken. If a valid position is found for
	 * that piece, it is added to the board and this method is called
	 * recursively. The tree of placeable pieces (where each node descendant is
	 * reached by placing a piece on the board) is traversed with a Depth-first
	 * search algorithm. When a leaf node is reached (there are no left
	 * available pieces to place on the board), a correct placement has been
	 * found. An optimization has been used to reduce the number of placements
	 * tested: since we are traversing the tree of placeable pieces, we can
	 * avoid trying to place the piece currently tested before the position of
	 * the last one put on the board.
	 * 
	 * @param lastPiece
	 *            The last piece put on the board, null if board is empty
	 * @return The number of correct placements found
	 */
	private int searchCorrectPlacements(Piece lastPiece) {
		int retVal = 0;

		ArrayList<PieceType> availablePieces = new ArrayList<PieceType>(board.getAvailablePieces());
		for (PieceType type : availablePieces) {
			int startIndex = 0;
			if (lastPiece != null) {
				startIndex = (lastPiece.getRow() * board.getWidth()) + lastPiece.getColumn() + 1;
			}
			for (int index = startIndex; index < board.getWidth() * board.getHeight(); index++) {
				int row = index / board.getWidth();
				int column = index % board.getWidth();
				Piece newPiece = PieceFactory.getPiece(type, row, column);
				totalPlacements++;
				if (board.canPutPiece(newPiece)) {
					board.addPiece(newPiece);
					if (board.hasAvailablePieces()) {
						retVal += searchCorrectPlacements(newPiece);
					} else {
						retVal++;

						// if there is a log file, dump current board
						// configuration to console and log file

						// commented for performance reasons
						// uncomment if output to console is needed
						// System.out.println(board.toString());
						
						if (outFileWriter != null) {
							try {
								outFileWriter.append(board.toString());
							} catch (IOException e) {
								// e.printStackTrace();
							}
						}
					}
					board.removePiece(newPiece);
				}
			}
		}
		lastAccess = System.currentTimeMillis();
		return retVal;
	}

	/**
	 * Releases all the resources used by this object
	 */
	public void releaseResources() {
		if (outFileWriter != null) {
			try {
				outFileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				outFileWriter = null;
			}
		}
		board = null;
	}

	/**
	 * Initialize this {@link BoardChecker}
	 * 
	 * @param outFileName
	 *            The log filename
	 */
	public void init(String outFileName) {
		initialTime = System.currentTimeMillis();
		totalPlacements = 0;
		try {
			outFileWriter = new BufferedWriter(new FileWriter(new File(outFileName)));
		} catch (IOException e) {
			outFileWriter = null;
			e.printStackTrace();
		}
	}

	/**
	 * @return Total execution time
	 */
	public long getTotalTime() {
		return lastAccess - initialTime;
	}

	/**
	 * @return Total placements tested
	 */
	public int getPlacements() {
		return totalPlacements;
	}
}
