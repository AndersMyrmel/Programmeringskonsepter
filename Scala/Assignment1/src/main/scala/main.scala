@main def run() = {
  val a = Array(1,0,11,4,7,6,5,3,8,2,9,13);
  val strings = Array("Come", "On", "You", "Spurs");
  val number = 5;

  val average = arrayAverage(a);
  val median = arrayMedian(a);
  printCapital(strings);
  val sum = recursiveSum(a, 0);
  val factor = recursiveFactorial(number);
  val filteredList = a.filter(isEven(_))

  //Make an anonymous (lambda) function that takes int as input and returns boolean if the number is divisible by 2. Use it to filter a list.
  val filtered = (arr : Array[Int]) => arr.filter((_ % 2 == 0));
  filtered(a);
}
  

// Make a function that returns the average of an array
def arrayAverage(arr: Array[Int]) : Double = {
  var sum = 0f;
  for(i <- arr){
    sum += i
  }
  sum = (sum/arr.length)
  return sum;
}

// Make a function that takes an array of Ints as input, and returns median value
def arrayMedian(arr: Array[Int]) : Double = {
  val temp = arr.sorted;
  if (temp.length % 2 != 0){
    return(temp(temp.length/2))
  } else {
    return(((temp(temp.length/2 - 1).toFloat) + (temp(temp.length/2).toFloat)) / 2);
  }
}

//Make a function that takes an array of String as input, and prints the capital letters.
def printCapital(arr : Array[String]) : Unit = {
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

// Write a recursive sum function that takes in an array of Ints, and returns the sum
def recursiveSum(arr : Array[Int], sum: Int) : Int = {
  if (arr.length == 0){
    return sum;
  }
  else {
    var newSum = sum + arr(0);
    var newArr = arr.tail;
    return recursiveSum(newArr, newSum);
  }
}

// Implement a recursive factorial function. The function should take in an int and return the factor of the int.
def recursiveFactorial(num: Int) : Int = {
  if (num > 1){
    return (num*recursiveFactorial(num-1));
  } else {
    return 1;
  }
}

// Make a non-anonymous function that takes int as input and returns boolean if the number is divisible by 2. Use it to filter a list.
def isEven(num: Int) : Boolean = {
  if (num % 2 == 0){
    return true;
  } else {
    return false;
  }
}
  