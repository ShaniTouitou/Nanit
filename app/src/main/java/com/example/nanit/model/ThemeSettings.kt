package com.example.nanit.model

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.nanit.R
import com.example.nanit.ui.theme.Elephant
import com.example.nanit.ui.theme.Fox
import com.example.nanit.ui.theme.Pelican
import com.example.nanit.ui.theme.elephantBackground
import com.example.nanit.ui.theme.foxBackground
import com.example.nanit.ui.theme.pelicanBackground

/**
 * Defined for every theme his objects.
 */
val themeMap = mapOf(
    "fox" to ThemeData(
        backgroundColor = Fox,
        backgroundImg = { foxBackground(modifier = Modifier.fillMaxSize()) },
        babyFaceImg = R.drawable.fox_baby_circle_ic,
        camImg = R.drawable.fox_camera_ic
    ),
    "elephant" to ThemeData(
        backgroundColor = Elephant,
        backgroundImg = { elephantBackground(modifier = Modifier.fillMaxSize()) },
        babyFaceImg = R.drawable.elephant_baby_circle_ic,
        camImg = R.drawable.elephant_camera_ic
    ),
    "pelican" to ThemeData(
        backgroundColor = Pelican,
        backgroundImg = { pelicanBackground(modifier = Modifier.fillMaxSize()) },
        babyFaceImg = R.drawable.pelican_baby_circle_ic,
        camImg = R.drawable.pelican_camera_ic
    )
)

