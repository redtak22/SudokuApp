package com.example.sudokuapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.sudokuapp.log.SudokuAppLog

/**
 * BaseFragment.
 */
open class BaseFragment : Fragment() {

    /** TAG */
    private val TAG = "BaseFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        SudokuAppLog.exit(TAG, "onViewCreated")
    }
}