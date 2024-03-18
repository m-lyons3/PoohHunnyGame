package com.zybooks.poohhunnygame

/*
    PoohHunnyGame
    3/6/24
    Author: Maria Lyons
    Desc: The class and methods for playing the PoohHunnyGame. To win, the
    player needs a score of at least 26, or Pooh to reach branch 26.
    The player rolls two dice. If they are the same value, Pooh goes down
    3 branches (or -3 score). If the rolls are different, the player gains
    the sum of those two dice rolls n (Pooh goes up n many branches)
 */
class PoohHunnyGame {

   private var score = 1
   public var roll1 = 0
   public var roll2 = 0

    fun scoreTotal(diceRoll1: Int, diceRoll2: Int){
        if (diceRoll1 == diceRoll2 && score > 4) {
            this.score -= 3
        } else if (diceRoll1 == diceRoll2 && score < 4){
            this.score = 1
        } else {
            this.score += diceRoll1 + diceRoll2
        }
        if (this.score>26)
            score = 26
    }

    fun getScore(): Int{
        return this.score
    }

    fun checkWin() : Boolean {
        return score >= 26
    }

    fun newGame(){
        this.score = 1
    }

}