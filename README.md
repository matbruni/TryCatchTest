TryCatchTest
============

TryCatch test assignment:

The problem is to find all unique configurations of a set of normal chess pieces on a chess board with dimensions M×N where none of the pieces is in a position to take any of the others. Assume the colour of the piece does not matter, and that there are no pawns among the pieces.

Write a program which takes as input:
      The dimensions of the board: M, N.
      The number of pieces of each type (King, Queen, Bishop, Rook and Knight) to try and place on the board.
      
As output, the program should list all the unique configurations to the console for which all of the pieces can be placed on the board without threatening each other.


============

Main class is it.mbruni.trycatch.BoardChecker, please find Javadocs and comments in source file.

No parameter is needed on the command line. Just launch the main class, after compiling:

java it.mbruni.trycatch.BoardChecker
