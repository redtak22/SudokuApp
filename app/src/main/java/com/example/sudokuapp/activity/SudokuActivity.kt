package com.example.sudokuapp.activity

import android.os.Bundle
import android.widget.Toast
import com.example.sudokuapp.R
import com.example.sudokuapp.fragment.SudokuFragment
import com.example.sudokuapp.fragment.SudokuGridFragment
import com.example.sudokuapp.log.SudokuAppLog
import kotlinx.android.synthetic.main.sudoku_fragment.*

/**
 * SudokuActivity (for sudoku screen).
 */
class SudokuActivity : BaseActivity() {

    /** TAG */
    private val TAG = "SudokuActivity"

    /** finish type */
    val FINISH_TYPE_GAME_CLEAR = 0
    val FINISH_TYPE_MISS_COUNT_OVER = 1
    val FINISH_TYPE_USER_END = 2

    /** sudoku fragment */
    lateinit var mSudokuFragment: SudokuFragment
    /** sudoku grid fragment */
    lateinit var mSudokuGridFragment: SudokuGridFragment

    /** difficulty kind */
    var mDifficultyKind: Int = SUDOKU_EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.sudoku_layout)

        // set difficulty
        mDifficultyKind = intent.getIntExtra(getString(R.string.KEY_DIFFICULTY_KIND), SUDOKU_EASY)

        // in the first creating.
        if (savedInstanceState == null) {
            // set Fragment
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.sudoku_fragment, SudokuFragment())
            transaction.add(R.id.sudoku_grid_fragment, SudokuGridFragment())
            transaction.commit()
        }

        SudokuAppLog.exit(TAG, "onCreate")
    }

    override fun onStart() {
        SudokuAppLog.enter(TAG, "onStart")
        super.onStart()

        SudokuAppLog.exit(TAG, "onStart")
    }

    override fun onResume() {
        SudokuAppLog.enter(TAG, "onResume")
        super.onResume()
        SudokuAppLog.exit(TAG, "onResume")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    /**
     * finish game and update screen.
     * @param finishType game finish type
     */
    fun finishGame(finishType: Int) {
        SudokuAppLog.enter(TAG, "finishGame")

        // set out of focus
        mSudokuGridFragment.mFocusCellX = 0
        mSudokuGridFragment.mFocusCellY = 0

        // update game status
        mSudokuFragment.mGameStatus = mSudokuFragment.GAME_STATUS_FINISH

        when (finishType) {
            // game clear
            FINISH_TYPE_GAME_CLEAR -> {
                // stop timer
                mSudokuFragment.handleTimer(false, false, true)
                // set text and bckground color
                mSudokuGridFragment.setAllCellsLayout(R.color.sudoku_grid_game_clear_text_color,
                    R.color.sudoku_grid_button_background)
                // set sudoku grid view tapped is invalid
                sudoku_fragment_linear_layout.isClickable = false
                // show toast
                showToastMessage(R.string.sudoku_game_clear_toast_message, Toast.LENGTH_LONG)
            }
            // miss count over
            FINISH_TYPE_MISS_COUNT_OVER -> {
                // stop timer
                mSudokuFragment.handleTimer(false, false, false)
                // set text and bckground color
                mSudokuGridFragment.setAllCellsLayout(null,
                    R.color.sudoku_grid_miss_count_over_background_color)
                // set sudoku grid view tapped is invalid
                sudoku_fragment_linear_layout.isClickable = false
                // show toast
                showToastMessage(R.string.sudoku_miss_count_over_toast_message, Toast.LENGTH_LONG)
            }
            // user end
            FINISH_TYPE_USER_END -> {
                // TODO
            }
        }

        SudokuAppLog.exit(TAG, "finishGame")
    }
}