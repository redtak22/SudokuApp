package com.example.sudokuapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.sudokuapp.R
import com.example.sudokuapp.fragment.MainFragment
import com.example.sudokuapp.log.SudokuAppLog

/**
 * MainActivity (for other sudoku screen).
 */
class MainActivity : BaseActivity() {

    /** TAG */
    private val TAG = "MainActivity"

    /** screen kind */
    val SUDOKU_EASY = 0
    val SUDOKU_NORMAL = 1
    val SUDOKU_HARD = 2

    /** easy button */
    private lateinit var mEasyButton : Button

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

        finish()

        var intent : Intent? = null
        when (type) {
            // for easy sudoku screen
            SUDOKU_EASY -> {
                SudokuAppLog.debug(TAG, "changeActivity: screen type is sudoku easy")
                intent = Intent(this, SudokuActivity::class.java)
            }
            // other
            else -> {
                SudokuAppLog.warning(TAG, "changeActivity: change kind is incorrect")
            }
        }
        startActivity(intent)

        SudokuAppLog.exit(TAG, "changeActivity")
    }

}