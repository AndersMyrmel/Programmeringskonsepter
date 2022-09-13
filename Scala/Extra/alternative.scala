import scala.io.Source

// Create a puzzle input file, as shown in Canvas (Pages/Description: Unequal)
// • Read the file, print out the number of puzzles and their sizes
// • Make a function that “solves” each puzzle by simply replacing each empty square by a random number between 1 and puzzle size.
// • Create a output file using the above solution(s)


// https://www.chegg.com/homework-help/questions-and-answers/program-solve-futoshiki-puzzle-grid-based-logic-puzzle-japan-also-known-unequal-learning-o-q32009377

@main def run() = {
    val fileName = "./Scala/Extra/Unequal.txt";
    val size = getSize(fileName)
    var puzzle = readPuzzle(fileName, size); // Index (i,j) = I x N + J    N = Size
    val constraints = getConstraints(fileName, size);
    printPuzzle(puzzle, size)
    isLegal(puzzle,constraints,size,3,0,1)
}

// Solve puzzle
def solvePuzzle(puzzle: Array[Int], constraints: Array[Int]) : Boolean = {
    return false;

}

// Check wheter it is legal to assign a particular number to a given box on the grid
def isLegal(puzzle: Array[Int], constraints: Array[Int], size: Int, row: Int, col: Int, number: Int) : Boolean = {
    if (puzzle(row*size+col) != 0) {
        return false;
    }
    println(puzzle(row*size+col)) //Index (i,j) = I x N + J    N = Size
    return false;
}


// Read the puzzle and store it in a one dimensional array
def readPuzzle(fileName : String, size: Int) : Array[Int] = {
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
                puzzle(count) = i.toInt - 48;
                count += 1;
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

// Constraints are saved in a seperate array
// For each box we store a value that encodes the relationship to the box to the left and below
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