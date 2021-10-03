package rs.sloman.iksoks

class IksOks {
    var xPlaying: Boolean = true
        private set
    var draw: Boolean = false
        private set
    var gameWon: Boolean = false
        private set
    var matrix: Array<Array<Int>> = emptyArray()
        private set

    fun setupMatrix() {
        matrix = Array(Constants.BOARD_SIZE) { Array(Constants.BOARD_SIZE) { Square.EMPTY.value } }
        xPlaying = true
        draw = false
    }

    fun play(x: Int, y: Int) {
        matrix[x][y] = xOrY(xPlaying = xPlaying)
        gameWon = isWinningMove(
            matrix = matrix,
            x = x,
            y = y,
            move = xOrY(xPlaying = xPlaying)
        )

        if (gameWon) return

        if (matrix.flatten().none { it == Square.EMPTY.value }) {
            draw = true
        }

        if (!gameWon && !draw) {
            xPlaying = !xPlaying
        }
    }

    private fun xOrY(xPlaying: Boolean): Int = if (xPlaying) Square.X.value else Square.O.value

    private fun isWinningMove(matrix: Matrix, x: Int, y: Int, move: Int): Boolean {

        if (checkRow(matrix, x, move)) return true
        if (checkColumn(matrix, y, move)) return true
        if (x == y) {
            if (checkDiagonal(matrix, move)) return true
        }
        if (x + y == Constants.BOARD_SIZE - 1) {
            if (checkReverseDiagonal(matrix, move)) return true
        }

        return false
    }

    private fun checkReverseDiagonal(
        matrix: Matrix,
        move: Int,
    ): Boolean {
        for (i in 0 until Constants.BOARD_SIZE) {
            if (matrix[i][(Constants.BOARD_SIZE - 1) - i] != move) {
                break
            }
            if (i == Constants.BOARD_SIZE - 1) {
                return true
            }
        }
        return false
    }

    private fun checkDiagonal(
        matrix: Matrix,
        move: Int,
    ): Boolean {
        for (i in 0 until Constants.BOARD_SIZE) {
            if (matrix[i][i] != move) {
                break
            }
            if (i == Constants.BOARD_SIZE - 1) {
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
        for (i in 0 until Constants.BOARD_SIZE) {
            if (board[i][y] != move) {
                break
            }
            if (i == Constants.BOARD_SIZE - 1) {
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
        for (i in 0 until Constants.BOARD_SIZE) {
            if (matrix[x][i] != move) {
                break
            }
            if (i == Constants.BOARD_SIZE - 1) {
                return true
            }
        }
        return false
    }

}