package rs.sloman.iksoks


import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat


class IksOksTest {

    lateinit var iksOks: IksOks

    @Before
    fun setUp() {
        assertThat(Constants.BOARD_SIZE).isEqualTo(3)
        iksOks = IksOks()
        iksOks.setupMatrix()
    }

    @Test
    fun shouldInitializeMatrixOnSetup(){
        val expectedArray = Array(Constants.BOARD_SIZE) { Array(Constants.BOARD_SIZE) { Square.EMPTY.value } }

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
    fun shouldProperlyInitializeGameWon(){
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