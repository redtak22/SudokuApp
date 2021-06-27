package com.example.sudokuapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sudokuapp.log.SudokuAppLog

open class BaseActivity : AppCompatActivity() {

    /** TAG */
    private val TAG = "BaseActivity"

    /** screen kind */
    val SUDOKU_EASY = 0
    val SUDOKU_NORMAL = 1
    val SUDOKU_HARD = 2

    /** toast instance */
    var mToast: Toast? = null

    /** difficulty status */
    var mDifficultyStatus = SUDOKU_EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * show toast message.
     * @param messageId
     */
    fun showToastMessage(messageId: Int, length: Int) {
        SudokuAppLog.enter(TAG, "showToast:")

        // if showing toast now, delete toast
        if (mToast != null) {
            mToast?.cancel()
        }

        // show toast
        mToast = Toast.makeText(this, getString(messageId), length)
        mToast?.show()

        SudokuAppLog.exit(TAG, "showToast:")
    }

}