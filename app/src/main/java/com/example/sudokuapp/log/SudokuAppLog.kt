package com.example.sudokuapp.log

import android.util.Log

/**
 * Sudoku App original log class.
 */
class SudokuAppLog {

    // static
    companion object {

        /** enter/exit log. */
        val ENTER = "<"
        val EXIT = ">"

        /**
         * output debug log.
         * @param tag tag
         * @param msg log message
         */
        fun debug(tag: String, msg: String) {
            Log.d(tag, msg)
        }

        /**
         * output enter log.
         * @param tag tag
         * @param msg log message
         */
        fun enter(tag: String, msg: String) {
            debug(tag, "$msg $ENTER")
        }

        /**
         * output exit log.
         * @param tag tag
         * @param msg log message
         */
        fun exit(tag: String, msg: String) {
            debug(tag, "$msg $EXIT")
        }

        /**
         * output warning log.
         * @param tag tag
         * @param msg log message
         */
        fun warning(tag: String, msg: String) {
            Log.w(tag, msg)
        }

        /**
         * output error log.
         * @param tag tag
         * @param msg log message
         */
        fun error(tag: String, msg: String) {
            Log.e(tag, msg)
        }

        /**
         * output information log.
         * @param tag tag
         * @param msg log message
         */
        fun information(tag: String, msg: String) {
            Log.i(tag, msg)
        }
    }
}