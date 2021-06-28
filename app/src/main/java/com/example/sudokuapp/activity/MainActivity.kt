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
     * @param difficultyKind difficulty kind
     */
    fun changeActivity(difficultyKind: Int) {
        SudokuAppLog.enter(TAG, "changeActivity")

        // start activity
        val intent = Intent(this, SudokuActivity::class.java)
        intent.putExtra(getString(R.string.KEY_DIFFICULTY_KIND), difficultyKind)
        startActivity(intent)

        SudokuAppLog.exit(TAG, "changeActivity")
    }

}