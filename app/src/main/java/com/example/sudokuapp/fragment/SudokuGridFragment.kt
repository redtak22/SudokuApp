package com.example.sudokuapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sudokuapp.R
import com.example.sudokuapp.activity.SudokuActivity
import com.example.sudokuapp.const.SudokuProblem.Companion.EASY_1_ANSWER
import com.example.sudokuapp.const.SudokuProblem.Companion.EASY_1_PROBLEM
import com.example.sudokuapp.log.SudokuAppLog
import com.example.sudokuapp.view.SudokuGridButton
import kotlinx.android.synthetic.main.sudoku_fragment.*
import kotlinx.android.synthetic.main.sudoku_grid_fragment.*

/**
 * Sudoku fragment.
 */
class SudokuGridFragment : BaseFragment(), View.OnClickListener {

    /** TAG */
    private val TAG = "SudokuGridFragment"

    /** grid max */
    private val GRID_MAX = 10
    /** grid first */
    private val GRID_FIRST = 1
    /** grid last */
    private val GRID_LAST = 9

    /** minimum grid max */
    private val MINIMUM_GRID_MAX = 3
    /** minimum grid first */
    private val MINIMUM_GRID_FIRST = 1

    /** out of focusing position */
    private val NOT_FOCUSING_POSITION = 0

    /** activity */
    private lateinit var mActivity: SudokuActivity

    /** grid button array */
    private var mGridButtonArray = Array(GRID_MAX) {arrayOfNulls<SudokuGridButton>(GRID_MAX)}

    /** X position in focus cell */
    var mFocusCellX = 0
    /** Y position in focus cell */
    var mFocusCellY = 0

    /** focusing cell is hint or answer */
    private var mIsFocusCellHint = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as SudokuActivity
        mActivity.mSudokuGridFragment = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SudokuAppLog.enter(TAG, "onCreateView")
        SudokuAppLog.exit(TAG, "onCreateView")
        return inflater.inflate(
            R.layout.sudoku_grid_fragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        prepareSudokuGridButton()

        SudokuAppLog.exit(TAG, "onViewCreated")
    }

    override fun onClick(view: View?) {
        SudokuAppLog.enter(TAG, "onClick")

        if (view != null) {
            // identify tapped button
            for (positionX in GRID_FIRST..GRID_LAST) {
                for (positionY in GRID_FIRST..GRID_LAST) {
                    if (mGridButtonArray[positionX][positionY] != null) {
                        if (view.id == mGridButtonArray[positionX][positionY]?.id) {
                            // if can identify tapped button, update focus Cell
                            updateFocusCellInformation(positionX, positionY)
                            updateFocusCellLayout()
                            SudokuAppLog.debug(TAG, "onClick: $positionX-$positionY tapped.")
                        }
                    }
                }
            }
        }
        SudokuAppLog.exit(TAG, "onClick")
    }

    /**
     * prepare sudoku grid button.
     */
    private fun prepareSudokuGridButton() {
        SudokuAppLog.enter(TAG, "setSudokuGridButton")

        // prepare sudoku grid button in below things
        // ・ put grid button instance in the grid button array
        // ・ set button listener
        // ・ set hint number to button instance

        for (positionX in GRID_FIRST..GRID_LAST) {
            for (positionY in GRID_FIRST..GRID_LAST) {
                // put grid button instance in the grid button array
                val tag = "$positionX-$positionY"
                mGridButtonArray[positionX][positionY] =
                    sudoku_grid_table_layout.findViewWithTag<SudokuGridButton>(tag)

                if (mGridButtonArray[positionX][positionY] != null) {
                    SudokuAppLog.debug(TAG, "prepareSudokuGridButton: $tag")
                    // set listener
                    mGridButtonArray[positionX][positionY]?.setOnClickListener(this)
                    // set and show hint number
                    mGridButtonArray[positionX][positionY]?.mHintNumber =
                        EASY_1_PROBLEM[positionY][positionX]
                    mGridButtonArray[positionX][positionY]?.text =
                        mGridButtonArray[positionX][positionY]?.mHintNumber
                    // set answer number
                    mGridButtonArray[positionX][positionY]?.mAnswerNumber =
                        EASY_1_ANSWER[positionY][positionX]
                }
            }
        }

        SudokuAppLog.exit(TAG, "setSudokuGridButton")
    }

