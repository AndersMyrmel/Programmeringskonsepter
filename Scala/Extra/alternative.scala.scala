import scala.io.Source

// Create a puzzle input file, as shown in Canvas (Pages/Description: Unequal)
// • Read the file, print out the number of puzzles and their sizes
// • Make a function that “solves” each puzzle by simply replacing each empty square by a random number between 1 and puzzle size.
// • Create a output file using the above solution(s)


// https://www.chegg.com/homework-help/questions-and-answers/program-solve-futoshiki-puzzle-grid-based-logic-puzzle-japan-also-known-unequal-learning-o-q32009377

@main def run() = {
    val fileName = "./Scala/Extra/Unequal.txt";
    var puzzle = readPuzzle(fileName); // Index (i,j) = I x N + J    N = Size
    val constraints = getConstraints(fileName);
    constraints.foreach(print);
}

def readPuzzle(fileName : String) : Array[Int] = {
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

// 1 = Box is less than box to the left (>)
// 2 = Box is greater than box to the left (<)
// 4 = Box is greater than box above (A)
// 8 = Box is less than box above (V)
def getConstraints(fileName : String) : Array[Int] = {
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
