package com.example.sudokuapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sudokuapp.R
import com.example.sudokuapp.activity.MainActivity
import com.example.sudokuapp.log.SudokuAppLog
import kotlinx.android.synthetic.main.title_fragment.*

/**
 * MainFragment (for other sudoku screen).
 */
class MainFragment : BaseFragment() {

    /** TAG */
    private val TAG = "MainFragment"

    /** Activity */
    private lateinit var mActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        mActivity = activity as MainActivity

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
            R.layout.title_fragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onCreateView")
        super.onViewCreated(view, savedInstanceState)

        // easy button tapped
        easy_button.setOnClickListener {
            SudokuAppLog.debug(TAG, "easy button tapped.")
            // TODO: use Fragment
            // change to sudoku screen
            mActivity.changeActivity(mActivity.SUDOKU_EASY)
        }

        SudokuAppLog.exit(TAG, "onCreateView")
    }

}