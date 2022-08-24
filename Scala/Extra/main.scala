import scala.io.Source

// Create a puzzle input file, as shown in Canvas (Pages/Description: Unequal)
// • Read the file, print out the number of puzzles and their sizes
// • Make a function that “solves” each puzzle by simply replacing each empty square by a random number between 1 and puzzle size.
// • Create a output file using the above solution(s)

@main def run() = {
    val fileName = "./Scala/Extra/Unequal.txt";
    var puzzle = getPuzzle(fileName);
    puzzle = getConstraints(fileName, puzzle);
    printPuzzle(puzzle);
}

class Cell(var x : Int) {
    var number = x;
    var greaterThanEast = false;
    var greaterThanWest = false;
    var greaterThanNorth = false;
    var greaterThanSouth = false;

    def setNumber(x : Int) : Unit = {
        number = x;
    }
    
    def setGreaterThanEast() : Unit = {
        greaterThanEast = true;
    }
    def setGreaterThanWest() : Unit = {
        greaterThanWest = true;
    }
    def setGreaterThanNorth() : Unit = {
        greaterThanNorth = true;
    }
    def setGreaterThanSouth() : Unit = {
        greaterThanSouth = true;
    }
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

// Display the puzzle in console
def printPuzzle(puzzle : Array[Array[Cell]]) : Unit = {
    var row, col = 0;
    while(row < puzzle.length){
        while(col < puzzle(row).length){
            print(puzzle(row)(col).number);
            col += 1;
        }
        println();
        row += 1;
        col = 0;
    }
}

// Generate a 2D array of the puzzle
def getPuzzle(fileName : String): Array[Array[Cell]] = {
    val size = getSize(fileName);
    val puzzle = Array.ofDim[Cell](size, size);
    var row = 0;
    var column = 0;
    var twice = 0;

    for (line <- Source.fromFile(fileName).getLines.drop(2)){
         for (i <- line){
            if (i.isDigit){
                puzzle(column)(row) = new Cell(i.toInt - 48)
                row += 1;
                if (row == size && column == size -1){
                    return puzzle;
                }
                else if (row == size){
                    row = 0;
                    column += 1;
                }
            }
            else if (i == '_' ) {
                twice += 1;
                if (twice == 2){
                    puzzle(column)(row) = new Cell(0)
                    twice = 0;
                    row += 1;
                }
                if (row == size && column == size-1){
                    return puzzle;
                }
                else if (row == size){
                    row = 0;
                    column += 1;
                }
            }
         }
    }
    return puzzle;
}

def getConstraints(fileName : String, puzzle : Array[Array[Cell]]): Array[Array[Cell]] = {
    val size = getSize(fileName);
    var row = 0;
    var column = 0;
    var twice = 0;

    var test = 3

    for (line <- Source.fromFile(fileName).getLines.drop(2)){
         for (i <- line){
            if (i.isDigit){
                row += 1;
                if (row == size && column == size -1){
                    return puzzle;
                }
                else if (row == size){
                    row = 0;
                    column += 1;
                }
            }
            else if (i == '<'){
                puzzle(column)(row).setGreaterThanWest();
            }
            else if(i == '>'){
                puzzle(column)(row-1).setGreaterThanEast();
            }
            else if (i == 'A'){
                puzzle(column)(row).setGreaterThanNorth();     
            }
            else if (i == 'V'){
                puzzle(column-1)(row).setGreaterThanSouth()
                puzzle(column-1)(row).setNumber(4) 

            }
            else if (i == '_' ) {
                twice += 1;
                if (twice == 2){
                    twice = 0;
                    row += 1;
                }
                if (row == size - 1 && column == size-1){
                    return puzzle;
                }
                else if (row == size){
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
