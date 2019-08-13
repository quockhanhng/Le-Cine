package com.rikkei.tranning.le_cine.ui.sortDialog

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SelectOptionStore @Inject constructor(context: Context) {

    companion object {
        private const val SELECTED_OPTION = "SELECTED_OPTION"
        private const val PREFS_NAME = "SORTING_OPTION"
    }

    private var prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getSelectedOption(): Int {
        return prefs.getInt(SELECTED_OPTION, 0)
    }

    fun setSelectedOption(sortType: SortType) {
        val editor = prefs.edit()
        editor.putInt(SELECTED_OPTION, sortType.value)
        editor.apply()
    }
}