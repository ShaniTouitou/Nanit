package com.example.nanit.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nanit.model.themeMap
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
    private val _state = MutableStateFlow(BirthdayState())
    val state: StateFlow<BirthdayState> = _state.asStateFlow()

    val babyFaceUri = mutableStateOf<Uri?>(null)

    // endregion

    // region Public Methods

    fun onEvent(event: BirthdayEvent) {
        when (event) {
            is BirthdayEvent.ConnectClicked -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, error = null) }
                    try {
                        val data = repository.connectAndGetBirthdayData(event.ip, event.port)
                        Log.d("BirthdayViewModel", "Received data: $data")
                        val theme = themeMap[data.theme.lowercase()]
                        _state.update {
                            it.copy(
                                isLoading = false,
                                birthdayData = data,
                                themeAssets = theme
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("BirthdayViewModel", "Error: ${e.message}")
                        _state.update {
                            it.copy(isLoading = false, error = e.message)
                        }
                    }
                }
            }
            else -> {}
        }
    }

    // Update the baby face image with a chosen image.
    fun updateBabyFace(uri: Uri) {
        babyFaceUri.value = uri
    }

    // endregion

}
