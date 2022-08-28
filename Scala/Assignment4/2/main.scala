import scala.language.implicitConversions

@main def run() = {
    Store.setApples(1)
    Store.setBananas(3)
    val res = Store.getter()
    var c = Multiple(Multiple(Red(),Blue()), Blue())
    println(simplify(c))
}

// Make a store-application with apples and bananas. The application should have “Getters” and “Setters” 
// for inserting and retrieving the fruit. No functions in the class should have side effects.
object Store {
    var apples : String = 0
    var bananas : String = 0
    def setApples(a: Int) = {
        a match
            case 1 => (apples = "one")
            case 2 => (apples = "two")
            case _ => (apples = "other number")
    }
    def setBananas(b: Int) = {
        b match
            case 1 => bananas = "one"
            case 2 => bananas = "two"
            case _ => bananas = "other number"
    }
    def getter() : Tuple = {
        return(apples,bananas)
    }
}

// Write a case statement that changes the int 1 into the text one, 2
// into the text two, and any other number to the text Other number.
implicit def intToString(i: Int) : String = i.toString

// Write a case statement that checks if a list starts with 1. 
// If so, it should return the text: The list starts with 1.
def checkList(l: List[Int]) : String = {
    l(0) match
        case 1 => return "The list starts with 1"
        case _ => return ""
}

// Use pattern matching to mix colors according to the following rules: 
// • You have three colors: Red, Blue and Purple.
// • Mixing Red and Blue becomes Purple.
// • Purple does not mix with any other color.
sealed abstract class Color;
case class Blue() extends Color;
case class Red() extends Color;
case class Purple() extends Color;
case class Multiple(c1:Color, c2:Color) extends Color;

def simplify(c: Color):Color = {
    c match{
        case x if x==Red()||x==Blue()||x==Purple() => x;
        case Multiple(x,y) if x==y => x
        case Multiple(x:Red,y:Blue) => Purple()
        case Multiple(x:Blue,y:Red) => Purple()
        case Multiple(x,y) if x==Purple() || y==Purple() => Purple()
        case Multiple(x,y) => simplify(Multiple(simplify(x),simplify(y)));
    }
}