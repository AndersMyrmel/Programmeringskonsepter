import scala.io.Source

// Create a puzzle input file, as shown in Canvas (Pages/Description: Unequal)
// • Read the file, print out the number of puzzles and their sizes
// • Make a function that “solves” each puzzle by simply replacing each empty square by a random number between 1 and puzzle size.
// • Create a output file using the above solution(s)

@main def run() = {
    val fileName = "./Scala/Extra/Unequal.txt";
    val size = getSize(fileName)
    var puzzle = readPuzzle(fileName, size); // Index (i,j) = I x N + J    N = Size
    val constraints = getConstraints(fileName, size);
    printPuzzle(puzzle, size)
    println()
    val res = solvePuzzle(puzzle, constraints, size)
    printPuzzle(puzzle, size)
    println(res)
}

// Solve puzzle
def solvePuzzle(puzzle: Array[Int], constraints: Array[Int], size: Int) : Boolean = {
    val firstEmptySquare = puzzle.indexOf(0) // Find the first empty square in puzzle
    if (firstEmptySquare == -1) {
        return true; // If no empty squares exist the puzzle is solved
    }

    // Convert 1D array index to row and column
    val row = firstEmptySquare / size
    val col = firstEmptySquare % size
    
    for (number <- 1 to size){
        val legalMove = isLegal(puzzle, constraints, size, row, col, number)
        if (legalMove) {
            puzzle(firstEmptySquare) = number
            if (solvePuzzle(puzzle, constraints, size)) {
                return true;
            }
            else {
                puzzle(firstEmptySquare) = 0 // Erase number and backtrack
            }
        }
    }
    return false; 
    
}

// Check wheter it is legal to assign a particular number to a given box on the grid
def isLegal(puzzle: Array[Int], constraints: Array[Int], size: Int, row: Int, col: Int, number: Int) : Boolean = {
    val sameRow = puzzle.slice(row*size, row*size+size)
    val sameCol = puzzle.slice(col, puzzle.size).zipWithIndex.collect{case (x,i) if (i) % size == 0 => x}
    
    if (sameRow.contains(number) || sameCol.contains(number)){
        return false;
    }

    constraints(row*size+col) match
        case 1 => if number > puzzle(row*size+col-1) && puzzle(row*size+col-1) != 0 then return false;
        case 2 => if number < puzzle(row*size+col-1) then return false;
        case 4 => if number < puzzle(row*size+col-size) then return false;
        case 8 => if number > puzzle(row*size+col-size) && puzzle(row*size+col-size) != 0 then return false;
        case _ => return true;

    return true;
}


// Read the puzzle and store it in a one dimensional array
def readPuzzle(fileName : String, size: Int) : Array[Int] = {
    val size = getSize(fileName);
    val puzzle = Array.ofDim[Int](size*size);
    var skip = 0;
    var count = 0;

    for (line <- Source.fromFile(fileName).getLines.drop(2)){
        if (line contains "size"){
            return puzzle;
        }
         for (i <- line){
            if (i.isDigit){
                puzzle(count) = i.toInt - 48;
                count += 1;
            }
            else if (i == '_' ) {
                skip += 1;
                if (skip == 2){
                    count += 1; 
                    skip = 0;
                }
            }
        }
    }
    return puzzle;
}

// Store constraints in a seperate array
// 1 = Box is less than box to the left (>)
// 2 = Box is greater than box to the left (<)
// 4 = Box is greater than box above (A)
// 8 = Box is less than box above (V)
def getConstraints(fileName : String, size: Int) : Array[Int] = {
    val size = getSize(fileName);
    val puzzle = Array.ofDim[Int](size*size);
    var twice = 0;
    var count = 0;
    for (line <- Source.fromFile(fileName).getLines.drop(2)){
        if (line contains "size"){
            return puzzle;
        }
         for (i <- line){
            if (i.isDigit){
                count += 1;
            }
            else if (i == '>'){
                puzzle(count) = 1;
            }
            else if (i == '<'){
                puzzle(count) = 2;
            }
            else if (i == 'A'){
                puzzle(count) = 4;
            }
            else if (i == 'V'){
                puzzle(count) = 8;
            }
            else if (i == '_' ) {
                twice += 1;
                if (twice == 2){
                    count += 1; 
                    twice = 0;
                }
            }
        }
    }
    return puzzle;
}

// Get the width and height of a puzzle
def getSize(fileName : String) : Int = {
    for (line <- Source.fromFile(fileName).getLines){
         if (line contains "size"){
            return (line(line.length - 1).toInt - 48);
        }
    }
    return -1;
}

// Display puzzle in terminal
def printPuzzle(puzzle: Array[Int], size: Int) : Unit = {
    var count = 0;
    for (i <- puzzle){
        print(puzzle(count))
        print(" ")
        count += 1
        if (count % size == 0){
            println()
        }
    }
}
