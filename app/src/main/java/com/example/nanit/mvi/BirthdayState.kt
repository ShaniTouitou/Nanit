package com.example.nanit.mvi

import com.example.nanit.model.BirthdayData
import com.example.nanit.model.ThemeData

/**
 * Represents the ui state in the Birthday screen.
 */
data class BirthdayState(
    val isLoading: Boolean = false,
    val birthdayData: BirthdayData? = null,
    val themeData: ThemeData? = null,
    val errorMsg: String? = null
)