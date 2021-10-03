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
    private var matrix: MutableLiveData<Array<Array<Int>>> = MutableLiveData(emptyArray())

    private val _list: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())
    val list = _list.asLiveData()

    private val _draw: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _gameWon: MutableLiveData<Boolean> = MutableLiveData(false)
    val gameWon = _gameWon.asLiveData()


    private val _message: MutableLiveData<String> = MutableLiveData(null)
    val message = _message.asLiveData()

    init {
        setupBoard()
    }


    fun play(position: Int) {
        val x = position / BOARD_SIZE
        val y = position % BOARD_SIZE

        matrix.value?.get(x)
            ?.set(y, if (_xPlaying.value == true) Square.X.value else Square.O.value)
        _list.value = matrix.value?.flatten()?.toMutableList()
        _gameWon.value =
            isWinningMove(x, y, if (_xPlaying.value == true) Square.X.value else Square.O.value)

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

    fun setupBoard() {
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

    private fun isWinningMove(x: Int, y: Int, move: Int): Boolean {

        val board = matrix.value

        // check the row
        for (i in 0 until BOARD_SIZE) {
            if (board?.get(x)?.get(i) != move) {
                break
            }
            if (i == BOARD_SIZE - 1) {
                return true
            }
        }

        // Check the column
        for (i in 0 until BOARD_SIZE) {
            if (board?.get(i)?.get(y) != move) {
                break
            }
            if (i == BOARD_SIZE - 1) {
                return true
            }
        }

        // Check the diagonal
        if (x == y) {
            for (i in 0 until BOARD_SIZE) {
                if (board?.get(i)?.get(i) != move) {
                    break
                }
                if (i == BOARD_SIZE - 1) {
                    return true
                }
            }
        }

        // Check anti-diagonal
        if (x + y == BOARD_SIZE - 1) {
            for (i in 0 until BOARD_SIZE) {
                if (board?.get(i)?.get((BOARD_SIZE - 1) - i) != move) {
                    break
                }
                if (i == BOARD_SIZE - 1) {
                    return true
                }
            }
        }

        return false
    }

}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
