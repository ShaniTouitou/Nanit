package com.example.nanit.screens

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.nanit.R
import com.example.nanit.model.getNumberIconRes
import com.example.nanit.mvi.BirthdayState
import com.example.nanit.ui.theme.BentonSans
import com.example.nanit.ui.theme.TextColor
import com.example.nanit.utils.calculateAgeInMonths
import com.example.nanit.viewmodel.BirthdayViewModel

/**
 * This is the birthday screen.
 */

// region Constant Members

const val DISPLAY_TEXT_TODAY = "TODAY"

const val DISPLAY_TEXT_IS = "IS"

const val DISPLAY_TEXT_MONTH_OLD = "MONTH OLD!"

const val DISPLAY_TEXT_YEAR_OLD = "YEARS OLD!"

const val DIALOG_TEXT_SELECT_OPTION = "Select Option"

const val DIALOG_TEXT_CHOICE = "Choose how to update the baby face image"

const val DIALOG_TEXT_CHOOSE_PICTURE = "Take Picture"

const val DIALOG_TEXT_CHOOSE_GALLERY = "Choose from Gallery"

// endregion

// region UI

@SuppressLint("UnusedBoxWithConstraintsScope")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BirthdayScreen(
    state: BirthdayState,
    onConnect: () -> Unit,
    viewModel: BirthdayViewModel
) {
    // region Members

    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.updateBabyFace(it) }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) cameraImageUri?.let { viewModel.updateBabyFace(it) }
    }

    /**
     * Creates a temporary image file URI for saving a captured photo.
     */
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

    // region UI Content

    var swirlsWidth by remember { mutableStateOf(0) }

    val density = LocalDensity.current

    state.birthdayData?.let { data ->
        val assets = state.themeAssets ?: return
        val ageMonths = calculateAgeInMonths(data.dob)
        val displayText = if (ageMonths < 12) DISPLAY_TEXT_MONTH_OLD else DISPLAY_TEXT_YEAR_OLD

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(assets.backgroundColor)
        ) {
            assets.backgroundImg()

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                val (title, swirlsRow, ageLabel, babyBox, logo) = createRefs()

                Box(
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (swirlsWidth > 0) {
                        val textWidth = with(density) { swirlsWidth.toDp() }

                        Text(
                            text = "$DISPLAY_TEXT_TODAY ${data.name.uppercase()} $DISPLAY_TEXT_IS",
                            fontSize = 21.sp,
                            color = TextColor,
                            fontFamily = BentonSans,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = Int.MAX_VALUE,
                            letterSpacing = (-0.42).sp,
                            softWrap = true,
                            modifier = Modifier.width(textWidth)
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            swirlsWidth = coordinates.size.width
                        }
                        .constrainAs(swirlsRow) {
                            top.linkTo(title.bottom, margin = 13.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Image(painter = painterResource( R.drawable.left_swirls_ic), contentDescription = null)

                    Spacer(Modifier.width(22.dp))

                    Image(painter = painterResource(id = getNumberIconRes(ageMonths)), contentDescription = null)

                    Spacer(Modifier.width(22.dp))

                    Image(painter = painterResource(R.drawable.right_swirls_ic), contentDescription = null)
                }

                Text(
                    text = displayText,
                    fontSize = 18.sp,
                    color = TextColor,
                    fontFamily = BentonSans,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.36).sp,
                    modifier = Modifier.constrainAs(ageLabel) {
                        top.linkTo(swirlsRow.bottom, margin = 14.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

                val babyFaceSizeDp = 220.dp

                Box(
                    modifier = Modifier
                        .constrainAs(babyBox) {
                            top.linkTo(ageLabel.bottom, margin = 15.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(horizontal = 50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val customImageUri = viewModel.babyFaceUri.value
                    val isCustomImage = customImageUri != null
                    val customImagePainter = customImageUri?.let { rememberAsyncImagePainter(it) }

                    val innerFaceSizeDp = babyFaceSizeDp - 10.dp
                    val density = LocalDensity.current
                    val offsetDp = with(density) {
                        val radiusPx = babyFaceSizeDp.toPx() / 2f
                        val offsetPx = radiusPx * 0.707f
                        offsetPx.toDp()
                    }

                    Image(
                        painter = painterResource(id = assets.themeFaceImg),
                        contentDescription = "Themed baby face background",
                        modifier = Modifier
                            .size(babyFaceSizeDp)
                            .clip(CircleShape)
                    )

                    Image(
                        painter = customImagePainter ?: painterResource(id = assets.babyFaceImg),
                        contentDescription = if (isCustomImage) "User baby face" else "Default baby face",
                        modifier = Modifier
                            .size(innerFaceSizeDp)
                            .clip(CircleShape)
                            .clickable { showDialog = true },
                        contentScale = ContentScale.Crop
                    )

                    Image(
                        painter = painterResource(id = assets.camImg),
                        contentDescription = "Camera icon",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = offsetDp, y = -offsetDp)
                            .clickable { showDialog = true }
                    )
                }

                Image(
                    painter = painterResource(R.drawable.nanit_ic),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(logo) {
                        top.linkTo(babyBox.bottom, margin = 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onConnect) {
            Text(BTN_TEXT_CONNECT)
        }
    }

    // region Dialog

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(DIALOG_TEXT_SELECT_OPTION) },
            text = { Text(DIALOG_TEXT_CHOICE) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    val uri = createImageUri(context)
                    if (uri != null) {
                        cameraImageUri = uri
                        cameraLauncher.launch(uri)
                    }
                }) {
                    Text(DIALOG_TEXT_CHOOSE_PICTURE)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    galleryLauncher.launch("image/*")
                }) {
                    Text(DIALOG_TEXT_CHOOSE_GALLERY)
                }
            }
        )
    }
    // endregion
}

// endregion
