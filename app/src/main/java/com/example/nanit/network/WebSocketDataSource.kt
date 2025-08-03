package com.example.nanit.network

import com.example.nanit.model.BirthdayData
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 *  Represent a data source responsible for managing the WebSocket connection and receiving birthday data.
 */
class WebSocketDataSource @Inject constructor(
    private val client: WebSocketClient
) {
    suspend fun connectAndReceive(ip: String, port: String): BirthdayData =
        suspendCancellableCoroutine { cont ->
            client.connect(
                ip = ip,
                port = port,
                onMessage = { data ->
                    if (cont.isActive) cont.resume(data)
                },
                onError = { error ->
                    if (cont.isActive) cont.resumeWithException(error)
                }
            )
        }
}
