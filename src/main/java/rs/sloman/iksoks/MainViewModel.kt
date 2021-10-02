package rs.sloman.iksoks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _list: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())
    val list = _list.asLiveData()

    private val _gameOver: MutableLiveData<Boolean> = MutableLiveData(false)
    val gameOver = _gameOver.asLiveData()


    private val _board = MutableLiveData(mutableListOf<MutableList<Int>>())
    val board = _board.asLiveData()
    private val boardSize = 3

    private var xPlaying = true

    private var m: MutableLiveData<Array<Array<Int>>> =
        MutableLiveData(Array(boardSize) { Array(boardSize) { 0 } })

    init {
        setupBoard()
    }


    private var isOdd = true

    fun play(int: Int) {
        val x = int / boardSize
        val y = int % boardSize

        m.value?.get(x)?.set(y, if(xPlaying) 1 else 2)
        _list.value = m.value?.flatten()?.toMutableList()
        _gameOver.value = isWinningMove(x, y ,if(xPlaying) 1 else 2)
        xPlaying = !xPlaying
    }

    fun setupBoard(){
        _gameOver.value = false
        xPlaying = true
        m.value = Array(boardSize) { Array(boardSize) { 0 } }
        _list.value = mutableListOf<Int>().apply{
            repeat(boardSize * boardSize){
                this.add(0)
            }
        }
    }

    private fun isWinningMove(x: Int, y: Int, move: Int): Boolean {

        val board = m.value

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
