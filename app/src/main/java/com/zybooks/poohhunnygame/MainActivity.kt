package com.zybooks.poohhunnygame

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.zybooks.poohhunnygame.PoohHunnyGame
import java.io.InputStream


const val MAX_DICE = 2
val roundScore = 1
var message = ""


class MainActivity : AppCompatActivity() {

    private lateinit var optionsMenu: Menu
    private var timer: CountDownTimer? = null

    private var numVisibleDice = MAX_DICE
    private lateinit var diceList: MutableList<Dice>
    private lateinit var diceImageViewList: MutableList<ImageView>

    val PoohGame = PoohHunnyGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.appbar_menu))

        // Create list of Dice
        diceList = mutableListOf()
        for (i in 0 until MAX_DICE) {
            diceList.add(Dice(i + 1))
        }

        // Create list of ImageViews
        diceImageViewList = mutableListOf(
            findViewById(R.id.dice1), findViewById(R.id.dice2))

        val seekBar = findViewById<SeekBar>(R.id.verticalSeekBar)
        seekBar.isEnabled = false
        seekBar.progress = PoohGame.getScore()

        showDice()
        val startDialog = StartingGameDialog()
        startDialog.show(supportFragmentManager, "startDialog")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        optionsMenu = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Determine which menu option was chosen
        return when (item.itemId) {
            R.id.action_stop -> {
                timer?.cancel()
                item.isVisible = false
                true
            }
            R.id.action_roll -> {
                rollDice()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun rollDice() {
        optionsMenu.findItem(R.id.action_stop).isVisible = true
        timer?.cancel()

        // Start a timer that periodically changes each visible dice
        timer = object : CountDownTimer(1000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                for (i in 0 until numVisibleDice) {
                    if (i == 0)
                        PoohGame.roll1 = diceList[i].roll()
                    if (i == 1)
                        PoohGame.roll2 = diceList[i].roll()
                }
                showDice()
            }

            override fun onFinish() {
                /*
                All of the rolling logic
                 */
                optionsMenu.findItem(R.id.action_stop).isVisible = false
                PoohGame.scoreTotal(PoohGame.roll1, PoohGame.roll2)

                if (PoohGame.roll1 == PoohGame.roll2) {
                    message = getString(R.string.beeDialogue, PoohGame.getScore())
                    findViewById<TextView>(R.id.branchNum).text = message
                    findViewById<ImageView>(R.id.bees).visibility = View.VISIBLE
                }
                else {
                    message = getString(R.string.branchDialogue, PoohGame.getScore())
                    findViewById<TextView>(R.id.branchNum).text = message
                    findViewById<ImageView>(R.id.bees).visibility = View.INVISIBLE
                }

                (findViewById<SeekBar>(R.id.verticalSeekBar)).progress = PoohGame.getScore()
                println(PoohGame.getScore()) //Checks to see if score is right in Logcat

                //Section checks if the players wins
                if (PoohGame.checkWin()){
                    val dialog = WinningDialogFragment()
                    dialog.show(supportFragmentManager, "winningDialog")
                    PoohGame.newGame()
                    (findViewById<SeekBar>(R.id.verticalSeekBar)).progress = PoohGame.getScore()
                    message = getString(R.string.branchDialogue, PoohGame.getScore())
                    findViewById<TextView>(R.id.branchNum).text = message
                }
            }
        }.start()
    }



    private fun showDice() {

        // Show visible dice
        for (i in 0 until numVisibleDice) {
            val diceDrawable = ContextCompat.getDrawable(this, diceList[i].imageId)
            diceImageViewList[i].setImageDrawable(diceDrawable)
            diceImageViewList[i].contentDescription = diceList[i].imageId.toString()
        }
    }
}