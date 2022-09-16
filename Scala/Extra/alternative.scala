import scala.io.Source
import scala.collection.mutable.ArrayBuffer

// Create a puzzle input file, as shown in Canvas (Pages/Description: Unequal)
// • Read the file, print out the number of puzzles and their sizes
// • Make a function that “solves” each puzzle by simply replacing each empty square by a random number between 1 and puzzle size.
// • Create a output file using the above solution(s)

@main def run() = {
    val fileName = "./Scala/Extra/Unequal.txt";
    val size = getSize(fileName)
    val puzzle = readPuzzle(fileName) 
    val constraints = readConstraints(fileName,size)
    //solvePuzzle(puzzle, constraints, size)
    printPuzzle(puzzle, size)
}

def solvePuzzle(puzzle: ArrayBuffer[Int], constraints: ArrayBuffer[Int], size: Int) : Boolean = {
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

// Check wheter it is legal to assign a particular number to a given square on the grid
def isLegal(puzzle: ArrayBuffer[Int], constraints: ArrayBuffer[Int], size: Int, row: Int, col: Int, number: Int) : Boolean = {
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


// Read the next puzzle and store it in a one dimensional array
// Index (i,j) = I x N + J    N = Size
def readPuzzle(file: String): ArrayBuffer[Int] = {
    var puzzle = new ArrayBuffer[Int]
    var lines = Source.fromFile(file).getLines()
    var amount = lines.next().split(" ")(1).toInt
    var size = lines.next().split(" ")(1).split("x").map(_.toInt)

    for (line <- lines.take(size(1) * 2 - 1)) {
      for (i <- 0 to size(0) - 1) {
        var value = line.slice(i * 3, i * 3 + 2)
        value match {
            case " A" => 
            case " V" => 
            case "  " => 
            case "__" => puzzle += 0
            case _ => puzzle+= value.trim().toInt
        }
      }
    }
    return puzzle;
}

// Store constraints in a separate array
// 1 = less than (>), 2 = greater than (<) square to the left
// 4 = less than (A), 8 = greater than (V) square above
def readConstraints(file : String, size: Int) : ArrayBuffer[Int] = {
    var constraints = new ArrayBuffer[Int]
    var lines = Source.fromFile(file).getLines()
    var amount = lines.next().split(" ")(1).toInt
    val intRegex = """(\d+)""".r
    var count = 0;
    var size = lines.next().split(" ")(1).split("x").map(_.toInt)

    for (line <- lines.take(size(1) * 2 - 1)) {
        for (i <- 0 to size(0) - 1) {
            var value = line.slice(i * 3, i * 3 + 2)
            value match {
                case " A" => constraints += 4
                case " V" => constraints += 8
                case " " =>
                case "  " => 
                case "__" => constraints += 0
                case _ => constraints += 0
            }
            line.slice(i * 3 + 2, i * 3 + 3) match {
                case ">" => constraints += 1
                case "<" => constraints += 2
                case _ =>
            }
            
        }
    }  
    return constraints;
}

// Get the width and height of puzzle
def getSize(fileName : String) : Int = {
    val lines = Source.fromFile(fileName).getLines()
    val amount = lines.next().split(" ")(1).toInt
    val size = lines.next().split(" ")(1).split("x").map(_.toInt)
    return size(0)
}

def printPuzzle(puzzle: ArrayBuffer[Int], size: Int) : Unit = {
    var count = 0;
    for (i <- puzzle){
        print(s"${puzzle(count)} ")
        count += 1
        if (count % size == 0){
            println()
        }
    }
}
