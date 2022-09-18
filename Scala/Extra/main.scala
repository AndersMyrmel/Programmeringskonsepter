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
            else {
                puzzle.board(row)(col) = Missing // Erase number and backtrack
            }
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

    val moves = List((1,0),(0,1))   



      //val (newRow, newCol) = (row,col) + moves(i)
    val previousRow = row -1
    val previousCol = col -1
    val nextRow = row + 1
    val nextCol = col + 1

    if (0 <= nextRow && nextRow < puzzle.board(0).length) {
      if (puzzle.board(nextRow)(col) == Up){
        if (getNumber(puzzle.board(nextRow+1)(col)) != 0){
          return (num.number < getNumber(puzzle.board(nextRow+1)(col)))
        } else{
          return true
        }
      }
    }

    
    if (0 <= previousCol && previousCol < puzzle.board(0).length) {
      if puzzle.board(row)(previousCol) == Left && getNumber(puzzle.board(row)(previousCol-1)) != 0 then return (num.number > getNumber(puzzle.board(row)(previousCol-1)));
      if puzzle.board(row)(previousCol) == Right then return (num.number < getNumber(puzzle.board(row)(previousCol-1)))

    }
    
    if (0 <= previousRow && previousRow < puzzle.board(0).length){
      if puzzle.board(previousRow)(col) == Down && getNumber(puzzle.board(previousRow - 1)(col)) != 0 then return (num.number < getNumber(puzzle.board(previousRow-1)(col)))
      //if puzzle.board(previousRow)(col) == Up then return (num.number > getNumber(puzzle.board(previousRow-1)(col)))
    }

    

    


    
  
    



    //if (puzzle.board(row)(col+1) == Left) {
    //  print(getNumber(puzzle.board(row)(col+2)))
    //  print(num.number < getNumber(puzzle.board(row)(col+2)))
    //  return num.number < getNumber(puzzle.board(row)(col+2))
    //} else {
    //  return true;
    //}

    //try {
    //  if (puzzle.board(row)(col+1) == Left) {return num.number < getNumber(puzzle.board(row)(col+2))}
    //  //else if (puzzle.board(row)(col+1) == Right) {return num.number > getNumber(puzzle.board(row)(col+2))}
    //  else return true
    //} catch {
    //  case e: ArrayIndexOutOfBoundsException => 
    //}
    
    



    

    //if puzzle.board(row)(col+1) == Left then return (num.number<getNumber(puzzle.board(row)(col+2)));
    //if puzzle.board(row)(col+1) == Right then return (num.number>getNumber(puzzle.board(row)(col+2)));
    //if puzzle.board(row+1)(col) == Up then return (num.number>getNumber(puzzle.board(row+2)(col)));
    //if puzzle.board(row+1)(col) == Down then return (num.number<getNumber(puzzle.board(row+2)(col)));
    

    
    // find a better solution to this exception handling
    //try {
    //  if puzzle.board(row)(col+1) == Left && getNumber(puzzle.board(row)(col+2))!= 0  then return (num.number<getNumber(puzzle.board(row)(col+2)));
    //  if puzzle.board(row)(col+1) == Right && getNumber(puzzle.board(row)(col+2))!= 0 then return (num.number>getNumber(puzzle.board(row)(col+2)));
    //  if puzzle.board(row+1)(col) == Up && getNumber(puzzle.board(row+2)(col))!= 0 then return (num.number>getNumber(puzzle.board(row+2)(col)));
    //  if puzzle.board(row+1)(col) == Down && getNumber(puzzle.board(row+2)(col)) != 0 then return (num.number<getNumber(puzzle.board(row+2)(col)));
    //} catch {
    //  case e: ArrayIndexOutOfBoundsException => 
    //}
  
    return true;
}

implicit class TuppleAdd(t: (Int, Int)) {
  def +(p: (Int, Int)) = (p._1 + t._1, p._2 + t._2)
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
    val filename = "./Scala/Extra/Unequal.txt"
    val puzzles = parsePuzzles(filename)
    var puzzle1 = puzzles(1)
    //println(puzzle1.board(0)(14))
    //val res = isLegal(puzzle1,0,14,Number(8))
    //println(res)
    val res = solvePuzzle(puzzle1)

    println(puzzle1)
    print(puzzle1.board(3)(6))
    
}
