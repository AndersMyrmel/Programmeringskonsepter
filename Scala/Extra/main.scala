import scala.io.Source

// Create a puzzle input file, as shown in Canvas (Pages/Description: Unequal)
// • Read the file, print out the number of puzzles and their sizes
// • Make a function that “solves” each puzzle by simply replacing each empty square by a random number between 1 and puzzle size.
// • Create a output file using the above solution(s)

@main def run() = {
    val fileName = "./Scala/Extra/Unequal.txt";
    //readFile(fileName);
    val puzzle = getPuzzle(fileName);
    puzzle.foreach { row => row foreach print; println }
}

// Read the file, print out the number of puzzles and their sizes
def readFile(fileName : String) = {
    for (line <- Source.fromFile(fileName).getLines){
        if (line contains "puzzles"){
            println(line);
        } else if (line contains "size"){
            println(line);
        }
    }
}

// Generate a 2D array of the puzzle
def getPuzzle(fileName : String): Array[Array[Int]] = {
    val size = getSize(fileName);
    val puzzle = Array.ofDim[Int](size, size);
    var row = 0;
    var column = 0;

    for (line <- Source.fromFile(fileName).getLines.drop(2)){
         for (i <- line){
            if (i.isDigit){
                println((i, (row, column)));
                puzzle(row)(column) = i.toInt - 48;
                row += 1;
                if (row >= size -1 && column >= size -1){
                    return puzzle;
                }
                else if (row  >= size){
                    row = 0;
                    column += 1;
                }
            }
            else if (i.toChar == '_') {
                println((0,(row, column)));
                puzzle(row)(column) = 0;
                row += 1;
                if (row >= size -1 && column >= size -1){
                    return puzzle;
                }
                else if (row  >= size){
                    row = 0;
                    column += 1;
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