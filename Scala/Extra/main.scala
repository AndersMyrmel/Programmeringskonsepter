import scala.io.Source



sealed trait Item
case object Up extends Item
case object Down extends Item
case object Left extends Item
case object Right extends Item
case object Empty extends Item
case object Nothing extends Item
case object Missing extends Item
case class Number(number: Int) extends Item



class Puzzle(wi: Int, he: Int, bo: Array[Array[Item]]) {
  var width: Int = wi
  var height: Int = he
  var board: Array[Array[Item]] = bo

  override def toString = {
    var output = new StringBuilder(s"size: ${width}x${height}\n")
    board foreach { row => row foreach { item => item match {
      case Up => output.append(" A")
      case Down => output.append(" V")
      case Left => output.append("<")
      case Right => output.append(">")
      case Empty => output.append(" ")
      case Nothing => output.append("  ")
      case Missing => output.append("__")
      case Number(number) => {
        if (number < 10) output.append(" ")
        output.append(number)
      }
    } }; output.append("\n") }
    output.toString
  }
}

def parsePuzzles(file: String): Array[Puzzle] = {
  var puzzles = Array[Puzzle]()
  var lines = Source.fromFile(file).getLines()
  var amount = lines.next().split(" ")(1).toInt

  // Read each puzzle
  while (puzzles.length < amount) {
    var size = lines.next().split(" ")(1).split("x").map(_.toInt)
    var rows = Array[Array[Item]]()
    for (line <- lines.take(size(1) * 2 - 1)) {
      var row = Array[Item]()
      for (i <- 0 to size(0) - 1) {
        var value = line.slice(i * 3, i * 3 + 2)
        value match {
          case " A" => row :+= Up
          case " V" => row :+= Down
          case "__" => row :+= Missing
          case "  " => row :+= Nothing
          case _ => row :+= Number(value.trim().toInt)
        }
        line.slice(i * 3 + 2, i * 3 + 3) match {
          case "<" => row :+= Left
          case ">" => row :+= Right
          case " " => row :+= Empty
          case _ => 
        }
      }
      rows :+= row
    }
    puzzles :+= new Puzzle(size(0), size(1), rows)
  }

  return puzzles
}

def isLegal(puzzle: Puzzle, row: Int, col: Int, num: Number) : Boolean = {
    val sameRow = getRow(puzzle, row)
    val sameCol = getColumn(puzzle, col)

    //if (sameRow.contains(number) || sameCol.contains(number)){
    //    return false;
    //}


    
    

    

    
    if puzzle.board(row)(col+1) == Left then return (num.number<getNumber(puzzle.board(row)(col+2)))
    
    
    
    
    
  
    return true;

    //constraints(row*size+col) match
    //    case 1 => if number > puzzle(row*size+col-1) && puzzle(row*size+col-1) != 0 then return false;
    //    case 2 => if number < puzzle(row*size+col-1) then return false;
    //    case 4 => if number < puzzle(row*size+col-size) then return false;
    //    case 8 => if number > puzzle(row*size+col-size) && puzzle(row*size+col-size) != 0 then return false;
    //    case _ => return true;
//
    //return true;
}

def getNumber(value: Item) : Int = {
  value match
    case _: Number => val str = value.toString; val res = str.substring(str.indexOf("(")+1, str.indexOf(")")); return res.toInt;
    case _: Missing$ => return 0
    case _ => return -1
}



def getColumn(puzzle: Puzzle, row: Int) : Array[Item] = {
    var column = Array.ofDim[Item](puzzle.board(0).length);
    for (i <- 0 to column.length - 1) {
        column(i) = puzzle.board(i)(row);
    }
    return column;
}

def getRow(puzzle: Puzzle, index: Int) : Array[Item] = {
    val row = puzzle.board(index)
    return row
}

// Get the width and height of puzzle
def getSize(fileName : String) : Int = {
    val lines = Source.fromFile(fileName).getLines()
    val amount = lines.next().split(" ")(1).toInt
    val size = lines.next().split(" ")(1).split("x").map(_.toInt)
    return size(0)
}

@main def run() = {
    val filename = "./Scala/Extra/Unequal.txt"
    val puzzles = parsePuzzles(filename)
    val puzzle1 = puzzles(0)
    val res = isLegal(puzzle1,0,12,Number(5))
    println(res)
}




