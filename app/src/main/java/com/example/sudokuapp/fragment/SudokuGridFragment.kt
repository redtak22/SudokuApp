package com.example.sudokuapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sudokuapp.R
import com.example.sudokuapp.log.SudokuAppLog

/**
 * Sudoku fragment.
 */
class SudokuGridFragment : BaseFragment() {

    /** TAG */
    private val TAG = "SudokuFragment"

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
        SudokuAppLog.exit(TAG, "onViewCreated")
    }

}