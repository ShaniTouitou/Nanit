package com.example.nanit.screens

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.nanit.R
import com.example.nanit.model.getNumberIconRes
import com.example.nanit.mvi.BirthdayState
import com.example.nanit.utils.calculateAgeInMonths
import com.example.nanit.viewmodel.BirthdayViewModel

/**
 * This is the birthday screen.
 */

// region UI

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BirthdayScreen(state: BirthdayState,
                   onConnect: () -> Unit,
                   viewModel: BirthdayViewModel) {

    // region Members

    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher to pick image from gallery
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.updateBabyFace(it)
        }
    }

    // Launcher to take photo with camera
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            cameraImageUri?.let { uri ->
                viewModel.updateBabyFace(uri)
            }
        }
    }

    // endregion

    // region Public Methods

    // Create a temporary file URI for camera
    fun createImageUri(context: Context): Uri? {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "baby_face_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    // endregion

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

                // Baby face image
                Box(contentAlignment = Alignment.TopEnd) {
                    if (viewModel.babyFaceUri.value != null) {
                        Image(
                            painter = rememberAsyncImagePainter(viewModel.babyFaceUri.value),
                            contentDescription = "Baby face",
                            modifier = Modifier
                                .size(100.dp)
                                .clickable { showDialog = true }
                        )
                    }
                    else {
                        Image(
                            painter = painterResource(id = assets.babyFaceImg),
                            contentDescription = "Baby face",
                            modifier = Modifier
                                .size(100.dp)
                                .clickable { showDialog = true }
                        )
                    }

                    Image(
                        painter = painterResource(id = assets.camImg),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .offset(x = (-8).dp, y = 8.dp)
                            .clickable { showDialog = true }
                    )
                }

                Spacer(Modifier.height(24.dp))

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

    // Dialog for choosing option for baby face
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select Option") },
            text = { Text("Choose how to update the baby face image") },
            confirmButton = {
                TextButton(onClick = {
                    // Take picture option
                    showDialog = false
                    val uri = createImageUri(context)
                    if (uri != null) {
                        cameraImageUri = uri
                        cameraLauncher.launch(uri)
                    }
                }) {
                    Text("Take Picture")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    // Choose from gallery option
                    showDialog = false
                    galleryLauncher.launch("image/*")
                }) {
                    Text("Choose from Gallery")
                }
            }
        )
    }
}

// endregion





