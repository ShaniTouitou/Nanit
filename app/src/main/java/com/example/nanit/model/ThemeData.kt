package com.example.nanit.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * This represent the data for every theme depend on each baby.
 */
data class ThemeData(
    val backgroundColor: Color,
    val backgroundImg: @Composable () -> Unit,
    val babyFaceImg: Int,
    val camImg: Int
)

