package it.external.tryout


object ShuffleDeck {

  
  type Card = (CardValue.Value, Seed.Value)

  
  implicit class CardRapr(val card: (ShuffleDeck.CardValue.Value, ShuffleDeck.Seed.Value)) {
    @Override
    override def toString = s"${card._1} of ${card._2}"
    def test = s"${card._1} of ${card._2}"
  }
  
  val deck: Set[Card] = {
    for {
      s <- Seed.values
      c <- CardValue.values
    } yield (c, s)
  }

  object Seed extends Enumeration {
    type Seed = Value
    val Club, Heart, Spade, Diamond= Value
  }
  object CardValue extends Enumeration {
    type Seed = Value
    val Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King = Value
  }

  
  def shuffleDeck() = {
//    val shuffledDeck = scala.util.Random.shuffle(1 to 52)
  	val shuffledDeck: Seq[Card] = scala.util.Random.shuffle(ShuffleDeck.deck).toSeq
    giveCards[Card](shuffledDeck, 5)
  }
  def giveCards[A](deck: Seq[A], numberPlayers: Int): List[Seq[A]] = {
    giveCardsRecursion(List(deck), numberPlayers)
  }

  def giveCardsRecursion[A](traversableHands: List[Seq[A]], recursion: Int): List[Seq[A]] = {
    if (recursion == -1) traversableHands
    else {
      traversableHands match {
        case List() => traversableHands
        case head :: tail =>
          val split = traversableHands.head.splitAt(5)
          giveCardsRecursion(List(split._2, split._1) ::: tail, recursion - 1)
      }
    }
  }
}

