package com.example.nanit.mvi

import com.example.nanit.model.BirthdayData

/**
 * Represents the events that can occur in the Birthday screen MVI flow.
 */
sealed class BirthdayEvent {
    // The connection to the server by the button.
    data class ConnectClicked(val ip: String, val port: String) : BirthdayEvent()

    // Getting data from the server.
    data class OnDataReceived(val data: BirthdayData) : BirthdayEvent()

    // Getting error from the server.
    data class OnError(val message: String) : BirthdayEvent()
}