package com.example.sudokuapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sudokuapp.R
import com.example.sudokuapp.activity.SudokuActivity
import com.example.sudokuapp.log.SudokuAppLog
import kotlinx.android.synthetic.main.sudoku_fragment.*

/**
 * sudoku fragment.
 */
class SudokuFragment : BaseFragment(), View.OnClickListener {

    /** TAG */
    private val TAG = "SudokuFragment"

    /** number button array max */
    private val NUMBER_BUTTON_MAX = 10
    /** grid first */
    private val NUMBER_BUTTON_FIRST = 1
    /** grid last */
    private val NUMBER_BUTTON_LAST = 9

    /** Activity */
    private lateinit var mActivity: SudokuActivity
    /** Sudoku grid fragment */
    private lateinit var mSudokuGridFragment: SudokuGridFragment

    /** grid button array */
    private var mNumberButtonArray = arrayOfNulls<Button>(NUMBER_BUTTON_MAX)

    override fun onCreate(savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        mActivity = activity as SudokuActivity
        mActivity.mSudokuFragment = this

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
            R.layout.sudoku_fragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SudokuAppLog.enter(TAG, "onCreateView")
        super.onViewCreated(view, savedInstanceState)

        prepareButtons()

        SudokuAppLog.exit(TAG, "onCreateView")
    }

    override fun onStart() {
        SudokuAppLog.enter(TAG, "onStart")
        super.onStart()

        // get SudokuGridFragment
        mSudokuGridFragment = mActivity.mSudokuGridFragment

        SudokuAppLog.exit(TAG, "onStart")
    }

    override fun onClick(view: View?) {
        SudokuAppLog.enter(TAG, "onClick")

        if (view != null) {
            // erase button
            if (view.id == eraser_button.id) {
                mSudokuGridFragment.eraseFocusCellNumber()
                SudokuAppLog.exit(TAG, "onClick: eraser button tapped")
                return
            }

            // back button
            if (view.id == back_button.id) {
                mActivity.showToastMessage(R.string.sudoku_back_button_under_construction_toast_message)
                SudokuAppLog.exit(TAG, "onClick: back button tapped")
                return
            }

            // identify tapped button
            for (number in NUMBER_BUTTON_FIRST..NUMBER_BUTTON_LAST) {
                if (mNumberButtonArray[number] != null) {
                    if (view.id == mNumberButtonArray[number]?.id) {
                        // if can identify tapped button, update focus Cell
                        mSudokuGridFragment.updateCellNumber(number)
                        SudokuAppLog.debug(TAG, "onClick: $number tapped.")
                    }
                }
            }
        }

        SudokuAppLog.exit(TAG, "onClick")
    }

    /**
     * prepare button.
     */
    private fun prepareButtons() {
        SudokuAppLog.enter(TAG, "prepareNumberButton")

        // eraser button
        eraser_button.setOnClickListener(this)

        // back button
        back_button.setOnClickListener(this)

        // number buttons
        for (number in NUMBER_BUTTON_FIRST..NUMBER_BUTTON_LAST) {
            // put number button instance in the number button array
            val tag = "number-$number"
            mNumberButtonArray[number] =
                number_button_linear_layout.findViewWithTag<Button>(tag)
            // set listener
            if (mNumberButtonArray[number] != null) {
                SudokuAppLog.debug(TAG, "prepareButtons: set number button listener $tag")
                mNumberButtonArray[number]?.setOnClickListener(this)
            }
        }

        SudokuAppLog.exit(TAG, "prepareNumberButton")
    }
}