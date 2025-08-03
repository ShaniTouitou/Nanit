package com.example.nanit.model

import com.example.nanit.R
import com.example.nanit.ui.theme.Elephant
import com.example.nanit.ui.theme.Fox
import com.example.nanit.ui.theme.Pelican

/**
 * Defined for every theme his objects.
 */
val themeMap = mapOf(
    "fox" to ThemeData(
        backgroundColor = Fox,
        backgroundImg = R.drawable.fox_background,
        babyFaceImg = R.drawable.fox_baby_circle_ic,
        camImg = R.drawable.fox_camera_ic
    ),
    "elephant" to ThemeData(
        backgroundColor = Elephant,
        backgroundImg = R.drawable.elephant_background,
        babyFaceImg = R.drawable.elephant_baby_circle_ic,
        camImg = R.drawable.elephant_camera_ic
    ),
    "pelican" to ThemeData(
        backgroundColor = Pelican,
        backgroundImg = R.drawable.pelican_background,
        babyFaceImg = R.drawable.pelican_baby_circle_ic,
        camImg = R.drawable.pelican_camera_ic
    )
)

