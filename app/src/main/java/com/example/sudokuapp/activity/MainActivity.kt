package com.example.sudokuapp.activity

import android.content.Intent
import android.os.Bundle
import com.example.sudokuapp.R
import com.example.sudokuapp.fragment.MainFragment
import com.example.sudokuapp.log.SudokuAppLog

/**
 * MainActivity (for other sudoku screen).
 */
class MainActivity : BaseActivity() {

    /** TAG */
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        // in the first creating.
        if (savedInstanceState == null) {
            SudokuAppLog.debug(TAG, "onCreate: set fragment.")
            // set Fragment
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.main_fragment, MainFragment())
            transaction.commit()
        }

        SudokuAppLog.exit(TAG, "onCreate")
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * change Activity.
     * @param type screen kind
     */
    fun changeActivity(type: Int) {
        SudokuAppLog.enter(TAG, "changeActivity")

        // set difficulty type
        when (type) {
            // for easy sudoku screen
            SUDOKU_EASY -> {
                SudokuAppLog.debug(TAG, "changeActivity: screen type is sudoku easy")
                mDifficultyStatus = SUDOKU_EASY
            }
            // for easy sudoku screen
            SUDOKU_NORMAL -> {
                SudokuAppLog.debug(TAG, "changeActivity: screen type is sudoku normal")
                mDifficultyStatus = SUDOKU_NORMAL
            }
            // for hard sudoku screen
            SUDOKU_NORMAL -> {
                SudokuAppLog.debug(TAG, "changeActivity: screen type is sudoku hard")
                mDifficultyStatus = SUDOKU_HARD
            }
            // other
            else -> {
                SudokuAppLog.warning(TAG, "changeActivity: change kind is incorrect")
            }
        }
        val intent = Intent(this, SudokuActivity::class.java)
        startActivity(intent)

        SudokuAppLog.exit(TAG, "changeActivity")
    }

}