package com.example.nanit.ui.utils

import com.example.nanit.R

/**
 * Defined for every age his icon.
 */

// region Public Methods

fun getNumberIconRes(ageMonths: Int): Int {
    return when {
        // Month icons
        ageMonths == 0 -> R.drawable.num_zero_ic
        ageMonths == 1 -> R.drawable.num_one_ic
        ageMonths == 2 -> R.drawable.num_two_ic
        ageMonths == 3 -> R.drawable.num_three_ic
        ageMonths == 4 -> R.drawable.num_four_ic
        ageMonths == 5 -> R.drawable.num_five_ic
        ageMonths == 6 -> R.drawable.num_six_ic
        ageMonths == 7 -> R.drawable.num_seven_ic
        ageMonths == 8 -> R.drawable.num_eight_ic
        ageMonths == 9 -> R.drawable.num_nine_ic
        ageMonths == 10 -> R.drawable.num_ten_ic
        ageMonths == 11 -> R.drawable.num_eleven_ic

        // Year icons
        ageMonths in 12..23 -> R.drawable.num_one_ic
        ageMonths in 24..35 -> R.drawable.num_two_ic
        ageMonths in 36..47 -> R.drawable.num_three_ic
        ageMonths in 48..59 -> R.drawable.num_four_ic
        ageMonths in 60..71 -> R.drawable.num_five_ic
        ageMonths in 72..83 -> R.drawable.num_six_ic
        ageMonths in 84..95 -> R.drawable.num_seven_ic
        ageMonths in 96..107 -> R.drawable.num_eight_ic
        ageMonths in 108..119 -> R.drawable.num_nine_ic

        // Fallback
        else -> R.drawable.num_zero_ic
    }
}

// endregion
