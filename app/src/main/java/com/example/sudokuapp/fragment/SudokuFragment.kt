package com.example.sudokuapp.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.sudokuapp.R
import com.example.sudokuapp.activity.SudokuActivity
import com.example.sudokuapp.log.SudokuAppLog
import kotlinx.android.synthetic.main.sudoku_fragment.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * sudoku fragment.
 */
class SudokuFragment : BaseFragment(), View.OnClickListener {

    /** TAG */
    private val TAG = "SudokuFragment"

    /** timer period */
    val TIMER_PERIOD: Long = 1000

    /** game status */
    val GAME_STATUS_PLAYING = 0
    val GAME_STATUS_PAUSE = 1
    val GAME_STATUS_FINISH = 2

    /** number button array max */
    private val NUMBER_BUTTON_MAX = 10
    /** grid first */
    private val NUMBER_BUTTON_FIRST = 1
    /** grid last */
    private val NUMBER_BUTTON_LAST = 9

    /** Activity */
    private lateinit var mActivity: SudokuActivity
    /** Sudoku grid fragment */
    private lateinit var mSudokuGridFragment: SudokuGridFragment

    /** grid button array */
    private var mNumberButtonArray = arrayOfNulls<Button>(NUMBER_BUTTON_MAX)

    /** miss count */
    var mMissCount = 0

    /** timer count */
    private var mTimerCount = 0
    /** timer date format */
    private lateinit var mTimerDateFormat: SimpleDateFormat
    /** handler */
    private lateinit var mHandler: Handler
    /** Runnable */
    private lateinit var mRunnable: Runnable

    /** game status */
    var mGameStatus = GAME_STATUS_PLAYING