    /**
     * update focus cell information.
     * @param focusCellX focus cell new position in the direction of the X axis
     * @param focusCellY focus cell new position in the direction of the Y axis
     */
    private fun updateFocusCellInformation(focusCellX: Int, focusCellY: Int) {
        SudokuAppLog.enter(TAG, "updateFocusCellInformation")

        // update focus position
        mFocusCellX = focusCellX
        mFocusCellY = focusCellY
        // update focusing cell is hint or answer
        mIsFocusCellHint = (mGridButtonArray[focusCellX][focusCellY]?.mHintNumber
                != mActivity.getText(R.string.sudoku_empty_cell))

        SudokuAppLog.exit(TAG, "updateFocusCellInformation")
    }

    /**
     * update focus cell and change color.
     */
    private fun updateFocusCellLayout() {
        SudokuAppLog.enter(TAG, "updateFocusCellLayout")
        SudokuAppLog.debug(TAG, "updateFocusCellLayout: mFocusCellX = $mFocusCellX, mFocusCellY = $mFocusCellY")

        // if not focusing cell, return the method
        if (mFocusCellX == NOT_FOCUSING_POSITION && mFocusCellY == NOT_FOCUSING_POSITION) {
            SudokuAppLog.exit(TAG, "updateFocusCellLayout: not focusing cell")
            return
        }

        // update all cells color to default
        setAllCellsLayout(null, R.color.sudoku_grid_button_background)

        // update X axis and Y axis cells of new focus cell color
        for (position in GRID_FIRST..GRID_LAST) {
            // update cell color in the direction of the X axis
            if (mGridButtonArray[position][mFocusCellY] != null) {
                mGridButtonArray[position][mFocusCellY]?.setBackgroundColor(
                    mActivity.getColor(R.color.sudoku_grid_focus_cell_matrix_background))
            }
            // update cell color in the direction of the Y axis
            if (mGridButtonArray[mFocusCellX][position] != null) {
                mGridButtonArray[mFocusCellX][position]?.setBackgroundColor(
                    mActivity.getColor(R.color.sudoku_grid_focus_cell_matrix_background))
            }
        }

        // update group cells of new focus cell color
        val minimumGroupWeightX = (mFocusCellX - 1) / MINIMUM_GRID_MAX
        val minimumGroupWeightY = (mFocusCellY - 1) / MINIMUM_GRID_MAX
        val minimumGroupPositionX =
            if (mFocusCellX % MINIMUM_GRID_MAX != 0) mFocusCellX % MINIMUM_GRID_MAX else MINIMUM_GRID_MAX
        val minimumGroupPositionY =
            if (mFocusCellY % MINIMUM_GRID_MAX != 0) mFocusCellY % MINIMUM_GRID_MAX else MINIMUM_GRID_MAX
        var minimumGroupStartPositionX = 0
        var minimumGroupStartPositionY = 0

        // calculate group start position
        when (minimumGroupPositionX) {
            MINIMUM_GRID_FIRST -> {
                when (minimumGroupPositionY) {
                    // ■□□
                    // □□□
                    // □□□
                    MINIMUM_GRID_FIRST -> {
                        // group start position and group position are same
                        minimumGroupStartPositionX = minimumGroupPositionX
                        minimumGroupStartPositionY = minimumGroupPositionY
                    }
                    // □□□
                    // ■□□
                    // □□□
                    MINIMUM_GRID_FIRST + 1 -> {
                        minimumGroupStartPositionX = minimumGroupPositionX
                        minimumGroupStartPositionY = minimumGroupPositionY - 1
                    }
                    // □□□
                    // □□□
                    // ■□□
                    MINIMUM_GRID_FIRST + 2 -> {
                        minimumGroupStartPositionX = minimumGroupPositionX
                        minimumGroupStartPositionY = minimumGroupPositionY - 2
                    }
                    else -> {
                        SudokuAppLog.warning(TAG,
                            "updateFocusCellLayout: minimumGroupPositionY is invalid -> $minimumGroupPositionY")
                    }
                }
            }
            MINIMUM_GRID_FIRST + 1 -> {
                when (minimumGroupPositionY) {
                    // □■□
                    // □□□
                    // □□□
                    MINIMUM_GRID_FIRST -> {
                        // group start position and group position are same
                        minimumGroupStartPositionX = minimumGroupPositionX - 1
                        minimumGroupStartPositionY = minimumGroupPositionY
                    }
                    // □□□
                    // □■□
                    // □□□
                    MINIMUM_GRID_FIRST + 1 -> {
                        minimumGroupStartPositionX = minimumGroupPositionX - 1
                        minimumGroupStartPositionY = minimumGroupPositionY - 1
                    }
                    // □□□
                    // □□□
                    // □■□
                    MINIMUM_GRID_FIRST + 2 -> {
                        minimumGroupStartPositionX = minimumGroupPositionX - 1
                        minimumGroupStartPositionY = minimumGroupPositionY - 2
                    }
                    else -> {
                        SudokuAppLog.warning(TAG,
                            "updateFocusCellLayout: minimumGroupPositionY is invalid -> $minimumGroupPositionY")
                    }
                }
            }
            MINIMUM_GRID_FIRST + 2 -> {
                when (minimumGroupPositionY) {
                    // □□■
                    // □□□
                    // □□□
                    MINIMUM_GRID_FIRST -> {
                        // group start position and group position are same
                        minimumGroupStartPositionX = minimumGroupPositionX - 2
                        minimumGroupStartPositionY = minimumGroupPositionY
                    }
                    // □□□
                    // □□■
                    // □□□
                    MINIMUM_GRID_FIRST + 1 -> {
                        minimumGroupStartPositionX = minimumGroupPositionX - 2
                        minimumGroupStartPositionY = minimumGroupPositionY - 1
                    }
                    // □□□
                    // □□□
                    // □□■
                    MINIMUM_GRID_FIRST + 2 -> {
                        minimumGroupStartPositionX = minimumGroupPositionX - 2
                        minimumGroupStartPositionY = minimumGroupPositionY - 2
                    }
                    else -> {
                        SudokuAppLog.warning(TAG,
                            "updateFocusCellLayout: minimumGroupPositionY is invalid -> $minimumGroupPositionY")
                    }
                }
            }
            else -> {
                SudokuAppLog.warning(TAG,
                    "updateFocusCellLayout: minimumGroupPositionX is invalid -> $minimumGroupPositionX")
            }
        }

        SudokuAppLog.debug(TAG, "updateFocusCellLayout: minimumGroupWeightX = $minimumGroupWeightX, " +
                "minimumGroupWeightY = $minimumGroupWeightY, " +
                "minimumGroupPositionX = $minimumGroupPositionX, " +
                "minimumGroupPositionY = $minimumGroupPositionY, " +
                "minimumGroupStartPositionX = $minimumGroupStartPositionX, " +
                "minimumGroupStartPositionY = $minimumGroupStartPositionY")

        // update minimum group cells
        for (floatingMinimumPositionX in minimumGroupStartPositionX + (minimumGroupWeightX * MINIMUM_GRID_MAX)..
                minimumGroupStartPositionX + (minimumGroupWeightX * MINIMUM_GRID_MAX) + (MINIMUM_GRID_MAX - 1)) {
            for (floatingMinimumPositionY in minimumGroupStartPositionY + (minimumGroupWeightY * MINIMUM_GRID_MAX)..
                    minimumGroupStartPositionY + (minimumGroupWeightY * MINIMUM_GRID_MAX) + (MINIMUM_GRID_MAX - 1)) {
                SudokuAppLog.debug(TAG, "updateFocusCellLayout: floatingMinimumPositionX = $floatingMinimumPositionX," +
                        " floatingMinimumPositionY = $floatingMinimumPositionY")
                if (mGridButtonArray[floatingMinimumPositionX][floatingMinimumPositionY] != null) {
                    mGridButtonArray[floatingMinimumPositionX][floatingMinimumPositionY]?.setBackgroundColor(
                        mActivity.getColor(R.color.sudoku_grid_focus_cell_matrix_background))
                }
            }
        }

        // update same number cell
        for (positionX in GRID_FIRST..GRID_LAST) {
            for (positionY in GRID_FIRST..GRID_LAST) {
                if (mGridButtonArray[positionX][positionY] != null) {
                    // if same number cell and not empty cell
                    if ((mGridButtonArray[positionX][positionY]?.text
                        == mGridButtonArray[mFocusCellX][mFocusCellY]?.text)
                        && (mGridButtonArray[mFocusCellX][mFocusCellY]?.text
                        != mActivity.getString(R.string.sudoku_empty_cell))) {
                        mGridButtonArray[positionX][positionY]?.setBackgroundColor(
                            mActivity.getColor(R.color.sudoku_grid_same_number_cell_background))
                    }
                }
            }
        }

        // focus cell
        mGridButtonArray[mFocusCellX][mFocusCellY]?.setBackgroundColor(
            mActivity.getColor(R.color.sudoku_grid_focus_cell_background))

        SudokuAppLog.exit(TAG, "updateFocusCellLayout")
    }

