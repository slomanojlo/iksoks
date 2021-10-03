package rs.sloman.iksoks

import rs.sloman.iksoks.Constants.Companion.BOARD_SIZE

data class IksOks(
    var xPlaying: Boolean = true,
    var draw: Boolean = false,
    var gameWon: Boolean = false,
    var matrix: MutableList<MutableList<Int>> = mutableListOf(),
) {

    fun setupMatrix() {
        matrix = generateMatrix()
        xPlaying = true
        draw = false
        gameWon = false
    }

    private fun generateMatrix() = generateSequence {
        generateSequence { 0 }.take(BOARD_SIZE).toMutableList()
    }.take(BOARD_SIZE).toMutableList()

    fun play(x: Int, y: Int) {
        matrix[x][y] = (xOrY(xPlaying = xPlaying))
        gameWon = isWinningMove(
            matrix = matrix,
            x = x,
            y = y,
            move = xOrY(xPlaying = xPlaying)
        )

        if (gameWon) return

        draw = isDraw()

        if (isGameActive()) {
            xPlaying = !xPlaying
        }
    }

    private fun isGameActive() = !gameWon && !draw

    private fun isDraw() = matrix.flatten().none { it == Square.EMPTY.value }

    private fun xOrY(xPlaying: Boolean): Int = if (xPlaying) Square.X.value else Square.O.value

    private fun isWinningMove(matrix: Matrix, x: Int, y: Int, move: Int): Boolean {

        if (checkRow(matrix, x, move)) return true
        if (checkColumn(matrix, y, move)) return true
        if (x == y) {
            if (checkDiagonal(matrix, move)) return true
        }
        if (x + y == BOARD_SIZE - 1) {
            if (checkReverseDiagonal(matrix, move)) return true
        }

        return false
    }

    private fun checkReverseDiagonal(
        matrix: Matrix,
        move: Int,
    ): Boolean {
        for (i in 0 until BOARD_SIZE) {
            if (matrix[i][(BOARD_SIZE - 1) - i] != move) {
                break
            }
            if (i == BOARD_SIZE - 1) {
                return true
            }
        }
        return false
    }

    private fun checkDiagonal(
        matrix: Matrix,
        move: Int,
    ): Boolean {
        for (i in 0 until BOARD_SIZE) {
            if (matrix[i][i] != move) {
                break
            }
            if (i == BOARD_SIZE - 1) {
                return true
            }
        }
        return false
    }

    private fun checkColumn(
        board: Matrix,
        y: Int,
        move: Int,
    ): Boolean {
        for (i in 0 until BOARD_SIZE) {
            if (board[i][y] != move) {
                break
            }
            if (i == BOARD_SIZE - 1) {
                return true
            }
        }
        return false
    }

    private fun checkRow(
        matrix: Matrix,
        x: Int,
        move: Int,
    ): Boolean {
        for (i in 0 until BOARD_SIZE) {
            if (matrix[x][i] != move) {
                break
            }
            if (i == BOARD_SIZE - 1) {
                return true
            }
        }
        return false
    }

}