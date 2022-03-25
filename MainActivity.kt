package net.shounak.mygame

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_second1.*

class MainActivity : AppCompatActivity() {

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

        button_Multiplayer.setOnClickListener {
            val intent = Intent(this, SecondActivity1::class.java)
            startActivity(intent)
        }

    }

    //function is mapping
    //the internal board to the ImageView array board
    private fun mapBoardToUi() {
        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {
                    Board.PLAYER -> {
                        boardCells[i][j]?.setImageResource(R.drawable.circle)
                        boardCells[i][j]?.isEnabled = false
                    }
                    Board.COMPUTER -> {
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
                boardCells[i][j]?.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.colorPrimary
                    )
                )
                boardCells[i][j]?.setOnClickListener(CellClickListener(i, j))
                layout_board.addView(boardCells[i][j])
            }
        }
    }

    var activeplayer = 1


    inner class CellClickListener(
        private val i: Int,
        private val j: Int
    ) : View.OnClickListener {

        override fun onClick(p0: View?) {


            if (!board.isGameOver) {

                val cell = Cell(i, j)

                if (activeplayer == 1) {

                    board.placeMove(cell, Boardmultiplayer.PLAYER)
                    activeplayer = 2

                } else {

                    board.placeMove(cell, Boardmultiplayer.PLAYER2)
                    activeplayer = 1
                }

                mapBoardToUi()
            }


            when {
                board.hasPlayer2Won() -> text_view_result.text = "X Won :("
                board.hasPlayerWon() -> text_view_result.text = "O Won :)"
                board.isGameOver -> text_view_result.text = "Game Tied :/"
            }
        }

    }
}

