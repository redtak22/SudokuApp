package com.example.sudokuapp.activity

import android.os.Bundle
import android.widget.Toast
import com.example.sudokuapp.R
import com.example.sudokuapp.fragment.SudokuFragment
import com.example.sudokuapp.fragment.SudokuGridFragment
import com.example.sudokuapp.log.SudokuAppLog

/**
 * SudokuActivity (for sudoku screen).
 */
class SudokuActivity : BaseActivity() {

    /** TAG */
    private val TAG = "SudokuActivity"

    /** sudoku fragment */
    lateinit var mSudokuFragment: SudokuFragment
    /** sudoku grid fragment */
    lateinit var mSudokuGridFragment: SudokuGridFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.sudoku_layout)

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
     * show toast message.
     * @param messageId
     */
    fun showToastMessage(messageId: Int) {
        SudokuAppLog.enter(TAG, "showToast:")
        Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show()
        SudokuAppLog.exit(TAG, "showToast:")
    }

}