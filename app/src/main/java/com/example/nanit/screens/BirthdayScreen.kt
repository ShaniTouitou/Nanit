package com.example.nanit.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nanit.R
import com.example.nanit.model.getNumberIconRes
import com.example.nanit.mvi.BirthdayState
import com.example.nanit.utils.calculateAgeInMonths

/**
 * This is the birthday screen.
 */

// region UI

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BirthdayScreen(state: BirthdayState, onConnect: () -> Unit) {
    if (state.isLoading) {
        CircularProgressIndicator()
        return
    }

    // region Members

    state.birthdayData?.let { data ->
        val assets = state.themeAssets ?: return

        val ageMonths = calculateAgeInMonths(data.dob)

        val isBabyLessThanOneYear = ageMonths < 12

        val displayText = if (isBabyLessThanOneYear) "MONTH OLD!" else "YEARS OLD!"

        // endregion

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(assets.backgroundColor)
        ) {
            // Background image
            Image(
                painter = painterResource(id = assets.backgroundImg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "TODAY ${data.name.uppercase()} IS",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))

                // Swirls + Number centered
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.left_swirls_ic),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = getNumberIconRes(ageMonths)),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Image(
                        painter = painterResource(R.drawable.right_swirls_ic),
                        contentDescription = null
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(displayText)

                Spacer(Modifier.height(24.dp))

                // Baby face with camera icon
                Box(contentAlignment = Alignment.TopEnd) {
                    Image(
                        painter = painterResource(id = assets.babyFaceImg),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Image(
                        painter = painterResource(id = assets.camImg),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .offset(x = (-8).dp, y = 8.dp)
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Nanit icon
                Image(
                    painter = painterResource(R.drawable.nanit_ic),
                    contentDescription = null
                )
            }
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onConnect) {
            Text("Connect")
        }
    }
}

// endregion





