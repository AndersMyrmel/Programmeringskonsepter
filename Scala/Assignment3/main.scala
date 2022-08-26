import scala.util.Random

@main def run() = {
    var board = Array.ofDim[Square](4);
    board(0) = new Square(0,"A")
    board(1) = new Square(1,"B")
    board(2) = new Square(2,"C")
    board(3) = new Square(3,"D")

    var pieces = Array.ofDim[Piece](4);
    pieces(0) = new Piece(0,"C")
    pieces(1) = new Piece(1,"D")
    pieces(2) = new Piece(2,"A")
    pieces(3) = new Piece(3,"B")

    while(!isSolved(board, pieces)){
        solvePuzzle(board, pieces)
        printPieces(pieces)
    }
}

class Piece(x: Int, v: String) {
    var index = x;
    var value = v;
}

class Square(x: Int, valid: String) {
    var index = x;
    var number = 0;
    val validPiece = valid;
}


// Extend the Jigsaw-examples with a function to get a square by x,y coordinate
def getSquare(x: Int, board: Array[Square]) : Square = {
    return board(x);
}

// Extend the Jigsaw-examples with a function to get a piece placed on a gameboard square
def getPiece(x: Int, board: Array[Piece]) : Piece = {
    return board(x);
}

//Implement a function that checks if a single piece is in a valid position on the board
def isValidPos(board: Array[Square], pieces: Array[Piece], x: Int) : Boolean = {
    return(board(x).validPiece == pieces(x).value);
}

// Implement a function that checks if a gameboard is solved
def isSolved(board: Array[Square], pieces: Array[Piece]) : Boolean = {
    var size = board.length - 1;
    for(i <- 0 to size){
        if (!isValidPos(board, pieces, i)){
            return false;
        }
    }
    return true;
}

// Implement a function to solve the puzzle
def solvePuzzle(board: Array[Square], pieces: Array[Piece]) : Unit = {
    var size = board.length - 1;
    for(i <- 0 to size){
        if (board(i).validPiece != pieces(i).value) {
            val random = Random.between(0, size)
            val temp = pieces(i);
            pieces(i) = pieces(random);
            pieces(random) = temp;
            }
    }
}

// Print board of squares
def printBoard(board : Array[Square]) : Unit = {
    println()
    board.foreach(e => print(e.validPiece + " "))
}

// Print board of pieces
def printPieces(pieces : Array[Piece]) : Unit = {
    println()
    pieces.foreach(e => print(e.value + " "))
}
