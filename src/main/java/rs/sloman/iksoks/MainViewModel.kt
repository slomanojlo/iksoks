package rs.sloman.iksoks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rs.sloman.iksoks.Constants.Companion.BOARD_SIZE
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _xPlaying: MutableLiveData<Boolean> = MutableLiveData(true)
    private val matrix: MutableLiveData<Matrix> = MutableLiveData(emptyArray())

    private val _list: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())
    val list = _list.asLiveData()

    private val _draw: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _gameWon: MutableLiveData<Boolean> = MutableLiveData(false)
    val gameWon = _gameWon.asLiveData()

    private val _message: MutableLiveData<String> = MutableLiveData(null)
    val message = _message.asLiveData()

    init {
        setupMatrix()
    }


    fun play(position: Int) {
        val x = position / BOARD_SIZE
        val y = position % BOARD_SIZE

        matrix.value?.let { matrix ->
            matrix[x][y] = if (_xPlaying.value == true) Square.X.value else Square.O.value
            _list.value = matrix.flatten().toMutableList()
            _gameWon.value =
                isWinningMove(
                    matrix, x, y, if (_xPlaying.value == true) Square.X.value else Square.O.value
                )

        }

        if (_gameWon.value == true) {
            _message.value = "${
                if (_xPlaying.value == true)
                    Square.X.name else Square.O.name
            } WON!"
            return
        }

        if (_list.value?.filter { it == Square.EMPTY.value }?.size == 0) {
            _draw.value = true
            _message.value = "DRAW!"
        }

        _xPlaying.value?.let { xPlaying ->
            _xPlaying.value = !xPlaying

        }
    }

    fun setupMatrix() {
        _draw.value = false
        _gameWon.value = false
        _xPlaying.value = true
        _message.value = null
        matrix.value = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Square.EMPTY.value } }
        _list.value = mutableListOf<Int>().apply {
            repeat(BOARD_SIZE * BOARD_SIZE) {
                this.add(Square.EMPTY.value)
            }
        }
    }

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
        move: Int
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
        move: Int
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
        move: Int
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
        move: Int
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

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
