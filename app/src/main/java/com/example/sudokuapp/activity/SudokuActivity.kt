package com.example.sudokuapp.activity

import android.os.Bundle
import android.os.PersistableBundle
import com.example.sudokuapp.R
import com.example.sudokuapp.fragment.SudokuGridFragment
import com.example.sudokuapp.log.SudokuAppLog

/**
 * SudokuActivity (for sudoku screen).
 */
class SudokuActivity : BaseActivity() {

    /** TAG */
    private val TAG = "SudokuActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.sudoku_layout)

        // in the first creating.
        if (savedInstanceState == null) {
            // set Fragment
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.sudoku_grid_fragment, SudokuGridFragment())
            transaction.commit()
        }

        SudokuAppLog.exit(TAG, "onCreate")
    }

    override fun onResume() {
        SudokuAppLog.enter(TAG, "onResume")
        super.onResume()
        SudokuAppLog.exit(TAG, "onResume")
    }

}