package com.example.nanit.repository

import com.example.nanit.model.BirthdayData
import javax.inject.Inject
import com.example.nanit.network.WebSocketDataSource

/**
 * Represent a Repository responsible for handling birthday-related data operations.
 * (A layer between the WebSocket data source and the rest of the app).
 */
class BirthdayRepo @Inject constructor(private val dataSource: WebSocketDataSource) {
    suspend fun connectAndGetBirthdayData(ip: String, port: String): BirthdayData {
        return try {
            dataSource.connectAndReceive(ip, port)
        } catch (e: Exception) {
            throw Exception("Failed to connect$e")
        }
    }
}
