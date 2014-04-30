package it.external.tryout

/**
 * Created by diego on 18/04/14.
 */
class Pitfalls {

}

class Vals {
  println(answer)
  val answer = 10
}

object Vals {
  def main(args: Array[String]) {
    CollectionIterators
  }
}

trait Foo{ val x:Int;  println(x) }

object CollectionIterators {
  val p = Seq(1,2,3).permutations
  if (p.size < 10) {
    println("here")
    p foreach println
  }

  val c = Seq(1,2,3).permutations.toStream
  if (c.size < 10) {
    println("here we are")
    c foreach println
  }
}