    /**
     * update focus cell nummber.
     * @param number tapped number
     */
    fun updateCellNumber(number: Int) {
        SudokuAppLog.enter(TAG, "updateCellNumber")

        // if not focusing cell, return the method
        if (mFocusCellX == 0 && mFocusCellY == 0) {
            SudokuAppLog.exit(TAG, "updateCellNumber : not focusing cell")
            return
        }

        // if focusing cell is for hint, show toast and return the method
        if (mIsFocusCellHint) {
            mActivity.showToastMessage(R.string.sudoku_hint_cell_update_alert_toast_message,
                Toast.LENGTH_SHORT)
            SudokuAppLog.exit(TAG, "updateCellNumber : is hint cell")
            return
        }

        // update focus cell number
        if (mGridButtonArray[mFocusCellX][mFocusCellY] != null) {
            mGridButtonArray[mFocusCellX][mFocusCellY]?.setTextColor(
                mActivity.getColor(R.color.sudoku_grid_answer_cell_text_color))
            mGridButtonArray[mFocusCellX][mFocusCellY]?.text = number.toString()
        }

        // check input number is correct
        if (number.toString() != mGridButtonArray[mFocusCellX][mFocusCellY]?.mAnswerNumber) {
            // if is incorrect number, set text color
            mGridButtonArray[mFocusCellX][mFocusCellY]?.setTextColor(
                mActivity.getColor(R.color.sudoku_grid_answer_cell_incorrect_number_text_color))
            // show toast message
            mActivity.showToastMessage(R.string.sudoku_incorrect_number_toast_message,
                Toast.LENGTH_SHORT)

            // increase miss count
            mActivity.mMissCount++
            // check miss count is over
            if (mActivity.mMissCount > 3) {
                mActivity.finishGame(mActivity.FINISH_TYPE_MISS_COUNT_OVER)
                SudokuAppLog.exit(TAG, "updateCellNumber : miss count is over")
                return
            }
            // set miss count text
            mActivity.updateMissCountMessage()

            SudokuAppLog.exit(TAG, "updateCellNumber : incorrect number")
            return
        }

        // check other cells are answered
        if (isAllCellsAnswer()) {
            mActivity.finishGame(mActivity.FINISH_TYPE_GAME_CLEAR)
        }

        SudokuAppLog.exit(TAG, "updateCellNumber")
    }

