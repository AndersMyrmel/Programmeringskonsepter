@main def run() = {
  val a = Array(1,0,11,4,7,6,5,3,8,2,9,13,0,29);
  val b = Array(1.0, 2.3, 13.1, 4.4, 5.9, 0.5);

  largerThanZero(a);
  allLargerThanZero(a);
  val minDouble = iterateDoubleList(b);
  val firstLastTuple = firstAndLast(a);
}


// Use foreach to iterate an Integer-list. If the list item is larger than 0 it should be printed.
def largerThanZero(arr: Array[Int]) : Unit = {
  arr.foreach(i => if (i > 0) println(i))
}

// Use forall to iterate a Integer-list. You should print out true if all elements in the list are larger than 0
def allLargerThanZero(arr: Array[Int]) : Unit = {
    println(arr.forall(i => {i > 0}));
}

// Use reduceLeft to iterate an Double-list. It should give the smallest value in the list.
def iterateDoubleList(arr: Array[Double]) : Double = {
    return arr.reduceLeft(_ min _);
}

// Make a function that takes in a list and returns the first and last value.
def firstAndLast(arr: Array[Int]) : Tuple = {
    return (arr(0), arr(arr.length-1));
}