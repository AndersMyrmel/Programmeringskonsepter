@main def run() = {
  val a = Array(1,0,11,4,7,6,5,3,8,2,9,13);
  val strings = Array("Come", "On", "You", "Spurs");
  val average = arrayAverage(a);
  val median = arrayMedian(a);
  printCapital(strings);
}
  

// Make a function that returns the average of an array
def arrayAverage(arr: Array[Int]) : Int = {
  var sum = 0;
  var i = 0;
  while (i < arr.length){
    sum += arr(i);
    i += 1;
  }
  sum = (sum/arr.length)
  return sum;
}

// Make a function that takes an array of Ints as input, and returns median value
def arrayMedian(arr: Array[Int]) : Double = {
  val temp = arr.sorted;
  if (temp.length % 2 != 0){
    return(temp(temp.length/2))
  } else{
    return(((temp(temp.length/2 - 1).toFloat) + (temp(temp.length/2).toFloat)) / 2);
  }
  return -1;
}

//Make a function that takes an array of String as input, and prints the capital letters.
def printCapital(arr : Array[String]) = {
  var i, j = 0;
  while (i < arr.length){
    while (j < arr(i).length){
      if (arr(i)(j).isUpper){
        println(arr(i)(j))
      }
      j += 1
    }
    j = 0
    i += 1
  }
}
    
  