package rs.sloman.iksoks

import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import rs.sloman.iksoks.Constants.Companion.BOARD_SIZE


class IksOksTest {

    lateinit var iksOks: IksOks

    @Before
    fun setUp() {
        iksOks = IksOks()
        iksOks.setupMatrix()
    }

    @Test
    fun shouldInitialBoardSizeBe3(){
        val boardSize = BOARD_SIZE
        assertThat(boardSize).isEqualTo(BOARD_SIZE)
    }

    @Test
    fun shouldInitializeMatrixOnSetup(){
        val expectedArray = generateSequence {
            generateSequence { 0 }.take(BOARD_SIZE).toMutableList()
        }.take(BOARD_SIZE).toMutableList()

        assertThat(expectedArray).isEqualTo(iksOks.matrix)
    }

    @Test
    fun shouldInitializePlayerOnSetup(){
        val expectedBooleanPlayer = true

        assertThat(expectedBooleanPlayer).isEqualTo(iksOks.xPlaying)
    }

    @Test
    fun shouldInitializeDrawOnSetup(){
        val expectedDraw = false

        assertThat(expectedDraw).isEqualTo(iksOks.draw)
    }

    @Test
    fun shouldProperlyInitializeGameNotWon(){
        iksOks.play(0,0)

        assertThat(iksOks.gameWon).isEqualTo(false)
    }

    @Test
    fun shouldProperlyChangeMatrixOnPlay(){
        iksOks.play(0,0)

        assertThat(iksOks.matrix[0][0]).isNotEqualTo(Square.EMPTY.value)
    }

    @Test
    fun shouldProperlyChangePlayerAfterPlay(){
        iksOks.play(0,0)

        assertThat(iksOks.xPlaying).isEqualTo(false)
    }

    @Test
    fun shouldProperlyChangePlayerAfterTwoPlays(){
        iksOks.play(0,0)
        iksOks.play(1,0)

        assertThat(iksOks.xPlaying).isEqualTo(true)
    }

    @Test
    fun shouldDrawAfterNinePlays(){
        iksOks.play(0,1) //X
        iksOks.play(0,0) //O
        iksOks.play(0,2) //X
        iksOks.play(1,1) //O
        iksOks.play(1,0) //X
        iksOks.play(1,2) //O
        iksOks.play(2,1) //X
        iksOks.play(2,0) //O
        iksOks.play(2,2) //X

        assertThat(iksOks.gameWon).isEqualTo(false)
        assertThat(iksOks.draw).isEqualTo(true)

    }

    @Test
    fun shouldXWinAfterFivePlaysInFirstRow(){
        iksOks.play(0,0) //X
        iksOks.play(1,0) //O
        iksOks.play(0,1) //X
        iksOks.play(1,1) //O
        iksOks.play(0,2) //X

        assertThat(iksOks.gameWon).isEqualTo(true)
        assertThat(iksOks.xPlaying).isEqualTo(true)

    }

    @Test
    fun shouldOWinAfterFivePlaysInFirstRow(){

        iksOks.play(2,2) //O
        iksOks.play(0,0) //X
        iksOks.play(1,0) //X
        iksOks.play(0,1) //O
        iksOks.play(1,1) //X
        iksOks.play(0,2) //O

        assertThat(iksOks.gameWon).isEqualTo(true)
        assertThat(iksOks.xPlaying).isEqualTo(false)

    }

    @Test
    fun shouldWinAfterFivePlaysInFirstColumn(){
        iksOks.play(0,0) //X
        iksOks.play(0,1) //O
        iksOks.play(1,0) //X
        iksOks.play(1,1) //O
        iksOks.play(2,0) //X

        assertThat(iksOks.gameWon).isEqualTo(true)

    }


    @Test
    fun shouldWinAfterFivePlaysInDiagonal(){
        iksOks.play(0,0) //X
        iksOks.play(1,0) //O
        iksOks.play(1,1) //X
        iksOks.play(1,2) //O
        iksOks.play(2,2) //X

        assertThat(iksOks.gameWon).isEqualTo(true)

    }

    @Test
    fun shouldWinAfterFivePlaysInReverseDiagonal(){
        iksOks.play(0,2) //X
        iksOks.play(0,0) //O
        iksOks.play(1,1) //X
        iksOks.play(1,2) //O
        iksOks.play(2,0) //X

        assertThat(iksOks.gameWon).isEqualTo(true)

    }

}