    /**
     * erase focusing cell number.
     */
    fun eraseFocusCellNumber() {
        SudokuAppLog.enter(TAG, "eraseFocusCellNumber")

        // if focusing cell is for hint, show toast and return the method
        if (mIsFocusCellHint) {
            mActivity.showToastMessage(R.string.sudoku_hint_cell_erase_alert_toast_message, Toast.LENGTH_SHORT)
            SudokuAppLog.exit(TAG, "eraseFocusCellNumber : is hint cell")
            return
        }

        // erase focusing cell number
        mGridButtonArray[mFocusCellX][mFocusCellY]?.text =
            mActivity.getString(R.string.sudoku_empty_cell)

        // update cell background color
        updateFocusCellLayout()

        SudokuAppLog.exit(TAG, "eraseFocusCellNumber")
    }

    /**
     * check all cells are answered.
     * @return true: all cells are answered (problem clear) / false: not
     */
    private fun isAllCellsAnswer() : Boolean {
        SudokuAppLog.enter(TAG, "isAllCellsAnswer")

        for (positionX in GRID_FIRST..GRID_LAST) {
            for (positionY in GRID_FIRST..GRID_LAST) {
                if (mGridButtonArray[positionX][positionY] != null) {
                    // if not answered cell is existing, return false
                    if (mGridButtonArray[positionX][positionY]?.text ==
                            mActivity.getString(R.string.sudoku_empty_cell)) {
                        SudokuAppLog.exit(TAG, "isAllCellsAnswer: false")
                        return false
                    }
                }
            }
        }

        SudokuAppLog.exit(TAG, "isAllCellsAnswer: true")
        return true
    }

    /**
     * set all cells text and background color.
     * @param textColor text color (if not need change, set null)
     * @param backgroundColor background color (if not need change, set null)
     */
    fun setAllCellsLayout(textColor: Int?, backgroundColor: Int?) {
        SudokuAppLog.enter(TAG, "setAllCellsLayout")

        // set text color
        if (textColor != null) {
            for (positionX in GRID_FIRST..GRID_LAST) {
                for (positionY in GRID_FIRST..GRID_LAST) {
                    // update all cells text color
                    if (mGridButtonArray[positionX][positionY] != null) {
                        mGridButtonArray[positionX][positionY]?.setTextColor(
                            mActivity.getColor(textColor))
                    }
                }
            }
        }

        // set background color
        if (backgroundColor != null) {
            for (positionX in GRID_FIRST..GRID_LAST) {
                for (positionY in GRID_FIRST..GRID_LAST) {
                    // update all cells text color
                    if (mGridButtonArray[positionX][positionY] != null) {
                        mGridButtonArray[positionX][positionY]?.setBackgroundColor(
                            mActivity.getColor(backgroundColor))
                    }
                }
            }
        }

        SudokuAppLog.exit(TAG, "setAllCellsLayout")
    }

}