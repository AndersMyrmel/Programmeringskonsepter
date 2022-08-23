import scala.io.Source

// Create a puzzle input file, as shown in Canvas (Pages/Description: Unequal)
// • Read the file, print out the number of puzzles and their sizes
// • Make a function that “solves” each puzzle by simply replacing each empty square by a random number between 1 and puzzle size.
// • Create a output file using the above solution(s)

@main def run() = {
    val fileName = "./Scala/Extra/Unequal.txt";
    //readFile(fileName);
    getPuzzle(fileName);
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

def getPuzzle(fileName : String) = {
    var size = 0
    //var puzzle = Array()
    for (line <- Source.fromFile(fileName).getLines){
         if (line contains "size"){
            size = line(line.length - 1).toInt - 48
            println(size)
            
        }
    }
    
    
}