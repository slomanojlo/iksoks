package rs.sloman.iksoks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val boardSize = 3

    private val _xPlaying: MutableLiveData<Boolean> = MutableLiveData(true)
    val xPlaying = _xPlaying.asLiveData()

    private var matrix: MutableLiveData<Array<Array<Int>>> = MutableLiveData(emptyArray())

    private val _list: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())
    val list = _list.asLiveData()

    private val _gameOver: MutableLiveData<Boolean> = MutableLiveData(false)
    val gameOver = _gameOver.asLiveData()

    init {
        setupBoard()
    }


    fun play(position: Int) {
        val x = position / boardSize
        val y = position % boardSize

        matrix.value?.get(x)?.set(y, if (_xPlaying.value == true) Square.X.value else Square.O.value)
        _list.value = matrix.value?.flatten()?.toMutableList()
        _gameOver.value =
            isWinningMove(x, y, if (_xPlaying.value == true) Square.X.value else Square.O.value)

        if (_gameOver.value == false) {
            _xPlaying.value?.let { xPlaying ->
                _xPlaying.value = !xPlaying
            }
        }
    }

    fun setupBoard() {
        _gameOver.value = false
        _xPlaying.value = true
        matrix.value = Array(boardSize) { Array(boardSize) { Square.EMPTY.value } }
        _list.value = mutableListOf<Int>().apply {
            repeat(boardSize * boardSize) {
                this.add(Square.EMPTY.value)
            }
        }
    }

    private fun isWinningMove(x: Int, y: Int, move: Int): Boolean {

        val board = matrix.value

        // check the row
        for (i in 0 until boardSize) {
            if (board?.get(x)?.get(i) != move) {
                break
            }
            if (i == boardSize - 1) {
                return true
            }
        }

        // Check the column
        for (i in 0 until boardSize) {
            if (board?.get(i)?.get(y) != move) {
                break
            }
            if (i == boardSize - 1) {
                return true
            }
        }

        // Check the diagonal
        if (x == y) {
            for (i in 0 until boardSize) {
                if (board?.get(i)?.get(i) != move) {
                    break
                }
                if (i == boardSize - 1) {
                    return true
                }
            }
        }

        // Check anti-diagonal
        if (x + y == boardSize - 1) {
            for (i in 0 until boardSize) {
                if (board?.get(i)?.get((boardSize - 1) - i) != move) {
                    break
                }
                if (i == boardSize - 1) {
                    return true
                }
            }
        }

        return false
    }

}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
