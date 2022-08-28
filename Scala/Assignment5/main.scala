import scala.io.Source

@main def run() = {
    val obj = new Traits()
    obj.checkStatus("Superman")
    readFile("Scala/Assignment5/Textfile.txt")
}

// Write a trait CheckFamous. It should have a function isFamous, 
// that returns whether an input String contains the string Bieber.
trait CheckFamous:
    def isFamous(s: String) : Boolean = s.contains("Bieber")

// Write a trait CheckSuperhero. It should have a function, isSuperhero
// that returns whether an input String contains the string Superman.
trait CheckSuperhero:
    def isSuperhero(s: String) : Boolean = s.contains("Superman")

// Write a class that used both above traits. It should have a
// function checkStatus that runs isFamous and isSuperhero.
class Traits extends CheckFamous, CheckSuperhero{
    def checkStatus(s: String) : Unit = {
        println(isFamous(s))
        println(isSuperhero(s))
    }
}

// Open a file and print the last word of every line.
def readFile(fileName : String) = {
    for (line <- Source.fromFile(fileName).getLines){
        print(line.split(" ").last + "\n")
    }
}