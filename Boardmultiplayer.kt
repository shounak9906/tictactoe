package net.shounak.mygame

import kotlin.math.min

class Boardmultiplayer {
    //Strings for PLAYER and COMPUTER
    companion object {
        const val PLAYER = "O"
        const val PLAYER2 = "X"
    }

    //This is our internal board
    //and for this we used a 3 by 3 array of Strings
    val board = Array(3) { arrayOfNulls<String>(3) }

    //This property is giving us
    //a list of all the empty cells
    val availableCells: List<Cell>
        get() {
            val cells = mutableListOf<Cell>()
            for (i in board.indices) {
                for (j in board.indices) {
                    if (board[i][j].isNullOrEmpty()) {
                        cells.add(Cell(i, j))
                    }
                }
            }
            return cells
        }

    //this property will tell
    //if the game is over or not
    val isGameOver: Boolean
        get() = hasPlayer2Won() || hasPlayerWon() || availableCells.isEmpty()


    //These functions are checking
    //Weather the player2 or player has won or not
    fun hasPlayer2Won(): Boolean {
        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == PLAYER2 ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == PLAYER2
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == PLAYER2 ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == PLAYER2
            ) {
                return true
            }
        }

        return false
    }

    fun hasPlayerWon(): Boolean {

        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == PLAYER ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == PLAYER
        ) {
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == PLAYER ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == PLAYER
            ) {
                return true
            }
        }

        return false
    }




//this function is placing a move in the given cell
    fun placeMove(cell: Cell, player: String) {
        board[cell.i][cell.j] = player
    }


}