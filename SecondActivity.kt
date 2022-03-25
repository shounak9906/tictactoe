package net.shounak.mygame

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Button
import android.content.Intent
import kotlinx.android.synthetic.main.layout_multiplayer.btnSingle

class SecondActivity : AppCompatActivity() {

    private val boardCells = Array(3) { arrayOfNulls<ImageView>(3) }


    //creating the board instance
    var board = Boardmultiplayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadBoard()

        //restart functionality
        button_restart.setOnClickListener {
            //creating a new board instance
            //it will empty every cell
            board = Boardmultiplayer()

            //setting the result to empty
            text_view_result.text = ""

            //this function will map the internal board
            //to the visual board
            mapBoardToUi()


        }

        val button = findViewById<Button>(R.id.btnSingle)
        btnSingle.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    //function is mapping
    //the internal board to the ImageView array board
    private fun mapBoardToUi() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    Boardmultiplayer.PLAYER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.circle)
                        boardCells[i][j]?.isEnabled = false
                    }
                    Boardmultiplayer.PLAYER2 -> {
                        boardCells[i][j]?.setImageResource(R.drawable.cross)
                        boardCells[i][j]?.isEnabled = false
                    }
                    else -> {
                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled = true
                    }
                }
            }
        }
    }


    private fun loadBoard() {
        for (i in boardCells.indices) {
            for (j in boardCells.indices) {
                boardCells[i][j] = ImageView(this)
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 250
                    height = 230
                    bottomMargin = 5
                    topMargin = 5
                    leftMargin = 5
                    rightMargin = 5
                }
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                boardCells[i][j]?.setOnClickListener(CellClickListener(i, j))
                layout_board.addView(boardCells[i][j])
            }
        }
    }

    inner class CellClickListener(
        private val i: Int,
        private val j: Int
    ) : View.OnClickListener {

        override fun onClick(p0: View?) {

            //checking if the game is not over
            if (!board.isGameOver) {

                //creating a new cell with the clicked index
                val cell = Cell(i, j)

                //placing the move for player
                board.placeMove(cell, Boardmultiplayer.PLAYER)

                //placing the move for player
                board.placeMove(cell, Boardmultiplayer.PLAYER2)


                //mapping the internal board to visual board
                mapBoardToUi()
            }

            //Displaying the results
            //according to the game status
            when {
                board.hasPlayer2Won() -> text_view_result.text = "X Won"
                board.hasPlayerWon() -> text_view_result.text = "O Won"
                board.isGameOver -> text_view_result.text = "Game Tied"
            }
        }
    }

}