    override fun onCreate(savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        // set instance
        mActivity = activity as SudokuActivity
        mActivity.mSudokuFragment = this

        // set timer
        mTimerDateFormat = SimpleDateFormat(
            mActivity.getString(R.string.sudoku_timer_date_format),
            Locale.JAPAN
        )
        mHandler = Handler(Looper.getMainLooper())
        mRunnable = Runnable {
            kotlin.run {
                mTimerCount++
                playing_time.text = mTimerDateFormat.format(mTimerCount*TIMER_PERIOD)
                mHandler.postDelayed(mRunnable, TIMER_PERIOD)
            }
        }

        SudokuAppLog.exit(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SudokuAppLog.enter(TAG, "onCreateView")
        SudokuAppLog.exit(TAG, "onCreateView")
        return inflater.inflate(
            R.layout.sudoku_fragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        // set layout
        var difficultyTextId: Int? = null
        var backgroundColorId: Int? = null
        when (mActivity.mDifficultyKind) {
            // easy
            mActivity.SUDOKU_EASY -> {
                difficultyTextId = R.string.sudoku_difficulty_easy
                backgroundColorId = R.color.sudoku_background_easy
            }
            // normal
            mActivity.SUDOKU_NORMAL -> {
                difficultyTextId = R.string.sudoku_difficulty_normal
                backgroundColorId = R.color.sudoku_background_normal
            }
            // hard
            mActivity.SUDOKU_HARD -> {
                difficultyTextId = R.string.sudoku_difficulty_hard
                backgroundColorId = R.color.sudoku_background_hard
            }
            mActivity.SUDOKU_DEBUG -> {
                difficultyTextId = R.string.sudoku_difficulty_easy
                backgroundColorId = R.color.sudoku_background_easy
            }
        }
        stage_no.text = mActivity.getString(difficultyTextId!!)
        sudoku_fragment_linear_layout.setBackgroundColor(mActivity.getColor(backgroundColorId!!))

        // set miss count text
        updateMissCountMessage()

        // prepare buttons
        prepareButtons()

        SudokuAppLog.exit(TAG, "onViewCreated")
    }

    override fun onStart() {
        SudokuAppLog.enter(TAG, "onStart")
        super.onStart()

        // get SudokuGridFragment
        mSudokuGridFragment = mActivity.mSudokuGridFragment

        SudokuAppLog.exit(TAG, "onStart")
    }

    override fun onResume() {
        SudokuAppLog.enter(TAG, "onResume")
        super.onResume()

        // timer start
        handleTimer(true, false, false)

        SudokuAppLog.exit(TAG, "onResume")
    }

    override fun onPause() {
        SudokuAppLog.enter(TAG, "onPause")
        super.onPause()

        // timer stop
        handleTimer(false, false, false)

        SudokuAppLog.exit(TAG, "onPause")
    }

    override fun onClick(view: View?) {
        SudokuAppLog.enter(TAG, "onClick")

        // if during playing game, finish the methos
        if (mGameStatus != GAME_STATUS_PLAYING) {
            SudokuAppLog.exit(TAG, "onClick: game is finished")
            return
        }

        if (view != null) {
            // erase button
            if (view.id == eraser_button.id) {
                mSudokuGridFragment.eraseFocusCellNumber()
                SudokuAppLog.exit(TAG, "onClick: eraser button tapped")
                return
            }

            // back button
            if (view.id == back_button.id) {
                mActivity.showToastMessage(R.string.sudoku_back_button_under_construction_toast_message,
                    Toast.LENGTH_SHORT)
                SudokuAppLog.exit(TAG, "onClick: back button tapped")
                return
            }

            // identify tapped button
            for (number in NUMBER_BUTTON_FIRST..NUMBER_BUTTON_LAST) {
                if (mNumberButtonArray[number] != null) {
                    if (view.id == mNumberButtonArray[number]?.id) {
                        // if can identify tapped button, update focus Cell
                        mSudokuGridFragment.updateCellNumber(number)
                        SudokuAppLog.debug(TAG, "onClick: $number tapped.")
                    }
                }
            }
        }

        SudokuAppLog.exit(TAG, "onClick")
    }

    /**
     * prepare button.
     */
    private fun prepareButtons() {
        SudokuAppLog.enter(TAG, "prepareNumberButton")

        // eraser button
        eraser_button.setOnClickListener(this)

        // back button
        back_button.setOnClickListener(this)

        // number buttons
        for (number in NUMBER_BUTTON_FIRST..NUMBER_BUTTON_LAST) {
            // put number button instance in the number button array
            val tag = "number-$number"
            mNumberButtonArray[number] =
                number_button_linear_layout.findViewWithTag<Button>(tag)
            // set listener
            if (mNumberButtonArray[number] != null) {
                SudokuAppLog.debug(TAG, "prepareButtons: set number button listener $tag")
                mNumberButtonArray[number]?.setOnClickListener(this)
            }
        }

        SudokuAppLog.exit(TAG, "prepareNumberButton")
    }

    /**
     * update miss count text.
     */
    fun updateMissCountMessage() {
        SudokuAppLog.enter(TAG, "updateMissCountMessage")

        val missCountText = getString(R.string.sudoku_miss_count_text1) +
                mMissCount.toString() +
                getString(R.string.sudoku_miss_count_text2)
        miss_count.text = missCountText

        SudokuAppLog.exit(TAG, "updateMissCountMessage")
    }

    /**
     * handle timer status.
     * @param isStart true: start timer / false: stop timer
     * @param isInitialize true: need initialize timer / false: not
     * @param isGameClear true: cleared the game / false: not
     */
    fun handleTimer(isStart: Boolean, isInitialize: Boolean, isGameClear: Boolean) {
        SudokuAppLog.enter(TAG, "handleTimer")

        if (isInitialize) {
            // initialize timer
            mTimerCount = 0
            playing_time.text = mTimerDateFormat.format(mTimerCount*TIMER_PERIOD)
            playing_time.setTextColor(mActivity.getColor(R.color.sudoku_stage_no_text_color))
        }

        if (isStart) {
            // start timer
            mHandler.post(mRunnable)
        } else {
            // stop timer
            mHandler.removeCallbacks(mRunnable)
            if (isGameClear) {
                // change text color
                playing_time.setTextColor(mActivity.getColor(R.color.sudoku_timer_game_clear_text_color))
            }
        }

        SudokuAppLog.exit(TAG, "handleTimer")
    }
}