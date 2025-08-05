package com.example.nanit.ui.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.nanit.R
import com.example.nanit.model.ThemeData
import com.example.nanit.ui.theme.Elephant
import com.example.nanit.ui.theme.Fox
import com.example.nanit.ui.theme.Pelican

// region Constant Members

const val FOX_SVG = "fox_background.svg"

const val ELEPHANT_SVG = "elephant_background.svg"

const val PELICAN_SVG = "pelican_background.svg"

// endregion

// region Members

/**
 * Defined for every theme his ui objects.
 */
val themeMap = mapOf(
    "fox" to ThemeData(
        backgroundColor = Fox,
        backgroundImg = { SvgBackground(FOX_SVG, modifier = Modifier.fillMaxSize()) },
        themeFaceImg = R.drawable.fox_baby_circle_ic,
        babyFaceImg = R.drawable.fox_baby_part_circle_ic,
        camImg = R.drawable.fox_camera_ic
    ),
    "elephant" to ThemeData(
        backgroundColor = Elephant,
        backgroundImg = { SvgBackground(ELEPHANT_SVG, modifier = Modifier.fillMaxSize()) },
        themeFaceImg = R.drawable.elephant_baby_circle_ic,
        babyFaceImg = R.drawable.elephant_baby_part_circle_ic,
        camImg = R.drawable.elephant_camera_ic
    ),
    "pelican" to ThemeData(
        backgroundColor = Pelican,
        backgroundImg = {SvgBackground(PELICAN_SVG, modifier = Modifier.fillMaxSize()) },
        themeFaceImg = R.drawable.pelican_baby_circle_ic,
        babyFaceImg = R.drawable.pelican_baby_part_circle_ic,
        camImg = R.drawable.pelican_camera_ic
    )
)

// endregion
