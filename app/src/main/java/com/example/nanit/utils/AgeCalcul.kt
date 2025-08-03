package com.example.nanit.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

// region Public Methods

/**
 * Calculate the age of the baby depend on his bod.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun calculateAgeInMonths(dobMillis: Long): Int {
    val birthDate = Instant.ofEpochMilli(dobMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val today = LocalDate.now()

    return ChronoUnit.MONTHS.between(birthDate, today).toInt()
}

// endregion
