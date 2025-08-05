package com.example.nanit.viewmodel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nanit.ui.utils.themeMap
import com.example.nanit.mvi.BirthdayEvent
import com.example.nanit.mvi.BirthdayState
import com.example.nanit.repository.BirthdayRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Represent the birthday view model to manage all the data.
 */
@HiltViewModel
class BirthdayViewModel @Inject constructor(private val repository: BirthdayRepo) : ViewModel() {

    // region Members

    // Track changes in the state of the birthday event.
    private val _state = MutableStateFlow(BirthdayState())
    val state: StateFlow<BirthdayState> = _state.asStateFlow()

    val babyFaceUri = mutableStateOf<Uri?>(null)

    // endregion

    // region Public Methods

    fun onEvent(event: BirthdayEvent) {
        when (event) {
            // Asking for network request to receive data from the server.
            is BirthdayEvent.connectClicked -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, errorMsg = null) }

                    try {
                        val data = repository.connectAndGetBirthdayData(event.ip, event.port)
                        // Getting the data after connect to the server.
                        onEvent(BirthdayEvent.OnDataReceived(data))
                    } catch (e: Exception) {
                        onEvent(BirthdayEvent.OnError(e.message ?: "Unknown error"))
                    }
                }
            }

            // Getting birthday data from the server.
            is BirthdayEvent.OnDataReceived -> {
                val theme = themeMap[event.data.theme.lowercase()]

                // Update the state with the received data.
                _state.update {
                    it.copy(
                        isLoading = false,
                        birthdayData = event.data,
                        themeData = theme
                    )
                }
            }

            // Error from the server/data.
            is BirthdayEvent.OnError -> {
                _state.update { it.copy(isLoading = false, errorMsg = event.message) }
            }
        }
    }

    /**
     * Update the baby face image with the chosen image.
     */
    fun updateBabyFace(uri: Uri) {
        babyFaceUri.value = uri
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

}
