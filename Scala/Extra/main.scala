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

def solvePuzzle(puzzle: Puzzle) : Boolean = {
  val firstEmptySquare = findFirstElement(puzzle) // Find the first empty square in puzzle
  if (firstEmptySquare == (-1,-1)) {
    return true; // If no empty squares exist the puzzle is solved
  }
  val (row, col) = firstEmptySquare
  for (number <- 1 to Math.ceil(puzzle.board(0).length / 2.0).toInt){
    val legalMove = isLegal(puzzle, row, col, Number(number))
    if (legalMove) {
      puzzle.board(row)(col) = Number(number)
      if (solvePuzzle(puzzle)) {
        return true;
      }
      puzzle.board(row)(col) = Missing; // Erase number and backtrack
      println(puzzle)
    }
  }
  return false; 
}

def isLegal(puzzle: Puzzle, row: Int, col: Int, num: Number) : Boolean = {
    val sameRow = getRow(puzzle, row);
    val sameCol = getColumn(puzzle, col);

    if (sameRow.contains(num) || sameCol.contains(num)){
      return false;
    }

    return checkNeighbours(puzzle, row, col, num);
}

def checkNeighbours(puzzle : Puzzle, row : Int, col : Int, num: Number) : Boolean = {
  val nextRow = row + 1;
  val nextCol = col + 1;
  val previousRow = row -1;
  val previousCol = col -1;
  var north,south,east,west = true;

  // check north
  if (0 <= previousRow && previousRow < puzzle.board(0).length){
    puzzle.board(previousRow)(col) match
      case Down => if getNumber(puzzle.board(previousRow-1)(col)) != 0 then north = (num.number < getNumber(puzzle.board(previousRow-1)(col)))
      case Up => north = (num.number > getNumber(puzzle.board(previousRow-1)(col)))
      case _ =>
  } 
  if (north == false){
    return north
  }

  // check south
  if (0 <= nextRow && nextRow < puzzle.board(0).length){
    puzzle.board(nextRow)(col) match
      case Down => south = (num.number > getNumber(puzzle.board(nextRow+1)(col)))
      case Up => if getNumber(puzzle.board(nextRow+1)(col)) != 0 then south = (num.number < getNumber(puzzle.board(nextRow+1)(col)))
      case _ =>
  }
  if (south == false){
    return south
  }
  
  // check east
  if (0 <= nextCol && nextCol < puzzle.board(0).length) {
    puzzle.board(row)(nextCol) match
      case Left => if getNumber(puzzle.board(row)(nextCol+1)) != 0 then east = (num.number < getNumber(puzzle.board(row)(nextCol+1)))
      case Right => east = (num.number > getNumber(puzzle.board(row)(nextCol+1)))
      case _ => 
  }
  if (east == false){
    return east
  }

  // check west
  if (0 <= previousCol && previousCol < puzzle.board(0).length) {
    puzzle.board(row)(previousCol) match
      case Left => if getNumber(puzzle.board(row)(previousCol-1)) != 0 then west = (num.number > getNumber(puzzle.board(row)(previousCol-1)))
      case Right => west = (num.number < getNumber(puzzle.board(row)(previousCol-1)))
      case _ =>
  }
  if (west == false){
    return west
  }

  return !List(north,south,east,west).contains(false)
}

def findFirstElement(puzzle: Puzzle): (Int, Int) = {
  val row = puzzle.board.indexWhere(_.contains(Missing)) 
  if (row > -1) {
    return (row, puzzle.board(row).indexOf(Missing))
  } else {
    return (-1, -1)
  }
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
  val t1 = System.nanoTime
  val filename = "./Scala/Extra/Unequal.txt"
  val puzzles = parsePuzzles(filename)

  for (puzzle <- puzzles) {
    solvePuzzle(puzzle)
    println(puzzle)
  }
  
  val duration = (System.nanoTime - t1) / 1e9d
  println(duration)
}
